import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class testTimestamp {

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		System.out.println(new Date().getTime());
		System.out.println(Calendar.getInstance().getTimeInMillis());
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String str = sdf.format(date);
		System.out.println(str);
		System.out.println(sdf2.format(date));
		
	}
}
