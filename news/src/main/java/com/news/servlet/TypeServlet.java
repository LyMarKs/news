package com.news.servlet;

import com.news.entity.News;
import com.news.entity.NewsType;
import com.news.impl.NewsDaoImpl;
import com.news.impl.NewsTypeDaoImpl;
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
@WebServlet(urlPatterns = "/typeServlet")
public class TypeServlet extends HttpServlet {
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
        NewsTypeDaoImpl newsTypeDao = new NewsTypeDaoImpl();
        List<NewsType> typeList = null;
        PrintWriter out = resp.getWriter();
        //判断操作类型
        switch (type) {
            case 1:
                //获取查询内容
                String select = req.getParameter("select");
                //判断内容
                if (select == null) {
                    //调用执行查询全部的方法
                    typeList = newsTypeDao.findAll();
                    //将集合转换为json对象
                    HashMap data = NewsServlet.data(typeList);
                    JSONObject json = JSONObject.fromObject(data);
//                System.out.println(json);
                    //输出json内容
                    out.println(json);
                } else {
                    //执行数据库添加操作
                    int selectStatus = newsTypeDao.findByName(select.trim());
                    //输出结果
                    out.println(selectStatus);
                }
                out.flush();
                out.close();
                break;
            case 2:
                newsTypeDao.add(req.getParameter("typeName"));
                break;
            case 3:
                //执行数据库更新操作
                newsTypeDao.update(req.getParameter("typeName"));
                break;
            case 4:
                //进行删除操作
                newsTypeDao.delete(Integer.parseInt(req.getParameter("id")));
                break;
            default:
                break;
        }
    }
}
