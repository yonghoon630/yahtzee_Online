package yahtzee_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.JTextArea;

public class ServerThread implements Runnable {

	ArrayList<User> userArray;
	ArrayList<Room> roomArray;
	User user;
	
	JTextArea jta;
	boolean online = true;
	
	private DataOutputStream thisUser;

	ServerThread(JTextArea jta, User person, ArrayList<User> userArray, ArrayList<Room> roomArray) {
		
		this.roomArray = roomArray;
		this.userArray = userArray;
		this.userArray.add(person); // 배열에 사용자 추가
		this.user = person;
		this.jta = jta;
		this.thisUser = person.getDos();
	}
	
	
	public void run() {
		DataInputStream dis = user.getDis(); // 입력 스트림 얻기

		while (online) {
			try {
				String receivedMsg = dis.readUTF(); // 메시지 받기(대기)
				dataParsing(receivedMsg); // 메시지 해석
				jta.append("성공 : 메시지 읽음 -" + receivedMsg + "\n");
				jta.setCaretPosition(jta.getText().length());
			} catch (IOException e) {
				try {
					user.getDis().close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					jta.append("에러 : 서버스레드-읽기 실패\n");
					break;
				}
			}
		}
	}
	
	//데이터 분석/토큰
	public synchronized void dataParsing(String data) {
		StringTokenizer token = new StringTokenizer(data, "/"); // 토큰 생성
		String protocol = token.nextToken(); // 토큰으로 분리된 스트링을 숫자로
		String id, pw, rNum, rName, msg;
		System.out.println("서버가 받은 데이터 : " + data);

		switch (protocol) {
		case User.LOGIN: // 로그인
			// 사용자가 입력한(전송한) 아이디와 패스워드
			id = token.nextToken();
			pw = token.nextToken();
			login(id, pw);
			break;
		}
	}
	
	
	private void login(String id, String pw) {
		
		StringBuffer str = new StringBuffer();
		
		try {

			DBLogin logdb = new DBLogin();
			int result = logdb.checkIDPW(id, pw);

			if (result == 0) { // result가 0이면 성공
				for (int i = 0; i < userArray.size(); i++) {
					if (id.equals(userArray.get(i).getId())) {
						try {
							System.out.println("접속중");
							thisUser.writeUTF(User.LOGIN + "/fail/이미 접속 중입니다.");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
					}
				}
				// 로그인 OK
				user.setId(id);
				user.setPw(pw);
				user.setNickName(id);
				thisUser.writeUTF(User.LOGIN + "/OK/" + user.getNickName());
				this.user.setOnline(true);

				// 대기실에 에코
				//echoMsg(User.ECHO01 + "/" + user.toString() + "님이 입장하셨습니다.");
				jta.append(id + " : 님이 입장하셨습니다.\n");

				//roomList(thisUser);
				for (int i = 0; i < userArray.size(); i++) {
				//	userList(userArray.get(i).getDos());
				}

				jta.append("성공 : DB 읽기 : " + id);
			} else { // result가 1이면 실패
				thisUser.writeUTF(User.LOGIN + "/fail/아이디와 비밀번호를 확인해 주세요!");
			}

		} catch (Exception e) {
			try {
				thisUser.writeUTF(User.LOGIN + "/fail/아이디가 존재하지 않습니다!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			jta.append("실패 : DB 읽기\n");
			return;
		}

	}
}
