package yahtzee_project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBLogin {
	String id = null;
	String pw = null;

	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost/yahtzee?characterEncoding=UTF-8&serverTimezone=UTC"; // ����Ŭ ��Ʈ��ȣ1521/@���Ŀ��� IP�ּ�
	String sql = null;

	Connection conn = null;

	int checkIDPW(String id, String pw) {
		this.id = id;
		this.pw = pw;
		int result = 1; //1:fail 0:success

		try {
			
			Class.forName("com.mysql.jdbc.Driver"); //�ڵ������

			conn = DriverManager.getConnection(url, "root", "root"); // ������ ������ �������ִ� ����̹��Ŵ����� ������

			stmt = conn.createStatement();

			sql = "select * from member_table where id='" + id + "'";
			rs = stmt.executeQuery(sql); //�о���°Ͱ� ��	//����Ÿ��  ResultSet

			if (rs.next() == false || (id.isEmpty()) == true) { // id�� �������� ������
				result = 1;
			} else {
				sql = "select * from (select * from member_table where id='" + id + "')a";
				rs = stmt.executeQuery(sql);
				while (rs.next() == true) { 		// ��������
					if (rs.getString(2).equals(pw)) { // pw�� ������ ��
						result = 0; 		// ������ �α��� ����
					} else {				// ���̵�°��� pw�� �ٸ����
						result = 1;
					}
				}
			}
		} catch (Exception ee) {
			System.out.println("��������");
			ee.printStackTrace();
		}
		return result;
	}
}