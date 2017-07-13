package com.tools.sendMail;


public class InitializationPushEmail {
	/** * 私有静态对象,加载时候不做初始化 */
	private static Mail push = new Mail("smtpdm.aliyun.com","push@mail.htchn.com","HISPMailPush2016","push@mail.htchn.com",25);//触发类邮件
	private static Mail pushAll = new Mail("smtpdm.aliyun.com","pushall@mail.htchn.com","HISPMailPush2016","pushall@mail.htchn.com",25);//批量推送类邮件
	/** * 私有构造方法,避免外部创建实例 */
	private InitializationPushEmail() {}
	/**
	 * 静态工厂方法,返回此类的唯一实例.当发现实例没有初始化的时候,才初始化.
	 * @return 一个唯一的邮件发送方法
	 */
	public static synchronized Mail push() {
		if (push == null) {
			push = new Mail("smtpdm.aliyun.com","push@mail.htchn.com","HISPMailPush2016","push@mail.htchn.com",25);//触发类邮件
		}
		return push;
	}
	public static synchronized Mail pushAll() {
		if (pushAll == null) {
			pushAll = new Mail("smtpdm.aliyun.com","pushall@mail.htchn.com","HISPMailPush2016","pushall@mail.htchn.com",25);//批量推送类邮件
		}
		return pushAll;
	}
}
