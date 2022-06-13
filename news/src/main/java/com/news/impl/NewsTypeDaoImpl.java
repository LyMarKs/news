package com.news.impl;

import com.news.dao.BaseDao;
import com.news.dao.NewsDao;
import com.news.dao.NewsTypeDao;
import com.news.entity.News;
import com.news.entity.NewsType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 谭志扬
 * @date 2022/5/22 20:05
 */
public class NewsTypeDaoImpl implements NewsTypeDao {
    @Override
    public void add(String typeName) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "insert into t_type(tname) values(?)";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, typeName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("添加数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
    }

    @Override
    public void update(String typeName) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update t_type set delete_status=0 where tname=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, typeName);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("更新数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
    }

    @Override
    public void delete(int tid) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "update t_type set delete_status=1 where tid=?";
//        String sql = "delete from t_type where tid=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, tid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("删除数据失败!");
        } finally {
            BaseDao.closeAll(null, ps, conn);
        }
    }

    @Override
    public List<NewsType> findAll() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        NewsType type = null;
        List<NewsType> typeList = null;
        String sql = "select tid,tname from t_type where delete_status=0";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            typeList = new ArrayList<>();
            while (rs.next()) {
                type = new NewsType();
                type.setTid(rs.getInt("tid"));
                type.setTname(rs.getString("tname"));
                typeList.add(type);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("查询所有数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return typeList;
    }

    @Override
    public int findByName(String typeName) {
        //0:不存在, 1:已存在, 2:存在但已逻辑删除
        int status = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "select * from t_type where tname=?";
        try {
            conn = BaseDao.getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, typeName);
            rs = ps.executeQuery();
            //判断结果集是否存在数据
            if (rs.next()) {
                //判断是否未逻辑删除
                if(rs.getInt("delete_status") == 0) {
                    status = 1;
                } else {
                    status = 2;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("查询所有数据失败!");
        } finally {
            BaseDao.closeAll(rs, ps, conn);
        }
        return status;
    }
}
