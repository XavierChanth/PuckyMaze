import javax.sound.sampled.*;

/*Sound Effects Class
 * Developer: Ian Reynolds
 * Purpose: Plays sound effects when methods are used
 * 
 * Details:
 * use the volume method to set the volume. 
 * No methods use parameters.
 * Each sound effect is short
 * Default volume is maximum with a value of 0
 * Sound effects CAN be layered on top of each other
 * All you really need to do is create an instance and use the methods. everything else is done.
 * MAKE SURE the audio files are in the correct folder and it is important that the codec is not altered.
 * I'll say it again. DO NOT HAMPER WITH THE AUDIO FILES OR IT WILL NOT WORK.
 * 
 * References: Same as Music class
 */
class SFX {
 // The SFX clips
 private Clip bounce;
 private Clip spike;
 private Clip bounceWall;
 private Clip stick;
 private Clip hole;
 private Clip horn;
 private int volume;

 // CONSTRUCTOR: Sets up all the SFX.
 public SFX() {
  // setting up the bounce clip
  try {
   AudioInputStream audioInputStreamBounce = AudioSystem
     .getAudioInputStream(this.getClass().getResource("SFX/bounce2.wav"));
   bounce = AudioSystem.getClip();
   bounce.open(audioInputStreamBounce);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (bounce)");
   ex.printStackTrace();
  }

  // setting up the spike clip
  try {
   AudioInputStream audioInputStreamSpike = AudioSystem
     .getAudioInputStream(this.getClass().getResource("SFX/spikes2.wav"));
   spike = AudioSystem.getClip();
   spike.open(audioInputStreamSpike);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (spikes)");
   ex.printStackTrace();
  }

  // setting up the bounce wall clip
  try {
   AudioInputStream audioInputStreamBounceWall = AudioSystem
     .getAudioInputStream(this.getClass().getResource("SFX/bounceWall2.wav"));
   bounceWall = AudioSystem.getClip();
   bounceWall.open(audioInputStreamBounceWall);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (bounceWall)");
   ex.printStackTrace();
  }

  // setting up the hole clip
  try {
   AudioInputStream audioInputStreamHole = AudioSystem
     .getAudioInputStream(this.getClass().getResource("SFX/hole2.wav"));
   hole = AudioSystem.getClip();
   hole.open(audioInputStreamHole);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (hole)");
   ex.printStackTrace();
  }

  // setting up the stick clip
  try {
   AudioInputStream audioInputStreamStick = AudioSystem
     .getAudioInputStream(this.getClass().getResource("SFX/stick2.wav"));
   stick = AudioSystem.getClip();
   stick.open(audioInputStreamStick);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (stick)");
   ex.printStackTrace();
  }
  //AIRHORN
  try {
   AudioInputStream audioInputStreamAirhorn = AudioSystem
     .getAudioInputStream(this.getClass().getResource("SFX/airhorn.wav"));
   horn = AudioSystem.getClip();
   horn.open(audioInputStreamAirhorn);
  } catch (Exception ex) {
   System.out.println("Couldn't find this Audio File (horn)");
   ex.printStackTrace();
  }
 }

 // METHOD SECTION
 // These are all instance methods by the way.

 // bounce off normal wall
 public void bounce() {
  bounce.setFramePosition(0);
  FloatControl gainControl = (FloatControl) bounce.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  bounce.start();
 }

 // hit spikes
 public void spikes() {
  spike.setFramePosition(0);
  FloatControl gainControl = (FloatControl) spike.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  spike.start();
 }

 // hit a bouncy wall
 public void bounceWall() {
  bounceWall.setFramePosition(0);
  FloatControl gainControl = (FloatControl) bounceWall.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  bounceWall.start();
 }

 // get stuck
 public void stick() {
  stick.setFramePosition(0);
  FloatControl gainControl = (FloatControl) stick.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  stick.start();
 }

 // fall in a hole
 public void hole() {
  hole.setFramePosition(0);
  FloatControl gainControl = (FloatControl) hole.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  hole.start();
 }
 
 public void horn() {
  horn.setFramePosition(0);
  FloatControl gainControl = (FloatControl) horn.getControl(FloatControl.Type.MASTER_GAIN);
  gainControl.setValue(volume * 2f); // Volume is a negative number so it multiplies. Max volume is 0 (0).
           // Minimum(muted) is -8 (-64f).
  horn.start();
 }

 // use this encapsulated method to set the volume. Can only do -8 to 0.
 public void setVolume(int vol) {
  if (vol >= -10 && vol <= 0) {
   if (vol == -10) {
    volume = -40;
   } else {
    volume = vol;
   }
  } else {
   System.out.println("Invalid volume setting - SFX");
  }
 }
}
