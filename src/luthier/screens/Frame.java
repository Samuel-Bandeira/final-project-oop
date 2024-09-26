package luthier.screens;

import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import luthier.repositories.InstrumentJson;
import luthier.repositories.OrderJson;
import luthier.repositories.UserJson;

public class Frame extends JFrame {
	public Frame() {
		//Box title
		super("Luthier Helper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//Repositories
		InstrumentJson instrumentJson = new InstrumentJson();
		UserJson userJson = new UserJson();
		OrderJson orderJson = new OrderJson();
		
		//Main panel configuration
		String defaultScreen = "login";
		JPanel mainPanel = new JPanel(new CardLayout());
		CardLayout mainPanelLayout = (CardLayout) mainPanel.getLayout();

	    //Screens
	    CustomPanel loginPanel = new LoginPanel(mainPanel, userJson);
	    CustomPanel homePanel = new HomePanel(mainPanel);
	    CustomPanel registerPanel = new RegisterPanel(mainPanel, userJson);
	    CustomPanel ordersPanel = new OrdersPanel(mainPanel, orderJson);
	    CustomPanel clientsPanel = new Clients(mainPanel, userJson);
	    CustomPanel createInstrument = new CreateInstrument(mainPanel, instrumentJson);
	    CustomPanel createOrder = new CreateOrder(mainPanel, instrumentJson, orderJson);
	    CustomPanel editOrder = new EditOrder(mainPanel, instrumentJson, orderJson);
	    CustomPanel instrumentsPanel = new InstrumentsPanel(mainPanel, instrumentJson);
	    CustomPanel editInstrument = new EditInstrument(mainPanel, instrumentJson);
	    
	    mainPanel.add(ordersPanel, "orders");
	    mainPanel.add(loginPanel, "login");
	    mainPanel.add(registerPanel, "register");
	    mainPanel.add(homePanel, "home");
	    mainPanel.add(createInstrument, "createInstrument");
	    mainPanel.add(createOrder, "createOrder");
	    mainPanel.add(clientsPanel, "clients");
	    mainPanel.add(editOrder, "editOrder");
	    mainPanel.add(instrumentsPanel, "instruments");
	    mainPanel.add(editInstrument, "editInstrument");
	    //Application main Configuration
	    mainPanelLayout.show(mainPanel, defaultScreen);
	    add(mainPanel);
	    pack();
	}
	
}
