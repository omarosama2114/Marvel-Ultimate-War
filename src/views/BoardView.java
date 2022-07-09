package views;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;

import javax.swing.*;

import engine.*;
import engine.PriorityQueue;
import exceptions.*;
import model.abilities.*;
import model.effects.Effect;
import model.world.*;

@SuppressWarnings("serial")
public class BoardView extends JFrame implements ActionListener, KeyListener, MouseListener {

	private static boolean attackKeyPressed;
	private static boolean directionalAbilityKeyPressed;
	private static boolean single_targetAbilityKeyPressed;
	private static Ability cur = null;
	
	public Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("src//Avengeance-E1zj.otf")).deriveFont(75f);
	public Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("src//Avengeance-E1zj.otf")).deriveFont(45f);

	private JPanel lowbar;
	private JPanel board;
	private JPanel sideInfo;
	private JPanel prev;

	private JLabel sideText;
	private JLabel instruction;
	private static JLabel background;

//	private static Timer timer;
	private static Timer abilityTimer;
	private static TimerTask abilityTask;

	private JButton exit;
//	private JButton next;
	private JButton howToPlay;
	private JButton previous;
	private JButton[][] grid;

	private Game game = ChampionsView.getGame();
	private Player player1 = game.getFirstPlayer();
	private Player player2 = game.getSecondPlayer();
	private Object[][] gameBoard = game.getBoard();

	public BoardView() throws FontFormatException, IOException {

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Marvel Ultimate War");
		ImageIcon logo = new ImageIcon("src/logo.png");
		this.setIconImage(logo.getImage());
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(customFont1);
	    ge.registerFont(customFont2);

		this.addKeyListener(this);
		this.addMouseListener(this);

		display();

		this.setVisible(true);
		this.validate();

	}

	public String getTurn() {
		PriorityQueue p = new PriorityQueue(game.getTurnOrder().size());
//		p=game.getTurnOrder();
		PriorityQueue temp = new PriorityQueue(6);
		while (!game.getTurnOrder().isEmpty()) {
			Champion c = (Champion) game.getTurnOrder().remove();
			p.insert(c);
			temp.insert(c);
		}
		while (!temp.isEmpty()) {
			game.getTurnOrder().insert(temp.remove());
		}
		String s = "<html>";
		int size = p.size();
		for (int i = 0; i < size; i++) {
			String team = "";
			if (player1.getTeam().contains(p.peekMin()))
				team =  player1.getName();
			else
				team =  player2.getName();
			s += "turn " + (i + 1) + ": " + ((Champion) p.remove()).getName() + " ( " + team + " )" + "<br>";
		}
		return s;

	}

	public void updateBoard() throws FontFormatException, IOException {
		gameBoard = game.getBoard();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();

		background.remove(sideInfo);
		sideInfo = new JPanel();
		sideInfo.setLayout(new FlowLayout());
		sideInfo.setBackground(Color.black);
		sideInfo.setOpaque(false);

		Champion cur = game.getCurrentChampion();
		String f1 = "Not Used";
		String f2 = "Not Used";
		if (game.isFirstLeaderAbilityUsed())
			f1 = "Used";
		if (game.isSecondLeaderAbilityUsed())
			f2 = "Used";
		String s =  "<html>" + player1.getName() + "'s Leader Ability: " + f1 + "<br>" + player2.getName()
		+ "'s Leader Ability: " + f2;
		s += "<br>" + "------------------------------------------------------------------" + "<br>" + getChampionInfo(cur);
		s += "<br>" + "------------------------------------------------------------------" + "<br>" + getTurn();
		sideText = new JLabel(s);
		sideText.setForeground(Color.white);
		sideText.setOpaque(false);
		Font f = new Font(Font.DIALOG, Font.BOLD,10);
		sideText.setFont(f);
		sideInfo.add(sideText);
		background.add(sideInfo, BorderLayout.EAST);

		if (game.checkGameOver() == player1 || game.checkGameOver() == player2) {
			
			ArrayList<Champion> c = new ArrayList<>();
			c = game.checkGameOver().getTeam();
			
			JLabel over = new JLabel();
			String s2 = game.checkGameOver().getName().toUpperCase();
			String s1 = "CONGRATS   " + s2 + "   YOU WON";
			JLabel win = new JLabel(s1);

			/*lowbar = new JPanel();
			lowbar.setLayout(new FlowLayout());
			lowbar.setOpaque(false);

			exit = new JButton();
			exit.setForeground(Color.white);
			exit.setContentAreaFilled(false);
			exit.setText("EXIT");
			exit.setFocusable(false);
			exit.addActionListener(this);
			exit.setOpaque(false);
			lowbar.add(exit);
			
			over.setLayout(new BorderLayout());
			over.add(lowbar, BorderLayout.SOUTH);*/
			
			over.setLayout(null);
			Font font1 = new Font(Font.MONOSPACED, Font.BOLD, 40);
			win.setForeground(Color.white);
			win.setFont(customFont1);
//			win.setOpaque(false);

			win.setBounds(width / 4, 30, width, height / 4);
			over.add(win);

			JLabel champ1 = new JLabel();
			JLabel champ2 = new JLabel();
			JLabel champ3 = new JLabel();

			String c1 = c.get(0).getName();
			String c2 = c.get(1).getName();
			String c3 = c.get(2).getName();

			ImageIcon i1 = new ImageIcon("src//" + c1 + ".png");
			ImageIcon i2 = new ImageIcon("src//" + c2 + ".png");
			ImageIcon i3 = new ImageIcon("src//" + c3 + ".png");

			Image scaleImage1 = i1.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT);
			i1 = new ImageIcon(scaleImage1);

			Image scaleImage2 = i2.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT);
			i2 = new ImageIcon(scaleImage2);

			Image scaleImage3 = i3.getImage().getScaledInstance(350, 350, Image.SCALE_DEFAULT);
			i3 = new ImageIcon(scaleImage3);

			champ1.setIcon(i1);
			champ1.setBounds(90, height / 4 + 100, (width / 3) - 50, height / 3 + 100);

			champ2.setIcon(i2);
			champ2.setBounds((width / 3) + 90, height / 4 + 100, (width / 3) - 50, height / 3 + 100);

			champ3.setIcon(i3);
			champ3.setBounds(2 * (width / 3) + 90, height / 4 + 100, (width / 3) - 50, height / 3 + 100);

			ImageIcon v = new ImageIcon("src//space2.jpg");

			Image scaleImageV = v.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_DEFAULT);
			v = new ImageIcon(scaleImageV);

			over.setIcon(v);

			over.add(champ1);
			over.add(champ2);
			over.add(champ3);
			
			howToPlay.setVisible(false);
			board.setVisible(false);
			sideInfo.setVisible(false);
			background.add(over);

		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (gameBoard[i][j] instanceof Champion) {
					String x = "src\\" + ((Champion) gameBoard[i][j]).getName() + ".png";
					String desc = "<html>"  + ((Champion) gameBoard[i][j]).getCurrentHP() + " HP" + "</html>";
					ImageIcon champion = new ImageIcon(x);
					Image scaleImage = champion.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT);
					champion = new ImageIcon(scaleImage);
					grid[i][j].setIcon(champion);
					grid[i][j].setText(desc);
					grid[i][j].setVerticalTextPosition(JButton.BOTTOM);
					grid[i][j].setHorizontalTextPosition(JButton.CENTER);
					grid[i][j].setActionCommand(((Champion) gameBoard[i][j]).getName());
