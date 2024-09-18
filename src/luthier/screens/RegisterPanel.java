package luthier.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterPanel extends CustomPanel {
	private void handleRegister(JTextField emailField, JPasswordField passwordField,
			JPasswordField passwordConfirmField) {
		String email = emailField.getText();
		String password = String.valueOf(passwordField.getPassword());
		String passwordConfirm = String.valueOf(passwordConfirmField.getPassword());
		System.out.println(email);
		System.out.println(password);
		System.out.println(passwordConfirm);
		navigate("home");
	}

	public RegisterPanel(JPanel mainPanel) {
		super(mainPanel);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		JLabel emailLabel = new JLabel("Email");
		JTextField emailField = new JTextField("");
		emailField.setMaximumSize(new Dimension(300, 30));

		JLabel passwordLabel = new JLabel("Senha");
		JPasswordField passwordField = new JPasswordField("");
		passwordField.setMaximumSize(new Dimension(300, 30));

		JLabel passwordConfirmLabel = new JLabel("Confirmar Senha");
		JPasswordField passwordConfirmField = new JPasswordField("");
		passwordConfirmField.setMaximumSize(new Dimension(300, 30));

		JButton loginButton = new JButton("Registrar-se");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleRegister(emailField, passwordField, passwordConfirmField);
			}
		});

		add(emailLabel);
		add(emailField);
		add(passwordLabel);
		add(passwordField);
		add(passwordConfirmLabel);
		add(passwordConfirmField);
		add(loginButton);
	}
}
