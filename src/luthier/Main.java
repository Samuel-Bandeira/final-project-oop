package luthier;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame frame = new screens.Frame();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setResizable(false);
		frame.setVisible(true);
	}	

}