//					Color custom = new Color(176, 224, 230);
					if ((Champion) gameBoard[i][j] == game.getCurrentChampion()) {
						grid[i][j].setOpaque(true);
						grid[i][j].setBackground(Color.gray);
					}
					if (player1.getTeam().contains((Champion) gameBoard[i][j])) {
						grid[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					} else if (player2.getTeam().contains((Champion) gameBoard[i][j])) {
						grid[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
					}
				} else if (gameBoard[i][j] instanceof Cover) {
					String x = "src\\cover.png";
					String desc = "<html>"  + ((Cover) gameBoard[i][j]).getCurrentHP() + " HP" + "</html>";
					ImageIcon cover = new ImageIcon(x);
					Image scaleImage = cover.getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT);
					cover = new ImageIcon(scaleImage);
					grid[i][j].setIcon(cover);
					grid[i][j].setText(desc);
					grid[i][j].setVerticalTextPosition(JButton.BOTTOM);
					grid[i][j].setHorizontalTextPosition(JButton.CENTER);
				} else if (gameBoard[i][j] == null) {
					grid[i][j].setIcon(null);
					grid[i][j].setText(null);
					grid[i][j].setOpaque(false);
					grid[i][j].setBorder(UIManager.getBorder("Button.border"));
				}
			}
		}
	}

	public void displayBoard() {

		board = new JPanel();
		board.addMouseListener(this);
		board.setPreferredSize(new Dimension(500, 500));
		board.setLayout(new GridLayout(5, 5));
		board.setBackground(Color.black);
		board.setOpaque(false);

		sideInfo = new JPanel();
		sideInfo.setLayout(new FlowLayout());
		sideInfo.setBackground(Color.black);
		sideInfo.setOpaque(false);

		Champion c = game.getCurrentChampion();
		String f1 = "Not Used";
		String f2 = "Not Used";
		if (game.isFirstLeaderAbilityUsed())
			f1 = "Used";
		if (game.isSecondLeaderAbilityUsed())
			f2 = "Used";
		String s = "<html>" + player1.getName() + "'s Leader Ability: " + f1 + "<br>" + player2.getName()
		+ "'s Leader Ability: " + f2;
		s += "<br>" + "------------------------------------------------------------------" + "<br>" + getChampionInfo(c);
		s += "<br>" + "------------------------------------------------------------------" + "<br>" + getTurn();
		sideText = new JLabel(s);
		sideText.setForeground(Color.white);
		Font f = new Font(Font.DIALOG, Font.BOLD,10);
		//sideText.setFont(getFont());
		sideInfo.add(sideText);
		sideText.setFont(f);

		background.add(sideInfo, BorderLayout.EAST);

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				JButton b = new JButton();
				b.addActionListener(this);
				b.addMouseListener(this);
				b.setBackground(Color.white);
				b.setFocusable(false);
				b.setOpaque(false);
				b.setContentAreaFilled(false);
				b.setForeground(Color.white);
				board.add(b);
				grid[i][j] = b;
				grid[i][j].setOpaque(false);
			}
		}

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				if (gameBoard[i][j] instanceof Champion) {
					String x = "src\\" + ((Champion) gameBoard[i][j]).getName() + ".png";
					String desc = "<html>"  + ((Champion) gameBoard[i][j]).getCurrentHP() + " HP" + "</html>";
					ImageIcon champion = new ImageIcon(x);
					Image scaleImage = champion.getImage().getScaledInstance(130, 130, Image.SCALE_DEFAULT);
					champion = new ImageIcon(scaleImage);
					grid[i][j].setIcon(champion);
					grid[i][j].setText(desc);
					grid[i][j].setVerticalTextPosition(JButton.BOTTOM);
					grid[i][j].setHorizontalTextPosition(JButton.CENTER);
					grid[i][j].setActionCommand(((Champion) gameBoard[i][j]).getName());
//					Color custom = new Color(176, 224, 230);
					if ((Champion) gameBoard[i][j] == game.getCurrentChampion()) {
						grid[i][j].setOpaque(true);
						grid[i][j].setBackground(Color.gray);
					}
					if (player1.getTeam().contains((Champion) gameBoard[i][j])) {
						grid[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
					} else if (player2.getTeam().contains((Champion) gameBoard[i][j])) {
						grid[i][j].setBorder(BorderFactory.createLineBorder(Color.GREEN, 2));
					}
				} else if (gameBoard[i][j] instanceof Cover) {
					String x = "src\\cover.png";
					ImageIcon cover = new ImageIcon(x);
					String desc = "<html>"  + ((Cover) gameBoard[i][j]).getCurrentHP() + " HP" + "</html>";
					Image scaleImage = cover.getImage().getScaledInstance(110, 110, Image.SCALE_DEFAULT);
					cover = new ImageIcon(scaleImage);
					grid[i][j].setIcon(cover);
					grid[i][j].setText(desc);
					grid[i][j].setVerticalTextPosition(JButton.BOTTOM);
					grid[i][j].setHorizontalTextPosition(JButton.CENTER);
				}
			}
		}

		background.add(board, BorderLayout.CENTER);

	}

	public void display() {

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();

		background = new JLabel();
		background.setLayout(new BorderLayout());
		ImageIcon bg = new ImageIcon("src//space2.jpg");

		Image scaleImageBg = bg.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		bg = new ImageIcon(scaleImageBg);

		background.setIcon(bg);

		lowbar = new JPanel();
		lowbar.setLayout(new FlowLayout());
		lowbar.setOpaque(false);

		howToPlay = new JButton();
		howToPlay.setForeground(Color.white);
		howToPlay.setContentAreaFilled(false);
		howToPlay.setText("How To Play");
		howToPlay.setFocusable(false);
		howToPlay.addActionListener(this);
		howToPlay.setOpaque(false);

		exit = new JButton();
		exit.setForeground(Color.white);
		exit.setContentAreaFilled(false);
		exit.setText("EXIT");
		exit.setFocusable(false);
		exit.addActionListener(this);
		exit.setOpaque(false);

		/*
		 * next = new JButton(); next.setForeground(Color.white);
		 * next.setContentAreaFilled(false); next.setText("NEXT");
		 * next.setFocusable(false); next.addActionListener(this);
		 * next.setOpaque(false);
		 */

		lowbar.add(exit);
//		lowbar.add(next);
		lowbar.add(howToPlay);
		background.add(lowbar, BorderLayout.SOUTH);

		grid = new JButton[5][5];
		displayBoard();

		this.add(background);
	}

	public void howToPlay() {

		sideInfo.setVisible(false);
		lowbar.setVisible(false);
		board.setVisible(false);
		background.setVisible(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();

		instruction = new JLabel();

		ImageIcon v = new ImageIcon("src//instructions.jpg");
		Image scaleImageV = v.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		v = new ImageIcon(scaleImageV);

		instruction.setIcon(v);
		instruction.setLayout(new BorderLayout());

		previous = new JButton();
		previous.setOpaque(false);
		previous.setContentAreaFilled(false);
		previous.setForeground(Color.white);
		previous.setText("PREVIOUS");
		previous.setFocusable(false);
		previous.addActionListener(this);

		prev = new JPanel();
		prev.setLayout(new FlowLayout());
		prev.setOpaque(false);
		prev.setVisible(true);
		prev.add(previous);
		instruction.add(prev, BorderLayout.SOUTH);

		this.add(instruction);

	}

	public String getChampionInfo(Champion c) {
		String y = "";
		String z = "";
		String f = "";
		for (Ability x : c.getAbilities()) {
			if (x instanceof HealingAbility) {
				z = "Healing Ability" + "<br>" + "Heal Amount: " + ((HealingAbility) x).getHealAmount();
			} else if (x instanceof DamagingAbility) {
				z = "Damaging Ability" + "<br>" + "Damage Amount: " + ((DamagingAbility) x).getDamageAmount();
			} else if (x instanceof CrowdControlAbility) {
				z = "Crowd Control Ability" + "<br>" + "Effect Name : "
						+ ((CrowdControlAbility) x).getEffect().getName();
			}
			y += "<br>" + x.getName() + ":" + "<br>" + "Base Cooldown: " + x.getBaseCooldown() + "<br>"
					+ "Current Cooldown: " + x.getCurrentCooldown() + "<br>" + "Cast Range: " + x.getCastRange()
					+ "<br>" + "Mana Cost: " + x.getManaCost() + "<br>" + "Required Action Points: "
					+ x.getRequiredActionPoints() + "<br>" + "Cast Area: " + x.getCastArea() + "<br>" + z + "<br>";

		}

		if (c.getAppliedEffects().size() == 0)
			f += "No Effects";
		else {
			for (Effect x : c.getAppliedEffects()) {
				f += x.getName() + " for " + x.getDuration() + " turns" + "<br>";
			}
		}

		String o = "";
		if (game.getFirstPlayer().getLeader() == c || game.getSecondPlayer().getLeader() == c)
			o = " Yes";
		else
			o = " No";

		String x = "<html>" + "Current Champion:  " + "<br>" + "<br>" + c.getName() + "<br>" + "<br>"
				+ getChampionType(c) + "<br>" + "Health: " + c.getCurrentHP() + "<br>" + "Mana: " + c.getMana() + "<br>"
				+ "Action Points: " + c.getCurrentActionPoints() + "<br>" + "Speed: " + c.getSpeed() + "<br>" + "Range: "
				+ c.getAttackRange() + "<br>" + "Damage: " + c.getAttackDamage() + "<br>" + "<br>" + "Abilities:"
				+ "<br>" + y + "<br>" + "Applied Effects:" + "<br>" + f + "<br>" + "<br>" + "Leader :" + o;

		return x;
	}

	public String getChampionType(Champion c) {
		if (c instanceof Hero)
			return "Hero";
		else if (c instanceof AntiHero)
			return "Anti-Hero";
		else
			return "Villain";
	}

	public static void main(String[] args) throws FontFormatException, IOException {
		new BoardView();
	}

	public void moveGUI(KeyEvent e) throws FontFormatException, IOException {

		switch (e.getKeyCode()) {

		case 'W':
			try {
				game.move(Direction.DOWN);
			} catch (UnallowedMovementException e1) {
				JOptionPane.showMessageDialog(null, "You can't move up!", "Unallowed Movement",
						JOptionPane.WARNING_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				JOptionPane.showMessageDialog(null, "You don't have enough points! Please press 'E' to end turn",
						"Not Enough Resources", JOptionPane.WARNING_MESSAGE);
			}
			updateBoard();
			break;
		case 'A':
			try {
				game.move(Direction.LEFT);
			} catch (UnallowedMovementException e1) {
				JOptionPane.showMessageDialog(null, "You can't move left!", "Unallowed Movement",
						JOptionPane.WARNING_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				JOptionPane.showMessageDialog(null, "You don't have enough points! Please press 'E' to end turn",
						"Not Enough Resources", JOptionPane.WARNING_MESSAGE);
			}
			updateBoard();
			break;
		case 'S':
			try {
				game.move(Direction.UP);
			} catch (UnallowedMovementException e1) {
				JOptionPane.showMessageDialog(null, "You can't move down!", "Unallowed Movement",
						JOptionPane.WARNING_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				JOptionPane.showMessageDialog(null, "You don't have enough points! Please press 'E' to end turn",
						"Not Enough Resources", JOptionPane.WARNING_MESSAGE);
			}
			updateBoard();
			break;
		case 'D':
			try {
				game.move(Direction.RIGHT);
			} catch (UnallowedMovementException e1) {
				JOptionPane.showMessageDialog(null, "You can't move right!", "Unallowed Movement",
						JOptionPane.WARNING_MESSAGE);
			} catch (NotEnoughResourcesException e1) {
				JOptionPane.showMessageDialog(null, "You don't have enough points! Please press 'E' to end turn",
						"Not Enough Resources", JOptionPane.WARNING_MESSAGE);
			}
			updateBoard();
			break;
		}
	}

	@SuppressWarnings("static-access")
	public void attackGUI(KeyEvent e) throws NotEnoughResourcesException, ChampionDisarmedException, InvalidTargetException {
		Timer attackTimer = new Timer();
		TimerTask attackTask = new TimerTask() {

			@SuppressWarnings("static-access")
			@Override
			public void run() {

				grid[game.getAttackedX()][game.getAttackedY()].setBackground(Color.white);
				grid[game.getAttackedX()][game.getAttackedY()].setOpaque(false);
				try {
					updateBoard();
				} catch (FontFormatException | IOException e) {

				}
				
				

			}

		};

		if (e.getKeyCode() == 'X') {

			Champion c = game.getCurrentChampion();
			ImageIcon playImage = new ImageIcon("src//" + c.getName() + ".png");
			Image scaleImage = playImage.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
			playImage = new ImageIcon(scaleImage);
			attackKeyPressed = true;
			JOptionPane.showMessageDialog(null, "Choose Attack Direction", "Attack", 0, playImage);

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT && attackKeyPressed) {

			game.attack(Direction.LEFT);
			if (game.getAttackedX() != -1) {
				grid[game.getAttackedX()][game.getAttackedY()].setOpaque(true);
				grid[game.getAttackedX()][game.getAttackedY()].setBackground(Color.RED);
				attackTimer.schedule(attackTask, 500);
			}
			attackKeyPressed = false;

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && attackKeyPressed) {

			game.attack(Direction.RIGHT);
			if (game.getAttackedX() != -1) {
				grid[game.getAttackedX()][game.getAttackedY()].setOpaque(true);
				grid[game.getAttackedX()][game.getAttackedY()].setBackground(Color.RED);
				attackTimer.schedule(attackTask, 500);
			}
			attackKeyPressed = false;

		} else if (e.getKeyCode() == KeyEvent.VK_UP && attackKeyPressed) {

			game.attack(Direction.DOWN);
			if (game.getAttackedX() != -1) {
				grid[game.getAttackedX()][game.getAttackedY()].setOpaque(true);
				grid[game.getAttackedX()][game.getAttackedY()].setBackground(Color.RED);
				attackTimer.schedule(attackTask, 500);
			}
			attackKeyPressed = false;

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && attackKeyPressed) {

			game.attack(Direction.UP);
			if (game.getAttackedX() != -1) {
				grid[game.getAttackedX()][game.getAttackedY()].setOpaque(true);
				grid[game.getAttackedX()][game.getAttackedY()].setBackground(Color.RED);
				attackTimer.schedule(attackTask, 500);
			}
			attackKeyPressed = false;

		}

	}

	@SuppressWarnings("static-access")
	public void castAbilityGUI(KeyEvent e)
			throws AbilityUseException, NotEnoughResourcesException, CloneNotSupportedException {

		abilityTimer = new Timer();
		abilityTask = new TimerTask() {

			@Override
			public void run() {
				for (int i = 0; i < game.getAttackedXs().size(); i++) {
					grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setBackground(Color.white);
					grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setOpaque(false);
				}
				try {
					updateBoard();
				} catch (FontFormatException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};

		Champion c = game.getCurrentChampion();
		ImageIcon playImage = new ImageIcon("src//" + c.getName() + ".png");
		Image scaleImage = playImage.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		playImage = new ImageIcon(scaleImage);

		if (e.getKeyCode() == 'C') {
			int length = c.getAbilities().size();
			String[] abilities = new String[length];
			for (int i = 0; i < length; i++) {
				if (c.getAbilities().get(i).getCurrentCooldown() == 0)
					abilities[i] = c.getAbilities().get(i).getName();
				else
					abilities[i] = "Cooling Down";
			}

			int a = JOptionPane.showOptionDialog(null, "Choose the ability you want to cast", "Cast Ability",
					JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, playImage, abilities, abilities[0]);
			//System.out.println(a);

			if (a != -1) {
				cur = c.getAbilities().get(a);

				if (cur.getCastArea() == AreaOfEffect.DIRECTIONAL) {
					directionalAbilityKeyPressed = true;
					JOptionPane.showMessageDialog(null, "Choose Casting Direction", "Ability Cast", 0, playImage);

				}

				else if (cur.getCastArea() == AreaOfEffect.SINGLETARGET) {
					single_targetAbilityKeyPressed = true;
					JOptionPane.showMessageDialog(null, "Click on the cell you want to cast an ability on",
							"Cast Ability", JOptionPane.INFORMATION_MESSAGE);
				}

				else if (cur.getCastArea() == AreaOfEffect.SELFTARGET || cur.getCastArea() == AreaOfEffect.TEAMTARGET
						|| cur.getCastArea() == AreaOfEffect.SURROUND) {
					game.castAbility(cur);
					for (int k = 0; k < game.getAttackedXs().size(); k++) {
						grid[game.getAttackedXs().get(k)][game.getAttackedYs().get(k)].setOpaque(true);
						grid[game.getAttackedXs().get(k)][game.getAttackedYs().get(k)].setBackground(Color.BLUE);
					}
					abilityTimer.schedule(abilityTask, 500);
				}
			}
		}

		else if (e.getKeyCode() == KeyEvent.VK_LEFT && directionalAbilityKeyPressed) {
			game.castAbility(cur, Direction.LEFT);
			for (int i = 0; i < game.getAttackedXs().size(); i++) {
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setOpaque(true);
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setBackground(Color.BLUE);
			}
			abilityTimer.schedule(abilityTask, 500);
			directionalAbilityKeyPressed = false;

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && directionalAbilityKeyPressed) {
			game.castAbility(cur, Direction.RIGHT);
			for (int i = 0; i < game.getAttackedXs().size(); i++) {
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setOpaque(true);
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setBackground(Color.BLUE);
			}
			abilityTimer.schedule(abilityTask, 500);
			directionalAbilityKeyPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_UP && directionalAbilityKeyPressed) {
			game.castAbility(cur, Direction.DOWN);
			for (int i = 0; i < game.getAttackedXs().size(); i++) {
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setOpaque(true);
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setBackground(Color.BLUE);
			}
			abilityTimer.schedule(abilityTask, 500);
			directionalAbilityKeyPressed = false;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && directionalAbilityKeyPressed) {
			game.castAbility(cur, Direction.UP);
			for (int i = 0; i < game.getAttackedXs().size(); i++) {
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setOpaque(true);
				grid[game.getAttackedXs().get(i)][game.getAttackedYs().get(i)].setBackground(Color.BLUE);
			}
			abilityTimer.schedule(abilityTask, 500);
			directionalAbilityKeyPressed = false;
		}
	}

	public void castLeaderAbilityGUI(KeyEvent e)
			throws LeaderNotCurrentException, LeaderAbilityAlreadyUsedException, CloneNotSupportedException, FontFormatException, IOException {
		if (e.getKeyCode() == 'L') {
			game.useLeaderAbility();
			updateBoard();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(0);
		} else if (e.getSource() == howToPlay)
			howToPlay();
		else if (e.getSource() == previous) {
			prev.setVisible(false);
			instruction.setVisible(false);
			display();
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if (!(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) && attackKeyPressed) {
			if (game.getCurrentChampion().getCurrentActionPoints() != 0) {
				JOptionPane.showMessageDialog(null, "You Should press one of the arrow keys", "Invalid Key",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		} else if (!(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP
				|| e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
				&& directionalAbilityKeyPressed) {
			if (game.getCurrentChampion().getCurrentActionPoints() >= cur.getRequiredActionPoints()) {
				JOptionPane.showMessageDialog(null, "You Should press one of the arrow keys", "Invalid Key",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		try {
			attackGUI(e);
		} catch (NotEnoughResourcesException e1) {
			JOptionPane.showMessageDialog(null, "You don't have enough points! Please press 'E' to end turn",
					"Not Enough Resources", JOptionPane.WARNING_MESSAGE);
			attackKeyPressed = false;
		} catch (ChampionDisarmedException e1) {
			JOptionPane.showMessageDialog(null, "You are disarmed!", "Unallowed Attack", JOptionPane.WARNING_MESSAGE);
			attackKeyPressed = false;
		} catch (InvalidTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			moveGUI(e);
		} catch (FontFormatException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		try {
			castAbilityGUI(e);
		} catch (NotEnoughResourcesException e1) {

			JOptionPane.showMessageDialog(null, "You don't have enough points! Please press 'E' to end turn",
					"Not Enough Resources", JOptionPane.WARNING_MESSAGE);

		} catch (AbilityUseException e1) {

			JOptionPane.showMessageDialog(null, "You can not cast an ability!", "Unallowed Ability Cast",
					JOptionPane.WARNING_MESSAGE);

		} catch (CloneNotSupportedException e1) {
		}

		try {
			try {
				castLeaderAbilityGUI(e);
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (LeaderNotCurrentException e1) {

			JOptionPane.showMessageDialog(null, "The current champion is not the Leader", "Leader Not Current",
					JOptionPane.WARNING_MESSAGE);

		} catch (LeaderAbilityAlreadyUsedException e1) {

			JOptionPane.showMessageDialog(null, "You have already used your Leader Ability",
					"Unallowed Leader Ability Cast", JOptionPane.WARNING_MESSAGE);

		} catch (CloneNotSupportedException e1) {
		}

		switch (e.getKeyCode()) {

		case 'E':
			int i = game.getCurrentChampion().getLocation().x;
			int j = game.getCurrentChampion().getLocation().y;
			grid[i][j].setOpaque(false);
			game.endTurn();
			try {
				updateBoard();
			} catch (FontFormatException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		case 27:
			System.exit(0);
			break;

		}

	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@SuppressWarnings("static-access")
	@Override
	public void mouseClicked(MouseEvent e) {
		if (single_targetAbilityKeyPressed)
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (gameBoard[i][j] instanceof Champion || gameBoard[i][j] instanceof Cover) {
						if (e.getSource() == grid[i][j])
							try {
								game.castAbility(cur, i, j);
								for (int k = 0; k < game.getAttackedXs().size(); k++) {
									grid[game.getAttackedXs().get(k)][game.getAttackedYs().get(k)]
											.setOpaque(true);
									grid[game.getAttackedXs().get(k)][game.getAttackedYs().get(k)]
											.setBackground(Color.BLUE);
								}
								abilityTimer.schedule(abilityTask, 500);
								single_targetAbilityKeyPressed = false;
							} catch (NotEnoughResourcesException e1) {
								JOptionPane.showMessageDialog(null,
										"You don't have enough resources! Please press 'E' to end turn",
										"Not Enough Resources", JOptionPane.WARNING_MESSAGE);
								single_targetAbilityKeyPressed = false;
							} catch (AbilityUseException e1) {
								JOptionPane.showMessageDialog(null, "You Can not Cast an ability!",
										"Unallowed Ability Cast", JOptionPane.WARNING_MESSAGE);
								single_targetAbilityKeyPressed = false;
							} catch (CloneNotSupportedException e1) {
							} catch (InvalidTargetException e1) {
								JOptionPane.showMessageDialog(null, "You can't cast an ability on this target",
										"Invalid Target", JOptionPane.WARNING_MESSAGE);
								single_targetAbilityKeyPressed = false;
							}
					}
				}
			}
		else {
			// timer = new Timer();
			// TimerTask task = new TimerTask() {

			// @Override
			// public void run() {
			for (int i = 0; i < 5; i++) {
				for (int j = 0; j < 5; j++) {
					if (gameBoard[i][j] instanceof Champion) {
						Champion c = (Champion) gameBoard[i][j];
						if (e.getSource() == grid[i][j]) {
							String x = getChampionInfo(c);
							x += "</html>";
							JOptionPane.showMessageDialog(null, x, "Champion's Details", JOptionPane.PLAIN_MESSAGE);
						}
					}

				}
			}

			// }

			// };
//			timer.schedule(task, 1000);

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
//		timer.cancel();
	}

}
