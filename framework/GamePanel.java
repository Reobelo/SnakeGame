package framework;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
//import java.util.Timer;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	static final String gameName = "Snake";
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT= 600;
	static int UNIT_SIZE = 10;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS];
	static final int INITIAL_BODY_PARTS = 7;
	int bodyParts;
	int applesEaten;
	int bonusTreatEaten;
	int totalScore;
	int appleX;
	int appleY;
	int bonusX;
	int bonusY;
	char direction = 'R';
	boolean running = false;
	boolean isPaused = false;
	private int gameTimer = 0;
	Timer timer;
	Random random;
	GameFrame gameFrame;
	Sound sound;
	
	 GamePanel(GameFrame frame) {
		this.gameFrame = frame;
		random = new Random();
		sound = new Sound();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
	}
	
	public void startGame() {
		resetGame();	
		playMusicLoop(0);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			/*
			for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			g.setColor(Color.yellow);
			g.fillOval(bonusX, bonusY, UNIT_SIZE, UNIT_SIZE);
			
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

			
			for(int i=0; i<bodyParts; i++) {
				if(i == 0) {
					g.setColor(Color.pink);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
		}
		else {
			gameOver(g);
		}
		
	}
	
	public void newApple() {
		/*
		 appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		 appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		*/
	    boolean onSnake;
	    do {
	        onSnake = false;
	        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
	        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

	        // Check if the new apple position is on the snake
	        for (int i = 0; i < bodyParts; i++) {
	            if (x[i] == appleX && y[i] == appleY) {
	                onSnake = true;
	                break;
	            }
	        }
	    } while (onSnake); // Keep trying until it's not on the snake
	}
	

	
	public void newBonusTreat() {
		 /*
		 bonusX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		 bonusY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		 */
		 boolean invalidPosition;
		    do {
		        invalidPosition = false;
		        bonusX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
		        bonusY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;

		        // Check if the new bonus position is on the snake or overlaps the apple
		        for (int i = 0; i < bodyParts; i++) {
		            if ((x[i] == bonusX && y[i] == bonusY) || (bonusX == appleX && bonusY == appleY)) {
		                invalidPosition = true;
		                break;
		            }
		        }
		    } while (invalidPosition); // Keep trying until it's a valid position
	}
	
	
	public void move() {
		for(int i = bodyParts; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction) {
		case 'U':
			y[0] = y[0]-UNIT_SIZE;
			break;
		case 'D':
			y[0] = y[0]+UNIT_SIZE;
			break;
		case 'L':
			x[0] = x[0]-UNIT_SIZE;
			break;
		case 'R':
			x[0] = x[0]+UNIT_SIZE;
			break;
		}
	}

	
	public void checkBonusTreat() {
		if((x[0] == bonusX) && (y[0] == bonusY)) {
			playSpecific(2);
			bodyParts +=3;
			bonusTreatEaten++;
			newBonusTreat();
			updateScoreDisplay();
		}
	}
	
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			playSpecific(1);
			bodyParts++;
			applesEaten++;
			newApple();
			updateScoreDisplay();
		}
	}
	
	public void checkCollisions() {
		//this checks if head collides with body
		for(int i= bodyParts; i>0; i--) {
			if((x[0] == x[i]) && (y[0] ==y [i])) {
				running = false;
			}
		}
		//check if head touch border
		if((x[0] < 0) || (x[0] > SCREEN_WIDTH)||(y[0] < 0) || (y[0] > SCREEN_HEIGHT)) {
			running = false;
		}
	}
	public void updateScoreDisplay() {
		totalScore = applesEaten + bonusTreatEaten*3;
		JTextField scoreField = gameFrame.getToolBar().getScoreField();
		scoreField.setText(String.valueOf(totalScore));
		
	}
	
	public void gameOver(Graphics g) {
		playSpecific(3);
	    if (timer != null) {
	        timer.stop();
	    }

	    // Draw the game over screen
	    g.setColor(Color.red);
	    g.setFont(new Font("Ink Free",Font.BOLD, 75));
	    FontMetrics metrics = getFontMetrics(g.getFont());
	    // Draw "GAME OVER" centered horizontally
	    String gameOverText = "GAME OVER";
	    int xGameOver = (SCREEN_WIDTH - metrics.stringWidth(gameOverText)) / 2;
	    int yGameOver = SCREEN_HEIGHT / 4;
	    g.drawString(gameOverText, xGameOver, yGameOver);

	    // Prepare to draw the second line of text
	    String creditText = "By Haben Andemicael";
	    g.setFont(new Font("Ink Free", Font.BOLD, 36)); // Smaller font size for the second line
	    FontMetrics creditMetrics = getFontMetrics(g.getFont());
	    int xCredit = (SCREEN_WIDTH - creditMetrics.stringWidth(creditText)) / 2;
	    int yCredit = yGameOver + creditMetrics.getHeight(); // Position below the first line
	    g.setColor(Color.cyan);
	    // Draw the second line of text
	    g.drawString(creditText, xCredit, yCredit);
	    
	    //score
	    String scoreText = "Your score: " + totalScore +" points";
	    g.setFont(new Font("Ink Free", Font.BOLD, 36)); // Smaller font size for the second line
	    FontMetrics scoreMetrics = getFontMetrics(g.getFont());
	    int xScore = (SCREEN_WIDTH - scoreMetrics.stringWidth(scoreText)) / 2;
	    //int yScore = yCredit + scoreMetrics.getHeight(); // Position below the first line
	    int yScore = (SCREEN_HEIGHT)-(SCREEN_HEIGHT / 4); // Position below the first line
	    g.setColor(Color.orange);
	    // Draw the second line of text
	    g.drawString(scoreText, xScore, yScore);
	    
	  //timer
	    String timeText = "Time: " + gameTimer +" seconds";
	    g.setFont(new Font("Ink Free", Font.BOLD, 36)); // Smaller font size for the second line
	    FontMetrics timeMetrics = getFontMetrics(g.getFont());
	    int xTime = (SCREEN_WIDTH - timeMetrics.stringWidth(scoreText)) / 2;
	    int yTime = yScore + timeMetrics.getHeight(); // Position below the first line
	    g.setColor(Color.yellow);
	    // Draw the second line of text
	    g.drawString(timeText, xTime, yTime);
	    
	}
	public void togglePause() {
	    if (isPaused) {
	        resumeGame();  // If the game is paused, resume it
	    } else {
	        pauseGame();   // If the game is running, pause it
	    }
	}

	public void pauseGame() {
	    if (running && timer != null) {
	        timer.stop();
	        isPaused = true;  // Mark the game as paused
	        running = false;
	    }
	}

	public void resumeGame() {
		 if (!running && isPaused) {
		     timer.start();  // Restart game updates
		     running = true;
		     isPaused = false;
		     requestFocus();  // Important to capture key events again
		 }
	}
	
	public void resetGame() {
		
		if (timer != null) {
            timer.stop();
        }
        applesEaten = 0;
        bonusTreatEaten = 0;
        bodyParts = INITIAL_BODY_PARTS;
        direction = 'R';  // Start direction to the right

        gameTimer = 0;
        gameFrame.getToolBar().getTimerField().setText("0");
        
        // Set the snake's starting position
        for (int i = 0; i < bodyParts; i++) {
            x[i] = (SCREEN_WIDTH / 2) - (i * UNIT_SIZE);  // Horizontally center the snake and line up body parts
            y[i] = SCREEN_HEIGHT / 2;  // Vertically center the snake
        }

        newApple();
        newBonusTreat();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();  // Restart the timer
        javax.swing.Timer gameClock = new javax.swing.Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (running && !isPaused) {
                    gameTimer++;
                    gameFrame.getToolBar().getTimerField().setText(String.valueOf(gameTimer));
                }
            }
        });
        gameClock.start();
        requestFocus();
        updateScoreDisplay();
        repaint();
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			move();
			checkApple();
			checkBonusTreat();
			checkCollisions();
			repaint();
		}		
	}
	public void playMusicLoop(int i) {
		sound.getFiles(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		sound.stop();
	}
	
	public void playSpecific(int i) {
		sound.getFiles(i);
		sound.play();
	}
	
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
			case KeyEvent.VK_SPACE:
                System.out.println("Toggling pause");
                togglePause();
                break;
			case KeyEvent.VK_ENTER:
                System.out.println("Game restarted");
                resetGame();
                break;
			}
		}
	}
	

	
	
}
