

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class UserBirthXmlUtil {

	private static final String FILEPATH = System.getProperty("user.dir").replace("\\", "/")+"/Test.xml";
	/* 美化格式输出 */
	private static final OutputFormat xmlFormat = OutputFormat.createPrettyPrint();

	static {
		// 设置文件编码
		xmlFormat.setEncoding("UTF-8");
		// 设置换行
		xmlFormat.setNewlines(true);
		// 生成缩进
		xmlFormat.setIndent(true);
		// 使用4个空格进行缩进, 可以兼容文本编辑器
		xmlFormat.setIndent("    ");
	}

	/*
	 * 建立xml文件，并写入根节点
	 */
	public static void write(Document document) {

		FileOutputStream fos = null;
		XMLWriter xmlWriter = null;

		File folder = new File("E://test");
		if (!folder.exists() && !folder.isDirectory()) {
			folder.mkdirs();
		}

		try {
			fos = new FileOutputStream(FILEPATH, false);

			// 创建写文件方法
			xmlWriter = new XMLWriter(fos, xmlFormat);
			// 写入文件
			xmlWriter.write(document);
			// 关闭
			xmlWriter.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (xmlWriter != null) {
					xmlWriter.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/*
	 * 建立xml文件，并写入根节点
	 */
	public static void createXML() {

		DocumentFactory factory = new DocumentFactory();
		Document document = factory.createDocument();

		// 建立根节点
		document.addElement("HumanBirthList");

		// 建立一个xml文件，将Dom4j树写入xml文件
		write(document);
	}

	/**
	 * xml追加内容
	 * @param birthList
	 */
	public static void appendList(List<human_users_birth> birthList) {
		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		try {
			File file = new File(FILEPATH);
			
			Document document = reader.read(file);

			// 获取根节点
			Element root = document.getRootElement();
			
			int birthCount = Integer.parseInt(root.attributeValue("birthCount"));
			String userIDs = root.attributeValue("userIDs");
			List<String> idList = Arrays.asList(userIDs.split(","));
			
			for (human_users_birth user : birthList) {
				if(!idList.contains(user.getHuman_user_id()+"")){
					Element e = root.addElement("UserBirth");
					// userID和type作为Attribute
					e.addAttribute("userID", user.getHuman_user_id() + "");
					e.addAttribute("type", user.getType() + "");
					
					e.addElement("userName").addText(user.getHuman_user_name());
					e.addElement("birthDate").addText(user.getBirthDate());
					e.addElement("userFace").addText(user.getFace());
					e.addElement("blessCount").addText(user.getBlessCount() + "");
					e.addElement("distanceDay").addText(user.getDay() + "");
					e.addElement("state").addText(user.getState() + "");
					e.addElement("sendUsers").addText("");
					
					birthCount++;
					userIDs += user.getHuman_user_id()+",";
				}
			}

			root.remove(root.attribute("birthCount"));
			root.addAttribute("birthCount", birthCount+"");
			
			root.remove(root.attribute("userIDs"));
			root.addAttribute("userIDs", userIDs);
			
			// 将Dom4j树写入xml文件
			write(document);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从xml中移除存在的
	 * @param delList
	 */
	public static void delList(List<human_users_birth> delList){
		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		try {
			File file = new File(FILEPATH);
			
			Document document = reader.read(file);

			// 获取根节点
			Element root = document.getRootElement();
			int birthCount = Integer.parseInt(root.attributeValue("birthCount"));
			
			String userIDs = root.attributeValue("userIDs");
			List<String> tempList = Arrays.asList(userIDs.split(","));
			List<String> idList = new ArrayList<String>(tempList);
			
			// 获取根节点包含的所有子节点
			List<?> nodes = root.elements();
			
			boolean flag = false;

			for (human_users_birth user : delList) {
				String delID = user.getHuman_user_id()+"";
				//遍历xml中存在的user标签
				if(idList.contains(delID)){
					for (Object element : nodes) {
						Element e = (Element) element;
						// 根据userID和type判断这条记录
						if (e.attributeValue("userID").equals(delID)) {
							
							//移除标签
							root.remove(e);
							
							//移除root id串属性中存在的id，改变xml中count
							idList.remove(delID);
							birthCount--;
							flag = true;
						}
					}
				}
			}
			
			if(flag){
				userIDs = "";
				for(String id : idList){
					userIDs += id + ",";
				}
				
				root.remove(root.attribute("birthCount"));
				root.addAttribute("birthCount", birthCount+"");
				
				root.remove(root.attribute("userIDs"));
				root.addAttribute("userIDs", userIDs);
				
				// 将Dom4j树写入xml文件
				write(document);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取寿星XML中寿星总数
	 * @return
	 */
	public static int getBirthCount(){
		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		int count = 0;
		try {
			File file = new File(FILEPATH);
			
			Document document = reader.read(file);

			// 获取根节点
			Element root = document.getRootElement();
			
			count = Integer.parseInt(root.attributeValue("birthCount"));
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	/**
	 * 保存寿星LIST
	 * 
	 */
	public static void saveList(List<human_users_birth> birthList, String month) {

		File file = new File(FILEPATH);

		if (file.exists()) {
			// 删除
			file.delete();
		}
		createXML();

		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		try {
			Document document = reader.read(file);

			// 获取根节点
			Element root = document.getRootElement();
			
			String userIDs = "";

			for (human_users_birth user : birthList) {
				Element e = root.addElement("UserBirth");
				// userID和type作为Attribute
				e.addAttribute("userID", user.getHuman_user_id() + "");
				e.addAttribute("type", user.getType() + "");

				e.addElement("userName").addText(user.getHuman_user_name());
				e.addElement("birthDate").addText(user.getBirthDate());
				e.addElement("userFace").addText(user.getFace());
				e.addElement("blessCount").addText(user.getBlessCount() + "");
				e.addElement("distanceDay").addText(user.getDay() + "");
				e.addElement("state").addText(user.getState() + "");
				e.addElement("sendUsers").addText("");
				
				userIDs += user.getHuman_user_id()+",";
			}
			//userID串
			root.addAttribute("userIDs", userIDs);
			root.addAttribute("month", month);
			root.addAttribute("birthCount", birthList.size()+"");

			// 将Dom4j树写入xml文件
			write(document);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	/*
	 * 查找所有数据列表
	 */
	public static synchronized Map<String, Object> getBirthXMLMap() throws Exception {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		File file = new File(FILEPATH);
		if (!file.exists()) {
			// 文件存在则添加
			createXML();
		}

		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		Document document = reader.read(file);

		
		// 获取根节点
		Element root = document.getRootElement();
		
		String month = root.attributeValue("month");
		List<human_users_birth> birthList = new ArrayList<human_users_birth>();
		
		returnMap.put("month", month);
		returnMap.put("birthList", birthList);

		// 获取根节点包含的所有子节点
		List<?> nodes = root.elements();

		for (Object element : nodes) {
			Element e = (Element) element;
			// 如果id值与设定的id值相等，则遍历该节点，获取信息
			human_users_birth user = new human_users_birth();

			user.setHuman_user_id(Integer.parseInt(e.attributeValue("userID")));
			user.setType(Integer.parseInt(e.attributeValue("type")));
			user.setBlessCount(Integer.parseInt(e.elementText("blessCount")));
			user.setDay(Integer.parseInt(e.elementText("distanceDay")));
			user.setState(Integer.parseInt(e.elementText("state")));
			user.setHuman_user_name(e.elementText("userName"));
			user.setBirthDate(e.elementText("birthDate"));
			user.setFace(e.elementText("userFace"));
			String[] sendUsers = e.elementText("sendUsers").split(",");
			user.setSendUsers(Arrays.asList(sendUsers));
			birthList.add(user);
		}
		return returnMap;
	}

	/*
	 * 根据id，查找，并修改信息 return 祝福数
	 */
	public static synchronized int doBless(String sendUser, String toUser, String type) {
		SAXReader reader = new SAXReader();
		reader.setStripWhitespaceText(true);
		int returnCount = -1;
		try {
			Document document = reader.read(new File(FILEPATH));

			// 获取根节点
			Element root = document.getRootElement();

			// 获取根节点包含的所有子节点
			List<?> nodes = root.elements();
			for (Object element : nodes) {
				Element e = (Element) element;

				// 根据userID和type判断这条记录
				if (e.attributeValue("userID").equals(toUser) && e.attributeValue("type").equals(type)) {
					// 增加count
					String count = e.element("blessCount").getText();
					returnCount = Integer.parseInt(count) + 1;
					e.element("blessCount").setText(returnCount + "");
					// 记录送祝福的人
					e.element("sendUsers").setText(e.element("sendUsers").getText() + sendUser + ",");
					break;
				}
			}
			// 将Dom4j树写入xml文件
			write(document);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return returnCount;
	}

}
