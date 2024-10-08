package luthier.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import luthier.entities.Instrument;
import luthier.entities.Order;
import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.repositories.interfaces.IOrderRepository;
import luthier.singletons.OrderEdition;
import luthier.singletons.UserSession;

public class OrdersPanel extends CustomPanel {
	private boolean initialized = false;
	private IOrderRepository orderRepository;

	public static Object[][] addElementToArray(Object[][] original, Object[] newElement) {
		Object[][] newArray = new Object[original.length + 1][];

		for (int i = 0; i < original.length; i++) {
			newArray[i] = original[i];
		}
		System.out.println("New element" + newElement[0]);
		newArray[original.length] = newElement;

		return newArray;
	}

	public Object[][] getTableData() {
		UserAbstract loggedUser = UserSession.getInstance().getLoggedUser();
		Order[] orders;
		if(UserSession.getInstance().getLoggedUser().role.equals("luthier")) {
			orders = orderRepository.list();
		} else {
			System.out.println("user id " + loggedUser.id);
			orders = orderRepository.list(loggedUser.id);
		}
		
		Object[][] objectOrders = new Object[orders.length][];
		
		if(orders.length > 0) {			
			for (int i = 0; i < orders.length; i++) {
				Object[] row = { orders[i].id, orders[i].serviceType, orders[i].instrument.name, orders[i].instrumentParts, orders[i].user.firstName, orders[i].status };
				objectOrders[i] = row;
			}
		}

		return objectOrders;
	}

	public OrdersPanel(JPanel mainPanel, IOrderRepository orderRepository) {
		super(mainPanel);
		this.orderRepository = orderRepository;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] columnNames = new String[] { "Id", "Tipo de Serviço", "Instrumento", "Peças", "Cliente", "Status" };
		JTable table = new JTable();
		JButton editOrderBtn = new JButton("Editar");
		JScrollPane scrollPane = new JScrollPane(table);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		JButton addOrderBtn = new JButton("Inserir");
		addOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("createOrder");
			}
		});

		editOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer rowId = table.getSelectedRow();

				if(rowId < 0) {
					JOptionPane.showMessageDialog(null, "Selecione uma linha para editar!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Integer id = (Integer) table.getValueAt(rowId, 0);
                Order order = orderRepository.find(id);
                OrderEdition.getInstance().setOrderEdition(order);
                navigate("editOrder");
			}
		});
		
		JButton deleteOrderBtn = new JButton("Excluir");
		deleteOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer rowId = table.getSelectedRow();

				if(rowId < 0) {
					JOptionPane.showMessageDialog(null, "Selecione uma linha para deletar!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Integer id = (Integer) table.getValueAt(rowId, 0);
                orderRepository.delete(id);
                ((DefaultTableModel)table.getModel()).removeRow(rowId);
			}
		});
		
		JButton notificationButton = new JButton("Gerar Notificação");
		notificationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer rowId = table.getSelectedRow();

				if(rowId < 0) {
					JOptionPane.showMessageDialog(null, "Selecione uma linha para gerar noficação!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Integer id = (Integer) table.getValueAt(rowId, 0);
				Order order = orderRepository.find(id);
				
				String notificationString = "O Instrumento " + order.instrument.name + " em nome do cliente "
						+ order.user.firstName + " " + order.user.lastName + " está " + order.status;
				
				if(order.instrumentParts.equals("")) {
					notificationString += " não necessitou de material peças.";
				} else {
					notificationString += " necessitou das seguinte(s) partes: " + order.instrumentParts;
				}
				
				JOptionPane.showMessageDialog(null, notificationString);
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				System.out.println("Screen loaded");
				initialized = true;
				table.setModel(new DefaultTableModel(getTableData(), columnNames));
				
				if(!UserSession.getInstance().getLoggedUser().role.equals("luthier")) {
					remove(editOrderBtn);
					remove(deleteOrderBtn);	
					remove(notificationButton);
					revalidate();
					repaint();
				} else {
					add(editOrderBtn);
					add(deleteOrderBtn);	
					add(notificationButton);
					revalidate();
					repaint();
				}
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
				navigate("home");
			}
		});

		add(scrollPane, BorderLayout.CENTER);
		add(addOrderBtn);
		add(editOrderBtn);
		add(deleteOrderBtn);
		add(backButton);
		add(notificationButton);
	}
}
