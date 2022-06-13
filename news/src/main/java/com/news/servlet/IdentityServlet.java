package com.news.servlet;

import com.news.dao.BaseDao;
import com.news.impl.UserDaoImpl;
import net.sf.json.JSONObject;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Author 谭志扬
 * @Date 2022/5/21 14 52
 */
@WebServlet( urlPatterns = "/identityServlet")
public class IdentityServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=utf-8");
        //获取用户名和密码
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //判断非空
        if (req.getHeader("referer") == null || username == null || password == null) {
            resp.sendRedirect("login.html");
            return;
        }
        //查询数据库
        Connection conn = BaseDao.getConn();
        JSONObject json = null;
        try {
            PreparedStatement ps = conn.prepareStatement("select * from t_user where uname=? and upwd=?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                json = new JSONObject();
                json.put("id", rs.getInt("uid"));
                json.put("identity", rs.getInt("uidentity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //输出查询结果
        PrintWriter out = resp.getWriter();
        //判断编号是否大于0
        out.print(json);
        out.flush();
        out.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doPost(req, resp);
    }
}
