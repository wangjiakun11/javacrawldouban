package myCrawlDemo;

import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DataStore {

	public void store(List<Integer> list, List<String> list1, List<Double> list2) throws Exception {
		String driverClassName = "com.mysql.jdbc.Driver";

		String url = "jdbc:mysql://localhost:3306/douban";

		String username = "root";

		String password = "123";
		// 加载驱动类
		Class.forName(driverClassName);
		Connection con = (Connection) DriverManager.getConnection(url, username, password);
		// 创建sql语句模板
		String sql = "INSERT MOVIE VALUES(?,?,?)";
		// 创建一个声明对象
		PreparedStatement pst = (PreparedStatement) con.prepareStatement(sql);
		// 用循环将数据添加到sql模板中
		for (int i = 0; i < list.size(); i++) {
			pst.setInt(1, list.get(i));
			pst.setString(2, list1.get(i));
			pst.setDouble(3, list2.get(i));

			pst.addBatch();
		}
		// 将sql语句发送到mysql上
		int[] res = pst.executeBatch();
		System.out.println(res);
		pst.close();
	}
}
