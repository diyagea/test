import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TestBigDecimal {
	public static void main(String[] args) {
		DecimalFormat format = new DecimalFormat("#.000000");   
		BigDecimal b = new BigDecimal(53865837800.000000);

		System.out.println(b);
		System.out.println(b.add(new BigDecimal(0.001)));
		System.out.println(format.format(b.add(new BigDecimal(0.001000))));
		System.out.println(new BigDecimal(format.format(b.add(new BigDecimal(0.001)))));

	}
}
