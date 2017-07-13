package com.mysql.common.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class AutoCreateTool {

	private static String tablename = "cbm_contract";

	private static String[] colnames; // 列名数组

	private static String[] colTypes; // 列名类型数组

	private static int[] colSizes; // 列名大小数组

	private static String folderPath = "D:/temp/";

	public static void main(String[] args) throws IOException {

		// 判断文件夹，创建文件夹
		File foder = new File(folderPath);
		// 如果文件夹不存在，则创建文件夹
		if (foder.exists() == false) {
			foder.mkdirs();// 多级目录
		}

		// 和数据库建立连接，解析table
		analyseTable();

		/** 生成实体文件.java **/
		createEntityJava(folderPath);

		/** 生成BIZ文件Biz.java **/
		createBizJava(folderPath);

		System.out.println("文件创建完成，请到【" + folderPath + "】下查看！");
	}

	public static void analyseTable() {
		String poolname = Env.getInstance().getProperty("poolname1");
		Connection conn = DBConnectionManager.getInstance().getConnection(poolname);// 得到数据库连接
		String strsql = "select * from " + tablename;
		try {
			PreparedStatement pstmt = conn.prepareStatement(strsql);
			ResultSetMetaData rsmd = pstmt.getMetaData();

			int size = rsmd.getColumnCount(); // 共有多少列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < rsmd.getColumnCount(); i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionManager.getInstance().freeConnection(poolname, conn);
		}
	}

	/**
	 * 解析处理(生成实体类主体代码)
	 * 
	 * @throws IOException
	 * 
	 */
	private static void createEntityJava(String path) throws IOException {
		StringBuffer sb = new StringBuffer();

		sb.append("package com.entity;\r\n\r\n\r\n");
		sb.append("public class " + tablename + " {\r\n");
		sb.append("\r\n");
		// 解析输出属性
		for (int i = 0; i < colnames.length; i++) {
			String colType = sqlType2JavaType(colTypes[i]);
			switch (colType) {
			case "String":
				sb.append("\tprivate " + colType + " " + colnames[i] + " = \"\";\r\n");
				break;

			default:
				sb.append("\tprivate " + colType + " " + colnames[i] + ";\r\n");
				break;
			}
		}
		sb.append("\r\n\r\n");
		// 生成所有的方法
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlType2JavaType(colTypes[i]) + " " + colnames[i] + "){\r\n");
			sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");

			sb.append("\tpublic " + sqlType2JavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
		}
		sb.append("}\r\n");

		FileWriter fw = new FileWriter(path + tablename + ".java");
		PrintWriter pw = new PrintWriter(fw);
		pw.println(sb.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * 生成sql操作：add、update、select方法 注意此处不会映射主外键关系
	 * 
	 * @param sb
	 * @throws IOException
	 */
	private static void createBizJava(String path) throws IOException {

		StringBuffer sb = new StringBuffer();
		sb.append("package com.biz;\r\n\r\n");
		sb.append("import java.sql.ResultSet;\r\n");
		sb.append("import java.sql.SQLException;\r\n");
		sb.append("import java.util.ArrayList;\r\n");
		sb.append("import java.util.List;\r\n");
		sb.append("import com.entity." + tablename + ";\r\n");
		sb.append("import com.mysql.common.dao.BaseJDBCDao;\r\n\r\n\r\n");

		sb.append("public class " + tablename + "Biz extends BaseJDBCDao {\r\n");

		sb.append("public final String TableName = \"" + tablename + "\";\r\n");

		// addMethod
		addMethod(sb);

		// updateMethod
		updateMethod(sb);

		// rsToObject
		rsToObjectMethod(sb);

		// select
		getMethod(sb);

		sb.append("}\r\n");

		FileWriter fw = new FileWriter(path + tablename + "Biz.java");
		PrintWriter pw = new PrintWriter(fw);
		pw.println(sb.toString());
		pw.flush();
		pw.close();
	}

	/**
	 * add
	 * 
	 * @param sb
	 * @return void
	 */
	private static void addMethod(StringBuffer sb) {

		// add
		sb.append("\r\n");
		sb.append("\t//add\r\n");
		sb.append("\tpublic int add(" + tablename + " param) {\r\n");
		sb.append("\tString sql=\"insert into \" + TableName + \" (");
		for (int i = 1; i < colnames.length; i++) {
			if (i < colnames.length - 1) {
				sb.append(colnames[i] + ",");
			} else {
				sb.append(colnames[i] + ") values (");
			}
		}
		for (int i = 1; i < colnames.length; i++) {
			if (i < colnames.length - 1) {
				sb.append("?,");
			} else {
				sb.append("?)\";\r\n");
			}
		}
		sb.append("\tList<Object> values = new ArrayList<Object>();\r\n");
		for (int i = 1; i < colnames.length; i++) {
			sb.append("\tvalues.add(param.get" + initcap(colnames[i]) + "());\r\n");
		}
		sb.append("\treturn super.executeWithOutResultReturnID(sql, values,1);\r\n");
		sb.append("}\r\n");
	}

	/**
	 * update
	 * 
	 * @param sb
	 * @return void
	 */
	private static void updateMethod(StringBuffer sb) {

		sb.append("\t//update 0正常、1异常\r\n");
		sb.append("\tpublic int update(" + tablename + " param) {\r\n");
		sb.append("\tString sql=\"update \" + TableName + \" set ");
		for (int i = 1; i < colnames.length; i++) {

			if (i == colnames.length - 1) {
				sb.append(colnames[i] + "=?");
			} else {
				sb.append(colnames[i] + "=?,");
			}
		}
		sb.append(" where " + colnames[0] + "=?\";\r\n");

		sb.append("\tList<Object> values = new ArrayList<Object>();\r\n");
		for (int i = 1; i < colnames.length; i++) {
			sb.append("\tvalues.add(param.get" + initcap(colnames[i]) + "());\r\n");
		}
		sb.append("\tvalues.add(param.get" + initcap(colnames[0]) + "());\r\n");
		sb.append("\treturn super.executeWithOutResult(sql, values,1);\r\n");
		sb.append("}\r\n");
	}

	/**
	 * rsToObject
	 * 
	 * @param sb
	 * @return void
	 */
	private static void rsToObjectMethod(StringBuffer sb) {

		sb.append("\t//rsToObject\r\n");
		sb.append("\tpublic void rsToObject(ResultSet rs, " + tablename + " object) throws SQLException {\r\n");
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tobject.set" + initcap(colnames[i]) + "(rs.get" + initcap(sqlType2JavaType(colTypes[i])) + "(\"" + colnames[i] + "\"));\r\n");
		}
		sb.append("}\r\n");
	}

	/**
	 * select
	 * 
	 * @param sb
	 * @return void
	 */
	private static void getMethod(StringBuffer sb) {

		sb.append("\t//select\r\n");
		sb.append("\tpublic " + tablename + " get(int id) {\r\n");
		sb.append("\tString sql = \"select * from \" + TableName + \" where id = ?\";\r\n");
		sb.append("\tList<Object> values = new ArrayList<Object>();\r\n");
		sb.append("\tvalues.add(id);\r\n");
		sb.append("\tfinal " + tablename + " object = new " + tablename + "();\r\n");
		sb.append("\tBaseJDBCDao bd = new BaseJDBCDao() {\r\n");
		sb.append("\t@Override\r\n");
		sb.append("\tpublic void returnResult(ResultSet rs) {\r\n");
		sb.append("\ttry {\r\n");
		sb.append("\twhile (rs.next()) {\r\n");
		sb.append("\trsToObject(rs, object);}\r\n");
		sb.append("\t} catch (SQLException e) {e.printStackTrace();}\r\n");
		sb.append("\t}};\r\n");
		sb.append("\tbd.executeWithResult(sql, values, 1);\r\n");
		sb.append("\treturn object;}\r\n");

	}

	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private static String initcap(String str) {
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	private static String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "bool";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "int";
			// return "byte";
		} else if (sqlType.equalsIgnoreCase("double")) {
			return "double";
			// return "short";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "int";
			// return "short";
		} else if (sqlType.equalsIgnoreCase("integer")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("int")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("int32")) {
			return "int";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric") || sqlType.equalsIgnoreCase("real")) {
			return "BigDecimal";
		} else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		}

		else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		}
		return null;
	}

}