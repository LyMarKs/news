package com.news.impl;

import com.news.dao.BaseDao;
import com.news.dao.UserDao;
import com.news.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDao的具体实现类
 * @Author 谭志扬
 * @Date 2022/5/21 13 33
 */
public class UserDaoImpl implements UserDao {

    /**
     * 实现添加用户
     */
    @Override
    public void add(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into t_user(uname,upwd,uidentity) values(?,?,?)";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUname());
            ps.setString(2, user.getUpwd());
            ps.setInt(3, user.getUidentity());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("添加数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
    }

    /**
     * 实现更新用户
     */
    @Override
    public void update(User user) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update t_user set uname=?,upwd=?,uidentity=? where uid=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUname());
            ps.setString(2, user.getUpwd());
            ps.setInt(3, user.getUidentity());
            ps.setInt(4, user.getUid());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("更新数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
    }

    /**
     * 实现删除用户
     */
    @Override
    public void delete(int uid) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "delete from t_user where uid=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("删除数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
    }

    /**
     * 根据id查询用户
     */
    @Override
    public User findById(int uid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        String sql = "select uname,upwd,uidentity from t_user where uid=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, uid);
            rs = ps.executeQuery();
            if(rs.next()) {
                user = new User();
                user.setUname(rs.getString("uname"));
                user.setUpwd(rs.getString("upwd"));
                user.setUidentity(rs.getInt("uidentity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("根据id查询数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return user;
    }
    /**
     * 根据id查询用户
     */
    @Override
    public int findByNameAndPwd(String username, String password) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int uid = 0;
        String sql = "select uid from t_user where uname=? and upwd=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                uid = rs.getInt("uid");
            }
            /*if(rs.next()) {
                User user = new User();
                user.setUname(rs.getString("uname"));
                user.setUpwd(rs.getString("upwd"));
                user.setUidentity(rs.getInt("uidentity"));
            }*/
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("根据用户名和密码查询数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return uid;
    }

    /**
     * 查询所有用户
     */
    @Override
    public List<User> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user;
        List<User> users = new ArrayList<>();
        String sql = "select uid,uname,upwd,uidentity,identity_name from t_user u join t_identity i on i.identity_id=u.uidentity";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                user = new User();
                user.setUid(rs.getInt("uid"));
                user.setUname(rs.getString("uname"));
                user.setUpwd(rs.getString("upwd"));
                user.setUidentity(rs.getInt("uidentity"));
                user.setIdentityName(rs.getString("identity_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("查询所有数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return users;
    }
}
