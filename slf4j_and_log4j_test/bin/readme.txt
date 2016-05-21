配置slf4j和log4j时出现如下异常：
	Exception in thread "main" java.lang.NoSuchMethodError: org.slf4j.helpers.Util.reportFailure(Ljava/lang/String;)V
	网上查了下，说是slf4j的jar包版本不对!

	log4j:WARN No appenders could be found for logger (com.unj.slf4j_and_log4j.LogTest).
	log4j:WARN Please initialize the log4j system properly.
	log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
	这个就是log4j的配置文件没有配所导致的结果，当然，英语过了四级的，一看就知道怎么回事了。
	
	>>>开始>>>>>
	log4j.properties配置如下：
		#可以设置级别：debug < info < warn < error
		#debug: 显示debug, info, warn, error
		#info: 显示info, warn, error
		#warn: 显示warn, error
		#error: 只显示error
		#日志的输出级别由rootLogger和普通Logger设置的最高级别决定。

		#log4j.rootLogger=debug,appender1
		#log4j.rootLogger=info,appender1
		log4j.rootLogger=warn,appender1
		#log4j.rootLogger=error,appender1

		#输出到控制台
		log4j.appender.appender1=org.apache.log4j.ConsoleAppender
		log4j.appender.appender2=org.apache.log4j.DailyRollingFileAppender
		#样式为TTCCLayout
		log4j.appender.appender1.layout=org.apache.log4j.TTCCLayout

		#这里配置的是类所在的包com.unj.slf4j_and_log4j, 逗号之前未配置日志输出级别，默认为根logger的级别
		log4j.logger.com.unj.slf4j_and_log4j=, TEST
		log4j.appender.TEST=org.apache.log4j.ConsoleAppender
		log4j.appender.TEST.layout=org.apache.log4j.TTCCLayout
		>>>结束>>>>>
		>>>开始>>>>>
			从上面可以看出，log4j.rootLogger是对整个项目的一个宏观控制，
			          logrj.logger可以对包或对类中的方法进行更精细的日志输出
		>>>结束>>>>>
	
	>>>开始>>>>>	
	对log4j配置的一些说明：
	
	log4j提供的Appender：
	org.apache.log4j.ConsoleAppender（控制台），
	org.apache.log4j.FileAppender（文件），
	org.apache.log4j.DailyRollingFileAppender（每天产生一个日志文件），
	org.apache.log4j.RollingFileAppender（文件大小到达指定尺寸的时候产生一个新的文件），
	org.apache.log4j.WriterAppender（将日志信息以流格式发送到任意指定的地方）
	
	log4j提供的Layout：
	org.apache.log4j.HTMLLayout（以HTML表格形式布局），
	org.apache.log4j.PatternLayout（可以灵活地指定布局模式），
	org.apache.log4j.SimpleLayout（包含日志信息的级别和信息字符串），
	org.apache.log4j.TTCCLayout（包含日志产生的时间、线程、类别等等信息）

	Log4J采用类似C语言中的printf函数的打印格式格式化日志信息，打印参数如下： 
　　	%m 输出代码中指定的消息
　　	%p 输出优先级，即DEBUG，INFO，WARN，ERROR，FATAL
　　	%r 输出自应用启动到输出该log信息耗费的毫秒数
　　	%c 输出所属的类目，通常就是所在类的全名
　　	%t 输出产生该日志事件的线程名
　　	%n 输出一个回车换行符，Windows平台为“rn”，Unix平台为“n”
　　	%d 输出日志时间点的日期或时间，默认格式为ISO8601，也可以在其后指定格式，比如：%d{yyy MMM dd HH:mm:ss,SSS}，输出类似：2002年10月18日 22：10：28，921
　　	%l 输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main(TestLog4.java:10)
	>>>结束>>>>>
		