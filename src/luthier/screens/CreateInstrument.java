package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import luthier.entities.Instrument;
import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.repositories.InstrumentJson;
import luthier.repositories.interfaces.IInstrumentRepository;
import luthier.singletons.UserSession;

public class CreateInstrument extends CustomPanel {
	private IInstrumentRepository instrumentRepository; 
	
	public void addInstrument(JTextField nameInput) {
		String name = nameInput.getText();
		
		if(name.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Insira o nome do instrumento");
	        return;
		}
		
		User userSession = (User) UserSession.getInstance().getLoggedUser();
		System.out.println("User session " + userSession.firstName + " id: " + userSession.id);
		this.instrumentRepository.add(new Instrument(name, userSession));
		nameInput.setText("");
		navigate("instruments");
	}
	
	public CreateInstrument(JPanel mainPanel, IInstrumentRepository instrumentJson) {
		super(mainPanel);
		this.instrumentRepository = instrumentJson;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel nameLabel = new JLabel("Nome do instrumento");
		JTextField nameInput = new JTextField("");
		nameInput.setMaximumSize(new Dimension(300, 30));

		JButton addInstrumentButton = new JButton("Adicionar Instrumento");
		JButton backButton = new JButton("Voltar");
		
		addInstrumentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addInstrument(nameInput);
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("instruments");
			}
		});
		
		
		add(nameLabel);
		add(nameInput);
		add(addInstrumentButton);
		add(backButton);
	}
}
