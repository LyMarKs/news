package com.news.servlet;

import com.news.dao.BaseDao;
import com.news.entity.News;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

/**
 * 查询指定发布状态的新闻
 * @author 谭志扬
 * @date 2022/5/26 15:06
 */
@WebServlet(urlPatterns = "/releaseServlet")
public class NewsReleaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        resp.setContentType("text/html;charset=utf-8");
        //获取操作类型
        int type = Integer.parseInt(Optional.ofNullable(req.getParameter("type")).orElse(String.valueOf(-1)));
        //获取用户编号
        int uid = Integer.parseInt(Optional.ofNullable(req.getParameter("uid")).orElse(String.valueOf(-1)));
        //获取操作数据库的对象
        Connection conn = BaseDao.getConn();
        PreparedStatement ps;
        ResultSet rs;
        News news;

        JSONObject json = new JSONObject();
        json.put("code", 0);
        try {
            ArrayList<News> list = new ArrayList<>();
            //获取查询结果
            String sql = "select * from t_news n join t_type t on t.tid=n.ntype_id " +
                        "where release_status=" + type + " and n.delete_status=0 ";
            //判断是否传递了uid
            if(uid != -1) {
                sql+=" and n.uid=" + uid;
            }
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            //遍历结果集
            while (rs.next()) {
                news = new News();
                news.setId(rs.getInt("nid"));
                news.setTitle(rs.getString("ntitle"));
                news.setAuthor(("".equals(rs.getString("author").trim())?"匿名":rs.getString("author").trim()));
                news.setTypeName(rs.getString("tname"));
                if (type == 0) {
                    news.setReason(rs.getString("fail_reason"));
                }
                list.add(news);
            }
            json.put("data", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(json);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
        out.close();
    }
}
