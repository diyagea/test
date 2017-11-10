import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.tools.GetDateDistance;
import com.tools.SimpleDate;

public class test {

	public static void main(String[] args) {
		//获得程序当前目录
		//System.out.println(System.getProperty("user.dir"));
		
/*		SimpleDate sd = new SimpleDate();
		
		//获取天数间隔
		int days = GetDateDistance.getDaysSpace("1600-01-01", sd.getSimpleDateYMD());
		System.out.println(days);
		
		System.out.println(sd.getSimpleDateYMD());
		
		*/
		
		
		List l = new ArrayList();
		l.add(1);
		l.add(11);
		l.add(111);
		l.add(1111);
		l.add(11111);
		
		Iterator it = l.iterator();
		while(it.hasNext()){
			Integer intV = (Integer)it.next();
			if(intV==111){
				it.remove();
			}
			
		}
		
	}

	
}
