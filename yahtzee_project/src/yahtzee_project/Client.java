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
	
	//생성자//
	public Client() {
		login = new LoginUI(this);
		
		Thread thread = new Thread(this);
		thread.start();
	}
	
	//스레드//
	public void run() {
		//소켓 통신 시작//
		while(!ready) {	//false 일때 스레드를 재우면서 대기
			try {
				Thread.sleep(10);		
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	//서버접속//
	public boolean serverAccess() {	
		
		//소켓 연결이 이뤄지지 않을때만 최초실행함.
		if(!ready) {
			socket = null;
			IP = login.ipBtn.getText();
			
			try {
				// 서버접속
				InetSocketAddress inetSockAddr = new InetSocketAddress(InetAddress.getByName(IP), PORT);
				socket = new Socket();

				// 지정된 주소로 접속 시도 (3초동안)
				socket.connect(inetSockAddr, 3000);
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//접속이 되면 실행//
			if (socket.isBound()) { //소켓이 바인딩 되었는지 체크함.
				//입출력 스트림 생성//
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
