package yahtzee_project;

import java.util.ArrayList;

public class Room {
	int roomNum;
	String roomName;
	ArrayList<User> userList;	//������
	User roomMaker;				//����
	GameRoomUI gameRoom;
	
	public Room() {
		userList = new ArrayList<User>();
	}
	
	public Room(String message) {
		userList = new ArrayList<User>();
		setRoomName(message);
	}
	
	public String toProtocol() {	//��������, ��ū "/", ���ȣ/���̸�
		return roomNum + "/" + roomName;
	}

	public int getRoomNum() {
		return roomNum;
	}
	
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	
	public String getRoomName() {
		return roomName;
	}

	public ArrayList<User> getUserArray() {
		return userList;
	}

	public User getMaker() {
		return roomMaker;
	}

	public void setMaker(User user) {
		this.roomMaker = user;
	}


}
