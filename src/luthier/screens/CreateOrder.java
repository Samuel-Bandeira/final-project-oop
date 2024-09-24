package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import luthier.entities.Instrument;
import luthier.repositories.interfaces.IInstrumentRepository;
import luthier.repositories.interfaces.IUserRepository;

public class CreateOrder extends CustomPanel {
	private IInstrumentRepository instrumentRepository;
	
	public CreateOrder(JPanel mainPanel, IInstrumentRepository instrumentRepository) {
		super(mainPanel);
		this.instrumentRepository = instrumentRepository;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel serviceTypeLabel = new JLabel("Tipo de Serviço");
		JTextField serviceTypeField = new JTextField("");
		serviceTypeField.setMaximumSize(new Dimension(300, 30));
		
		JLabel instrumentLabel = new JLabel("Instrumento");
		JTextField instrumentField = new JTextField("");
		Instrument[] instruments = instrumentRepository.listar();
		
		String[] options = new String[instruments.length];
		for(int i = 0; i < instruments.length; i++) {
			options[i] = instruments[i].name;
		}
		
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboBox.setSize(new Dimension(150, 30));
        	
        JLabel partsLabel  = new JLabel("Instrumento");
		JTextField partsField = new JTextField("");
//		JButton loginButton = new JButton("Login");
//		loginButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				handleLogin(emailField, passwordField);
//			}
//		});
//		
//		JButton registerButton = new JButton("Registrar-se");
//		registerButton.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				handleRegister();
//			}
//		});
		
		add(serviceTypeLabel);
		add(serviceTypeField);
		add(instrumentLabel);
		add(comboBox);
		add(instrumentField);
		add(partsLabel);
		add(partsField);
	}
}
