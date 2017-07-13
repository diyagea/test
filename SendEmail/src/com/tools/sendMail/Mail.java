package com.tools.sendMail;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * 邮件发送类 支持普通模式和HTML模式，可发送多个附件，支持SMTP服务器认证。
 */
public class Mail {
	private String smtpHost = "";//smtp服务器IP
	private int smtpPort = 25;// 端口默认 25
	private String smtpUser = "";//smtp账户名
	private String smtpPassword = "";//smtp密码
	private String from = "";//发信人
	private String formName = "HISP-哈他信息系统";//发信人名字
	private String content_type = "text/html;charset=utf-8";//邮件类型 "text/plain;charset=utf-8" or "text/html;charset=utf-8"
	private boolean isAuthenticationSMTP = true;//指定是否需要SMTP验证，只有输入了用户名密码，才自动开启必须的认证。
	
	private Address[] to = null;//收信人
	private Address[] cc = null;//抄送
	private Address[] bcc = null;//暗送
	private String title = "";//标题
	private String content = "";//内容
	private List<Object> vFiles = new ArrayList<Object>();// 添加附件 本地文件
	private List<Object> vURLs = new ArrayList<Object>();// 添加附件 网络文件 
	/**
	 * 有参数构造方法,为单列模式提供固定的加载内容
	 * 
	 * @param SMTPHost
	 *            SMTP服务器
	 * @param user
	 *            登录SMTP服务器的帐号
	 * @param password
	 *            登录SMTP服务器的密码
	 * @param from
	 *            发件人邮箱
	 * @param port
	 *            SMTP端口
	 */
	public Mail(String SMTPHost, String user, String password, String from,int port) {
		this.smtpHost = SMTPHost;
		this.smtpPort = port;
		this.smtpUser = user;
		this.smtpPassword = password;
		this.from = from;
	}
	
	/**
	 * 设置收件人地址
	 * 
	 * @param aEmail
	 *            收件人Email地址
	 */
	public void setTo(List<String> aEmail) {
		if(aEmail == null){
			this.to = null;
		}else{
			Address[] a = new Address[aEmail.size()];
			//将收信人地址转换为Address[]
			for(int i = 0; i < aEmail.size(); i++){
				try {
					a[i] = new InternetAddress(aEmail.get(i));
				} catch (Exception ex) {
					a = null;
				}
			}
			this.to = a;
		}
	}
	
	/**
	 * 设置抄送地址
	 * 
	 * @param Emails
	 *            抄送地址
	 */
	public void setCC(List<String> aEmail) {
		if(aEmail == null){
			this.cc = null;
		}else{
			Address[] a = new Address[aEmail.size()];
			//将收信人地址转换为Address[]
			for(int i = 0; i < aEmail.size(); i++){
				try {
					a[i] = new InternetAddress(aEmail.get(i));
				} catch (Exception ex) {
					a = null;
				}
			}
			this.cc = a;
		}
	}
	
	/**
	 * 设置暗送地址
	 * 
	 * @param Emails
	 *            暗送地址
	 */
	public void setBCC(List<String> aEmail) {
		if(aEmail == null){
			this.bcc = null;
		}else{
			Address[] a = new Address[aEmail.size()];
			//将收信人地址转换为Address[]
			for(int i = 0; i < aEmail.size(); i++){
				try {
					a[i] = new InternetAddress(aEmail.get(i));
				} catch (Exception ex) {
					a = null;
				}
			}
			this.bcc = a;
		}
	}

	/**
	 * 设置邮件主题，解决标题的中文问题
	 * 
	 * @param mailTitle
	 *            邮件主题
	 */
	public void setTitle(String mailTitle) {
		try {
			this.title = new String(mailTitle.getBytes("utf-8"), "utf-8");
		} catch (Exception ex) {
			this.title = "";
		}
	}

	/**
	 * 设置邮件文字内容，解决内容的中文问题
	 * 
	 * @param mailContent
	 *            邮件文字内容
	 */
	public void setContent(String mailContent) {
		try {
			this.content = new String(mailContent.getBytes("utf-8"), "utf-8");
		} catch (Exception ex) {
			this.content = "";
		}
	}
	
	 /**
	 * 添加附件
	 * @param afile 本地文件
	 */
	 public void addAttachment(File afile) {vFiles.add(afile);}
	 
	 /**
	 * 添加附件
	 * @param fileURL 文件URL
	 */
	 public void addAttachment(URL fileURL) {vURLs.add(fileURL);}

	 /**
	 * 验证发送模式
	 */
	public boolean isHtmlModeMail() {return this.content_type.equals("text/html;charset=utf-8");}

