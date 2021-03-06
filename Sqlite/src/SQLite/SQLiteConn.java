package SQLite;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteConn implements Serializable {  
    private static final long serialVersionUID = 102400L;  
  
   /* private DbFile dbfile ;  
  
    public SQLiteConn(DbFile dbfile) {  
        super();  
        this.dbfile = dbfile;  
    }  */
      
    /** 
     * 与SQLite嵌入式数据库建立连接 
     * @return Connection 
     * @throws Exception 
     */  
    public static Connection getConnection() throws Exception {  
        Connection connection = null ;  
        try{  
            Class.forName("org.sqlite.JDBC") ;  
//            connection = DriverManager.getConnection("jdbc:sqlite:" + dbfile.getDbfilepath());  
              connection = DriverManager.getConnection("jdbc:sqlite:d:\\test.db");  
        }catch (Exception e) {  
            throw new Exception("" + e.getLocalizedMessage(), new Throwable("可能由于数据库文件受到非法修改或删除。")) ;  
        }  
        return connection ;  
    }   
}  