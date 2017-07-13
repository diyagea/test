package com.mysql.common.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

public class BaseJDBCDao {

	/* 主数据库 */
	protected static final int hisp = 1;
	/* 表单数据库 */
	protected static final int hisp_form = 2;
	/* 供应链系统数据库 */
	protected static final int hecp = 3;

	// 新版本传入类型，返回数据库连接池的对象名称，方便灵活调用
	private static final String witchPoolName(int typeID) {
		String poolname = "mysql_hisp";
		switch (typeID) {
		case 1:
			poolname = "mysql_hisp";
			break;
		case 2:
			poolname = "mysql_hisp_form_usercreat_version";
			break;
		case 3:
			poolname = "mysql_hecp";
			break;
		default:
			poolname = "mysql_hisp";
			break;
		}
		return poolname;
	}

	/**
	 * 进行增删改的方法，SQL语句没有参数2010-06-08
	 * 
	 * @throws Exception
	 */
	public int executeWithOutResult(String sql, int poolNameType) {
		int result;// 返回结果：0正常、1异常
		PreparedStatement pstmt = null;
		Connection conn = DBConnectionManager.getInstance().getConnection(witchPoolName(poolNameType));
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			result = 0; // 正常执行
		} catch (SQLException e) {
			result = 1; // 操作异常
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null, poolNameType);
		}
		return result;
	}

	/**
	 * 进行增删改的方法，SQL语句有参数2010-06-08
	 * 
	 * @throws Exception
	 */
	public int executeWithOutResult(String sql, List<Object> values, int poolNameType) {
		int result;// 返回结果：0正常、1异常
		PreparedStatement pstmt = null;
		Connection conn = DBConnectionManager.getInstance().getConnection(witchPoolName(poolNameType));
		try {
			pstmt = conn.prepareStatement(sql);
			setValues(pstmt, values);
			pstmt.executeUpdate();
			result = 0;
		} catch (SQLException e) {
			result = 1;
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null, poolNameType);
		}
		return result;
	}

	/**
	 * 进行增删改的方法，SQL语句有参数2010-06-08 同时返回自增长ID
	 */
	public int executeWithOutResultReturnID(String sql, List<Object> values, int poolNameType) {
		int result = 0;// 返回结果：0异常 其他代表新增的主键ID
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		Connection conn = DBConnectionManager.getInstance().getConnection(witchPoolName(poolNameType));
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			setValues(pstmt, values);
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			while (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			result = 0;
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, null, poolNameType);
		}
		return result;
	}

	/**
	 * 进行查询的方法，其中的sql没有参数
	 * 
	 * @throws Exception
	 */
	public void executeWithResult(String sql, int poolNameType) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = DBConnectionManager.getInstance().getConnection(witchPoolName(poolNameType));
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			returnResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs, poolNameType);
		}
	}

	/**
	 * 进行查询的方法，其中的sql有参数
	 * 
	 * @throws Exception
	 */
	public void executeWithResult(String sql, List<Object> values, int poolNameType) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = DBConnectionManager.getInstance().getConnection(witchPoolName(poolNameType));
		try {
			pstmt = conn.prepareStatement(sql);
			setValues(pstmt, values);
			rs = pstmt.executeQuery();

			returnResult(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, pstmt, rs, poolNameType);
		}
	}

	/**
	 * 进行回调的方法
	 */
	public void returnResult(ResultSet rs) {
	}

	/**
	 * 设定参数
	 */
	public void setValues(PreparedStatement pstmt, List<Object> values) throws SQLException {
		for (int i = 0; i < values.size(); i++) {
			Object v = values.get(i);
			pstmt.setObject(i + 1, v);
		}
	}

	/**
	 * 关闭2010-06-08
	 */
	public void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs, int poolNameType) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				DBConnectionManager.getInstance().freeConnection(witchPoolName(poolNameType), conn);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
