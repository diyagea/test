package SQLite;
import java.sql.Connection;
import java.util.Vector;

public class SQLiteTest {

	public static void main(String[] args) {
		try {
			Connection conn = SQLiteConn.getConnection();
			SQLiteCRUD crud = new SQLiteCRUD(conn);
			boolean isSucc = false;
			
			//建表
			/*System.out.println("********建表**********");
			isSucc = crud.createTable("create table person (id integer, name string)");
			System.out.println("create table : " + isSucc);*/
			//插入数据
			System.out.println("*********插入数据*********");
			int id=2;
			String[] params = {id + "", "test"};
			isSucc = crud.insert("person", params);
			System.out.println("insert : " + isSucc);
			//删除数据
			System.out.println("********删除数据**********");
			isSucc = crud.delete("person", "id", ""+id);
			System.out.println("delete : " + isSucc);
			//更新数据
			System.out.println("********更新数据**********");
			String[] fields = {"id"};
			String[] params2 = {"101"};
			isSucc = crud.update("person", "1","id" , fields, params2);
			System.out.println("update : " + isSucc);
			//查询记录count
			System.out.println("*********记录count*********");
			System.out.println("table count : " + crud.getTableCount("person"));
			//查询字段count
			System.out.println("********字段count**********");
			int fieldsCounts = crud.getFieldsCounts("person");
			System.out.println("field count : " + fieldsCounts);
			//查询数据，返回Vector<Vector<Object>>,二维有序数列
			System.out.println("********查询数据**********");
			Vector<Vector<Object>> v = crud.select("person");
			int index = 0;
			for(Vector<Object> ve : v){
				for(Object o : ve){
					System.out.print(o.toString() + " : ");
				}
				System.out.println();
				index++;
			}
			System.out.println("*********查询数据2*********");
			//查询数据，返回Object[][]，返回二维数组
			Object[][] oo = crud.selectObject("person");
			for(int i=0; i<oo.length; i++){
				for(int j=0; j<oo[i].length ;j++){
					System.out.print(oo[i][j] + " : ");
				}
				System.out.println();
			}
			
			//条件查询
			System.out.println("*******条件查询***********");
			v = crud.selectVector("person", "id", "101");
			index = 0;
			for(Vector<Object> ve : v){
				for(Object o : ve){
					System.out.print(o.toString() + " : ");
				}
				System.out.println();
				index++;
			}
			//条件查询2
			System.out.println("*******条件查询2***********");
			oo = crud.selectObject("person", "id", "101");
			for(int i=0; i<oo.length; i++){
				for(int j=0; j<oo[i].length ;j++){
					System.out.print(oo[i][j] + " : ");
				}
				System.out.println();
			}
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
