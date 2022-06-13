package com.news.dao;

import com.news.entity.News;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author 谭志扬
 * @Date 2022/5/21 13 27
 */
public interface ContentDao {
    /**
     * 添加方法
     * @param news 新闻
     */
    public void add(News news) throws SQLException;

    /**
     * 更新方法
     * @param news 新闻
     */
    public void update(News news) throws SQLException;

    /**
     * 删除方法
     * @param nid 新闻编号
     */
    public void delete(int nid) throws SQLException;

    /**
     * 查找方法
     * @param nid 新闻编号
     */
    public News findById(int nid) throws SQLException;

    /**
     * 查找方法2
     * @param content 查询内容
     */
    public List<News> find(String content) throws SQLException;

    /**
     * 查找所有新闻
     */
    public List<News> findAll() throws SQLException;
}
