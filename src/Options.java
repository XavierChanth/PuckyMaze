import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.*;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class Options implements ActionListener {
 private boolean opened = false;
 private MainMenu menu;
 private JPanel panel;

 private boolean muteSFX;
 private boolean muteMusic;
 private int volSFX;
 private int volMusic;
 private int outSFX;
 private int outMusic;

 private int prefs[] = {0, 0, -5, -5, 1, 1};

 private int dif = 1;
 private int size = 1;

 private JButton[] volumeButtons = new JButton[6];
 private JButton[] difButtons = new JButton[3];
 private JButton[] sizeButtons = new JButton[3];

 Options(MainMenu menu, JPanel panel) {
  this.menu = menu;
  this.panel = panel;
  String[] tempPref;
  // File is read as follows:muteSFX/muteMusic/volSFX/outMusic/dif/size
  // for booleans 1 = true, 0 = false
  try {
   FileReader fr = new FileReader("Resources/Preferences.txt");
   BufferedReader br = new BufferedReader(fr);
   tempPref = br.readLine().split("/");
   for (int n = 0; n < tempPref.length; n++) {
    prefs[n] = Integer.parseInt(tempPref[n]);
   }
   br.close();
  } catch (IOException e) {
  }
  dif = prefs[4];
  size = prefs[5];

  // Button size
  Dimension volSize = new Dimension(100, 60);
  Dimension difSize = new Dimension(120, 60);

  // Button Names
  String[] typeNames = { "SFX", "Music" };
  String[] volumeNames = { "Mute", "Up", "Down" };
  // Buttons
  for (int i = 0; i < typeNames.length; i++) {
   for (int n = 0; n < volumeNames.length; n++) {
    volumeButtons[3 * i + n] = new JButton(volumeNames[n]);
    volumeButtons[3 * i + n].setPreferredSize(volSize);
    int temp = 312 * n - 262;
    if (n == 0) {
     temp = 480;
    }
    volumeButtons[3 * i + n].setBounds(120 * i + 60, temp, volSize.width, volSize.height);
    volumeButtons[3 * i + n].setActionCommand(typeNames[i] + " " + volumeNames[n]);
    volumeButtons[3 * i + n].addActionListener(this);
   }
  }
  //Must be after buttons have been made
  if (prefs[0] == 1) {
   toggleMuteSFX();
  }
  if (prefs[1] == 1) {
   toggleMuteMusic();
  }
  volSFX = prefs[2];
  volMusic = prefs[3];
  outSFX = getVolSFX();
  outMusic = getVolMusic();
  
  // Difficulty Names
  String[] difNames = { "Easy", "Normal", "Hard" };
  // Buttons
  for (int i = 0; i < difNames.length; i++) {
   difButtons[i] = new JButton(difNames[i]);
   difButtons[i].setPreferredSize(difSize);
   difButtons[i].setBounds(320, 120 * i + 50, difSize.width, difSize.height);
   difButtons[i].setActionCommand(difNames[i]);
   difButtons[i].addActionListener(this);
  }
  difButtons[dif].setEnabled(false);
  // Size names
  String[] sizeNames = { "Small", "Medium", "Large" };
  // Buttons
  for (int i = 0; i < sizeNames.length; i++) {
   sizeButtons[i] = new JButton(sizeNames[i]);
   sizeButtons[i].setPreferredSize(difSize);
   sizeButtons[i].setBounds(160 * i + 360, 500, difSize.width, difSize.height);
   sizeButtons[i].setActionCommand(sizeNames[i]);
   sizeButtons[i].addActionListener(this);
  }
  sizeButtons[size].setEnabled(false);
 }

 public void storePrefs() {
  if (muteSFX) {
   prefs[0] = 1;
  } else {
   prefs[0] = 0;
  }
  if (muteMusic) {
   prefs[1] = 1;
  } else {
   prefs[1] = 0;
  }
  prefs[2] = volSFX;
  prefs[3] = volMusic;
  prefs[4] = dif;
  prefs[5] = size;
  try {
   FileWriter fw = new FileWriter("Resources/Preferences.txt");
   PrintWriter pw = new PrintWriter(fw, false);
   pw.print(prefs[0]);
   for (int i = 1; i < prefs.length; i++) {
    pw.print("/" + prefs[i]);
   }
   pw.close();
  } catch (IOException e) {
  }
 }

 // ### PAINT ###
 public void paint(Graphics2D g2d) {
  if (muteSFX) {
   outSFX = -100;
  } else {
   outSFX = volSFX;
  }
  if (muteMusic) {
   outMusic = -100;
  } else {
   outMusic = volMusic;
  }
  g2d.setColor(new Color(220, 70, 30));
  for (int i = volSFX; i > -10; i--) {
   g2d.fillRect(62, 365 - 25 * (i + 10), 96, 15);
  }
  for (int i = volMusic; i > -10; i--) {
   g2d.fillRect(182, 365 - 25 * (i + 10), 96, 15);
  }
  g2d.setColor(new Color(255, 78, 35));
  for (int i = outSFX; i > -10; i--) {
   g2d.fillRect(62, 365 - 25 * (i + 10), 96, 15);
  }
  for (int i = outMusic; i > -10; i--) {
   g2d.fillRect(182, 365 - 25 * (i + 10), 96, 15);
  }
 }

 // ### BUTTON ACTIONS ###
 public void actionPerformed(ActionEvent action) {
  String act = action.getActionCommand();
  // Mute Toggles
  if ("SFX Mute".equals(act)) {
   toggleMuteSFX();
   menu.resetVolumes();
   menu.sfx.bounce();
  }
  if ("Music Mute".equals(act)) {
   toggleMuteMusic();
   menu.resetVolumes();
  }

  // Volume Toggles
  if ("SFX Up".equals(act) && volSFX < 0) {
   volSFX++;
   menu.resetVolumes();
   menu.sfx.bounce();
  }
  if ("SFX Down".equals(act) && volSFX > -10) {
   volSFX--;
   menu.resetVolumes();
   menu.sfx.bounce();
  }
  if ("Music Up".equals(act) && volMusic < 0) {
   volMusic++;
   menu.resetVolumes();
  }
  if ("Music Down".equals(act) && volMusic > -10) {
   volMusic--;
   menu.resetVolumes();
  }

  // Difficulty Toggles
  if ("Easy".equals(act)) {
   difButtons[dif].setEnabled(true);
   dif = 0;
   difButtons[dif].setEnabled(false);
  }
  if ("Normal".equals(act)) {
   difButtons[dif].setEnabled(true);
   dif = 1;
   difButtons[dif].setEnabled(false);
  }
  if ("Hard".equals(act)) {
   difButtons[dif].setEnabled(true);
   dif = 2;
   difButtons[dif].setEnabled(false);
  }
  // Size Toggles
  if ("Small".equals(act)) {
   sizeButtons[size].setEnabled(true);
   size = 0;
   sizeButtons[size].setEnabled(false);
  }
  if ("Medium".equals(act)) {
   sizeButtons[size].setEnabled(true);
   size = 1;
   sizeButtons[size].setEnabled(false);
  }
  if ("Large".equals(act)) {
   sizeButtons[size].setEnabled(true);
   size = 2;
   sizeButtons[size].setEnabled(false);
  }
 }

 // ### METHODS ###
 public void open() {
  for (int i = 0; i < volumeButtons.length; i++) {
   panel.add(volumeButtons[i]);
  }
  for (int i = 0; i < difButtons.length; i++) {
   panel.add(difButtons[i]);
  }
  for (int i = 0; i < sizeButtons.length; i++) {
   panel.add(sizeButtons[i]);
  }
  opened = true;
 }

 public void openIGO() {
  for (int i = 0; i < volumeButtons.length; i++) {
   panel.add(volumeButtons[i]);
  }
  opened = true;
 }

 public void close() {
  for (int i = 0; i < volumeButtons.length; i++) {
   panel.remove(volumeButtons[i]);
  }
  for (int i = 0; i < difButtons.length; i++) {
   panel.remove(difButtons[i]);
  }
  for (int i = 0; i < sizeButtons.length; i++) {
   panel.remove(sizeButtons[i]);
  }
  opened = false;
  storePrefs();
 }

 // ### ACCESSORS ###
 public boolean getOpened() {
  return opened;
 }

 public int getVolSFX() {
  if (muteSFX) {
   outSFX = -10;
  } else {
   outSFX = volSFX;
  }
  return outSFX;
 }

 public int getVolMusic() {

  if (muteMusic) {
   outMusic = -10;
  } else {
   outMusic = volMusic;
  }
  return outMusic;
 }

 public int getDif() {
  return dif;
 }

 public int getSize() {
  return size;
 }

 // ### MUTATORS ###
 public void toggleMuteSFX() {
   if (muteSFX) {
    volumeButtons[0].setText("Mute");
   } else {
    volumeButtons[0].setText("Un-Mute");
   }
  muteSFX = !muteSFX;
 }

 public void toggleMuteMusic() {
   if (muteMusic) {
    volumeButtons[3].setText("Mute");
   } else {
    volumeButtons[3].setText("Un-Mute");
   }
  muteMusic = !muteMusic;
 }
}