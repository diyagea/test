package com.mysql.common.dao;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

public class DBConnectionManager 
{
	protected static DBConnectionManager instance;	////唯一数据库连接池管理实例类 
	private Vector<DBbean> drivers  = new Vector<DBbean>();//驱动信息 
	private Hashtable<String,DBConnectionPool> pools=new Hashtable<String, DBConnectionPool>();//连接池
	
	/** 
	  * 重构
	  */ 
	
	protected DBConnectionManager() {
		this.init();
	}
	
	/** 
	  * 得到唯一实例管理类 
	  */ 
	
	public static synchronized DBConnectionManager getInstance()
	{
		if(instance==null) 
		  { 
		   instance=new DBConnectionManager(); 
		  } 
		  return instance; 
	}
	
	/** 
	  * 初始化连接池的参数 
	  */ 
	
	private void init() 
	{ 
	  //加载驱动程序 
	  this.loadDrivers();
	  //创建连接池 
	  Iterator<DBbean> alldriver=drivers.iterator();
	  while(alldriver.hasNext())
	  { 
	   this.createPools((DBbean)alldriver.next());
	  } 
	} 
	
	/** 
	  * 加载驱动程序 
	  */ 
	
	private void loadDrivers() 
	{ 
		//加载驱动为Mysql的
		String driver = Env.getInstance().getProperty("driver");
		String dbName = Env.getInstance().getProperty("user");
		String dbPass = Env.getInstance().getProperty("password");
		//查看该驱动器下面有几个要操作的连接池
		int tableSum = Integer.parseInt(Env.getInstance().getProperty("tableSum"));
		for(int i = 1; i<=tableSum; i++){
			DBbean bean = new DBbean();
			bean.setDriver(driver);
			bean.setUsername(dbName);
			bean.setPassword(dbPass);
			bean.setPoolname(Env.getInstance().getProperty("poolname"+i));
			bean.setUrl(Env.getInstance().getProperty("url"+i));
			bean.setMaxconn(Integer.parseInt(Env.getInstance().getProperty("max"+i)));
			bean.setMinconn(Integer.parseInt(Env.getInstance().getProperty("min"+i)));
			drivers.add(bean);
		}
	}
	/** 
	  * 创建连接池 
	  */ 
	private void createPools(DBbean dsb) 
	{ 
	  DBConnectionPool dbpool= new DBConnectionPool(dsb);
	  pools.put(dsb.getPoolname(), dbpool); 
	} 
	
	/** 
	  * 根据连接池的名字 name 得到链接
	  * @param name 
	  * @return 
	  * @throws InterruptedException 
	  */
	
	public Connection getConnection(String poolname)
	{ 
	  DBConnectionPool pool=null; 
	  Connection conn=null; 
	  pool=pools.get(poolname);//从名字中获取连接池 
	  conn=pool.getConnection();//从选定的连接池中获得连接
	  return conn; 
	} 
	
	/** 
	  * 释放所有连接 
	  */ 
	
	public synchronized void release() 
	{ 
	  Enumeration<DBConnectionPool> allpools=pools.elements(); 
	  while(allpools.hasMoreElements()) 
	  { 
	   DBConnectionPool pool=(DBConnectionPool)allpools.nextElement(); 
	   if(pool!=null)pool.release(); 
	  } 
	  pools.clear(); 
	}
	
	/** 
	  * 释放连接 
	  */ 
	
	public void freeConnection(String poolname, Connection conn) 
	{ 
	  DBConnectionPool pool=(DBConnectionPool)pools.get(poolname);//根据关键名字得到连接池 
	  if(pool!=null) 
	  pool.freeConnection(conn);//释放连接 
	} 
}
