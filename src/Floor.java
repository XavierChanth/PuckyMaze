import java.awt.*;

public class Floor {
 private int decel = 1;
 private int priority = 1;
 private int type;
 private int[] loc = new int[2];
 private int width;
 private boolean goal = false;
 private boolean boost = false;
 // first boost arrow
 private int[] boostx1 = new int[6]; // {loc[0] + 5, 130, 138, 130, 125, 130};
 private int[] boosty1 = new int[6]; // {130, 130, 150, 170, 170, 150};
 // second boost arrow
 private int[] boostx2 = new int[6];
 private int[] boosty2 = new int[6];
 // third boost arrow
 private int[] boostx3 = new int[6];
 private int[] boosty3 = new int[6];
 // how much each value of the arrows needs to increase relative to the corner of
 // the floor
 private int[] xaddv = { 6, 6, 18, 30, 30, 18 };
 private int[] yaddv = { 3, 6, 11, 6, 3, 6 };

 private int[] xaddh = { 3, 6, 11, 6, 3, 6 };
 private int[] yaddh = { 6, 6, 18, 30, 30, 18 };

 // ice floor shiny parts
 // first line
 private int[] icex1 = new int[4];
 private int[] icey1 = new int[4];
 private int[] xadd1 = { 9, 12, 0, 0 };
 private int[] yadd1 = { 0, 0, 18, 9 };
 // second line
 private int[] icex2 = new int[4];
 private int[] icey2 = new int[4];
 private int[] xadd2 = { 21, 24, 0, 0 };
 private int[] yadd2 = { 0, 0, 24, 21 };
 // third line
 private int[] icex3 = new int[4];
 private int[] icey3 = new int[4];
 private int[] xadd3 = { 30, 36, 12, 0 };
 private int[] yadd3 = { 0, 0, 36, 30 };
 // fourth line
 private int[] icex4 = new int[4];
 private int[] icey4 = new int[4];
 private int[] xadd4 = { 36, 36, 9, 0 };
 private int[] yadd4 = { 0, 18, 36, 36 };

 public Floor(int x, int y, int type, int width) {
  loc[0] = x;
  loc[1] = y;
  this.type = type;
  this.width = width;
  // this.direc = direc;
  // determines the locations of the points of the boost arrows
  if (type % 10 == 2) {
   priority = 3;
   boost = true;
   decel = 0;
   // east
   if (type / 10 == 2) {
    for (int i = 0; i < 6; i++) {
     boostx1[i] = loc[0] + xaddh[i];
     boosty1[i] = loc[1] + yaddh[i];
    }
    for (int i = 0; i < 6; i++) {
     boostx2[i] = loc[0] + xaddh[i] + 11;
     boosty2[i] = loc[1] + yaddh[i];
    }
    for (int i = 0; i < 6; i++) {
     boostx3[i] = loc[0] + xaddh[i] + 22;
     boosty3[i] = loc[1] + yaddh[i];
    }
   }
   // west
   else if (type / 10 == 0) {
    for (int i = 0; i < 6; i++) {
     boostx1[i] = loc[0] - xaddh[i] + 36;
     boosty1[i] = loc[1] + yaddh[i];
    }
    for (int i = 0; i < 6; i++) {
     boostx2[i] = loc[0] - xaddh[i] - 11 + 36;
     boosty2[i] = loc[1] + yaddh[i];
    }
    for (int i = 0; i < 6; i++) {
     boostx3[i] = loc[0] - xaddh[i] - 22 + 36;
     boosty3[i] = loc[1] + yaddh[i];
    }
   }
   // south
   if (type / 10 == 3) {
    for (int i = 0; i < 6; i++) {
     boostx1[i] = loc[0] + xaddv[i];
     boosty1[i] = loc[1] + yaddv[i];
    }
    for (int i = 0; i < 6; i++) {
     boostx2[i] = loc[0] + xaddv[i];
     boosty2[i] = loc[1] + yaddv[i] + 11;
    }
    for (int i = 0; i < 6; i++) {
     boostx3[i] = loc[0] + xaddv[i];
     boosty3[i] = loc[1] + yaddv[i] + 22;
    }
   }
   if (type / 10 == 1) {
    for (int i = 0; i < 6; i++) {
     boostx1[i] = loc[0] + xaddv[i];
     boosty1[i] = loc[1] - yaddv[i] + 36;
    }
    for (int i = 0; i < 6; i++) {
     boostx2[i] = loc[0] + xaddv[i];
     boosty2[i] = loc[1] - yaddv[i] - 11 + 36;
    }
    for (int i = 0; i < 6; i++) {
     boostx3[i] = loc[0] + xaddv[i];
     boosty3[i] = loc[1] - yaddv[i] - 22 + 36;
    }
   }

  } else if (type % 10 == 3) {
   for (int i = 0; i < 4; i++) {
    icex1[i] = loc[0] + xadd1[i];
    icey1[i] = loc[1] + yadd1[i];
    icex2[i] = loc[0] + xadd2[i];
    icey2[i] = loc[1] + yadd2[i];
    icex3[i] = loc[0] + xadd3[i];
    icey3[i] = loc[1] + yadd3[i];
    icex4[i] = loc[0] + xadd4[i];
    icey4[i] = loc[1] + yadd4[i];
   }
  }
  // Regular floor = type 0
  // Sticky floor = type 1
  if (type % 10 == 1) {
   priority = 2;
   decel = 5;
  }
  // Ice floor = type 3
  else if (type % 10 == 3) {
   priority = 0;
   decel = 0;
  }
  // Goal floor = type 4
  else if (type % 10 == 4)
  {
   goal = true;
  }
 }

