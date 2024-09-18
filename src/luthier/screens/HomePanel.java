package luthier.screens;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class HomePanel extends CustomPanel {
	public HomePanel(JPanel mainPanel) {
	    super(mainPanel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JButton createOrderButton = new JButton("Criar Ordem de Serviço");
		createOrderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				navigate("createOrder");
			}
		});
		JButton clientsButton = new JButton("Clientes");
		JButton instrumentsButton = new JButton("Instrumentos");
		JButton notificationsButton = new JButton("Notificações");

	    add(createOrderButton);
	    add(clientsButton);
	    add(instrumentsButton);
	    add(notificationsButton);
	}
}
