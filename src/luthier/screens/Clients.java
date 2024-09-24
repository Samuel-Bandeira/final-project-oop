package luthier.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import luthier.entities.User;
import luthier.repositories.interfaces.IUserRepository;

public class Clients extends CustomPanel {
	private boolean initialized = false;
	private IUserRepository userJson;
	
	public Object[][] getTableData() {
		User[] users = userJson.listar();
		Object[][] objectUsers = new Object[users.length][];
		
		for(int i = 0; i < users.length; i++) {
			if(users[i].role.equals("client")) {				
				Object[] row = { users[i].id, users[i].firstName, users[i].lastName, users[i].email };
				objectUsers[i] = row;
			}
		}
		
		return objectUsers;
	}
	
	public Clients(JPanel mainPanel, IUserRepository userJson) {
		super(mainPanel);
		this.userJson = userJson;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] columnNames = new String[] { "Id", "Primeiro Nome", "Último Nome", "Email" };
		JTable table = new JTable();
		JButton editOrderBtn = new JButton("Editar");
		JButton deleteOrderBtn = new JButton("Excluir");
		JScrollPane scrollPane = new JScrollPane(table);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		
		JButton addOrderBtn = new JButton("Inserir");
		addOrderBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("home");
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
		
		add(scrollPane, BorderLayout.CENTER);
//		add(editOrderBtn);
//		add(deleteOrderBtn);
	}
}
