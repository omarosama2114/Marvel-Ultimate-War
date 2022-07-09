package views;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import engine.*;
import model.abilities.*;
import model.world.*;

@SuppressWarnings("serial")
public class ChampionsView extends JFrame implements ActionListener {

	private static int numberOfChoices;

	private ArrayList<JButton> championButtons;

	private Player player1;
	private Player player2;
	private static Game game;

//	private JPanel buttonPanel;
//	private JPanel extra;
	private JPanel playersInfoPanel;
//	private JPanel board;
//	private JPanel player1Info;
//	private JPanel player2Info;
	private JPanel championChoices;
	private JPanel lowbar;

//	private JLabel championInfo;
	private JLabel choosingInfo;
	private static JLabel background;

	private JButton exit;
	private JButton next;
//	private JButton playButton;
//	private JButton startChoosing;

	public ChampionsView() throws IOException {
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();
		
		player1 = new Player(mainView.getP1());
		player2 = new Player(mainView.getP2());

		Game.loadAbilities("Abilities.csv");
		Game.loadChampions("Champions.csv");

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Marvel Ultimate War");
		
		background = new JLabel();
		background.setLayout(new BorderLayout());
		ImageIcon bg = new ImageIcon("src//space2.jpg");

		Image scaleImageBg = bg.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		bg = new ImageIcon(scaleImageBg);

		background.setIcon(bg);

		ImageIcon logo = new ImageIcon("src/logo.png");
		this.setIconImage(logo.getImage());
		
//		ImageIcon choiceBg = new ImageIcon("src/championChoices.png");
//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		int width = (int) screenSize.getWidth();
//		int height = (int) screenSize.getHeight();
//		Image scaleImage = choiceBg.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
//		choiceBg.
		/*
		 * JPanel background = new JPanel() {
		 * 
		 * @Override protected void paintComponent(Graphics g) {
		 * super.paintComponent(g); g.drawImage(scaleImage, 0, 0, null); } };
		 */
		// JLabel b =new JLabel();
		// b.setIcon((Icon) scaleImage);

		// this.setComponentZOrder(background, 0);
		// this.setContentPane(b);
		
		displayChampionChoices();
		lowbar = new JPanel();
		lowbar.setLayout(new FlowLayout());
		lowbar.setBackground(Color.black);
		lowbar.setOpaque(false);
		
		exit = new JButton();
		exit.setForeground(Color.white);
		exit.setContentAreaFilled(false);
		exit.setText("EXIT");
		exit.setFocusable(false);
		exit.addActionListener(this);
		exit.setOpaque(false);
		
		next = new JButton();
		next.setForeground(Color.white);
		next.setContentAreaFilled(false);
		next.setText("NEXT");
		next.setFocusable(false);
		next.addActionListener(this);
		next.setOpaque(false);
		
		lowbar.add(exit);
		lowbar.add(next);
		background.add(lowbar, BorderLayout.SOUTH);
		
		
		this.add(background);
		this.setVisible(true);
		JOptionPane.showMessageDialog(null, mainView.getP1() + " Please Choose your Leader", "Team 1",
				JOptionPane.PLAIN_MESSAGE);
		this.validate();
	}

	public String getChampionType(Champion c) {
		if (c instanceof Hero)
			return "Hero";
		else if (c instanceof AntiHero)
			return "Anti-Hero";
		else
			return "Villain";
	}

