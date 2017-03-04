package wtf;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.junit.Test;

/*
 * author:wangtenfee@gmail.com
 * date:2017/3/4
 * 
 * */
public class Tools {
	
	//处理中英文对照
	@Test
	public void handleCNtoENU() {
		//System.out.println(1111);
		//参考: http://blog.csdn.net/Senton/article/details/4083127 
		//参考: http://www.cnblogs.com/bakari/p/3562244.html
//		InputStream in = new BufferedInputStream(new FileInputStream(""));
//		Properties p = new Properties();
//		p.load(in);
		
		try {
			//读ENU属性文件
			InputStream inputStreamENU = new BufferedInputStream(new FileInputStream("src/core/org/apache/jmeter/resources/messages.properties"));
			Properties propertiesENU = new Properties();
			propertiesENU.load(inputStreamENU);
			//System.out.println(propertiesENU.get("about"));
			Enumeration enumerationENU = propertiesENU.propertyNames();
			//获取所有的属性值
			while (enumerationENU.hasMoreElements()) {
				String temp1,temp2;
				temp1 = (String)enumerationENU.nextElement();
				temp2 = propertiesENU.getProperty(temp1);
				//System.err.println("key="+temp1+";value="+temp2);
			}
			
			//读CN属性文件
			InputStream inputStreamCN = new BufferedInputStream(new FileInputStream("src/core/org/apache/jmeter/resources/messages_zh_CN.properties"));
			Properties propertiesCN = new Properties();
			propertiesCN.load(inputStreamCN);
			//System.out.println(propertiesCN.get("about"));
			
			//处理两个文件相同的key,获取英语对应的中文翻译;不是所有的英文都有中文对应的翻译.
			Enumeration enumerationCN = propertiesCN.propertyNames();
			Properties enuToCN = new Properties();
			OutputStream out = new FileOutputStream("src/core/org/apache/jmeter/resources/enuToCN.properties");
			while (enumerationCN.hasMoreElements()) {
				String temp1 = (String)enumerationCN.nextElement();
				String temp2 = propertiesCN.getProperty(temp1);
				String temp3 = propertiesENU.getProperty(temp1);
				enuToCN.setProperty(temp3, temp2);
				System.err.println(temp3+"="+temp2);
			}
			enuToCN.store(out, "jemeter ENU to CN");
			
			out.close();
			inputStreamCN.close();
			inputStreamENU.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
