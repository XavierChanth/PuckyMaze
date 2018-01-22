import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;

public class Level implements ActionListener {
 private JPanel panel;
 private MainMenu menu;
 private Options options;
 private InGameOptions igo;
 private SFX sfx;
 private Music music;

 private int width = 36;

 private boolean generated = false;
 private boolean opened = false;
 private int number;
 protected Puck puck;

 private static Floor[] floors;
 private static Wall[] walls;
 private static Trap[] traps;

 private BufferedImage[] levelArt = new BufferedImage[6];
 private BufferedImage vicScreen;
 private BufferedImage finalScreen;
 
 private static JButton[] levelSelect = new JButton[6];
 private static JButton[] vicButtons = new JButton[2];

 public Level(JPanel panel, MainMenu menu, Music music, SFX sfx, Dimension size, Options options,
   InGameOptions igo) {
  this.panel = panel;
  this.menu = menu;
  this.music = music;
  this.options = options;
  this.igo = igo;
  this.sfx = sfx;

  try {
   for (int i = 0; i < levelArt.length; i++) {
    levelArt[i] = ImageIO.read(this.getClass().getResource("ARTWORK/Level" + (i + 1) + ".png"));
   }
   vicScreen = ImageIO.read(this.getClass().getResource("ARTWORK/Victory.png"));
   finalScreen = ImageIO.read(this.getClass().getResource("ARTWORK/VictoryFinal.png"));
  } catch (IOException e) {
  }

  // Level Buttons
  
  for (int i = 0; i < 2; i++) {
		for (int n = 0; n < 3; n++) {
				levelSelect[3 * i + n] = new JButton("Level " + ((3 * i) + n + 1));
				levelSelect[3 * i + n].setPreferredSize(size);
				levelSelect[3 * i + n].setBounds(510 * i + 150, 240 * n + 25, size.width, size.height);
				levelSelect[3 * i + n].setActionCommand("Level-0" + ((3 * i) + n + 1));
				levelSelect[3 * i + n].addActionListener(this);
			}
		}

  String[] vicNames = { "Next Level", "To Credits" };
  // Buttons
  for (int i = 0; i < vicNames.length; i++) {
   vicButtons[i] = new JButton(vicNames[i]);
   vicButtons[i].setPreferredSize(size);
   vicButtons[i].setBounds(550, 380, size.width, size.height);
   vicButtons[i].setActionCommand(vicNames[i]);
   vicButtons[i].addActionListener(this);
  }

 }

 public void resetEnvironment() {
  puck.resetSpeed();
 }

 public void move() {
  if (generated) {
   puck.move();
  }
 }

 public void win() {
  igo.vicScreenClose();
  if (number == 6) {
   panel.add(vicButtons[1]);
  } else {
   panel.add(vicButtons[0]);
  }
 }

 public void closeVicButtons() {
  for (int i = 0; i < vicButtons.length; i++) {
   panel.remove(vicButtons[i]);
  }
 }

 public void genLevel() {
  igo.enableIGO();
  int[][] board = getLevel(number);
  int floorIndex = 0;
  int wallIndex = 0;
  int trapIndex = 0;
  for (int i = 0; i < 20; i++) {
   for (int n = 0; n < 20; n++) {
    // FLOOR
    if (board[i][n] % 10 == 0) {
     floorIndex++;
    }
    // WALL
    if (board[i][n] % 10 == 1) {
     wallIndex++;
    }
    // TRAP
    if (board[i][n] % 10 == 2) {
     trapIndex++;
    }
   }
  }
  floors = new Floor[floorIndex];
  walls = new Wall[wallIndex];
  traps = new Trap[trapIndex];

  floorIndex = 0;
  wallIndex = 0;
  trapIndex = 0;

  for (int i = 0; i < 20; i++) {
   for (int n = 0; n < 20; n++) {
    // FLOOR
    if (board[i][n] % 10 == 0) {
     floors[floorIndex] = new Floor(n * width + 554, i * width, (board[i][n] / 10), width);
     floorIndex++;
    }
    // WALL
    if (board[i][n] % 10 == 1) {
     walls[wallIndex] = new Wall(n * width + 554, i * width, (board[i][n] / 10), width);
     wallIndex++;
    }
    // TRAP
    if (board[i][n] % 10 == 2) {
     traps[trapIndex] = new Trap(n * width + 554, i * width, (board[i][n] / 10), width);
     trapIndex++;
    }
   }
  }
  int[] puckLocation = getPuck(number);
  puck = new Puck(menu, panel, this, options, sfx, music, puckLocation[0] * width + 554 + 18,
    puckLocation[1] * width + 18);
  generated = true;
  puck.resetPuck();
  puck.resetShots();
  if (number == 1) {
   music.playLevel1();
  }
  if (number == 2) {
   music.playLevel2();
  }
  if (number == 3) {
   music.playLevel3();
  }
  if (number == 4) {
   music.playLevel4();
  }
  if (number == 5) {
   music.playLevel5();
  }
  if (number == 6) {
   music.playLevel6();
  }
 }

