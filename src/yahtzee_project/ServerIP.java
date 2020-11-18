package yahtzee_project;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServerIP extends JFrame{

	private JTextField ipField;
	private JButton confBtn;
	private LoginUI loginUI;
	
	//생성자//
	public ServerIP(LoginUI loginUI) {	
		this.loginUI = loginUI;
		init();
	}
	
	//초기화
	private void init() {
		setTitle("서버 아이피 주소 입력");
		setBounds(100, 100, 300, 85);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(12, 10, 255, 25);
		add(panel);
		
		ipField = new JTextField();
		ipField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					loginUI.ipBtn.setText(ipField.getText());
					loginUI.setVisible(true);
					loginUI.idField.requestFocus();
					dispose();
				}
			}
		});
		ipField.setText("192.168.0.163");
		ipField.setBounds(0, 0, 175, 25);
		panel.add(ipField);
		
		confBtn = new JButton("확인");
		confBtn.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				loginUI.ipBtn.setText(ipField.getText());
				loginUI.setVisible(true);
				loginUI.idField.requestFocus();
				dispose();
			}
		});
		confBtn.setBounds(185, 0, 70, 25);
		panel.add(confBtn);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
}
