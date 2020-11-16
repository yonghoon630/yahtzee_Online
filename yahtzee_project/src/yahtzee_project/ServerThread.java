package yahtzee_project;

import java.util.ArrayList;

import javax.swing.JTextArea;

public class ServerThread {

	ArrayList<User> userArray;
	ArrayList<Room> roomArray;
	User user;
	
	JTextArea jta;
	boolean online = false;
	
	
	
	public ServerThread() {
		
	}
	
	
	//데이터 분석/토큰
	public synchronized void dataParsing(String data) {
		
	}
}
