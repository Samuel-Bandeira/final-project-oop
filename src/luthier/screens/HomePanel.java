package luthier.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import luthier.entities.UserAbstract;
import luthier.singletons.UserSession;

public class HomePanel extends CustomPanel {
	private Boolean initialized = false;
	
	public HomePanel(JPanel mainPanel) {
	    super(mainPanel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton createOrderButton = new JButton("Ordens Serviço");
		createOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				navigate("orders");
			}
		});
		JButton clientsButton = new JButton("Clientes");
		clientsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				navigate("clients");
			}
		});
		
		JButton instrumentsButton = new JButton("Instrumentos");
		instrumentsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				navigate("instruments");
			}
		});
		JButton notificationsButton = new JButton("Notificações");
		
		JButton logoutButton = new JButton("Sair");
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UserSession.getInstance().setLoggedUser(null);
				navigate("login");
			}
		});
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				System.out.println("Home screen loaded");
				initialized = true;
				UserAbstract user = UserSession.getInstance().getLoggedUser();
				System.out.println("logged user " + user.firstName);
				add(createOrderButton);
			    if(!user.role.equals("luthier")) {
			    	remove(clientsButton);
//			    	remove(notificationsButton);
			    	revalidate();
			    	repaint();
			    } else {
			    	add(clientsButton);
//				    add(notificationsButton);
				    revalidate();
			    	repaint();
			    }
			}
			
			public void componentHidden(ComponentEvent e) {
				if(initialized) {					
					System.out.println("Home screen removed");
				}
			}
		});
		
		add(createOrderButton);
	    add(instrumentsButton);
	    add(logoutButton);
	}
}
