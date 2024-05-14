package framework;

import java.awt.*;
import javax.swing.*;


public class GameSnakeToolBar extends JPanel{
	
	static final int TOOLBAR_WIDTH = 600;
	static final int TOOLBAR_HEIGHT = 30;
	private JButton newGameButton;
	private JButton pauseButton;
	private JTextField scoreField;
	private JTextField timerField;
	private JLabel scoreLabel;
	private JLabel timerLabel;
	GamePanel gamePanel;

	GameSnakeToolBar(GamePanel panel){
		this.gamePanel = panel;
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT));
		this.setBackground(Color.orange);
		//this.setFocusable(true);
		newGameButton = new JButton("New Game");
		pauseButton = new JButton("Pause Game");
		scoreField = new JTextField(5);
		timerField = new JTextField(10);
		scoreLabel = new JLabel("Score: ");
		timerLabel = new JLabel("Time: ");
		this.add(newGameButton, BorderLayout.EAST);
		this.add(pauseButton, BorderLayout.WEST);
		JPanel centerPanel = new JPanel(new FlowLayout()); // A sub-panel for organizing central components
        centerPanel.add(scoreLabel);
        centerPanel.add(scoreField);
        centerPanel.add(timerLabel);
        centerPanel.add(timerField);

        this.add(centerPanel, BorderLayout.CENTER);
	}
	
		public JButton getNewGameButton() {
		return newGameButton;
	}


	public JButton getPauseButton() {
		return pauseButton;
	}


	public JTextField getScoreField() {
		return scoreField;
	}


	public JTextField getTimerField() {
		return timerField;
	}
}
