package luthier.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import luthier.entities.Order;
import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.repositories.interfaces.IUserRepository;
import luthier.singletons.UserSession;

public class Clients extends CustomPanel {
	private boolean initialized = false;
	private IUserRepository userRepository;
	
	public Object[][] getTableData() {
		UserAbstract[] users = userRepository.listClients();
		Object[][] objectUsers = new Object[users.length][];
		
		for(int i = 0; i < users.length; i++) {
			Object[] row = { users[i].id, users[i].firstName, users[i].lastName, users[i].email };
			objectUsers[i] = row;
		}
		
		return objectUsers;
	}
	
	public Clients(JPanel mainPanel, IUserRepository userRepository) {
		super(mainPanel);
		this.userRepository = userRepository;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] columnNames = new String[] { "Id", "Primeiro Nome", "Último Nome", "Email" };
		JTable table = new JTable();
		JButton deleteClientBtn = new JButton("Excluir");
		JScrollPane scrollPane = new JScrollPane(table);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		JButton addClientBtn = new JButton("Inserir");
		addClientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("home");
			}
		});
		
		deleteClientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer rowId = table.getSelectedRow();

				if(rowId < 0) {
					JOptionPane.showMessageDialog(null, "Selecione uma linha para gerar noficação!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Integer id = (Integer) table.getValueAt(rowId, 0);
				userRepository.remove(id);
                ((DefaultTableModel)table.getModel()).removeRow(rowId);
			}
		});
		
		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				System.out.println("Screen loaded");
				initialized = true;
				table.setModel(new DefaultTableModel(getTableData(), columnNames));
			}
			
			public void componentHidden(ComponentEvent e) {
				if(initialized) {					
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
		add(backButton);
		add(deleteClientBtn);
	}
}
