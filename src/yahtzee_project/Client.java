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

	static int PORT = 9999;			//서버포트
	static String IP = "192.168.0.163";	//서버IP
	Socket socket;					//소켓
	User user;						//사용자
	
	LoginUI login;					
	WaitRoomUI waitRoom;		
	
		
	DataInputStream dis;
	DataOutputStream dos;
	
	boolean ready = false;
	
	//생성자//
	public Client() {
		login = new LoginUI(this);			//로그인 UI 실행
		
		Thread thread = new Thread(this);	//스레드 실행
		thread.start();
	}
	
	
	//MAIN//
	public static void main(String[] args) {
		System.out.println("Client Start!");
		new Client();
	}
	
	
	//스레드//
	public void run() {
		//
		//소켓 통신 시작//
		//
		while(!ready) {	//준비되지 않았을 때, 스레드를 재우면서 대기
			try {
				Thread.sleep(10);		
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//사용자가 객체 생성 및 아이피 설정
		user = new User(dis, dos);
		user.setIP(socket.getInetAddress().getHostAddress());
		
		//메시지 읽음
		while (true) {
			try {
				String receivedMsg = dis.readUTF(); //메시지 받기(대기)
				dataParsing(receivedMsg); //메시지 해석
			} catch(IOException e) {
				e.printStackTrace();
				try {						//오류 발생시 유저데이터 입출력 스트림 및 소켓 닫기
					user.getDis().close();
					user.getDos().close();
					socket.close();
					break;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		//프로그램 종료됨
		System.out.println("서버프로그램 종료됨");
		waitRoom.dispose();	
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

	
	//데이터 해석
	public synchronized void dataParsing(String data) {
		StringTokenizer token = new StringTokenizer(data, "/"); //토큰생성
		String protocol = token.nextToken(); //토큰으로 분리된 String
		String id, pw, rNum, nickName, rName, msg, result;
		System.out.println("받은 데이터: "+data);
		
		//프로토콜
		switch (protocol) {
		case User.LOGIN: 
			result = token.nextToken();
			if(result.equals("OK")) {
				System.out.println("로그인 성공");
				nickName = token.nextToken();
				login(nickName);
			}
		}		
	}
	
	//login
	private void login(String nickName) {
		//로그인정보 가져오기
		user.setId(login.idField.getText());
		user.setNickName(login.idField.getText());
		
		//loginUI 닫고 waitRoomUI 열기
		login.dispose();
		waitRoom = new WaitRoomUI(Client.this);
		
	}
	
	public DataOutputStream getDos() {
		return dos;
	}

}
