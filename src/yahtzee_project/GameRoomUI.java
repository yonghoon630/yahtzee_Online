package yahtzee_project;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class GameRoomUI extends JFrame implements ActionListener {
	
	Client client;
	Room room;
	
	
	JPanel diePanel;
	JPanel scorePanel;
	JButton rollBtn, diceBtn, scoreBtn;
	JLabel text;
	
	GameRoomUI(Client client, Room room) {
		this.client = client;
		this.room = room;
		
		setTitle("Game : "+room.toProtocol());
		
		init();
		
	}
	
	private void init() {
		
		setBounds(150, 5, 660, 750);
		setLayout(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu gameMenu = new JMenu("게임");
		gameMenu.addActionListener(this);
		menuBar.add(gameMenu);
		JMenuItem startGame = new JMenuItem("게임시작");
		startGame.addActionListener(this);
		gameMenu.add(startGame);
		JMenuItem exitGame = new JMenuItem("방 나가기");
		exitGame.addActionListener(this);
		gameMenu.add(exitGame);
		JMenuItem helpGame = new JMenuItem("도움말");
		helpGame.addActionListener(this);
		gameMenu.add(helpGame);
		
		JMenu setMenu = new JMenu("설정");
		setMenu.addActionListener(this);
		menuBar.add(setMenu);
		JMenuItem maker = new JMenuItem("프로그램 정보");
		maker.addActionListener(this);
		setMenu.add(maker);
		
		diePanel = new JPanel();
		diePanel.setBounds(0, 0, 650, 160);
		diePanel.setBackground(Color.BLUE);
		diePanel.setLayout(null);
		add(diePanel);
		
		Dice[] dice = new Dice[5];
		Dice[] sortedDice = new Dice[5];
		
		JButton dice1, dice2, dice3, dice4, dice5;
		dice1 = new JButton("1");
		dice2 = new JButton("2");
		dice3 = new JButton("3");
		dice4 = new JButton("4");
		dice5 = new JButton("5");
		
		dice1.setBounds(50, 43, 70, 70);
		dice1.setBackground(Color.WHITE);
		dice2.setBounds(125, 43, 70, 70);
		dice2.setBackground(Color.WHITE);
		dice3.setBounds(200, 43, 70, 70);
		dice3.setBackground(Color.WHITE);
		dice4.setBounds(275, 43, 70, 70);
		dice4.setBackground(Color.WHITE);
		dice5.setBounds(350, 43, 70, 70);
		dice5.setBackground(Color.WHITE);
		
		diePanel.add(dice1);
		diePanel.add(dice2);
		diePanel.add(dice3);
		diePanel.add(dice4);
		diePanel.add(dice5);
		
		JButton rollBtn = new JButton("roll");
		rollBtn.setBounds(440, 33, 150, 90);
		rollBtn.setBackground(Color.WHITE);
		diePanel.add(rollBtn);
		
		scorePanel = new JPanel();
		
		
		setVisible(true);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
