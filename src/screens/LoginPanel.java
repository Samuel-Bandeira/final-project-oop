package screens;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends CustomPanel {
	private void handleLogin(JTextField emailField, JPasswordField passwordField) {
		String email =  emailField.getText();
		String password = String.valueOf(passwordField.getPassword());
		System.out.println(email);	
		System.out.println(password);		
		
		navigate("home");
//		if() {
//			mainPanelLayout.show(mainPanel, "home");
//		}
	}
	
	private void handleRegister() {
		System.out.println("helloaa");			
		navigate("register");
//		if() {
//			mainPanelLayout.show(mainPanel, "home");
//		}
	}

	public LoginPanel(JPanel mainPanel) {
		super(mainPanel);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel emailLabel = new JLabel("Email");
		JTextField emailField = new JTextField("");
		emailField.setMaximumSize(new Dimension(300, 30));

		JLabel passwordLabel = new JLabel("Senha");
		JPasswordField passwordField = new JPasswordField("");
		passwordField.setMaximumSize(new Dimension(300, 30));

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleLogin(emailField, passwordField);
			}
		});
		
		JButton registerButton = new JButton("Registrar-se");
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleRegister();
			}
		});

		add(emailLabel);
		add(emailField);
		add(passwordLabel);
		add(passwordField);
		add(loginButton);
		add(registerButton);
	}
}
