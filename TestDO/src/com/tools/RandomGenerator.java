package com.tools;

public class RandomGenerator 
{
	public static String[] array= new String[]{"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","0","1","2","3","4","5","6","7","8","9"};
	
	/**
	 * 生成N位随机密码
	 * @return
	 */
	public static String getPWD(int number){
		String pwd="";
		for (int i = 0; i < number; i++) {
			int index=(int)(Math.random()*array.length);
			pwd+=array[index];
		}
		return pwd;
	}
	
	/**
	 * return random num
	 * @param count 
	 * @return
	 */
	public static int getRandomNum(int count){
		int randomNumber = (int) Math.round(Math.random()*(Math.pow(10, count)-1)+1);  
		return randomNumber;
	}
	
	public static void main(String[] args) {
		System.out.println(getRandomNum(5));
		
	}
}
