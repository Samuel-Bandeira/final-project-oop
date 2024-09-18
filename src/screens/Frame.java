package screens;

import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
	public Frame() {
		//Box title
		super("Luthier Helper");

		//Main panel configuration
		JPanel mainPanel = new JPanel(new CardLayout());
		CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();

	    //Screens
	    CustomPanel loginPanel = new LoginPanel(mainPanel);
	    CustomPanel homePanel = new HomePanel(mainPanel);
	    CustomPanel registerPanel = new RegisterPanel(mainPanel);
	   
	    mainPanel.add(loginPanel, "login");
	    mainPanel.add(registerPanel, "register");
	    mainPanel.add(homePanel, "home");
	    
	    //Application main Configuration
	    mainPanelLayout.show(mainPanel, "home");
	    add(mainPanel);
	    pack();
	}
	
}
