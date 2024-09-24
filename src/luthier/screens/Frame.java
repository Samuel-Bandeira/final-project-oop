package luthier.screens;

import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import luthier.repositories.InstrumentJson;
import luthier.repositories.UserJson;

public class Frame extends JFrame {
	public Frame() {
		//Box title
		super("Luthier Helper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Repositories
		InstrumentJson instrumentJson = new InstrumentJson();
		UserJson userJson = new UserJson();

		//Main panel configuration
		String defaultScreen = "createOrder";
		JPanel mainPanel = new JPanel(new CardLayout());
		CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();

	    //Screens
	    CustomPanel loginPanel = new LoginPanel(mainPanel, userJson);
	    CustomPanel homePanel = new HomePanel(mainPanel);
	    CustomPanel registerPanel = new RegisterPanel(mainPanel, userJson);
	    CustomPanel ordersPanel = new OrdersPanel(mainPanel);
	    CustomPanel clientsPanel = new Clients(mainPanel, userJson);
	    CustomPanel createInstrument = new CreateInstrument(mainPanel, instrumentJson);
	    CustomPanel createOrder = new CreateOrder(mainPanel, instrumentJson);
	    
	    mainPanel.add(ordersPanel, "orders");
	    mainPanel.add(loginPanel, "login");
	    mainPanel.add(registerPanel, "register");
	    mainPanel.add(homePanel, "home");
	    mainPanel.add(createInstrument, "createInstrument");
	    mainPanel.add(createOrder, "createOrder");
	    mainPanel.add(clientsPanel, "clients");
	    
	    //Application main Configuration
	    mainPanelLayout.show(mainPanel, defaultScreen);
	    add(mainPanel);
	    pack();
	}
	
}
