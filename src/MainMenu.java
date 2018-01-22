import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class MainMenu extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static boolean open = true;
	private static JFrame frame;
	private static Interface panel;

	protected int x;
	protected int y;

	// Pictures
	BufferedImage startMenu = null;
	BufferedImage instructions = null;
	BufferedImage credits = null;
	BufferedImage optionScreen = null;
	BufferedImage igOptionScreen = null;
	BufferedImage levelScreen = null;

	// Buttons
	private static JButton[] menuItems = new JButton[5];
	private static JButton backButton;
	private static Dimension size;

	// Classes
	private Level level;
	private Options options;
	private InGameOptions igo;

	private boolean instructionsOpened = false;
	private boolean creditsOpened = false;
	private boolean menuOpened = true;
	private boolean menuMusic = true;

	protected Music music;
	protected SFX sfx;
	private boolean levelSelect = false;

	public MainMenu() {
		// Add Pictures
		try {
			startMenu = ImageIO.read(this.getClass().getResource("ARTWORK/MainMenu.png"));
			levelScreen = ImageIO.read(this.getClass().getResource("ARTWORK/Level.png"));
			instructions = ImageIO.read(this.getClass().getResource("ARTWORK/Instructions.png"));
			credits = ImageIO.read(this.getClass().getResource("ARTWORK/Credits.png"));
			optionScreen = ImageIO.read(this.getClass().getResource("ARTWORK/Options.png"));
			igOptionScreen = ImageIO.read(this.getClass().getResource("ARTWORK/InGameOptions.png"));
		} catch (IOException e) {
		}

		// Frame Setup
		frame = new JFrame("Pucky-Maze by Static Reference");
		panel = new Interface(this);

		// Button sizes
		size = new Dimension(180, 60);

		// Menu Button Names
		String[] menuNames = { "Start", "Instructions", "Credits", "Options", "Exit" };

		// Main Menu Buttons
		for (int n = 0; n < menuItems.length; n++) {
			menuItems[n] = new JButton(menuNames[n]);
			menuItems[n].setPreferredSize(size);
			menuItems[n].setBounds(60, (90 * n + 240), size.width, size.height);
			menuItems[n].setActionCommand(menuNames[n]);
			menuItems[n].addActionListener(this);
		}

		// Back Button
		backButton = new JButton("Back");
		backButton.setPreferredSize(size);
		backButton.setBounds(60, 600, size.width, size.height);
		backButton.setActionCommand("Back");
		backButton.addActionListener(this);

		// Class Setup
		options = new Options(this, panel);
		igo = new InGameOptions(panel, this, size, options);

		music = new Music();
		sfx = new SFX();

		level = new Level(panel, this, music, sfx, size, options, igo);
	}

	// ### MAIN ###
	public static void main(String[] args) throws InterruptedException {
		MainMenu menu = new MainMenu();
		// Level level = menu.level;
		panel.add(menu);
		frame.add(panel);
		frame.setSize(new Dimension(1280, 749));
		frame.setResizable(false);
		// frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu.loadMenu();
		menu.resetVolumes();
		while (open) {
			menu.move();
			panel.repaint();
			Thread.sleep(8);
		}
	}

	// ### PAINT ###
	public void paint(Graphics2D g2d) {
		if (menuOpened) {
			g2d.drawImage(startMenu, 0, 0, startMenu.getWidth(), startMenu.getHeight(), null);
		}
		if (levelSelect) {
			g2d.drawImage(levelScreen, 0, 0, levelScreen.getWidth(), levelScreen.getHeight(), null);
		}
		if (instructionsOpened) {
			g2d.drawImage(instructions, 0, 0, instructions.getWidth(), instructions.getHeight(), null);
		}
		if (creditsOpened) {
			g2d.drawImage(credits, 0, 0, credits.getWidth(), credits.getHeight(), null);
		}
		if (options.getOpened()) {
			g2d.drawImage(optionScreen, 0, 0, optionScreen.getWidth(), optionScreen.getHeight(), null);
		}
		if (level.getOpened()) {
			level.paint(g2d);
		}

		if (igo.getOptionsOpened()) {
			g2d.drawImage(igOptionScreen, 0, 0, igOptionScreen.getWidth(), igOptionScreen.getHeight(), null);
		}
		if (options.getOpened()) {
			options.paint(g2d);
		}
	}

	// ### BUTTON ACTIONS ###
	public void actionPerformed(ActionEvent action) {
		String act = action.getActionCommand();
		if ("Start".equals(act)) {
			start();
		}
		if ("Instructions".equals(act)) {
			instructions();
		}
		if ("Credits".equals(act)) {
			credits();
		}
		if ("Options".equals(act)) {
			options();
		}
		if ("Back".equals(act)) {
			back();
		}
		if ("Exit".equals(act)) {
			exit();
		}
	}

	// ### METHODS ###
	public void loadMenu() {
		resetVolumes();
		for (int i = 0; i < menuItems.length; i++) {
			panel.add(menuItems[i]);
		}
		if (menuMusic) {
			music.playMenu();
			menuMusic = false;
		}
	}

	public void move() {
		level.move();
	}

	public void returnHome() {
		menuOpened = true;
		level.close();
		back();
		level.resetEnvironment();
		music.stopLevel1();
		music.stopLevel2();
		music.stopLevel3();
		music.stopLevel4();
		music.stopLevel5();
		music.stopLevel6();
	}

	public void back() {
		level.removeLevels();
		options.close();
		igo.close();
		instructionsOpened = false;
		creditsOpened = false;
		panel.remove(backButton);
		frame.repaint();
		loadMenu();
		levelSelect = false;
	}

	public void removeMenu() {
		// remove current buttons
		for (int i = 0; i < menuItems.length; i++) {
			panel.remove(menuItems[i]);
		}
		// clear board
		frame.repaint();
	}

	public void start() {
		removeMenu();
		panel.add(backButton);
		level.loadLevels();
		levelSelect = true;
	}

	public void instructions() {
		instructionsOpened = true;
		removeMenu();
		panel.add(backButton);
	}

	public void credits() {
		creditsOpened = true;
		removeMenu();
		panel.add(backButton);
	}

	public void options() {
		removeMenu();
		panel.add(backButton);
		options.open();
	}

	public void launchLevel() {
		menuMusic = true;
		music.stopMenu();
		menuOpened = false;
		levelSelect = false;
		panel.remove(backButton);
		frame.repaint();
	}

	public void exit() {
		frame.dispose();
		open = false;
		music.stopMenu();
	}

	public void resetVolumes() {
		music.setVolume(options.getVolMusic());
		sfx.setVolume(options.getVolSFX());
	}
}