 // ### PAINT ###
 public void paint(Graphics2D g2d) {
  if (generated) {
   g2d.drawImage(levelArt[number - 1], 0, 0, levelArt[number - 1].getWidth(), levelArt[number - 1].getHeight(),
     null);
  }
  for (int i = 0; i < floors.length; i++) {
   floors[i].paint(g2d);
  }
  for (int i = 0; i < walls.length; i++) {
   walls[i].paint(g2d);
  }
  for (int i = 0; i < traps.length; i++) {
   traps[i].paint(g2d);
  }
  puck.paint(g2d);

  if (puck.getWon()) {
	  if(number == 6) {
		  g2d.drawImage(finalScreen, 400, 225, finalScreen.getWidth(), finalScreen.getHeight(), null);
	  } else {
   g2d.drawImage(vicScreen, 400, 225, vicScreen.getWidth(), vicScreen.getHeight(), null);
	  }
   igo.disableIGO();
   g2d.setFont(new Font("Dialog", Font.BOLD, 30));
   g2d.drawString(String.valueOf(puck.getShots()), 630, 335);
  }
 }

 // ### BUTTON ACTIONS ###
 public void actionPerformed(ActionEvent action) {
  String act = action.getActionCommand();
  // Levels
  if (act.length() == 8 && "Level-0".equals(act.substring(0, 7))) {
   int temp = Integer.parseInt(action.getActionCommand().substring(7));
   removeLevels();
   menu.launchLevel();
   opened = true;
   number = temp;
   igo.open();
   genLevel();
  }
  if("Next Level".equals(act))
  {
   close();
   number++;
   opened = true;
   genLevel();
  }
  if("To Credits".equals(act))
  {
   menu.returnHome();
   menu.credits();
  }
 }

 public Wall[] getWalls() {
  return walls;
 }

 public Floor[] getFloors() {
  return floors;
 }

 public Trap[] getTraps() {
  return traps;
 }

 // ### BUTTON ENCAPSULATION ###
 public void loadLevels() {
  for (int i = 0; i < levelSelect.length; i++) {
   panel.add(levelSelect[i]);
  }
 }

 public void removeLevels() {
  for (int i = 0; i < levelSelect.length; i++) {
   panel.remove(levelSelect[i]);
  }
 }

 // ### GET LEVEL DESIGN ###
 public int[][] getLevel(int number) {
  String[] temp = new String[20];
  int[][] board = new int[20][20];
  try {
	  FileReader f = new FileReader("Resources/level" + number + ".txt");
	   BufferedReader br = new BufferedReader(f);
   br.readLine();
   for (int i = 0; i < 20; i++) {
    temp = br.readLine().split("/");
    for (int n = 0; n < 20; n++) {
     board[i][n] = Integer.parseInt(temp[n]);
    }
   }
   br.close();
  } catch (IOException e) {
  }
  return board;
 }

 public int[] getPuck(int number) {
  String[] temp = new String[2];
  int[] puck = new int[2];
  try {
   FileReader fr = new FileReader("Resources/level" + number + ".txt");
   BufferedReader br = new BufferedReader(fr);
   temp = br.readLine().split("/");
   for (int i = 0; i < 2; i++) {
    puck[i] = Integer.parseInt(temp[i]);
   }
   br.close();
  } catch (IOException e) {
  }
  return puck;
 }

 public boolean getOpened() {
  return opened;
 }

 public boolean isGenerated() {
  return generated;
 }

 public void close() {
  opened = false;
  generated = false;
  music.stopLevel1();
  music.stopLevel2();
  music.stopLevel3();
  music.stopLevel4();
  music.stopLevel5();
  music.stopLevel6();
  puck.resetPuck();
  puck.deactivate();
  closeVicButtons();
 }
}