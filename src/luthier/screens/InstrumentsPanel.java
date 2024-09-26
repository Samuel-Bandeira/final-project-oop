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
import luthier.entities.UserAbstract;
import luthier.repositories.interfaces.IInstrumentRepository;
import luthier.singletons.InstrumentEdition;
import luthier.singletons.UserSession;

public class InstrumentsPanel extends CustomPanel {
	private boolean initialized = false;
	private IInstrumentRepository instrumentsRepository;

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
		Instrument[] instruments;
		if(UserSession.getInstance().getLoggedUser().role.equals("luthier")) {			
			instruments = instrumentsRepository.list();
		} else {
			System.out.println("user id " + loggedUser.id);
			instruments = instrumentsRepository.list(loggedUser.id);
		}
		
		Object[][] objectOrders = new Object[instruments.length][];
		
		if(instruments.length > 0) {			
			for (int i = 0; i < instruments.length; i++) {
				Object[] row = { instruments[i].id, instruments[i].name, instruments[i].user.firstName };
				objectOrders[i] = row;
			}
		}

		return objectOrders;
	}

	public InstrumentsPanel(JPanel mainPanel, IInstrumentRepository instrumentsRepository) {
		super(mainPanel);
		this.instrumentsRepository = instrumentsRepository;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		String[] columnNames = new String[] { "Id", "Instrumento", "Dono" };
		JTable table = new JTable();
		JButton editInstrumentBtn = new JButton("Editar");
		JScrollPane scrollPane = new JScrollPane(table);
		table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

		JButton addInstrumentBtn = new JButton("Inserir");
		addInstrumentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				navigate("createInstrument");
			}
		});

		editInstrumentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer rowId = table.getSelectedRow();

				if(rowId < 0) {
					JOptionPane.showMessageDialog(null, "Selecione uma linha para editar!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				System.out.println("rowId: " + rowId);
				Integer id = (Integer) table.getValueAt(rowId, 0);
                Instrument instrument = instrumentsRepository.find(id);
                InstrumentEdition.getInstance().setInstrumentEdition(instrument);
                navigate("editInstrument");
			}
		});
		
		JButton deleteInstrumentBtn = new JButton("Excluir");
		deleteInstrumentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Integer rowId = table.getSelectedRow();

				if(rowId < 0) {
					JOptionPane.showMessageDialog(null, "Selecione uma linha para deletar!", "Swing Tester", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Integer id = (Integer) table.getValueAt(rowId, 0);
				instrumentsRepository.remove(id);
                ((DefaultTableModel)table.getModel()).removeRow(rowId);
			}
		});

		this.addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				System.out.println("Screen loaded");
				initialized = true;
				table.setModel(new DefaultTableModel(getTableData(), columnNames));
				if(!UserSession.getInstance().getLoggedUser().role.equals("luthier")) {
					remove(editInstrumentBtn);
					remove(deleteInstrumentBtn);					
					revalidate();
					repaint();
				} else {
					add(editInstrumentBtn);
					add(deleteInstrumentBtn);	
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
		add(addInstrumentBtn);
		add(editInstrumentBtn);
		add(deleteInstrumentBtn);
		add(backButton);
	}
}
