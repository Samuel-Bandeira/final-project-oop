package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import luthier.entities.Instrument;
import luthier.entities.Order;
import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.entities.UserClient;
import luthier.entities.UserLuthier;
import luthier.repositories.interfaces.IInstrumentRepository;
import luthier.repositories.interfaces.IOrderRepository;
import luthier.singletons.UserSession;

public class CreateOrder extends CustomPanel {
	private IInstrumentRepository instrumentRepository;
	private IOrderRepository orderRepository;
	private Boolean initialized = false; 
	
	private void handleCreateOrder(JTextField serviceTypeField, JComboBox<String> instrumentsSelect) {
		String serviceTypeFieldText = serviceTypeField.getText();
		String instrumentSelected = (String) instrumentsSelect.getSelectedItem();
		
		if(serviceTypeFieldText.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Preencha o tipo de serviço");
	        return;
		} 
		
		UserAbstract user = UserSession.getInstance().getLoggedUser();
		Instrument instrument = instrumentRepository.find(instrumentSelected);
		User userToAdd = (User) user;

		orderRepository.add(new Order(userToAdd, instrument, "", serviceTypeFieldText, "Recebido"));		
		serviceTypeField.setText("");
		instrumentsSelect.setSelectedIndex(0);
		
		navigate("orders");
	}

	public CreateOrder(JPanel mainPanel, IInstrumentRepository instrumentRepository, IOrderRepository orderRepository) {
		super(mainPanel);
		this.instrumentRepository = instrumentRepository;
		this.orderRepository = orderRepository;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel serviceTypeLabel = new JLabel("Tipo de Serviço");
		JTextField serviceTypeField1 = new JTextField("");
		serviceTypeField1.setMaximumSize(new Dimension(600, 30));

		JLabel instrumentLabel = new JLabel("Instrumento");
		
		String[] instrumentsSelectOptions = {};

		JComboBox<String> instrumentsSelect = new JComboBox<>(instrumentsSelectOptions);
		instrumentsSelect.setMaximumSize(new Dimension(600, 30));

		
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				initialized = true;
				User loggedUser = (User) UserSession.getInstance().getLoggedUser();
				Instrument[] instruments;

				if(loggedUser.role.equals("luthier")) {
					instruments = instrumentRepository.list();
				} else {
					instruments = instrumentRepository.list(loggedUser.id);
				}
				
				String[] instrumentsSelectOptions = new String[instruments.length];
				for (int i = 0; i < instruments.length; i++) {
					instrumentsSelectOptions[i] = instruments[i].name;
				}
				
				instrumentsSelect.setModel(new DefaultComboBoxModel<String>(instrumentsSelectOptions));
			}

			public void componentHidden(ComponentEvent e) {
				if (initialized) {
					System.out.println("Screen removed");
				}
			}
		});
		
		JLabel partsLabel = new JLabel("Descrição de Peças");
		JTextField partsField = new JTextField("");
		partsField.setMaximumSize(new Dimension(600, 100));

		JButton createOrderButton = new JButton("Criar Pedido");
		createOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleCreateOrder(serviceTypeField1, instrumentsSelect);
			}
		});
		
		JButton backButton = new JButton("Voltar");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("orders");
			}
		});
		
		add(serviceTypeLabel);
		add(serviceTypeField1);
		add(instrumentLabel);
		add(instrumentsSelect);
//		add(partsLabel);
//		add(partsField);
		add(createOrderButton);
		add(backButton);
	}
}
