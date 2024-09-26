package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import luthier.entities.User;
import luthier.entities.UserAbstract;
import luthier.entities.UserClient;
import luthier.entities.UserLuthier;
import luthier.repositories.interfaces.IUserRepository;
import luthier.singletons.UserSession;

public class RegisterPanel extends CustomPanel {
	private IUserRepository userRepository;

	private void handleRegister(JTextField firstNameField, JTextField lastNameField, JTextField emailField,
			JPasswordField passwordField, JPasswordField passwordConfirmField, JComboBox<String> luthierOrClient) {
		String firstName = String.valueOf(firstNameField.getText());
		String lastName = String.valueOf(lastNameField.getText());
		String email = String.valueOf(emailField.getText());
		String password = String.valueOf(passwordField.getPassword());
		String passwordConfirm = String.valueOf(passwordConfirmField.getPassword());
		String message = "Erro(s):\n";
		String luthierOrClientOption = String.valueOf(luthierOrClient.getSelectedItem());
		
		System.out.println("Fname:" + firstName);
		System.out.println("lname:" + lastName);
		System.out.println("email:" + email);
		System.out.println("password:" + password);
		System.out.println("password conf:" + passwordConfirm);

		Boolean error = false;

		if (firstName.isBlank()) {
			error = true;
			message += " - Preencha o primeiro nome\n";
		}

		if (lastName.isBlank()) {
			error = true;
			message += " - Preencha o último nome\n";
		}

		if (email.isBlank()) {
			error = true;
			message += " - Preencha o email\n";
		}

		if (password.isBlank()) {
			error = true;
			message += " - Preencha a senha\n";
		}

		if (password.length() < 6) {
			error = true;
			message += " - Sua senha deve ter no mínimo 6 caracteres";
		}

		if (passwordConfirm.isBlank()) {
			error = true;
			message += " - Confirme a senha\n";
		}

		if (!passwordConfirm.equals(password)) {
			error = true;
			message += " - As senhas não são iguais\n";
		}

		if (error) {
			JOptionPane.showMessageDialog(null, message, "Swing Tester", JOptionPane.ERROR_MESSAGE);
		} else {
			User user;
			
			if(luthierOrClientOption == "luthier") {
				user = new UserLuthier(firstName, lastName, email, password, luthierOrClientOption);
			} else {
				user = new UserClient(firstName, lastName, email, password, luthierOrClientOption);
			}
			
			userRepository.add(user);
			UserSession.getInstance().setLoggedUser(user);
			
			firstNameField.setText("");
			lastNameField.setText("");
			emailField.setText("");
			passwordField.setText("");
			passwordConfirmField.setText("");
			navigate("home");
		}

	}

	public RegisterPanel(JPanel mainPanel, IUserRepository userJson) {
		super(mainPanel);
		this.userRepository = userJson;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel firstNameLabel = new JLabel("Primeiro Nome:");
		JTextField firstNameField = new JTextField("");
		firstNameField.setMaximumSize(new Dimension(300, 30));

		JLabel lastNameLabel = new JLabel("Último Nome:");
		JTextField lastNameField = new JTextField("");
		lastNameField.setMaximumSize(new Dimension(300, 30));

		JLabel emailLabel = new JLabel("Email");
		JTextField emailField = new JTextField("");
		emailField.setMaximumSize(new Dimension(300, 30));

		JLabel passwordLabel = new JLabel("Senha");
		JPasswordField passwordField = new JPasswordField("");
		passwordField.setMaximumSize(new Dimension(300, 30));

		JLabel passwordConfirmLabel = new JLabel("Confirmar Senha");
		JPasswordField passwordConfirmField = new JPasswordField("");
		passwordConfirmField.setMaximumSize(new Dimension(300, 30));

		
		String[] options = {"luthier", "cliente"};
		JComboBox<String> isLuthierOrClientSelect = new JComboBox<>(options);
		isLuthierOrClientSelect.setMaximumSize(new Dimension(300, 30));

		JButton registerButton = new JButton("Registrar-se");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleRegister(firstNameField, lastNameField, emailField, passwordField, passwordConfirmField, isLuthierOrClientSelect);
			}
		});

		JButton backButton = new JButton("Voltar");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				firstNameField.setText("");
				lastNameField.setText("");
				emailField.setText("");
				passwordField.setText("");
				passwordConfirmField.setText("");
				navigate("login");
			}
		});
		add(firstNameLabel);
		add(firstNameField);
		add(lastNameLabel);
		add(lastNameField);
		add(emailLabel);
		add(emailField);
		add(passwordLabel);
		add(passwordField);
		add(passwordConfirmLabel);
		add(passwordConfirmField);
		add(isLuthierOrClientSelect);
		add(registerButton);
		add(backButton);
	}
}
