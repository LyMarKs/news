package com.news.servlet;

import com.news.dao.BaseDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 修改发布状态
 * @author 谭志扬
 * @date 2022/5/26 18:10
 */
@WebServlet(urlPatterns = "/releaseOperate")
public class ReleaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        resp.setContentType("text/html;charset=utf-8");
        //获取操作类型
        int type = Integer.parseInt(Optional.ofNullable(req.getParameter("type")).orElse(String.valueOf(-1)));
        //获取新闻编号
        int nid = Integer.parseInt(Optional.ofNullable(req.getParameter("nid")).orElse(String.valueOf(-1)));
        //执行数据库操作
        Connection conn = BaseDao.getConn();
        try {
            //修改发布状态
            PreparedStatement ps = conn.prepareStatement("update t_news set release_status=? where nid=?");
            ps.setInt(1, type);
            ps.setInt(2, nid);
            ps.executeUpdate();
//            System.out.println(ps);
            //判断操作类型是否为驳回
            if (type == 0) {
                //获取驳回理由
                String reason = req.getParameter("reason").trim();
                //将驳回理由存至数据库
                ps = conn.prepareStatement("update t_news set fail_reason=? where nid=?");
                ps.setString(1, reason);
                ps.setInt(2, nid);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
