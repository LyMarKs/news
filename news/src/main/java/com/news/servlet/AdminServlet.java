package com.news.servlet;

import com.news.entity.NewsType;
import com.news.entity.User;
import com.news.impl.NewsTypeDaoImpl;
import com.news.impl.UserDaoImpl;
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
@WebServlet(urlPatterns = "/adminServlet")
public class AdminServlet extends HttpServlet {
    /*public static final int SELECT = 1;
    public static final int INSERT = 2;
    public static final int UPDATE = 3;
    public static final int DELETE = 4;*/


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        resp.setContentType("text/html;charset=utf-8");
        //获取请求页面地址
        String referer = req.getHeader("referer");
        //获取操作类型
        int type = Integer.parseInt(Optional.ofNullable(req.getParameter("type")).orElse(String.valueOf(1)));
        UserDaoImpl userDao = new UserDaoImpl();
        List<User> typeList = null;
        PrintWriter out = resp.getWriter();
        User user = new User();
        //判断操作类型
        switch (type) {
            case 1:
                //获取值
                String name = req.getParameter("name");
                String pwd = req.getParameter("pwd");
                //判断值
                if (name != null && pwd != null) {
                    //查找
                    int uid = userDao.findByNameAndPwd(name, pwd);
                    //输出结果
                    out.println(uid > 0);
                } else {
                    //调用执行查询全部的方法
                    typeList = userDao.findAll();
                    //将集合转换为json对象
                    HashMap data = NewsServlet.data(typeList);
                    JSONObject json = JSONObject.fromObject(data);
                    //输出json内容
                    out.println(json);
                }
                out.flush();
                out.close();
                break;
            case 2:
                //创建对象，设置属性
                user.setUname(req.getParameter("name"));
                user.setUpwd(req.getParameter("pwd"));
                user.setUidentity(Integer.parseInt(req.getParameter("identity").trim()));
                //执行数据库添加操作
                userDao.add(user);
                break;
            case 3:
                //创建对象，设置属性
                user.setUid(Integer.parseInt(req.getParameter("id")));
                user.setUname(req.getParameter("name"));
                user.setUpwd(req.getParameter("pwd"));
                user.setUidentity(Integer.parseInt(req.getParameter("identity").trim()));
                //执行数据库更新操作
                userDao.update(user);
                break;
            case 4:
                //进行删除操作
                userDao.delete(Integer.parseInt(req.getParameter("id")));
                break;
            default:
                break;
        }
    }
}
