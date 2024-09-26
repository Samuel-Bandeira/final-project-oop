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

import luthier.entities.Instrument;
import luthier.entities.Order;
import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.entities.UserLuthier;
import luthier.repositories.interfaces.IInstrumentRepository;
import luthier.repositories.interfaces.IOrderRepository;
import luthier.singletons.OrderEdition;
import luthier.singletons.UserSession;

public class EditOrder extends CustomPanel {
	private IInstrumentRepository instrumentRepository;
	private IOrderRepository orderRepository;
	private Boolean initialized = false;

	private void handleEditOrder(JTextField serviceTypeField, JComboBox<String> instrumentsSelect,
			JTextField partsField, UserAbstract user, JComboBox<String> status) {
		String serviceTypeFieldText = serviceTypeField.getText();
		String instrumentSelected = (String) instrumentsSelect.getSelectedItem();
		String statusItem = (String) status.getSelectedItem();
		String partsFieldText = partsField.getText();

		if (serviceTypeFieldText.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha o tipo de serviço");
			return;
		}

		Order orderEdition = OrderEdition.getInstance().getOrderEdition();
		Instrument instrument = instrumentRepository.find(instrumentSelected);
		User userToAdd = (User) user;
		Order orderEdited = new Order(userToAdd, instrument, partsFieldText, serviceTypeFieldText, statusItem);
		orderEdited.setId(orderEdition.id);
		orderRepository.edit(orderEdition.id, orderEdited);
		OrderEdition.getInstance().clear();
		navigate("orders");
	}

	public EditOrder(JPanel mainPanel, IInstrumentRepository instrumentRepository, IOrderRepository orderRepository) {
		super(mainPanel);
		this.instrumentRepository = instrumentRepository;
		this.orderRepository = orderRepository;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel serviceTypeLabel = new JLabel("Tipo de Serviço");
		JTextField serviceTypeField = new JTextField("");
		serviceTypeField.setMaximumSize(new Dimension(600, 30));

		JLabel instrumentLabel = new JLabel("Instrumento");
		String[] instrumentsSelectOptions = {};
		JComboBox<String> instrumentsSelect = new JComboBox<String>(instrumentsSelectOptions);
		instrumentsSelect.setMaximumSize(new Dimension(600, 30));

		String[] statusOptions = { "Recebido", "Em Conserto", "Em Manutenção", "Pronto", "Em Fabricação" };
		JComboBox<String> statusSelect = new JComboBox<>(statusOptions);
		statusSelect.setMaximumSize(new Dimension(600, 30));

		JLabel partsLabel = new JLabel("Peças");
		JTextField partsField = new JTextField("");
		partsField.setMaximumSize(new Dimension(600, 100));

		JButton editOrderButton = new JButton("Editar Pedido");
		editOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Order orderEdition = OrderEdition.getInstance().getOrderEdition();
				handleEditOrder(serviceTypeField, instrumentsSelect, partsField, orderEdition.user, statusSelect);
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				initialized = true;
				System.out.println("Screen initialized");
				Order orderEdition = OrderEdition.getInstance().getOrderEdition();
				System.out.println(orderEdition.serviceType);
				System.out.println(orderEdition.instrument.name);
				System.out.println(orderEdition.instrumentParts);
				serviceTypeField.setText(orderEdition.serviceType);
				System.out.println("Instrument name: " + orderEdition.instrument.name);
				partsField.setText(orderEdition.instrumentParts);				
				Instrument[] instruments = instrumentRepository.list();
				String[] instrumentsSelectOptions = new String[instruments.length];
				for (int i = 0; i < instruments.length; i++) {
					instrumentsSelectOptions[i] = instruments[i].name;
				}
				
				instrumentsSelect.setModel(new DefaultComboBoxModel<String>(instrumentsSelectOptions));
				instrumentsSelect.setSelectedItem(orderEdition.instrument.name);
				revalidate();
				repaint();
			}

			public void componentHidden(ComponentEvent e) {
				if (initialized) {
					System.out.println("Screen removed");
				}
			}
		});

		JButton backButton = new JButton("Voltar");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("orders");
			}
		});

		add(serviceTypeLabel);
		add(serviceTypeField);
		add(instrumentLabel);
		add(instrumentsSelect);
		add(partsLabel);
		add(partsField);
		add(statusSelect);
		add(editOrderButton);
		add(backButton);
	}
}
