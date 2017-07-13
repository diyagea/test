package com.mysql.common.dao;

public class DBbean 
{

	private String poolname 	= ""; //连接池名字
	private String driver 		= ""; //数据库驱动 
	private String url 			= ""; //数据库URL 
	private String username 	= ""; //用户名 
	private String password 	= ""; //密码 
	private int maxconn 		= 0;  //最大连接数 
	private int minconn 		= 0;  //最小连接数
	public String getPoolname() {
		return poolname;
	}
	public void setPoolname(String poolname) {
		this.poolname = poolname;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getMaxconn() {
		return maxconn;
	}
	public void setMaxconn(int maxconn) {
		this.maxconn = maxconn;
	}
	public int getMinconn() {
		return minconn;
	}
	public void setMinconn(int minconn) {
		this.minconn = minconn;
	}
	
	
	
}
