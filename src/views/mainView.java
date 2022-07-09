package views;

import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class mainView extends JFrame implements ActionListener, MouseListener {

	private static String p1;
	private static String p2;
	
	public Font customFont1 = Font.createFont(Font.TRUETYPE_FONT, new File("src//Avengeance-E1zj.otf")).deriveFont(75f);
	public Font customFont2 = Font.createFont(Font.TRUETYPE_FONT, new File("src//Avengeance-E1zj.otf")).deriveFont(45f);

	private JPanel buttonPanel;
//	private JPanel extra;
//	private JPanel lowbar;
	private JPanel prev;
//	private JPanel description;

//	private JLabel descText;
	private JLabel mainScreen;
	private JLabel instruction;

	private JButton playButton;
	private JButton exit;
//	private JButton next;
	private JButton howToPlay;
	private JButton previous;

	public mainView() throws UnsupportedAudioFileException, IOException, LineUnavailableException, FontFormatException {

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    ge.registerFont(customFont1);
	    ge.registerFont(customFont2);

		ImageIcon logo = new ImageIcon("src/logo.png");
		this.setIconImage(logo.getImage());
		this.setTitle("Marvel Ultimate War");

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();

		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);
		buttonPanel.setLayout(null);

		mainScreen = new JLabel();

		// imagelabel.setLayout(new FlowLayout());
		ImageIcon v = new ImageIcon("src//Marvel.png");
		Image scaleImageV = v.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		v = new ImageIcon(scaleImageV);

		mainScreen.setIcon(v);
		mainScreen.setLayout(new BorderLayout());

		playButton = new JButton();
		playButton.setText("PLAY");
		//Font f1 = new Font(Font.MONOSPACED, Font.BOLD, 50);
		playButton.setForeground(Color.red);
		playButton.setFont(customFont1);

		playButton.setOpaque(false);
		playButton.addActionListener(this);
		playButton.setFocusable(false);

		playButton.setBorder(null);
		playButton.setContentAreaFilled(false);
		playButton.setBorderPainted(false);
		playButton.setFocusPainted(false);

		playButton.setBounds((int) ((width / 2) - 118), height / 2 - 100, 200, 150);
		buttonPanel.add(playButton);

		/*
		 * next = new JButton(); next.setBackground(Color.red); Font f2 = new
		 * Font(Font.MONOSPACED, Font.BOLD, 25); next.setText("NEXT"); next.setFont(f2);
		 * next.setFocusable(false); next.addActionListener(this);
		 * next.setOpaque(false); next.setBorder(null);
		 * next.setContentAreaFilled(false); next.setForeground(Color.red);
		 * next.setBorderPainted(false); next.setFocusPainted(false);
		 * next.setBounds(width/2 - 70,height/2 + 50,100,30); buttonPanel.add(next);
		 */

		exit = new JButton();
		exit.setBackground(Color.red);
		//Font f3 = new Font(Font.MONOSPACED, Font.BOLD, 25);
		exit.setText("EXIT");
		exit.setFont(customFont2);

		exit.setFocusable(false);
		exit.addActionListener(this);
		exit.setOpaque(false);

		exit.setBorder(null);
		exit.setForeground(Color.red);
		exit.setBorderPainted(false);
		exit.setContentAreaFilled(false);
		exit.setFocusPainted(false);

		exit.setBounds((int) ((width / 2) - 100), (int) ((height / 2) + (height / 10.8)), 150, 80);
		buttonPanel.add(exit);

		howToPlay = new JButton();
		howToPlay.setBackground(Color.red);
		//Font f4 = new Font(Font.MONOSPACED, Font.BOLD, 25);
		howToPlay.setText("How to play");
		howToPlay.setFont(customFont2);

		howToPlay.setFocusable(false);
		howToPlay.addActionListener(this);
		howToPlay.setOpaque(false);

		howToPlay.setBorder(null);
		howToPlay.setForeground(Color.red);
		howToPlay.setContentAreaFilled(false);
		howToPlay.setBorderPainted(false);
		howToPlay.setFocusPainted(false);

		howToPlay.setBounds((width / 2) - 135, (int) ((height / 2) + (height / 7.2)), 220, 150);
		buttonPanel.add(howToPlay);

		buttonPanel.setBounds(width / 8, height / 5, 100, 100);

		mainScreen.add(buttonPanel);
		this.add(mainScreen);

		/*
		 * Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); int
		 * height = (int) screenSize.getHeight(); int width = (int)
		 * screenSize.getWidth(); ImageIcon image = new ImageIcon("src//Marvel.png");
		 * Image scaledImage = image.getImage().getScaledInstance(width, height,
		 * Image.SCALE_DEFAULT); image = new ImageIcon(scaledImage); JLabel label = new
		 * JLabel(image); this.add(label); //label.setPreferredSize(new
		 * Dimension(width,height)); //label.setIcon(image); this.add(label);
		 * this.setLayout(new BorderLayout());
		 * this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 * this.setTitle("SuperHeroes Showdown");
		 * 
		 * ImageIcon logo = new ImageIcon("src/logo.png");
		 * this.setIconImage(logo.getImage());
		 * 
		 * playButton = new JButton(); playButton.setText("PLAY");
		 * playButton.setOpaque(false); playButton.setSize(new Dimension(500, 500));
		 * playButton.addActionListener(this); playButton.setFocusable(false);
		 * 
		 * /* ImageIcon playImage = new ImageIcon("src/Start.jpg");
		 * playButton.setIcon(playImage); playButton.setMargin(new Insets(0, 0, 0, 0));
		 * playButton.setBackground(Color.red);
		 * 
		 * buttonPanel = new JPanel(); buttonPanel.setOpaque(false);
		 * buttonPanel.setPreferredSize(new Dimension(500, 500));
		 * buttonPanel.setBackground(Color.black); buttonPanel.add(playButton);
		 * 
		 * Font f1 = new Font(Font.MONOSPACED, Font.BOLD, 22); descText = new
		 * JLabel("<html>" +
		 * "SuperHeros Showdon is a Marvel themed Superhero Fighting game" + "<br>" +
		 * "Pick your team and take on other enemy teams to become the Champion!");
		 * descText.setForeground(Color.RED); descText.setFont(f1);
		 * 
		 * extra = new JPanel(); extra.setOpaque(false); extra.setPreferredSize(new
		 * Dimension(200, 180)); extra.setBackground(Color.black); extra.add(descText);
		 * 
		 * this.add(buttonPanel, BorderLayout.CENTER); this.add(extra,
		 * BorderLayout.NORTH);
		 * 
		 * lowbar = new JPanel(); lowbar.setOpaque(false); lowbar.setLayout(new
		 * FlowLayout()); lowbar.setBackground(Color.black);
		 * 
		 * howToPlay = new JButton(); howToPlay.setBackground(Color.red);
		 * howToPlay.setText("How To Play"); howToPlay.setFocusable(false);
		 * howToPlay.addActionListener(this);
		 * 
		 * exit = new JButton(); exit.setBackground(Color.red); exit.setText("EXIT");
		 * exit.setFocusable(false); exit.addActionListener(this);
		 * 
		 * next = new JButton(); next.setBackground(Color.red); next.setText("NEXT");
		 * next.setFocusable(false); next.addActionListener(this);
		 * 
		 * lowbar.add(exit); lowbar.add(next); lowbar.add(howToPlay); this.add(lowbar,
		 * BorderLayout.SOUTH);
		 */
		playMusic();
		this.setVisible(true);
		this.validate();
	}

	public void EnterNames() throws IOException {

		p1 = JOptionPane.showInputDialog("Enter first player's Name:");
		
		if(p1.length() > 10 ) {
			JOptionPane.showMessageDialog(this, "Player names should have a maximum of 10 characters", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		p2 = JOptionPane.showInputDialog("Enter Second player's Name:");
		
		if(p2.length() > 10 ) {
			JOptionPane.showMessageDialog(this, "Player names should have a maximum of 10 characters", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (p1 == null || p2 == null || p1.length() == 0 || p2.length() == 0) {
			JOptionPane.showMessageDialog(this, "You haven't entered your name", "Warning",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		new ChampionsView();

		this.dispose();
	}

	public void howToPlay() {

		buttonPanel.setVisible(false);
		mainScreen.setVisible(false);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int height = (int) screenSize.getHeight();
		int width = (int) screenSize.getWidth();

		instruction = new JLabel();

		// imagelabel.setLayout(new FlowLayout());
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

		/*
		 * description = new JPanel(); description.setLayout(new FlowLayout());
		 * description.setBackground(Color.red); description.setVisible(true);
		 * description.setPreferredSize(new Dimension(1200, 1000));
		 * instruction.add(description, BorderLayout.NORTH);
		 */

		this.add(instruction);

	}

	public static String getP1() {
		return p1;
	}

	public static String getP2() {
		return p2;
	}

	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException, FontFormatException {
		new mainView();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == playButton) {
			try {
				EnterNames();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource() == exit) {
			System.exit(0);
		} /*
			 * else if (e.getSource() == next) { if (p1 == null || p2 == null) {
			 * JOptionPane.showMessageDialog(this, "You haven't entered your name",
			 * "Warning", JOptionPane.WARNING_MESSAGE); } else { try { new ChampionsView();
			 * } catch (IOException e1) { // TODO Auto-generated catch block
			 * e1.printStackTrace(); } this.dispose(); } }
			 */ else if (e.getSource() == howToPlay)
			howToPlay();
		else if (e.getSource() == previous) {
			prev.setVisible(false);
			instruction.setVisible(false);


			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int height = (int) screenSize.getHeight();
			int width = (int) screenSize.getWidth();

			buttonPanel = new JPanel();
			buttonPanel.setOpaque(false);
			buttonPanel.setLayout(null);

			mainScreen = new JLabel();
			ImageIcon v = new ImageIcon("src//Marvel.png");
			Image scaleImageV = v.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
			v = new ImageIcon(scaleImageV);

			mainScreen.setIcon(v);
			mainScreen.setLayout(new BorderLayout());

			playButton = new JButton();
			playButton.setText("PLAY");
			//Font f1 = new Font(Font.MONOSPACED, Font.BOLD, 50);
			playButton.setForeground(Color.red);
			playButton.setFont(customFont1);

			playButton.setOpaque(false);
			playButton.addActionListener(this);
			playButton.setFocusable(false);

			playButton.setBorder(null);
			playButton.setContentAreaFilled(false);
			playButton.setBorderPainted(false);
			playButton.setFocusPainted(false);

			playButton.setBounds((int) ((width / 2) - 118), height / 2 - 100, 200, 150);
			buttonPanel.add(playButton);

			/*
			 * next = new JButton(); next.setBackground(Color.red); Font f2 = new
			 * Font(Font.MONOSPACED, Font.BOLD, 25); next.setText("NEXT"); next.setFont(f2);
			 * next.setFocusable(false); next.addActionListener(this);
			 * next.setOpaque(false); next.setBorder(null);
			 * next.setContentAreaFilled(false); next.setForeground(Color.red);
			 * next.setBorderPainted(false); next.setFocusPainted(false);
			 * next.setBounds(width/2 - 70,height/2 + 50,100,30); buttonPanel.add(next);
			 */

			exit = new JButton();
			exit.setBackground(Color.red);
			//Font f3 = new Font(Font.MONOSPACED, Font.BOLD, 25);
			exit.setText("EXIT");
			exit.setFont(customFont2);

			exit.setFocusable(false);
			exit.addActionListener(this);
			exit.setOpaque(false);

			exit.setBorder(null);
			exit.setForeground(Color.red);
			exit.setBorderPainted(false);
			exit.setContentAreaFilled(false);
			exit.setFocusPainted(false);

			exit.setBounds((int) ((width / 2) - 100), (int) ((height / 2) + (height / 10.8)), 150, 80);
			buttonPanel.add(exit);

			howToPlay = new JButton();
			howToPlay.setBackground(Color.red);
			//Font f4 = new Font(Font.MONOSPACED, Font.BOLD, 25);
			howToPlay.setText("How to play");
			howToPlay.setFont(customFont2);

			howToPlay.setFocusable(false);
			howToPlay.addActionListener(this);
			howToPlay.setOpaque(false);

			howToPlay.setBorder(null);
			howToPlay.setForeground(Color.red);
			howToPlay.setContentAreaFilled(false);
			howToPlay.setBorderPainted(false);
			howToPlay.setFocusPainted(false);

			howToPlay.setBounds((width / 2) - 135, (int) ((height / 2) + (height / 7.2)), 220, 150);
			buttonPanel.add(howToPlay);

			buttonPanel.setBounds(width / 8, height / 5, 100, 100);

			mainScreen.add(buttonPanel);
			this.add(mainScreen);

		}

	}
	
	public void playMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		
		File file = new File("src//music.wav");
		//AudioInputStream audioStream = AudioSystem.getAudioInputStream(file); //Removed music file too large to upload 
		Clip clip = AudioSystem.getClip();
		//clip.open(audioStream);
		clip.start();
				
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
