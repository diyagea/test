import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
	/**
	 * Ҫ��������ҳ
	 */
	String htmlUrl;
	/**
	 * �������
	 */
	ArrayList<String> hrefList = new ArrayList();
	/**
	 * ��ҳ���뷽ʽ
	 */
	String charSet;

	public HtmlParser(String htmlUrl) {
		// TODO �Զ����ɵĹ��캯�����
		this.htmlUrl = htmlUrl;
	}

	/**
	 * ��ȡ�������
	 * 
	 * @throws IOException
	 */
	public ArrayList<String> getHrefList() throws IOException {
		parser();
		return hrefList;
	}

	/**
	 * ������ҳ����
	 * 
	 * @return
	 * @throws IOException
	 */
	private void parser() throws IOException {
		URL url = new URL(htmlUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		String contenttype = connection.getContentType();
		charSet = getCharset(contenttype);
		InputStreamReader isr = new InputStreamReader(connection.getInputStream(), charSet);
		BufferedReader br = new BufferedReader(isr);
		String str = null, rs = null;
		while ((str = br.readLine()) != null) {
			rs = getHref(str);
			if (rs != null)
				hrefList.add(rs);
		}
	}

	/**
	 * ��ȡ��ҳ���뷽ʽ
	 * 
	 * @param str
	 */
	private String getCharset(String str) {
		Pattern pattern = Pattern.compile("charset=.*");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find())
			return matcher.group(0).split("charset=")[1];
		return "GBK";
	}

	/**
	 * ��һ���ַ����ж�ȡ����
	 * 
	 * @return
	 */
	private String getHref(String str) {
		/*Pattern pattern = Pattern.compile("<a href=.*</a>");
		Matcher matcher = pattern.matcher(str);
		if (matcher.find())
			return matcher.group(0);
		return null;*/
		
		Pattern pattern = Pattern.compile("<div class='content'>.*</div>");
		Matcher matcher = pattern.matcher(str);
		String content = matcher.group(0);
		pattern = Pattern.compile("herf=\".*\"");
		matcher = pattern.matcher(content);
		
		if (matcher.find())
			return matcher.group(0);
		return null;
	}

	public static void main(String[] arg) throws IOException {
		//HtmlParser a = new HtmlParser("http://news.163.com/");
		HtmlParser a = new HtmlParser("http://www.jb51.net/article/45059.htm");
		ArrayList<String> hrefList = a.getHrefList();
		for (int i = 0; i < hrefList.size(); i++)
			System.out.println(hrefList.get(i));
	}
}