package com.news.servlet;

import com.news.entity.News;
import com.news.impl.NewsDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


/**
 * @author 谭志扬
 * @date 2022/5/23 7:47
 */
@WebServlet(urlPatterns = "/newsServlet")
public class NewsServlet extends HttpServlet {
    /*public static final int SELECT = 1;
    public static final int INSERT = 2;
    public static final int UPDATE = 3;
    public static final int DELETE = 4;*/


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        resp.setContentType("text/html;charset=utf-8");
        //获取操作类型
        int type = Integer.parseInt(Optional.ofNullable(req.getParameter("type")).orElse(String.valueOf(1)));
        NewsDaoImpl newsDao = new NewsDaoImpl();
        News news = new News();
        PrintWriter out = resp.getWriter();
        int rows = -1;
        //判断操作类型
        switch (type) {
            case 1:
                //获取查询内容
                String select = req.getParameter("select");
                List<News> newsList = null;
                //判断查询内容是否为空
                if (select == null) {
                    //调用执行查询全部的方法
                    newsList = newsDao.findAll();
                } else {
                    //调用执行查询的方法
                    newsList = newsDao.find(select);
                }
                HashMap data = data(newsList);
                //将集合转换为json对象
                JSONObject json = JSONObject.fromObject(data);
//                System.out.println(json);
                //输出json内容
                out.println(json);
                break;
            case 2:
                //获取相关数据
                news.setTitle(req.getParameter("title"));
                news.setTypeId(Integer.parseInt(req.getParameter("typeId")));
                news.setAuthor(req.getParameter("author"));
                news.setContent(req.getParameter("content"));
                news.setUid(Integer.parseInt(req.getParameter("uid")));
                //执行数据库添加操作
                rows = newsDao.add(news);
                //输出结果
                out.println(rows > 0);
                break;
            case 3:
                //执行数据库更新操作
//                OperatorDb.updateRow(args, req.getParameter("bookId"));
                break;
            case 4:
                //进行删除操作
                rows = newsDao.delete(Integer.parseInt(req.getParameter("id")));
                //输出结果
                out.println(rows > 0);
                break;
            default:
                break;
        }
        out.flush();
        out.close();
    }
    public static HashMap data(List data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("msg", "");
        map.put("count", data.size());
        map.put("data", data);
        return map;
    }
}
