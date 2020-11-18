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
	
	ArrayList<User> userArray; //���� ������
	ArrayList<Room> roomArray; //������ �� ä�ù�
	
	DataOutputStream dos;
	DataInputStream dis;
	
	JTextArea jta;
	JPanel panel;
	
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
		
		JScrollPane jsp = new JScrollPane(jta); // �ؽ�Ʈ���� ��ũ�� �߰�
		jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add(jsp);
		jta.setText("Server Start...1\n");
		
		add(panel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
	public void run() {
		//Ŭ���̾�Ʈ ���
		
		//�������� ����//
		try {
			InetAddress addr = InetAddress.getLocalHost(); // ����ȣ��Ʈ �ּ�
			serverSocket = new ServerSocket(PORT); // �������� ����
			jta.append(PORT + "�� ��Ʈ�� ���������� ������ �����Ǿ����ϴ�.\n" + "���� ���� ������ IP �ּҴ� " 
							+ addr.getHostAddress().toString() + "�Դϴ�. \n");
		} catch(IOException e) {
			e.printStackTrace();
			jta.append("���� ���� ��������\n");
		}
		
		/*
		while (true) {
			socket = null;
			dis = null;
			dos = null;
			try {
				// ���ѹݺ�, ����� ������ ���ų� ���α׷��� ����� ������ ����
				socket = serverSocket.accept(); // Ŭ���̾�Ʈ ���� ���
				jta.append("Ŭ���̾�Ʈ " + socket.getInetAddress().getHostAddress()	+ "�� ���ӵǾ����ϴ�.\n");

			} catch (IOException e) {
				e.printStackTrace();
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				jta.append("Ŭ���̾�Ʈ ���ӿ���\n");
			}
			try {
				// ��Ʈ�� ��ü ����
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
				try {
					dis.close();
					dos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					jta.append("��Ʈ�� ��������\n");
				}
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					jta.append("���� ��������\n");
				}
				jta.append("��Ʈ�� ��������\n");
			}
			User person = new User(dis, dos); // ������ ����� ��ü ����
			person.setIP(socket.getInetAddress().getHostName()); // �������ּ� ���� �ο�

			Thread thread = new Thread(new ServerThread(jta, person, userArray,	roomArray));
			thread.start(); // ������ ����
		*/
	}
}