	@SuppressWarnings("static-access")
	public void displayChampionChoices() {
		championChoices = new JPanel();
		championChoices.setPreferredSize(new Dimension(500, 500));
		championChoices.setLayout(new GridLayout(3, 5, 5, 5));
		championChoices.setOpaque(false);
		championButtons = new ArrayList<>();
		playersInfoPanel = new JPanel();
		choosingInfo = new JLabel("<html>" + "Choose a team consisting of 3 Champions" + "<br>" + "<br>" + "<br>"
				+ "The first Champion will be your leader" + "<br>" + "<br>" + "<br>"
				+ "Each Leader has a special leader ability" + "<br>" + "<br>" + "<br>"
				+ "Leader ability can be used once per game" + "</html>");
		choosingInfo.setForeground(Color.red);
		choosingInfo.setOpaque(false);
		playersInfoPanel.add(choosingInfo);
		playersInfoPanel.setOpaque(false);

		for (Champion c : game.getAvailableChampions()) {

			String y = "";
			for (Ability x : c.getAbilities()) {
				y += "<br>" + x.getName();

			}

			String x = "<html>" + "  " + c.getName() + "<br>" + "<br>" + getChampionType(c) + "<br>" + "Health: "
					+ c.getMaxHP() + "<br>" + "Mana: " + c.getMana() + "<br>" + "Action Points:"
					+ c.getMaxActionPointsPerTurn() + "<br>" + "Speed: " + c.getSpeed() + "<br>" + "Range: "
					+ c.getAttackRange() + "<br>" + "Damage: " + c.getAttackDamage() + "<br>" + "<br>" + "Abilities:"
					+ y + "</html>";

			String pic = "src\\" + c.getName() + ".png";

			ImageIcon playImage = new ImageIcon(pic);
			Image scaleImage = playImage.getImage().getScaledInstance(120, 120, Image.SCALE_DEFAULT);
			playImage = new ImageIcon(scaleImage);
			JButton bt = new JButton();
			bt.setOpaque(false);
			bt.setContentAreaFilled(false);
			bt.setForeground(Color.white);
//			bt.setBorderPainted(false);
//			bt.setFocusPainted(false);
			
			bt.setActionCommand(c.getName());
			bt.setSize(50, 50);
			bt.setText(x);
			bt.setFont(new Font("Mantessa", Font.BOLD, 10));
			bt.setIcon(playImage);
			
			bt.setFocusable(false);

			bt.addActionListener(this);
			championChoices.add(bt);
			championButtons.add(bt);
		}
		background.add(championChoices, BorderLayout.CENTER);
	//	background.add(playersInfoPanel, BorderLayout.EAST);

	}

	public void chooseChampions(Champion x) throws IOException {
		if (numberOfChoices % 2 == 0) {
			if (numberOfChoices == 0)
				player1.setLeader(x);
			player1.getTeam().add(x);
		} else if (numberOfChoices % 2 != 0) {

			if (numberOfChoices == 1)
				player2.setLeader(x);

			player2.getTeam().add(x);
		}
		numberOfChoices++;
		if (numberOfChoices == 6) {
			game = new Game(player2, player1);
		}
	}

	public static Game getGame() {
		return game;
	}

	public static void main(String[] args) throws IOException {
		new ChampionsView();
	}

	@SuppressWarnings({ "unused", "static-access" })
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		if (e.getSource() == exit) {
			System.exit(0);
		} else if (e.getSource() == next) {
			if (numberOfChoices < 6) {
				JOptionPane.showMessageDialog(this, "You haven't filled your champions", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				try {
					BoardView b = new BoardView();
				} catch (FontFormatException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.dispose();
			}
		} else if (numberOfChoices < 6) {
			if (numberOfChoices == 0)
				JOptionPane.showMessageDialog(null, mainView.getP2() + " Please Choose your Leader", "Team 2",
						JOptionPane.PLAIN_MESSAGE);
			else if (numberOfChoices % 2 == 0)
				JOptionPane.showMessageDialog(null, mainView.getP2() + " Please Choose your Champion", "Team 2",
						JOptionPane.PLAIN_MESSAGE);
			else if (numberOfChoices % 2 != 0 && numberOfChoices != 5)
				JOptionPane.showMessageDialog(null, mainView.getP1() + " Please Choose your Champion", "Team 1",
						JOptionPane.PLAIN_MESSAGE);
			int championIndex = championButtons.indexOf(btn);
			Champion temp = game.getAvailableChampions().get(championIndex);
			btn.setEnabled(false);
			btn.setBackground(Color.gray);
			try {
				chooseChampions(temp);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
