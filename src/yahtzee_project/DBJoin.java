package yahtzee_project;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DBJoin extends JFrame implements MouseListener {


	private JTextField nameTf, idTf, pwTf, birthTf;
	private JButton confBtn, resetBtn;
		
	BufferedImage img = null;
	
	//생성자(UI)//
	public DBJoin() {		
		
		setTitle("회원가입");
		setBounds(150, 200, 300, 260);
		setResizable(false);
		setLayout(null);
		
		try {
			img = ImageIO.read(new File("img/dbback.png"));
					
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"이미지 불러오기 실패");
		}		
		
		JPanel panel = new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(img,0, 0, null);
		}};
		
		panel.setBounds(12, 10, 262, 200);
		panel.setLayout(null);
		add(panel);
		
		JLabel nameLabel = new JLabel("NAME");
		JLabel idLabel = new JLabel("I D");
		JLabel pwLabel = new JLabel("P W");
		JLabel birthLabel = new JLabel("생년월일");
		
		nameLabel.setBounds(15, 20, 75, 25);
		idLabel.setBounds(15, 50, 75, 25);
		pwLabel.setBounds(15, 80, 75, 25);
		birthLabel.setBounds(15, 110, 75, 25);
		
		panel.add(nameLabel);
		panel.add(idLabel);
		panel.add(pwLabel);
		panel.add(birthLabel);
		
		nameTf = new JTextField(20);
		idTf = new JTextField(20);
		pwTf = new JTextField(20);
		birthTf = new JTextField("ex)961227",20);
		
		nameTf.setBounds(100, 20, 150, 25);
		idTf.setBounds(100, 50, 150, 25);
		pwTf.setBounds(100, 80, 150, 25);
		birthTf.setBounds(100, 110, 150, 25);
		birthTf.addMouseListener(this);
		
		panel.add(nameTf);
		panel.add(idTf);
		panel.add(pwTf);
		panel.add(birthTf);

		confBtn = new JButton("확인");
		confBtn.addMouseListener(this);
		resetBtn = new JButton("리셋");
		resetBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Thread(new Runnable() { // 리셋 버튼 누르면 효과음 추가 //
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
				nameTf.setText("");
				idTf.setText("");
				pwTf.setText("");
				birthTf.setText("");
			}
		});
		
		confBtn.setBounds(140, 150, 80, 25);
		resetBtn.setBounds(50, 150, 80, 25);
		
		panel.add(confBtn);
		panel.add(resetBtn);
		
		setLocationRelativeTo(null);
		
		setVisible(true);
		
	}
	
	
	Statement stmt = null;
	ResultSet rs = null;
	String url = "jdbc:mysql://localhost/yahtzee?characterEncoding=UTF-8&serverTimezone=UTC";
	String sql = null;
	Connection conn = null;
	JOptionPane msgBox = new JOptionPane();
	
	@Override
	public void mouseClicked(MouseEvent e) {
		new Thread(new Runnable() { // 확인 버튼 누르면 효과음 추가 //
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
		
		if(e.getSource().equals(birthTf)) {
			birthTf.setText("");
		}
		
		try {
			Class.forName("com.mysql.jdbc.Driver"); //자동연결됨
			System.out.println("드라이브적재");
			conn = DriverManager.getConnection(url, "root", "root");
			System.out.println("연결됨");
			stmt = conn.createStatement();
			
			if(e.getSource().equals(confBtn)) {
				sql = "SELECT * FROM member_table WHERE id='"+idTf.getText()+"'";
				rs = stmt.executeQuery(sql);
				//회원가입 양식 오류검출
				if(idTf.getText().isEmpty()==true||pwTf.getText().isEmpty()==true||
						nameTf.getText().isEmpty()==true||birthTf.getText().isEmpty()==true) {
					msgBox.showMessageDialog(null, "비어있는 칸이 존재합니다.", "에러", JOptionPane.ERROR_MESSAGE);
				} else if(birthTf.getText().length()!=6) {
					msgBox.showMessageDialog(null, "생년월일 서식이 잘못되었습니다. \n ex)990101", "에러", JOptionPane.ERROR_MESSAGE);
				} else if(rs.next()==true) {
					msgBox.showMessageDialog(null, "해당 아이디는 사용 불가합니다.", "에러", JOptionPane.ERROR_MESSAGE);
				} else {
					sql = "INSERT INTO member_table VALUES ('"+0+"','"+idTf.getText()+"','"
							+pwTf.getText()+"','"+nameTf.getText()+"','"+birthTf.getText()+"');";
					stmt.executeUpdate(sql);
					msgBox.showMessageDialog(null, "가입성공!", "회원가입", JOptionPane.INFORMATION_MESSAGE);
					dispose();
					dbClose();
				}
			}
		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("DBJoin Error!");
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	
	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
}
