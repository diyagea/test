package com.tools.sendMail;

import java.io.File;
import java.net.URL;
import java.util.List;

public class SendEmail extends Thread {
	Mail push = InitializationPushEmail.push();
	Mail pushAll = InitializationPushEmail.pushAll();

	List<String> to;//收信人列表
	List<String> cc;//抄送人列表
	List<String> bcc;//暗送人列表
	String title;//标题
	String content;//内容
	List<File> vFiles;// 添加附件 本地文件
	List<URL> vURLs;// 添加附件 网络文件 
	int sendType=0;//发送调用邮箱
	
	/**
	 * 有参数构造方法,为单列模式提供固定的加载内容
	 * 
	 * @param List<String> to
	 *            收件人列表
	 * @param List<String> cc
	 *            抄送列表
	 * @param List<String> bcc
	 *            暗送列表
	 * @param String title
	 *            标题
	 * @param String content
	 *            内容
	 * @param List<File> vFiles
	 *            本地物理文件列表
	 * @param List<URL> vURLs
	 *            网络文件URL
	 * @param int sendType
	 *            调用哪个发信账户 0触发类、1批量推送类
	 */
	public SendEmail(List<String> to,List<String> cc,List<String> bcc, String title, String content, List<File> vFiles, List<URL> vURLs, int sendType) {
		this.to = to;
		this.cc = cc;
		this.bcc = bcc;
		this.title = title;
		this.content = content;
		this.vFiles = vFiles;
		this.vURLs = vURLs;
		this.sendType = sendType;
	}

	@Override
	// 保证线程同步，加锁.
	public void run() {
		if(sendType == 0){
			//单独触发 账户
			synchronized (push){
				push.setTo(to);
				push.setCC(cc);
				push.setBCC(bcc);
				push.setTitle(title);
				push.setContent(content);
				if(vFiles != null){
					for(File f : vFiles){
						push.addAttachment(f);
					}
				}
				if(vURLs != null){
					for(URL url : vURLs){
						push.addAttachment(url);
					}
				}
				push.send();
			}
		}else{
			//批量推送 账户
			synchronized (pushAll){
				pushAll.setTo(to);
				pushAll.setCC(cc);
				pushAll.setBCC(bcc);
				pushAll.setTitle(title);
				pushAll.setContent(content);
				if(vFiles != null){
					for(File f : vFiles){
						pushAll.addAttachment(f);
					}
				}
				if(vURLs != null){
					for(URL url : vURLs){
						pushAll.addAttachment(url);
					}
				}
				pushAll.send();
			}
		}
	}
}
