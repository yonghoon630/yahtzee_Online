package yahtzee_project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class LoginUI extends JFrame implements Runnable {

	boolean confirm = false;
	JTextField idField;
	JPasswordField passField;
	JButton loginBtn, signUpBtn, ipBtn, bgmBtn, ruleBtn;
	Client client;
	DBJoin jdb;

	private Player player;
	BufferedImage img = null;	
	//생성자//
	public LoginUI(Client client) {

		setTitle("로그인");
		this.client = client;
		//mp.start();
		
		setBounds(100, 100, 510, 530);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(null);
		
		try {
			img = ImageIO.read(new File("img/backimg2.png"));
					
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"이미지 불러오기 실패");
		}				
		
		JPanel panel = new JPanel(){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			g.drawImage(img,0, 0, null);
		}};
		panel.setBounds(0, 0, 510, 530);
		
		panel.setLayout(null);
		
		JButton idLabel = new JButton(new ImageIcon("img/idtext.png"));
		idLabel.setBorderPainted(false);
		idLabel.setContentAreaFilled(false);
		idLabel.setBounds(160, 255, 57, 15);		
		panel.add(idLabel);		
		
		JButton passLabel = new JButton(new ImageIcon("img/passtext.png"));
		passLabel.setBorderPainted(false);
		passLabel.setContentAreaFilled(false);
		passLabel.setBounds(160, 286, 57, 15);
		panel.add(passLabel);
		
		idField = new JTextField();
		idField.setBounds(229, 252, 116, 21);
		panel.add(idField);
		idField.setColumns(10);
		
		passField = new JPasswordField();
		passField.setBounds(229, 283, 116, 21);
		panel.add(passField);
		passField.setColumns(10);
		
		loginBtn = new JButton(new ImageIcon("img/logintext.png"));
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				msgSummit();
				new Thread(new Runnable() {  // 버튼 누르면 효과음 추가 //
					@Override
					public void run(){ 
						try{
							 AudioInputStream stream = AudioSystem.getAudioInputStream(new File("bgm/clicksound.wav")); 
				              Clip clip = AudioSystem.getClip(); 
				              clip.open( stream ); 
				              clip.start();
						}catch(Exception e) { 
				              e.printStackTrace(); 
				          } 
					}
				}).start();
			}
		});
		
		loginBtn.setBounds(150, 311, 97, 23);
		panel.add(loginBtn);
		
		signUpBtn = new JButton(new ImageIcon("img/signtext.png"));
		signUpBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//회원가입
				jdb = new DBJoin();
				new Thread(new Runnable() {     // 버튼 누르면 효과음 추가 //
					@Override
					public void run(){
						try{
							 AudioInputStream stream = AudioSystem.getAudioInputStream(new File("bgm/clicksound.wav")); 
				              Clip clip = AudioSystem.getClip(); 
				              clip.open( stream ); 
				              clip.start();
						}catch(Exception e) { 
				              e.printStackTrace(); 
				          } 
					}
				}).start();
			}
		});
		
		signUpBtn.setBounds(249, 311, 97, 23);
		panel.add(signUpBtn);
		
	
		ruleBtn = new JButton(new ImageIcon("img/rulebtimg.png"));  //룰 버튼 추가 //		
		ruleBtn.setBorderPainted(false);
		ruleBtn.setContentAreaFilled(false);
	    
		ruleBtn.addActionListener(new ActionListener(){		 //룰버튼 누르면 룰페이지로 이동//
			@Override
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==ruleBtn){					
					rulepage p = new rulepage(); // 버튼 누르면  추가한 rulepage 클래스 뜨게 //
					new Thread(new Runnable() {
						@Override
						public void run(){  // 버튼 누르면 효과음 추가 //
							try{
								 AudioInputStream stream = AudioSystem.getAudioInputStream(new File("bgm/clicksound.wav")); 
					              Clip clip = AudioSystem.getClip(); 
					              clip.open( stream ); 
					              clip.start();
							}catch(Exception e) { 
					              e.printStackTrace(); 
					          } 
						}
					}).start();
				}
			}
		});
		
		ruleBtn.setBounds(350, 350, 50, 50);
		panel.add(ruleBtn);			
		
		JButton ipLabel = new JButton(new ImageIcon("img/iptext.png"));
		ipLabel.setBorderPainted(false);
		ipLabel.setContentAreaFilled(false);
		ipLabel.setBounds(150, 210, 78, 15);
		panel.add(ipLabel);
		
		ipBtn = new JButton("서버IP 입력");
		ipBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ServerIP serverIP = new ServerIP(LoginUI.this);
				setVisible(false);
			}
		});
		ipBtn.setBounds(230, 206, 113, 23);
		ipBtn.setBackground(Color.WHITE);
		panel.add(ipBtn);
		add(panel);
		setLocationRelativeTo(null);
		setVisible(true);
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
			@SuppressWarnings("deprecation")
			public void run() {
				//소켓생성				
				if(client.serverAccess()) {
					try {
						//로그인정보 (아이디+패스워드) 전송
						client.getDos().writeUTF(User.LOGIN + "/" + idField.getText() + "/" + passField.getText());
					} catch (IOException e1) {
						e1.printStackTrace(); 
						
					}
				}				
			}	
		}).start();
		
		//msgSummit() end
	}
	class playBGM extends Thread { //원래 클래스 안에 있던걸 스레드로 
		public  playBGM() {
			try {
			
				FileInputStream fis = new FileInputStream("bgm/bgm2.mp3");
				BufferedInputStream bis = new BufferedInputStream(fis);
				player = new Player(bis);				
			
			} catch(Exception e) {
				e.printStackTrace();
			}	
		}
		public void run(){
			try {
				player.play();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
