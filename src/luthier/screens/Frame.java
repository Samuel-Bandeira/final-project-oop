package luthier.screens;

import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame extends JFrame {
	public Frame() {
		//Box title
		super("Luthier Helper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Main panel configuration
		String defaultScreen = "home";
		JPanel mainPanel = new JPanel(new CardLayout());
		CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();

	    //Screens
	    CustomPanel loginPanel = new LoginPanel(mainPanel);
	    CustomPanel homePanel = new HomePanel(mainPanel);
	    CustomPanel registerPanel = new RegisterPanel(mainPanel);
	    CustomPanel createOrderPanel = new CreateOrderPanel(mainPanel);
	    
//	    createOrderPanel.addComponentListener(new ComponentAdapter() {
//	    	public void componentShown(ComponentEvent e) {
//	    		System.out.println("APARECI!");
//	    	}
//	    });
	    mainPanel.add(createOrderPanel, "createOrder");
	    mainPanel.add(loginPanel, "login");
	    mainPanel.add(registerPanel, "register");
	    mainPanel.add(homePanel, "home");
	    
	    //Application main Configuration
	    mainPanelLayout.show(mainPanel, defaultScreen);
	    add(mainPanel);
	    pack();
	}
	
}
