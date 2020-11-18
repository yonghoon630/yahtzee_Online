package yahtzee_project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class rulepage extends JFrame{   // 룰에 대한 설명 프레임 추가 //
	BufferedImage img = null;	
	public rulepage(){
		setLayout(null);
		setBounds(0,0,720,540);	
		setResizable(false);				
		
		try {
			img = ImageIO.read(new File("img/ruleimg.png"));
					
		}catch (IOException e){
			JOptionPane.showMessageDialog(null,"이미지 불러오기 실패");
		}
		JPanel rulepanel= new JPanel(){
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(img,0, 0, null);
		}};
			
		rulepanel.setBounds(0, 0, 740, 540);
		//panel.setBackground(Color.WHITE);
		rulepanel.setLayout(null);
		add(rulepanel);
		setLocationRelativeTo(null);
		setVisible(true);
	}					
}
