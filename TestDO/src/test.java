public class test {

	public static void main(String[] args) {
		String a = null + "|"+"123";
		System.out.println(a);
		System.out.println(a.split("\\|")[0]+" "+a.split("\\|")[1]);
	}

	
}
