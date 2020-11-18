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
	String url = "jdbc:mysql://localhost/yahtzee?characterEncoding=UTF-8&serverTimezone=UTC"; // 오라클 포트번호1521/@이후에는 IP주소
	String sql = null;

	Connection conn = null;

	int checkIDPW(String id, String pw) {
		this.id = id;
		this.pw = pw;
		int result = 1; //1:fail 0:success

		try {
			
			Class.forName("com.mysql.jdbc.Driver"); //자동연결됨

			conn = DriverManager.getConnection(url, "root", "root"); // 연결할 정보를 가지고있는 드라이버매니저를 던진다

			stmt = conn.createStatement();

			sql = "select * from member_table where id='" + id + "'";
			rs = stmt.executeQuery(sql); //읽어오는것과 비교	//리턴타입  ResultSet

			if (rs.next() == false || (id.isEmpty()) == true) { // id가 존재하지 않을때
				result = 1;
			} else {
				sql = "select * from (select * from member_table where id='" + id + "')a";
				rs = stmt.executeQuery(sql);
				while (rs.next() == true) { 		// 다음값의
					if (rs.getString(2).equals(pw)) { // pw와 같은지 비교
						result = 0; 		// 같으면 로그인 성공
					} else {				// 아이디는같고 pw가 다른경우
						result = 1;
					}
				}
			}
		} catch (Exception ee) {
			System.out.println("문제있음");
			ee.printStackTrace();
		}
		return result;
	}
}