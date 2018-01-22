
/* Music Class
 * Developer: Ian Reynolds
 * Purpose: plays and stops music.
 * 
 * Details:
 * INSTANCE class similar to SFX
 * All methods are INSTANCE methods. NOTHING is static.
 * Loops the music until it is stopped by the stop method
 * Goal has no stop because why should it
 * You don't have to do anything except stop and start it. Everything else is done for you in the code.
 * MAKE SURE the audio files are in the correct folder and it is important that the codec is not altered.
 * I'll say it again. DO NOT HAMPER WITH THE AUDIO FILES OR IT WILL NOT WORK.
 *
 * Refs
 * https://stackoverflow.com/questions/6045384/playing-mp3-and-wav-in-java
 * https://stackoverflow.com/questions/953598/audio-volume-control-increase-or-decrease-in-java
 * https://docs.oracle.com/javase/7/docs/api/javax/sound/sampled/Clip.html
 * https://docs.oracle.com/javase/7/docs/api/java/io/File.html
*/
import javax.sound.sampled.*;

class Music {
 // All of the clips
 private Clip menu;
 private Clip goal;
 private Clip level1;
 private Clip level2;
 private Clip level3;
 private Clip level4;
 private Clip level5;
 private Clip level6;
 // By default, volume is max or 0 setting.
 private Clip playing = null;
 private int volume;

 // CONSTRUCTOR: THIS IS AN INSTANCE CLASS SO THE CONSTRUCTOR IS NECESSARY
 public Music() {
  // setting up Menu music.
  try {
   AudioInputStream audioInputStreamMenu = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/menu.wav"));
   menu = AudioSystem.getClip();
   menu.open(audioInputStreamMenu);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (menu)");
   ex.printStackTrace();
  }

  // setting up the Goal clip
  try {
   AudioInputStream audioInputStreamGoal = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/goal.wav"));
   goal = AudioSystem.getClip();
   goal.open(audioInputStreamGoal);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(goal)");
   ex.printStackTrace();
  }

  // setting up the Level 1 music
  try {
   AudioInputStream audioInputStreamLevel1 = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/Level1.wav"));
   level1 = AudioSystem.getClip();
   level1.open(audioInputStreamLevel1);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(Level1)");
   ex.printStackTrace();
  }

  // setting up the Level 2 music
  try {
   AudioInputStream audioInputStreamLevel2 = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/Level2.wav"));
   level2 = AudioSystem.getClip();
   level2.open(audioInputStreamLevel2);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(Level2)");
   ex.printStackTrace();
  }

  // setting up the Level 3 music
  try {
   AudioInputStream audioInputStreamLevel3 = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/Level3.wav"));
   level3 = AudioSystem.getClip();
   level3.open(audioInputStreamLevel3);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(Level3)");
   ex.printStackTrace();
  }

  // setting up the Level 4 music
  try {
   AudioInputStream audioInputStreamLevel4 = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/Level4.wav"));
   level4 = AudioSystem.getClip();
   level4.open(audioInputStreamLevel4);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(Level4)");
   ex.printStackTrace();
  }

  // setting up the Level 5 music
  try {
   AudioInputStream audioInputStreamLevel5 = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/Level5.wav"));
   level5 = AudioSystem.getClip();
   level5.open(audioInputStreamLevel5);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(Level5)");
   ex.printStackTrace();
  }

  // setting up the Level 6 music
  try {
   AudioInputStream audioInputStreamLevel6 = AudioSystem
     .getAudioInputStream(this.getClass().getResource("MUSIC/Level6.wav"));
   level6 = AudioSystem.getClip();
   level6.open(audioInputStreamLevel6);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File(Level6)");
   ex.printStackTrace();
  }
 }

 // METHODS: ALL INSTANCE METHODS

 // Plays the menu theme
 public void playMenu() {
  FloatControl gainControl = (FloatControl) menu.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  menu.setLoopPoints(0, -1);
  menu.start();
  menu.loop(Clip.LOOP_CONTINUOUSLY);
  playing = menu;
 }

 // Stops menu theme
 public void stopMenu() {
  menu.stop();
 }

 // Plays goal sound
 public void playGoal() {
  goal.setFramePosition(0);
  FloatControl gainControl = (FloatControl) goal.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  goal.start();
  playing = goal;
 }

 // Plays Level 1 music
 public void playLevel1() {
  FloatControl gainControl = (FloatControl) level1.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  level1.setLoopPoints(0, -1);
  level1.start();
  level1.loop(Clip.LOOP_CONTINUOUSLY);
  playing = level1;
 }

 // Stops level 1 music
 public void stopLevel1() {
  level1.stop();
  level1.setFramePosition(0);
 }

 // Plays Level 2 music
 public void playLevel2() {
  FloatControl gainControl = (FloatControl) level2.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  level2.setLoopPoints(0, -1);
  level2.start();
  level2.loop(Clip.LOOP_CONTINUOUSLY);
  playing = level2;
 }

 // Stops level 2 music
 public void stopLevel2() {
  level2.stop();
  level2.setFramePosition(0);
 }

 // Plays level 3 music
 public void playLevel3() {
  FloatControl gainControl = (FloatControl) level3.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  level3.setLoopPoints(0, -1);
  level3.start();
  level3.loop(Clip.LOOP_CONTINUOUSLY);
  playing = level3;
 }

 // Stops level 3 music
 public void stopLevel3() {
  level3.stop();
  level3.setFramePosition(0);
 }

 // Plays level 4 music
 public void playLevel4() {
  FloatControl gainControl = (FloatControl) level4.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  level4.setLoopPoints(0, -1);
  level4.start();
  level4.loop(Clip.LOOP_CONTINUOUSLY);
  playing = level4;
 }

 // Stops level 4 music
 public void stopLevel4() {
  level4.stop();
  level4.setFramePosition(0);
 }

 // Plays level 5 music
 public void playLevel5() {
  FloatControl gainControl = (FloatControl) level5.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  level5.setLoopPoints(0, -1);
  level5.start();
  level5.loop(Clip.LOOP_CONTINUOUSLY);
  playing = level5;
 }

 // Stops level 5 music
 public void stopLevel5() {
  level5.stop();
  level5.setFramePosition(0);
 }

 // Plays level 6 music
 public void playLevel6() {
  FloatControl gainControl = (FloatControl) level6.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  level6.setLoopPoints(0, -1);
  level6.start();
  level6.loop(Clip.LOOP_CONTINUOUSLY);
  playing = level6;
 }

 // stops level 6 music
 public void stopLevel6() {
  level6.stop();
  level6.setFramePosition(0);
 }

 public Clip getPlaying() {
  return playing;
 }

 // Sets volume. Can be between -8 or 0.
 public void setVolume(int vol) {
  if (vol >= -10 && vol <= 0) {
   if (vol == -10) {
    vol = -40;
   }
   volume = vol;
   if (playing != null) {
    FloatControl gainControl = (FloatControl) playing.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(volume * 2f);
   }
  } else {
   System.out.println("Invalid volume setting - Music");
  }
 }
}
