package com.news.dao;

import com.news.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author 谭志扬
 * @Date 2022/5/21 13 27
 */
public interface UserDao {
    /**
     * 添加方法
     * @param user 用户
     */
    public void add(User user) throws SQLException;

    /**
     * 更新方法
     * @param user 用户
     */
    public void update(User user) throws SQLException;

    /**
     * 删除方法
     * @param uid 用户编号
     */
    public void delete(int uid) throws SQLException;

    /**
     * 查找方法
     * @param uid 用户编号
     */
    public User findById(int uid) throws SQLException;

    /**
     * 查找方法2
     * @param username 用户名
     * @param password 密码
     */
    public int findByNameAndPwd(String username, String password) throws SQLException;

    /**
     * 查找所有
     */
    public List<User> findAll() throws SQLException;
}
