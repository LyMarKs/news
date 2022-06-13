package com.news.dao;

import com.news.entity.News;
import com.news.entity.NewsType;

import java.sql.SQLException;
import java.util.List;

/**
 * @Author 谭志扬
 * @Date 2022/5/21 13 27
 */
public interface NewsTypeDao {
    /**
     * 添加方法
     * @param type 新闻类别
     */
    public void add(String typeName) throws SQLException;

    /**
     * 更新方法
     * @param type 新闻类别
     */
    public void update(String typeName) throws SQLException;

    /**
     * 删除方法
     * @param tid 新闻类别编号
     */
    public void delete(int tid) throws SQLException;

    /**
     * 查找所有新闻类别
     */
    public List<NewsType> findAll() throws SQLException;

    /**
     * 查找指定类别名称是否存在
     */
    public int findByName(String typeName) throws SQLException;
}
