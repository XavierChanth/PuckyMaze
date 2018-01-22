import java.awt.*;

public class Wall {
	private int type;
	private int[] loc = new int[2];
	private int width;

	public Wall(int x, int y, int type, int width) {
		loc[0] = x;
		loc[1] = y;
		this.type = type;
		this.width = width;
	}

	public int[] getLoc() {
		return loc;
	}

	public int getType() {
		return type;
	}

	public void paint(Graphics2D g2d) {
		if (type == 0) {
			// Regular
			g2d.setColor(new Color(118, 224, 220));
			g2d.fillRect(loc[0], loc[1], width, width);
		} else if (type == 1) {
			// Sticky
			g2d.setColor(new Color(158, 211, 134));
			g2d.fillRect(loc[0], loc[1], width, width);
		} else if (type == 2) {
			// Bouncloc[1]
			g2d.setColor(new Color(17, 85, 204));
			g2d.fillRect(loc[0], loc[1], width, width);
		}
	}
}