package luthier.screens;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CreateOrderPanel extends CustomPanel {
	private boolean initialized = false;
	
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
		return new Object[][] { { "Reparo", "Violão Modelo X", "Braço", "Gustavo Ferreira" },
			{ "Reparo", "Violão Modelo Y", "Braço", "Samuel Bandeira" },
			{ "Reparo", "Violão Modelo Z", "Braço", "Cleber Arnaldo" } };
	}

	public CreateOrderPanel(JPanel mainPanel) {
		super(mainPanel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] columnNames = new String[] { "Tipo de Serviço", "Instrumentos", "Peças", "Cliente" };
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
		add(addOrderBtn);
		add(editOrderBtn);
		add(deleteOrderBtn);
	}
}
