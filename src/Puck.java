import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class Puck {
	private MainMenu menu;
	private JPanel panel;
	private Level level;
	private SFX sfx;
	private Music music;
	private float x;
	private float y;
	private float startX;
	private float startY;
	private static float xa = 0;
	private static float ya = 0;
	private static float xi = 0;
	private static float yi = 0;
	private static int increment = 0;
	private static boolean shoot = true;
	private boolean collide = false;
	private boolean won = false;
	private boolean active = true;
	private float ps;
	private float rad;
	private float gx;
	private float sqSize = 36;
	private int difficulty;
	protected Arrow arrow;
	private int shots;
	private boolean falling = false;

	public Puck(MainMenu menu, JPanel panel, Level level, Options options, SFX sfx, Music music, float x, float y) {
		this.menu = menu;
		this.panel = panel;
		this.level = level;
		this.sfx = sfx;
		this.music = music;
		this.x = x;
		this.y = y;
		startX = x;
		startY = y;
		gx = x - 554;
		arrow = new Arrow(panel, x, y);
		difficulty = options.getDif();
		setRadius(12 + (options.getSize() * 3));
		MouseListener mouseListener = new MouseAdapter() {
			public void mousePressed(MouseEvent mouse) {
				// Puck
				if (mouse.getX() >= 554 && xa == 0 && ya == 0 && !won && !falling) {
					shoot = true;
					arrow.setAim(true);
				} else {
					shoot = false;
					arrow.setAim(false);
				}
				// Arrow
				if (arrow.getAim()) {
					arrow.aimArrow(mouse.getX(), mouse.getY());
				}
			}

			public void mouseReleased(MouseEvent mouse) {
				arrow.setAim(false);
				if (shoot) {
					shoot(mouse.getX(), mouse.getY());
				}
			}
		};
		panel.addMouseListener(mouseListener);

		MouseMotionListener mouseMotionListener = new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent mouse) {
				if (arrow.getAim()) {
					arrow.aimArrow(mouse.getX(), mouse.getY());
				}
			}
		};
		panel.addMouseMotionListener(mouseMotionListener);

	}

	public void setStart(int[] loc) {
		startX = loc[0];
		startY = loc[1];
	}

	public void shoot(int mouseX, int mouseY) {
		if (difficulty == 1) {
			startX = x;
			startY = y;
		}
		shots++;
		if (shots == 69 && active) {
			sfx.horn();
		}
		float mX = (float) (mouseX);
		float mY = (float) (mouseY);

		float dx = getDiff(x, mX);
		float dy = getDiff(y, mY);
		float r = getDist(dx, dy);
		int[] power = new int[2];
		if (difficulty == 2) {
			power[0] = 18;
			power[1] = 4;
		} else {
			power[0] = 12;
			power[1] = 6;
		}
		if (r > 72) {
			xa = (float) (dx * power[0] / r);
			ya = (float) (dy * power[0] / r);
		} else {
			xa = (float) (dx / power[1]);
			ya = (float) (dy / power[1]);
		}
	}

	public void deactivate() {
		active = false;
	}

	public void resetShots() {
		shots = 0;
	}

	public boolean getWon() {
		return won;
	}

	public float getDiff(int a, int b) {
		return (float) (a - b);
	}

	public float getDiff(float a, float b) {
		return (float) (a - b);
	}

	public float getDist(int x, int y) {
		return (float) (Math.sqrt((x * x) + (y * y)));
	}

	public float getDist(float x, float y) {
		return (float) (Math.sqrt((x * x) + (y * y)));
	}

	public void decel(int decel, int increments) {
		if (Math.abs(xa) <= 0.7 && Math.abs(ya) <= 0.7) {
			resetSpeed();
		} else {
			float decelFactor = (float) (1 - (0.01 * decel / increment));
			xa *= decelFactor;
			ya *= decelFactor;
		}
	}

	public void move() {
		increment = ((int) Math.ceil(Math.max(Math.abs(xa), Math.abs(ya))));
		for (int i = 0; i < increment; i++) {
			xi = xa / increment;
			yi = ya / increment;
			x += xi;
			y += yi;
			if (collide) {
				collide = false;
			} else {
				interact(increment);
			}
			menu.repaint();
			panel.repaint();
		}
		arrow = new Arrow(panel, x, y);
	}

	public void interact(int increment) {
		if (!falling) {
			// floor interactions
			Floor[] floors = level.getFloors();
			Floor[] iFloors = new Floor[8];
			int iFloorCount = 0;
			int priority = 0;
			for (int i = 0; i < floors.length; i++) {
				float[] loc = new float[2];
				loc[0] = (float) floors[i].getLoc()[0];
				loc[1] = (float) floors[i].getLoc()[1];
				if (getIntersection(getDif(loc))) {
					iFloors[iFloorCount] = floors[i];
					iFloorCount++;
					if (floors[i].getPrior() > priority) {
						priority = floors[i].getPrior();
					}
				}
			}

			if (priority == 3 && difficulty != 0) {
				for (int n = 0; n < iFloorCount; n++) {
					if (iFloors[n].getPrior() == priority) {
						boost(iFloors[n].getDirec());
					}
				}
			} else {
				int decelNum = 0;
				for (int n = 0; n < iFloorCount; n++) {
					if (iFloors[n].getPrior() == priority) {
						decelNum = iFloors[n].getDecel();
					}
				}
				decel(decelNum, increment);
			}

			// wall interactions
			Wall[] walls = level.getWalls();
			for (int i = 0; i < walls.length; i++) {
				float[] wallLoc = new float[2];
				wallLoc[0] = walls[i].getLoc()[0] - xi;
				wallLoc[1] = walls[i].getLoc()[1] - yi;
				float[] hitDif = getDif(wallLoc);

				if (getIntersection(hitDif)) {
					int type = walls[i].getType();
					collide(hitDif, wallLoc, type);
					if (type == 0) {
						sfx.bounce();
					}
					if (type == 1) {
						sfx.stick();
					}
					if (type == 2) {
						sfx.bounceWall();
					}
				}
			}
			if (difficulty != 0) {
				// trap interactions
				Trap[] traps = level.getTraps();
				for (int i = 0; i < traps.length; i++) {
					float[] trapLoc = new float[2];
					trapLoc[0] = traps[i].getLoc()[0];
					trapLoc[1] = traps[i].getLoc()[1];
					float[] tempLoc = getDif(trapLoc);
					if (getIntersection(tempLoc)) {
						float[] loc = new float[2];
						loc[0] = (traps[i].getLoc()[0] + (sqSize / 2));
						loc[1] = (traps[i].getLoc()[1] + (sqSize / 2));
						float dx = x - (loc[0]);
						float dy = y - (loc[1]);
						// Hidden hole
						if (traps[i].getHidden()) {
							if (getDist(dx, dy) < rad) {
								sfx.hole();
								traps[i].unhide();
								falling(traps[i]);
								if (difficulty == 2) {
									traps[i].rehide();
								}
							}
						}
						// spikes
						if (traps[i].getType() % 10 == 0) {
							sfx.spikes();
							trapReset();
						}
						// hole
						if (traps[i].getType() % 10 == 2) {
							if (getDist(dx, dy) < rad) {
								sfx.hole();
								falling(traps[i]);
							}
						}
					}
				}
			}
			// goal floor win
			for (int i = 0; i < iFloorCount && !won; i++) {
				float[] iFloorLoc = { iFloors[i].getLoc()[0], iFloors[i].getLoc()[1] };
				if (iFloors[i].isGoal() && xa == 0 && ya == 0 && checkGoal(iFloorLoc)) {
					music.playGoal();
					level.win();
					won = true;
				}
			}
		}
	}

	public void trapReset() {
		x = startX;
		y = startY;
		xa = 0;
		ya = 0;
	}

	public boolean isMoving() {
		if (xa != 0 && ya != 0) {
			return true;
		} else {
			return false;
		}
	}

	public float[] getDif(float[] wall) {
		float[] close = getClose(wall);
		float[] dif = new float[2];
		dif[0] = x - close[0];
		dif[1] = y - close[1];
		return dif;
	}

	public float[] getClose(float[] wall) {
		float closeX = Math.max(wall[0], Math.min(x, wall[0] + sqSize));
		float closeY = Math.max(wall[1], Math.min(y, wall[1] + sqSize));
		float[] close = { closeX, closeY };
		return close;
	}

	public boolean checkGoal(float[] floor) {
		float closeX = Math.max(floor[0], Math.min(x, floor[0] + sqSize));
		float closeY = Math.max(floor[1], Math.min(y, floor[1] + sqSize));

		return (closeX == x && closeY == y);
	}

	public boolean getIntersection(float[] dif) {
		return (((dif[0]) * (dif[0])) + ((dif[1]) * (dif[1])) <= (rad * rad));
	}

	public void boost(int direc) {
		float strength = 1;
		if (difficulty == 1) {
			strength /= 2;
		}
		if (direc == 0) {
			xa -= strength;
		} else if (direc == 1) {
			ya -= strength;
		} else if (direc == 2) {
			xa += strength;
		} else if (direc == 3) {
			ya += strength;
		}
	}

	public float[] getLoc() {
		float[] loc = new float[2];
		loc[0] = x;
		loc[1] = y;
		return loc;
	}

	public void paint(Graphics2D g2d) {
		Ellipse2D.Float outer = new Ellipse2D.Float(x - rad, y - rad, ps, ps);
		g2d.setColor(new Color(255, 78, 35));
		g2d.fill(outer);
		Ellipse2D.Float inner = new Ellipse2D.Float(x - (ps / 3), y - (ps / 3), 2 * ps / 3, 2 * ps / 3);
		g2d.setColor(new Color(220, 70, 30));
		g2d.fill(inner);
		arrow.paint(g2d);
		g2d.setFont(new Font("Dialog", Font.BOLD, 15));
		g2d.drawString("Shots: " + String.valueOf(shots), 475, 20);
	}

	public int getShots() {
		return shots;
	}

	public void resetSpeed() {
		xa = 0;
		ya = 0;
	}

	public void falling(Trap hole) {
		falling = true;
		float tempRad = rad;
		x = hole.getLoc()[0] + (sqSize / 2);
		y = hole.getLoc()[1] + (sqSize / 2);
		resetSpeed();
		for (int i = 0; i < rad; i++) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
			}
			setRadius(rad - 1);
			panel.repaint();
		}
		setRadius(tempRad);
		trapReset();
		falling = false;
	}

	public void setRadius(float radius) {
		rad = radius;
		ps = radius * 2;
	}

	public void resetPuck() {
		x = startX;
		y = startY;
		resetSpeed();
	}

	public float dot(float x1, float y1, float x2, float y2) {
		return (x1 * x2) + (y1 * y2);
	}

	public float getAngle(float x, float y) {
		float angle = (float) (Math.toRadians(Math.atan(y / x)));
		if (x < 0) {
			angle += Math.PI;
		}
		return angle;
	}

	public float normalize(float a, float b) {
		return a / getDist(a, b);
	}

	public int[] getRelSquares() {
		int relx = (int) (gx % sqSize);
		int rely = (int) (y % sqSize);
		int[] rel = { relx, rely };
		return rel;
	}

	public void flipX(float elast) {
		xa *= -elast;
		ya *= elast;
		x -= xi;
		y += yi;
	}

	public void flipY(float elast) {
		xa *= elast;
		ya *= -elast;
		x += xi;
		y -= yi;
	}
	
	public void flipC(float[] dv, float[] wallLoc, float elast) {
		float xMult = (x-wallLoc[0])/Math.abs(x-wallLoc[0]);
		float yMult = (y-wallLoc[1])/Math.abs(y-wallLoc[1]);
		
		float temp = xa;
		xa = elast * ya * xMult;
		ya = elast * temp * yMult;
		
		x += xMult * yi;
		y += yMult * xi;
	}

	public void collide(float[] dv, float[] wallLoc, int type) {
		float elast = 0;
		if (type == 0) {
			elast = (float) 1;
		}
		if (type == 2) {
			elast = (float) 1.2;
		}
		if (Math.abs(dv[0]) > Math.abs(dv[1])) {
			if(xa * dv[0] >= 0)
			{
				flipY(elast);
			} else {
			flipX(elast);
			}
		} else  {
			if(ya * dv[1] >= 0)
			{
				flipX(elast);
			} else {
			flipY(elast);
			}
		}
	}

}