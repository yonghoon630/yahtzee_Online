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

	
	//������//
	public LoginUI(Client client) {

		setTitle("�α���");
		this.client = client;
		
		setBounds(100, 100, 335, 218);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(12, 10, 295, 160);
		add(panel);
		panel.setLayout(null);
		
		JLabel idLabel = new JLabel("���̵�");
		idLabel.setBounds(60, 55, 57, 15);
		panel.add(idLabel);
		
		JLabel passLabel = new JLabel("��й�ȣ");
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
		
		loginBtn = new JButton("�α���");
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				msgSummit();
			}
		});
		
		loginBtn.setBounds(50, 111, 97, 23);
		panel.add(loginBtn);
		
		signUpBtn = new JButton("ȸ������");
		signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//ȸ������
				jdb = new DBJoin();
			}
		});
		signUpBtn.setBounds(149, 111, 97, 23);
		panel.add(signUpBtn);
		
		JLabel ipLabel = new JLabel("Server IP");
		ipLabel.setBounds(60, 10, 78, 15);
		panel.add(ipLabel);
		
		ipBtn = new JButton("����IP �Է�");
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
			//player.close(); bgm����
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//���θ޼���
	public void msgSummit() {
		System.out.println("msgSummit");
		
		new Thread(new Runnable() {
			public void run() {
				//���ϻ���
				/*
				if(client.serverAccess()) {
					try {
						//�α������� (���̵�+�н�����) ����
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
