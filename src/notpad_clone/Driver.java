package notpad_clone;

import javax.swing.SwingUtilities;

public class Driver {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Notepad();
			}
		});
	}
}
