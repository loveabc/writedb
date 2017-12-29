package cn.cathaylife;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * @author 0100060928 获取数据库连接
 */

public class DataSourceConnection {

	private static final Properties prop = new Properties();;

	public  Connection getDataSouceConnection() throws IOException, ClassNotFoundException, SQLException {
		/** 先加载配置文件 */
		this.parseProperties();
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");

		Class.forName("com.ibm.db2.jcc.DB2Driver");
		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}

	private void parseProperties() throws IOException {
		InputStream is = new FileInputStream("src/db2.properties");
		prop.load(is);
	}

	public static void main(String[] args) throws IOException {
		new DataSourceConnection().parseProperties();
	}
}
