package yahtzee_project;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class User {

	String IP;
	String nickName;
	String ID;
	String PW;
	boolean online = false;
	ArrayList<Room> useRooms;
	
	DataInputStream dis;
	DataOutputStream dos;
	
	//Game game;
	Socket socket;
	BufferedReader input;
	PrintWriter output;
	User other1, other2;
	
	//Protocols
	public static final String LOGIN = "LI";
	
	
	public User(String id, String nick) {
		this.ID = id;
		this.nickName = nick;
	}
	public User(DataInputStream dis, DataOutputStream dos) {
		this.dis = dis;
		this.dos = dos;
		useRooms = new ArrayList<Room>();
	}
	public String toStringforLogin() {
		return ID + "/" + PW;
	}

	public String toProtocol() {
		return ID + "/" + nickName;
	}

	public String toString() {
		return nickName + "(" + ID + ")";
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getId() {
		return ID;
	}

	public void setId(String id) {
		this.ID = id;
	}

	public String getPw() {
		return PW;
	}

	public void setPw(String pw) {
		this.PW = pw;
	}

	public DataInputStream getDis() {
		return dis;
	}

	public void setDis(DataInputStream dis) {
		this.dis = dis;
	}

	public DataOutputStream getDos() {
		return dos;
	}

	public void setDos(DataOutputStream dos) {
		this.dos = dos;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public ArrayList<Room> getRoomArray() {
		return useRooms;
	}

	public void setRooms(ArrayList<Room> rooms) {
		this.useRooms = rooms;
	}
	
	
	
	
	
	
}
