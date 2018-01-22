import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.*;
import java.io.*;

public class Trap {
 private boolean hidden = false;
 private int[] loc = new int[2];
 private int width;
 private int height;
 private int type = 0;
 private BufferedImage img = null;

 Trap(int x, int y, int type, int width) {
  this.type = type;
  this.width = width;
  loc[0] = x;
  loc[1] = y;

  if (this.type == 10 || this.type == 30) {
   try {
    img = ImageIO.read(this.getClass().getResource("ARTWORK/spikesv.png"));
   } catch (IOException e) {
   }
   if (type == 10) {
    this.height = width;
   } else if (type == 30) {
    this.height = -width;
   }
  } else if (this.type == 00 || this.type == 20) {
   try {
    img = ImageIO.read(this.getClass().getResource("ARTWORK/spikesh.png"));
   } catch (IOException e) {
   }
   if (type == 00) {
    this.height = width;
   } else if (type == 20) {
    this.width *= -1;
    this.height = width;
   }
  } else if (this.type == 02) {
   this.width = width;
  } else if (this.type == 03) {
   hidden = true;
   this.width = width;
  }
 }

 public int[] getLoc() {
  return loc;
 }

 public boolean getHidden() {
  return (type % 10 == 3);
 }

 public void unhide() {
  hidden = false;
 }
 
 public void rehide(){
   hidden = true;
 }

 public int getType() {
  return type;
 }

 // ##PAINT##
 public void paint(Graphics2D g2d) {
  // the spikes
  if (type % 10 == 0) {
   // these are the default of the pic, so they don't need to be changed
   if (type == 00 || type == 10)
    g2d.drawImage(img, loc[0], loc[1], width, height, null);
   else if (type == 20)
    g2d.drawImage(img, loc[0] - width, loc[1], width, height, null);
   else if (type == 30)
    g2d.drawImage(img, loc[0], loc[1] - height, width, height, null);
  }
  // hole
  else if (type % 10 == 2) {
   Floor floor = new Floor(loc[0], loc[1], type / 10, width);
   floor.paint(g2d);
   g2d.setColor(Color.BLACK);
   g2d.fillOval(loc[0], loc[1], width, width);
  }
  // hidden hole
  else if (type % 10 == 3) {
	  Floor floor = new Floor(loc[0], loc[1], type / 10, width);
	   floor.paint(g2d);
   if (!hidden) {
    g2d.setColor(Color.BLACK);
    g2d.fillOval(loc[0], loc[1], width, width);
   }
  }
 }
}