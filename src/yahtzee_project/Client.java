package yahtzee_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Client implements Runnable {

	static int PORT = 9999;			//������Ʈ
	static String IP = "192.168.0.163";	//����IP
	Socket socket;					//����
	User user;						//�����
	
	LoginUI login;					
	WaitRoomUI waitRoom;		
	
		
	DataInputStream dis;
	DataOutputStream dos;
	
	boolean ready = false;
	
	//������//
	public Client() {
		login = new LoginUI(this);			//�α��� UI ����
		
		Thread thread = new Thread(this);	//������ ����
		thread.start();
	}
	
	
	//MAIN//
	public static void main(String[] args) {
		System.out.println("Client Start!");
		new Client();
	}
	
	
	//������//
	public void run() {
		//
		//���� ��� ����//
		//
		while(!ready) {	//�غ���� �ʾ��� ��, �����带 ���鼭 ���
			try {
				Thread.sleep(10);		
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//����ڰ� ��ü ���� �� ������ ����
		user = new User(dis, dos);
		user.setIP(socket.getInetAddress().getHostAddress());
		
		//�޽��� ����
		while (true) {
			try {
				String receivedMsg = dis.readUTF(); //�޽��� �ޱ�(���)
				dataParsing(receivedMsg); //�޽��� �ؼ�
			} catch(IOException e) {
				e.printStackTrace();
				try {						//���� �߻��� ���������� ����� ��Ʈ�� �� ���� �ݱ�
					user.getDis().close();
					user.getDos().close();
					socket.close();
					break;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//���α׷� �����
		System.out.println("�������α׷� �����");
		waitRoom.dispose();	
	}
	
	
	//��������//
	public boolean serverAccess() {	
		
		//���� ������ �̷����� �������� ���ʽ�����.
		if(!ready) {
			socket = null;
			IP = login.ipBtn.getText();
			
			try {
				// ��������
				InetSocketAddress inetSockAddr = new InetSocketAddress(InetAddress.getByName(IP), PORT);
				socket = new Socket();

				// ������ �ּҷ� ���� �õ� (3�ʵ���)
				socket.connect(inetSockAddr, 3000);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//������ �Ǹ� ����//
			if (socket.isBound()) { //������ ���ε� �Ǿ����� üũ��.
				//����� ��Ʈ�� ����//
				try {
					dis = new DataInputStream(socket.getInputStream());
					dos = new DataOutputStream(socket.getOutputStream());
				} catch (IOException e) {
					e.printStackTrace();
				}
				ready = true;
			}
		}
		return ready;
	}

	
	//������ �ؼ�
	public synchronized void dataParsing(String data) {
		StringTokenizer token = new StringTokenizer(data, "/"); //��ū����
		String protocol = token.nextToken(); //��ū���� �и��� String
		String id, pw, rNum, nickName, rName, msg, result;
		System.out.println("���� ������: "+data);
		
		//��������
		switch (protocol) {
		case User.LOGIN: 
			result = token.nextToken();
			if(result.equals("OK")) {
				System.out.println("�α��� ����");
				nickName = token.nextToken();
				login(nickName);
			}
		}		
	}
	
	//login
	private void login(String nickName) {
		//�α������� ��������
		user.setId(login.idField.getText());
		user.setNickName(login.idField.getText());
		
		//loginUI �ݰ� waitRoomUI ����
		login.dispose();
		waitRoom = new WaitRoomUI(Client.this);
		
	}
	
	public DataOutputStream getDos() {
		return dos;
	}

}
