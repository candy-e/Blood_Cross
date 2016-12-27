package main;

import javax.swing.JFrame;

public class Game {
	
	private String title;
	private GamePanel gp;
	
	public Game(String title, GamePanel gp){
		this.title = title;
		this.gp = gp;
	}
	
	public void startGame(boolean isResizable){
		final JFrame fullscreenFrame = new JFrame(title);
 		fullscreenFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
// 		fullscreenFrame.setUndecorated(true);
 		fullscreenFrame.setResizable(isResizable);
 		fullscreenFrame.validate();
 		fullscreenFrame.setContentPane(gp);
 		GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(fullscreenFrame);

//		JFrame window = new JFrame(title);
//		window.setContentPane(gp);
//		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		window.setResizable(isResizable);
//		window.pack();
//		window.setVisible(true);
	}
	
}
