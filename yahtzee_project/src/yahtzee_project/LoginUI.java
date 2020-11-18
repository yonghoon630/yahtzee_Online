package yahtzee_project;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javazoom.jl.player.Player;

public class LoginUI extends JFrame implements Runnable {

	boolean confirm = false;
	JTextField idField;
	JPasswordField passField;
	JButton loginBtn, signUpBtn, ipBtn, bgmBtn;
	Client client;
	DBJoin jdb;

	private Player player;

	
	//생성자//
	public LoginUI(Client client) {

		setTitle("로그인");
		this.client = client;
		
		setBounds(100, 100, 335, 218);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 295, 160);
		add(panel);
		panel.setLayout(null);
		
		JLabel idLabel = new JLabel("아이디");
		idLabel.setBounds(60, 55, 57, 15);
		panel.add(idLabel);
		
		JLabel passLabel = new JLabel("비밀번호");
		passLabel.setBounds(60, 86, 57, 15);
		panel.add(passLabel);
		
		idField = new JTextField();
		idField.setBounds(129, 52, 116, 21);
		panel.add(idField);
		idField.setColumns(10);
		
		passField = new JPasswordField();
		passField.setBounds(129, 83, 116, 21);
		panel.add(passField);
		passField.setColumns(10);
		
		loginBtn = new JButton("로그인");
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				msgSummit();
			}
		});
		
		loginBtn.setBounds(50, 111, 97, 23);
		panel.add(loginBtn);
		
		signUpBtn = new JButton("회원가입");
		signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//회원가입
				jdb = new DBJoin();
			}
		});
		signUpBtn.setBounds(149, 111, 97, 23);
		panel.add(signUpBtn);
		
		JLabel ipLabel = new JLabel("Server IP");
		ipLabel.setBounds(60, 10, 78, 15);
		panel.add(ipLabel);
		
		ipBtn = new JButton("서버IP 입력");
		ipBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerIP serverIP = new ServerIP(LoginUI.this);
				setVisible(false);
			}
		});
		ipBtn.setBounds(130, 6, 113, 23);
		ipBtn.setBackground(Color.WHITE);
		panel.add(ipBtn);
		
		setVisible(true);
	}
	
	public void playBGM() {
		try {
			
			FileInputStream fis = new FileInputStream("bgm/bgm2.mp3");
			BufferedInputStream bis = new BufferedInputStream(fis);
			player = new Player(bis);
			run();
			
		} catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void run() {
		try {
			player.play();
			//player.close(); bgm종료
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//승인메서드
	public void msgSummit() {
		System.out.println("msgSummit");
		
		new Thread(new Runnable() {
			public void run() {
				//소켓생성
				/*
				if(client.serverAccess()) {
					try {
						//로그인정보 (아이디+패스워드) 전송
						client.getDos().writeUTF(User.LOGIN + "/" + idField.getText() + "/" + passField.getText());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				*/
				
			}	
		}).start();
		
		//msgSummit() end
	}

}
