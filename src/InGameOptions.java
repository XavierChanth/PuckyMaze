import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class InGameOptions implements ActionListener {
	private JPanel panel;
	private MainMenu menu;
	private Options options;
	private boolean optionsOpened = false;
	private boolean opened = false;

	private JButton igoButton;
	private JButton backButton;

	public InGameOptions(JPanel panel, MainMenu menu, Dimension size, Options options) {
		this.panel = panel;
		this.menu = menu;
		this.options = options;

		// Options Button
		igoButton = new JButton("Options");
		igoButton.setPreferredSize(size);
		igoButton.setBounds(260, 600, size.width, size.height);
		igoButton.setActionCommand("igo");
		igoButton.addActionListener(this);

		// Back Button
		backButton = new JButton("Return Home");
		backButton.setPreferredSize(size);
		backButton.setBounds(60, 600, size.width, size.height);
		backButton.setActionCommand("Return Home");
		backButton.addActionListener(this);
	}

	public void enableIGO() {
		igoButton.setEnabled(true);
	}

	public void disableIGO() {
		igoButton.setEnabled(false);
	}

	public boolean getOpened() {
		return opened;
	}

	public boolean getOptionsOpened() {
		return optionsOpened;
	}

	public void open() {
		panel.add(igoButton);
		panel.add(backButton);
		opened = true;
	}

	public void close() {
		if (optionsOpened) {
			closeOptions();
		}
		panel.remove(igoButton);
		panel.remove(backButton);
		opened = false;
	}

	public void vicScreenClose() {
		if (optionsOpened) {
			closeOptions();
		}
		opened = false;
	}

	public void openOptions() {

		igoButton.setText("Close Options");
		options.openIGO();
		optionsOpened = !optionsOpened;
	}

	public void closeOptions() {
		igoButton.setText("Options");
		options.close();
		optionsOpened = !optionsOpened;
		menu.resetVolumes();
	}

	public void actionPerformed(ActionEvent action) {
		String act = action.getActionCommand();
		if ("igo".equals(act)) {
			if (optionsOpened) {
				closeOptions();
			} else {
				openOptions();
			}
		}
		if ("Return Home".equals(act)) {
			menu.returnHome();
			close();
		}
	}
}