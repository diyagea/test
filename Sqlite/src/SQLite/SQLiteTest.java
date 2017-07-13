package SQLite;
import java.sql.Connection;
import java.util.Vector;

public class SQLiteTest {

	public static void main(String[] args) {
		try {
			Connection conn = SQLiteConn.getConnection();
			SQLiteCRUD crud = new SQLiteCRUD(conn);
			boolean isSucc = false;
			
			//����
			/*System.out.println("********����**********");
			isSucc = crud.createTable("create table person (id integer, name string)");
			System.out.println("create table : " + isSucc);*/
			//��������
			System.out.println("*********��������*********");
			int id=2;
			String[] params = {id + "", "test"};
			isSucc = crud.insert("person", params);
			System.out.println("insert : " + isSucc);
			//ɾ������
			System.out.println("********ɾ������**********");
			isSucc = crud.delete("person", "id", ""+id);
			System.out.println("delete : " + isSucc);
			//��������
			System.out.println("********��������**********");
			String[] fields = {"id"};
			String[] params2 = {"101"};
			isSucc = crud.update("person", "1","id" , fields, params2);
			System.out.println("update : " + isSucc);
			//��ѯ��¼count
			System.out.println("*********��¼count*********");
			System.out.println("table count : " + crud.getTableCount("person"));
			//��ѯ�ֶ�count
			System.out.println("********�ֶ�count**********");
			int fieldsCounts = crud.getFieldsCounts("person");
			System.out.println("field count : " + fieldsCounts);
			//��ѯ���ݣ�����Vector<Vector<Object>>,��ά��������
			System.out.println("********��ѯ����**********");
			Vector<Vector<Object>> v = crud.select("person");
			int index = 0;
			for(Vector<Object> ve : v){
				for(Object o : ve){
					System.out.print(o.toString() + " : ");
				}
				System.out.println();
				index++;
			}
			System.out.println("*********��ѯ����2*********");
			//��ѯ���ݣ�����Object[][]�����ض�ά����
			Object[][] oo = crud.selectObject("person");
			for(int i=0; i<oo.length; i++){
				for(int j=0; j<oo[i].length ;j++){
					System.out.print(oo[i][j] + " : ");
				}
				System.out.println();
			}
			
			//������ѯ
			System.out.println("*******������ѯ***********");
			v = crud.selectVector("person", "id", "101");
			index = 0;
			for(Vector<Object> ve : v){
				for(Object o : ve){
					System.out.print(o.toString() + " : ");
				}
				System.out.println();
				index++;
			}
			//������ѯ2
			System.out.println("*******������ѯ2***********");
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
