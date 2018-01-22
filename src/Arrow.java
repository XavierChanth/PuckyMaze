import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Arrow {
	private float x;
	private float y;
	private static float xHead;
	private static float yHead;
	// private static boolean aiming = false;
	private static boolean aim = false;

	public Arrow(JPanel panel, float x, float y) {
		this.x = x;
		this.y = y;

	}

	public void aimArrow(int mouseX, int mouseY) {
		float dx = getDiff(x, mouseX);
		float dy = getDiff(y, mouseY);
		float r = getDist(dx, dy);
		if (r > 72) {
			xHead = x + dx * 72 / r;
			yHead = y + dy * 72 / r;
		} else {
			xHead = x + dx;
			yHead = y + dy;
		}
		aim = true;
	}

	public boolean getAim() {
		return aim;
	}

	public void setAim(boolean a) {
		aim = a;
	}

	public int getDiff(int a, int b) {
		return a - b;
	}

	public float getDiff(float a, float b) {
		return a - b;
	}

	public int getDist(int x, int y) {
		return (int) (Math.sqrt((x * x) + (y * y)));
	}

	public float getDist(float x, float y) {
		return (float) (Math.sqrt((x * x) + (y * y)));
	}

	public void paint(Graphics2D g2d) {
		if (aim) {
			g2d.setColor(Color.BLACK);
			Line2D.Float line = new Line2D.Float(x, y, xHead, yHead);
			g2d.draw(line);
		}
	}
}