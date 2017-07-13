package com.mysql.common.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;


public class DBConnectionPool 
{
	private Connection conn = null;
	private String driver;   		//驱动 
	private String url;
	private String username;    	//用户名 
	private String password; 		//密码 
	private int minConn = 0;		//初始化连接数
	private int maxConn = 0;     	//最大连接数

	private int createCount = 0;	//创建的连接数
	@SuppressWarnings("unused")
	private int usedCount =	0;    	//使用的连接数
	private LinkedList<Connection> freeConnections = new LinkedList<Connection>();//连接池
	
	public DBConnectionPool(DBbean bean){
		this.driver=bean.getDriver();
		this.url=bean.getUrl();
		this.username=bean.getUsername();
		this.password=bean.getPassword();		
		this.maxConn=bean.getMaxconn();
		this.minConn=bean.getMinconn();
		for(int i = 0; i<minConn; i++){
			Connection conn = newConnection(); //新建连接 
			freeConnections.add(conn);//添加到地址池中
		}
	}
	
	
	/** 
	  * 释放连接  
	  * @param conn
	  **/ 
	
	public synchronized void freeConnection(Connection conn) 
	{
		freeConnections.add(conn);//添加到空闲连接的末尾 
		notify();
		this.usedCount--;
	} 
		
	
	/** 
	  * 
	  * 从连接池里得到连接 
	  * @return 
	 * @throws InterruptedException 
	  */ 
	public synchronized Connection getConnection()
	{ 
	  Connection conn=null;
	  if(freeConnections.size()>0) 
	  { 
		   conn=freeConnections.get(0); //分配出去一个链接
		   freeConnections.remove(0);	//从地址池里删除 
		   this.usedCount++;
	  }else if(this.createCount<this.maxConn){ 
		  conn=newConnection();
		  this.usedCount++;
	  }else{
		try {
			wait();
			conn = getConnection();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
	  }
	  return conn; 
	} 
	/** 
	  *释放全部连接 
	  * 
	  */ 
	public synchronized void release() 
	{ 
	  Iterator<Connection> allConns=this.freeConnections.iterator(); 
	  while(allConns.hasNext()) 
	  { 
		   Connection conn=allConns.next(); 
		   try 
		   { 
		    conn.close(); 
		   } 
		   catch(SQLException e) 
		   { 
		    e.printStackTrace(); 
		   } 
	  } 
	  this.freeConnections.clear(); 
	  this.createCount = 0;
	} 
	
	/** 
	  * 创建新连接 
	  */ 
	private Connection newConnection() 
	{ 
	   try {
		Class.forName(driver);
		conn =DriverManager.getConnection(url, username, password);
		this.createCount++;
	   } catch (Exception e) {
		   DBConnectionManager.instance = new DBConnectionManager();
		   //e.printStackTrace();
	   }
	   return conn; 
	}
}
