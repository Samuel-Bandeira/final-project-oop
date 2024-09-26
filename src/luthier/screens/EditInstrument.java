package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import luthier.entities.Instrument;
import luthier.entities.Order;
import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.repositories.InstrumentJson;
import luthier.repositories.interfaces.IInstrumentRepository;
import luthier.singletons.InstrumentEdition;
import luthier.singletons.OrderEdition;
import luthier.singletons.UserSession;

public class EditInstrument extends CustomPanel {
	private IInstrumentRepository instrumentRepository; 
	private Boolean initialized = false;
	
	public void handleEditInstrument(JTextField nameInput) {
		String name = nameInput.getText();
		
		if(name.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Insira o nome do instrumento");
	        return;
		}
		
		Instrument instrumentEdition = InstrumentEdition.getInstance().getInstrumentEdition();
		Instrument editedInstrument = new Instrument(name, instrumentEdition.user);
		editedInstrument.setId(instrumentEdition.id);
		this.instrumentRepository.edit(instrumentEdition.id, editedInstrument);
		nameInput.setText("");
		navigate("instruments");
	}
	
	public EditInstrument(JPanel mainPanel, IInstrumentRepository instrumentJson) {
		super(mainPanel);
		this.instrumentRepository = instrumentJson;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel nameLabel = new JLabel("Nome do instrumento");
		JTextField nameInput = new JTextField("");
		nameInput.setMaximumSize(new Dimension(300, 30));

		JButton editInstrumentButton = new JButton("Editar Instrumento");
		JButton backButton = new JButton("Voltar");
		
		editInstrumentButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleEditInstrument(nameInput);
			}
		});
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				initialized = true;
				Instrument instrumentEdition = InstrumentEdition.getInstance().getInstrumentEdition();
				nameInput.setText(instrumentEdition.name);
				revalidate();
				repaint();
			}

			public void componentHidden(ComponentEvent e) {
				if (initialized) {
					System.out.println("Screen removed");
				}
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("instruments");
			}
		});
		
		
		add(nameLabel);
		add(nameInput);
		add(editInstrumentButton);
		add(backButton);
	}
}
