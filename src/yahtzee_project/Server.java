package yahtzee_project;

import java.awt.GridLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class Server extends JFrame implements Runnable {

	static final int PORT = 9999;
	ServerSocket serverSocket;
	Socket socket;
	
	ArrayList<User> userArray; //서버 접속자
	ArrayList<Room> roomArray; //서버가 연 채팅방
	
	DataOutputStream dos;
	DataInputStream dis;
	
	JTextArea jta;
	JPanel panel;
	
	
	//생성자 (UI)//
	public Server() {
		userArray = new ArrayList<User>();
		roomArray = new ArrayList<Room>();
		
		setTitle("Server");
		setBounds(100, 100, 500, 500);
		
		jta = new JTextArea();
		panel = new JPanel();
		
		panel.setLayout(new GridLayout(1,2));
		jta.setEditable(false);
		jta.setLineWrap(true);
		
		JScrollPane jsp = new JScrollPane(jta); // 텍스트에어리어에 스크롤 추가
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(jsp);
		jta.setText("Server Start...1\n");
		
		add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	
	//MAIN//
	public static void main(String[] args) {
		Server server = new Server();
		Thread thread = new Thread(server);
		thread.start();
	}
	
	
	//스레드//
	public void run() {
		//클라이언트 대기
		
		//서버소켓 생성//
		try {
			InetAddress addr = InetAddress.getLocalHost(); // 로컬호스트 주소
			serverSocket = new ServerSocket(PORT); // 서버소켓 생성
			jta.append(PORT + "번 포트로 정상적으로 소켓이 생성되었습니다.\n" + "현재 열린 서버의 IP 주소는 " 
							+ addr.getHostAddress().toString() + "입니다. \n");
		} catch(IOException e) {
			e.printStackTrace();
			jta.append("서버 소켓 생성에러\n");
		}
		
		
		
		while (true) {
			socket = null;
			dis = null;
			dos = null;
			try {
				// 무한반복, 입출력 에러가 나거나 프로그램이 종료될 때까지 실행
				socket = serverSocket.accept(); // 클라이언트 접속 대기
				jta.append("클라이언트 " + socket.getInetAddress().getHostAddress()	+ "가 접속되었습니다.\n");

			} catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				jta.append("클라이언트 접속에러\n");
			}
			try {
				// 스트림 객체 생성
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				try {
					dis.close();
					dos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					jta.append("스트림 해제에러\n");
				}
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					jta.append("소켓 해제에러\n");
				}
				jta.append("스트림 생성에러\n");
			}
			User person = new User(dis, dos); // 가명의 사용자 객체 생성
			person.setIP(socket.getInetAddress().getHostName()); // 아이피주소 설정 부여

			Thread thread = new Thread(new ServerThread(jta, person, userArray,	roomArray));
			thread.start(); // 스레드 시작
		
		}
	}
}
