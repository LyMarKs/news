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

/**
 * @author 谭志扬
 * @date 2022/5/25 20:41
 */
@WebServlet(urlPatterns = "/addCount")
public class CountServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取编号和访问量
        int id = Integer.parseInt(req.getParameter("id"));
        //增加访问量
        Connection conn = BaseDao.getConn();
        try {
            PreparedStatement prep = conn.prepareStatement("update t_news set nclicks=nclicks+1 where nid=?");
            prep.setInt(1, id);
//            System.out.println(prep);
            prep.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
