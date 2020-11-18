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
		this.userArray.add(person); // �迭�� ����� �߰�
		this.user = person;
		this.jta = jta;
		this.thisUser = person.getDos();
	}
	
	
	public void run() {
		DataInputStream dis = user.getDis(); // �Է� ��Ʈ�� ���

		while (online) {
			try {
				String receivedMsg = dis.readUTF(); // �޽��� �ޱ�(���)
				dataParsing(receivedMsg); // �޽��� �ؼ�
				jta.append("���� : �޽��� ���� -" + receivedMsg + "\n");
				jta.setCaretPosition(jta.getText().length());
			} catch (IOException e) {
				try {
					user.getDis().close();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					jta.append("���� : ����������-�б� ����\n");
					break;
				}
			}
		}
	}
	
	//������ �м�/��ū
	public synchronized void dataParsing(String data) {
		StringTokenizer token = new StringTokenizer(data, "/"); // ��ū ����
		String protocol = token.nextToken(); // ��ū���� �и��� ��Ʈ���� ���ڷ�
		String id, pw, rNum, rName, msg;
		System.out.println("������ ���� ������ : " + data);

		switch (protocol) {
		case User.LOGIN: // �α���
			// ����ڰ� �Է���(������) ���̵�� �н�����
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

			if (result == 0) { // result�� 0�̸� ����
				for (int i = 0; i < userArray.size(); i++) {
					if (id.equals(userArray.get(i).getId())) {
						try {
							System.out.println("������");
							thisUser.writeUTF(User.LOGIN + "/fail/�̹� ���� ���Դϴ�.");
						} catch (IOException e) {
							e.printStackTrace();
						}
						return;
					}
				}
				// �α��� OK
				user.setId(id);
				user.setPw(pw);
				user.setNickName(id);
				thisUser.writeUTF(User.LOGIN + "/OK/" + user.getNickName());
				this.user.setOnline(true);

				// ���ǿ� ����
				//echoMsg(User.ECHO01 + "/" + user.toString() + "���� �����ϼ̽��ϴ�.");
				jta.append(id + " : ���� �����ϼ̽��ϴ�.\n");

				//roomList(thisUser);
				for (int i = 0; i < userArray.size(); i++) {
				//	userList(userArray.get(i).getDos());
				}

				jta.append("���� : DB �б� : " + id);
			} else { // result�� 1�̸� ����
				thisUser.writeUTF(User.LOGIN + "/fail/���̵�� ��й�ȣ�� Ȯ���� �ּ���!");
			}

		} catch (Exception e) {
			try {
				thisUser.writeUTF(User.LOGIN + "/fail/���̵� �������� �ʽ��ϴ�!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			jta.append("���� : DB �б�\n");
			return;
		}

	}
}
