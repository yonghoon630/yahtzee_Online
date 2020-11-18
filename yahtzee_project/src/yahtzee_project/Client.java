package yahtzee_project;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable {

	static int PORT = 9999;
	static String IP = "0.0.0.0";
	Socket socket;
	User user;
	
	LoginUI login;
	WaitRoomUI waitRoom;
	
	DataInputStream dis;
	DataOutputStream dos;
	
	boolean ready = false;
	
	//������//
	public Client() {
		login = new LoginUI(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	//������//
	public void run() {
		//���� ��� ����//
		while(!ready) {	//false �϶� �����带 ���鼭 ���
			try {
				Thread.sleep(10);		
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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
	
	public static void main(String[] args) {
		System.out.println("Client Start!");
		new Client();
	}
}
