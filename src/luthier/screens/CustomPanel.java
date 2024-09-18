package luthier.screens;

import java.awt.CardLayout;

import javax.swing.JPanel;

public abstract class CustomPanel extends JPanel {
	private JPanel mainPanel;
	private CardLayout mainPanelLayout;
	
	public CustomPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
		this.mainPanelLayout = (CardLayout) mainPanel.getLayout();
	}
	
	public void navigate(String screenName) {
		mainPanelLayout.show(mainPanel, screenName);
	}
}
