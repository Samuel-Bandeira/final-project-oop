package luthier.screens;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import luthier.entities.User;
import luthier.repositories.interfaces.IUserRepository;
import luthier.singletons.UserSession;

public class LoginPanel extends CustomPanel {
	private IUserRepository userRepository; 

	private void handleLogin(JTextField emailField, JPasswordField passwordField) {
		String email =  emailField.getText();
		String password = String.valueOf(passwordField.getPassword());	
		Boolean error = false;
		String message = "Erro(s):\n";
		
		if(email.isBlank()) {
			error = true;
			message += " - Preencha o email\n";
		}

		if(password.isBlank()) {
			error = true;
			message += " - Preencha a senha\n";
		} 
		
		if(error) {
			JOptionPane.showMessageDialog(null, message, "Swing Tester", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		User user = userRepository.procurar(email);
		
		if(user.password.equals(password)) {	
			UserSession.getInstance().setLoggedUser(user);
			navigate("home");
		} else {
			JOptionPane.showMessageDialog(null, "Senha inválida", "Swing Tester", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private void handleRegister() {
		System.out.println("helloaa");			
		navigate("register");
//		if() {
//			mainPanelLayout.show(mainPanel, "home");
//		}
	}

	public LoginPanel(JPanel mainPanel, IUserRepository userJson) {
		super(mainPanel);
		this.userRepository = userJson;
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