 public int[] getLoc() {
  return loc;
 }

 public int getDecel() {
  return decel;
 }

 public int getDirec() {
  return type / 10;
 }

 public boolean isBoost() {
  return boost;
 }

 public boolean isGoal() {
  return goal;
 }

 public void paint(Graphics2D g2d) {
  // regular
  if (type % 10 == 0) {
   g2d.setColor(new Color(68, 135, 140));
   g2d.fillRect(loc[0], loc[1], width, width);
  }
  // sticky
  else if (type % 10 == 1) {
   g2d.setColor(new Color(158, 211, 134));
   g2d.fillRect(loc[0], loc[1], width, width);
   g2d.setColor(new Color(201, 229, 188));
   g2d.fillOval(loc[0] + 12, loc[1] + 12, 6, 6);
   g2d.fillOval(loc[0] + 6, loc[1] + 2, 9, 9);
   g2d.fillOval(loc[0] + 27, loc[1] + 21, 6, 6);
   g2d.fillOval(loc[0] + 8, loc[1] + 23, 11, 11);
   g2d.fillOval(loc[0] + 30, loc[1] + 3, 5, 5);
   g2d.fillOval(loc[0] + 24, loc[1] + 11, 6, 6);
  }
  // boost
  else if (type % 10 == 2) {
   g2d.setColor(new Color(42, 83, 87));
   g2d.fillRect(loc[0], loc[1], width, width);
   g2d.setColor(Color.YELLOW);
   g2d.fillPolygon(boostx1, boosty1, 6);
   g2d.fillPolygon(boostx2, boosty2, 6);
   g2d.fillPolygon(boostx3, boosty3, 6);
  }
  // ice
  else if (type % 10 == 3) {
   g2d.setColor(new Color(207, 226, 243));
   g2d.fillRect(loc[0], loc[1], width, width);
   g2d.setColor(new Color(239, 239, 239));
   g2d.fillPolygon(icex1, icey1, 4);
   g2d.fillPolygon(icex2, icey2, 4);
   // g2d.fillPolygon(icex3, icey3, 4);
   g2d.fillPolygon(icex4, icey4, 4);
  }
  // goal
  else if (type % 10 == 4) {
   g2d.setColor(new Color(106, 255, 121));
   g2d.fillRect(loc[0], loc[1], width, width);
  }
  // empty - (background menu color)
  else if (type % 10 == 5) {
   g2d.setColor(new Color(200, 200, 200));
   g2d.fillRect(loc[0], loc[1], width, width);
  }
 }

 public int getPrior() {
  return priority;
 }
}