	 /**
	 * 标示邮件是否附带附件
	 * @return
	 */
	 public boolean hasAttachment() {return vFiles.size() + vURLs.size() > 0;}

	/**
	 * 定义一个SMTP授权验证类
	 */
	static class SmtpAuth extends Authenticator {
		String user, password;
		/**
		 * 设置帐号信息
		 */
		void setAccount(String user, String password) {
			this.user = user;
			this.password = password;
		}
		/**
		 * 取得PasswordAuthentication对象
		 */
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(user, password);
		}
	}

	/**
	 * 发送邮件
	 */
	public void send() {
		boolean doSend  = false;
		//标题、内容均不为空
		if(!this.title.equals("") && !this.content.equals("")){
			if(this.to != null && this.to.length > 0 ){//收件人有值
				doSend = true;
			}else if(this.cc != null && this.cc.length > 0 ){//抄送有值
				doSend = true;
			}else if(this.bcc != null && this.bcc.length > 0 ){//暗抄送有值
				doSend = true;
			}
		}
		if(doSend){
			try {
				// 创建一个属性对象
				Properties server = new Properties();
				// //指定SMTP的端口
				server.put("mail.smtp.port", String.valueOf(this.smtpPort));
				// 指定SMTP服务器
				server.put("mail.smtp.host", this.smtpHost);
				// 指定是否需要SMTP验证，只有输入了用户名密码，才自动开启必须的认证。
				if (this.isAuthenticationSMTP) {server.put("mail.smtp.auth", "true");}
				// 创建一个授权验证对象
				SmtpAuth auth = new SmtpAuth();
				auth.setAccount(this.smtpUser, this.smtpPassword);
				// 创建一个Session对象
				Session conn = Session.getDefaultInstance(server, auth);
				// 创建一个Message对象
				MimeMessage msg = new MimeMessage(conn);
				//设置发信人信息
				msg.setFrom(new InternetAddress(this.from, new String(this.formName.getBytes("utf-8"), "utf-8")));
				// 插入收件人
				if(this.to != null && this.to.length > 0){msg.setRecipients(Message.RecipientType.TO, this.to);}
				// 插入抄送
				if (this.cc != null && this.cc.length > 0) {msg.setRecipients(Message.RecipientType.CC, this.cc);}
				// 插入暗送
				if (this.bcc != null && this.bcc.length > 0) {msg.setRecipients(Message.RecipientType.BCC, this.bcc);}
				// 插入标题
				msg.setSubject(this.title);
				// 指定邮件发送日期
				msg.setSentDate(new Date());
				// 指定邮件优先级 1：紧急 3：普通 5：缓慢
				msg.setHeader("X-Priority", "3");
				//如果没有附件
				if(!hasAttachment()){
					// 插入HTML邮件内容
					msg.setContent(this.content, this.content_type);
				}else{
					//如果有带附件
					 Multipart mp = new MimeMultipart();
					 MimeBodyPart mbp = null;
					 //邮件正文
					 for (int i = 0; i < vFiles.size(); i++) {
						 mbp = new MimeBodyPart();
						 File file = (File) vFiles.get(i);
						 FileDataSource fds = new FileDataSource(file);
						 mbp.setDataHandler(new DataHandler(fds));
						 mbp.setFileName(new
						 String(file.getName().getBytes("utf-8"),"utf-8"));
						 mp.addBodyPart(mbp);
					 }
					 for (int i = 0; i < vURLs.size(); i++) {
						 mbp = new MimeBodyPart();
						 URL url = (URL) vURLs.get(i);
						 // URLDataSource uds=new URLDataSource(url);
						 mbp.setDataHandler(new DataHandler(url));
						 mbp.setFileName(new String(url.getFile().getBytes("utf-8"),"utf-8"));
						 mp.addBodyPart(mbp);
					 }
					 mbp = new MimeBodyPart();
					 mbp.setContent(this.content, this.content_type);
					 mp.addBodyPart(mbp);
					 msg.setContent(mp);
				 }
				//存储变量
				msg.saveChanges();
				// 如果需要SMTP验证发送
				if (this.isAuthenticationSMTP) {
					// 创建一个Transport对象
					Transport transport = conn.getTransport("smtp");
					// 连接SMTP服务器
					transport.connect(this.smtpHost, this.smtpUser, this.smtpPassword);
					// 发送邮件
					transport.sendMessage(msg, msg.getAllRecipients());
					//关闭连接
					transport.close();
				}else{
					// 发送邮件
					Transport.send(msg, msg.getAllRecipients());
				}
			} catch (Exception e) {
				//System.out.println("Mail Send Error");
			}
		}else{
			//System.out.println("Can't Send Mail");
		}
	}
}