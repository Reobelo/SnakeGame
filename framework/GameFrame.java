package framework;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class GameFrame extends JFrame implements ActionListener{
	private GamePanel panel;
	private GameSnakeToolBar toolBar;
	public GameFrame(){		
		panel = new GamePanel(this);
		toolBar = new GameSnakeToolBar(panel);
		this.add(panel, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.SOUTH);
		this.setTitle(panel.gameName);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		toolBar.getNewGameButton().addActionListener(this);
		toolBar.getPauseButton().addActionListener(this);
	}
	public GameSnakeToolBar getToolBar() {
		return toolBar;
	}
	
	public void startGame() {
        panel.startGame();
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Action event received: " + e.getSource());
		if(e.getSource() == toolBar.getNewGameButton()) {
			
			panel.resetGame();
			panel.requestFocusInWindow();
			//SwingUtilities.updateComponentTreeUI(this);
			//this.revalidate();
	        //this.repaint();	
		}
		if(e.getSource() == toolBar.getPauseButton()) {
			panel.togglePause();
			//panel.requestFocusInWindow();
		}

		
	}

	
}


