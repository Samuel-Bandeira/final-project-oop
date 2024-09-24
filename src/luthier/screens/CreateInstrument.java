package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import luthier.entities.Instrument;
import luthier.repositories.InstrumentJson;
import luthier.repositories.interfaces.IInstrumentRepository;

public class CreateInstrument extends CustomPanel {
	private IInstrumentRepository instrumentRepository; 
	
	public void addInstrument(JTextField nameInput) {
		String name = nameInput.getText();
		this.instrumentRepository.inserir(new Instrument(name));
	}
	
	public CreateInstrument(JPanel mainPanel, IInstrumentRepository instrumentJson) {
		super(mainPanel);
		this.instrumentRepository = instrumentJson;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel nameLabel = new JLabel("Nome do instrumento");
		JTextField nameInput = new JTextField("");
		nameInput.setMaximumSize(new Dimension(300, 30));

		JButton addInstrumentButton = new JButton("Login");
		addInstrumentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addInstrument(nameInput);
			}
		});
		
		add(nameLabel);
		add(nameInput);
		add(addInstrumentButton);
	}
}
