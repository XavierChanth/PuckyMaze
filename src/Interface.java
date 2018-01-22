import javax.swing.*;
import java.awt.*;

class Interface extends JPanel {
	private static final long serialVersionUID = 1L;
	private MainMenu menu;

	public Interface(MainMenu menu) {
		super();
		super.setLayout(null);
		this.setVisible(true);
		this.setOpaque(true);
		this.menu = menu;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		menu.paint(g2d);
		super.paintComponents(g2d);
	}
}
