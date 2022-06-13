package com.news.impl;

import com.news.dao.BaseDao;
import com.news.dao.NewsDao;
import com.news.entity.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谭志扬
 * @date 2022/5/22 20:05
 */
public class NewsDaoImpl extends BaseDao implements NewsDao {
    @Override
    public int add(News news) {
        int rows = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into t_news(ntitle,ntype_id,author,content,uid) values(?,?,?,?,?)";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, news.getTitle());
            ps.setInt(2, news.getTypeId());
            ps.setString(3, news.getAuthor());
            ps.setString(4, news.getContent());
            ps.setInt(5, news.getUid());
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("添加数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
        return rows;
    }

    @Override
    public int update(News news) {
        int rows = -1;
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update t_news set ntitle=?,ntype_id=?,nclicks=?,alert_time=NOW() where nid=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, news.getTitle());
            ps.setInt(2, news.getTypeId());
            ps.setInt(3, news.getClicks());
            ps.setInt(4, news.getId());
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("更新数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
        return rows;
    }

    @Override
    public int delete(int nid) {
        int rows = -1;
        Connection conn = null;
        PreparedStatement ps = null;
//        String sql = "delete from t_news where nid=?";
        String sql = "update t_news set delete_status=1 where nid=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, nid);
            rows = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("删除数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
        return rows;
    }

    @Override
    public News findById(int nid) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        News news = null;
        String sql = "select nid,ntitle,ntype_id,nclicks,alert_time,content from t_news where nid=? and delete_status=0";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, nid);
            rs = ps.executeQuery();
            if(rs.next()) {
                news = new News();
                news.setId(rs.getInt("nid"));
                news.setTitle(rs.getString("ntitle"));
                news.setTypeId(rs.getInt("ntype_id"));
                news.setClicks(rs.getInt("nclicks"));
                news.setAlertTime(rs.getString("alert_time"));
                news.setContent(rs.getString("content"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("根据id查询数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return news;
    }

    @Override
    public List<News> find(String content) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        News news = null;
        List<News> newsList = new ArrayList<>();
        //设置sql
        String sql = "select * from t_news n inner join t_type t on t.tid=n.ntype_id " +
                    "where (ntitle like ? or t.tname like ?) and n.delete_status=0 and release_status=2";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            for (int i = 1; i <= ps.getParameterMetaData().getParameterCount(); i++) {
                ps.setString(i, '%'+content+'%');
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                news = new News();
                news.setId(rs.getInt("nid"));
                news.setTitle(rs.getString("ntitle"));
                news.setTypeId(rs.getInt("ntype_id"));
                news.setTypeName(rs.getString("t.tname"));
                news.setClicks(rs.getInt("nclicks"));
                news.setAlertTime(rs.getString("alert_time"));
                news.setContent(rs.getString("content"));
                news.setAuthor(("".equals(rs.getString("author").trim())?"匿名":rs.getString("author").trim()));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("查询所有数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return newsList;
    }

    @Override
    public List<News> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        News news = null;
        List<News> newsList = null;
        String sql = "select * from t_news n join t_type t on t.tid=n.ntype_id where n.delete_status=0 and release_status=2";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            newsList = new ArrayList<>();
            while (rs.next()) {
                news = new News();
                news.setId(rs.getInt("nid"));
                news.setTitle(rs.getString("ntitle"));
                news.setTypeId(rs.getInt("ntype_id"));
                news.setTypeName(rs.getString("t.tname"));
                news.setClicks(rs.getInt("nclicks"));
                news.setAlertTime(rs.getString("alert_time"));
                news.setContent(rs.getString("content"));
                news.setReleaseTime(rs.getString("release_time"));
                news.setAuthor(("".equals(rs.getString("author").trim())?"匿名":rs.getString("author").trim()));
                newsList.add(news);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("查询所有数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return newsList;
    }
}
