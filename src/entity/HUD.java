package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.MultipleGradientPaint;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import entity.player.Player;
import main.GamePanel;

public class HUD {

	public static final Color life_RED = new Color(244, 75, 75, 255);
	public static final Color life_MAROON = new Color(149, 5, 48, 255);
	public static final Color life_BLUE = new Color(33, 44, 86, 255);
	
	public static final Color stamina_PURPLE = new Color(83, 88, 184, 255);
	public static final Color stamina_BLUE = new Color(140, 203, 255, 255);
	
	public static final Color mana_PURPLE = new Color(185, 0, 251, 255);
	public static final Color mana_BLUE = new Color(119, 0, 255, 255);
	private Player player;
	
	private BufferedImage frame_left;
	private BufferedImage frame_right;
	private BufferedImage frame_face;
	
	private BufferedImage face;
	private BufferedImage face_active;
	private BufferedImage eye;
	
	private BufferedImage radar_bg;
	private Image mana_bg;
	
	private Paint paint_stamina;
	private BasicStroke stroke_stamina;
	
	private Paint paint_life_bg;
	private BasicStroke stroke_life_bg;
	
	private Paint paint_life;
	private BasicStroke stroke_life;
	
	private Paint paint_mana;
	private BasicStroke stroke_mana;	
	
	int offsetX_frame_right;
	int offsetX_radar_bg;
	
	private Font font;
	
	public HUD(Player player){
		this.player = player;
		
		//load image
		try {
			
			this.frame_left = ImageIO.read(this.getClass().getResourceAsStream("/HUD/frame-left.png"));
			this.frame_right = ImageIO.read(this.getClass().getResourceAsStream("/HUD/frame-right.png"));
			
			this.face = ImageIO.read(this.getClass().getResourceAsStream("/HUD/face.png"));
			this.face_active = ImageIO.read(this.getClass().getResourceAsStream("/HUD/face-active.png"));
			this.frame_face = face;			
			this.eye = ImageIO.read(this.getClass().getResourceAsStream("/HUD/eye.png"));
			
			this.radar_bg = ImageIO.read(this.getClass().getResourceAsStream("/HUD/radar-bg.png"));
//			this.mana_bg = ImageIO.read(this.getClass().getResourceAsStream("/HUD/mana-bg.gif"));
			this.mana_bg = new ImageIcon(this.getClass().getResource("/HUD/mana-bg.gif")).getImage();
		
			this.offsetX_frame_right = GamePanel.WIDTH-frame_right.getWidth();
			this.offsetX_radar_bg = GamePanel.WIDTH-179;
			
			this.paint_stamina = new GradientPaint(0, 0, stamina_BLUE, 100, 150, stamina_PURPLE, true);
			this.stroke_stamina = new BasicStroke(7);
			
			this.paint_life = new LinearGradientPaint(0, 39, 0, 50, new float[] {0, 0.1f, 0.1001f}, new Color[] {life_RED, life_RED, life_MAROON}, MultipleGradientPaint.CycleMethod.NO_CYCLE);
			this.paint_life_bg = new LinearGradientPaint(0, 39, 0, 50, new float[] {0, 0.1f, 0.1001f}, new Color[] {life_BLUE, life_BLUE, life_BLUE}, MultipleGradientPaint.CycleMethod.NO_CYCLE);
			this.stroke_life_bg = new BasicStroke(21, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
			this.stroke_life = new BasicStroke(1);
			
			this.paint_mana = new GradientPaint(0, 0, mana_PURPLE, 100, 100, mana_BLUE, true);
//			this.paint_mana = new TexturePaint(mana_bg, new Rectangle(0, 0, 362, 8));
			this.stroke_mana = new BasicStroke(1);
			
			this.font = new Font("Arial", Font.PLAIN, 14);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g){
	
		g.setPaint(paint_life_bg);
		g.setStroke(stroke_life_bg);	
		g.drawLine(174, 39, 521, 39);
		
		g.setPaint(paint_life);	
		g.setStroke(stroke_life);	
		// Fill range 0 to 362 (third parameter)
		g.fillRoundRect(167, 29, 362, 21, 21, 21);
		
	
		g.setPaint(paint_mana);
		g.setStroke(stroke_mana);
		// Fill range 0 to 312 (third parameter)
//		g.drawImage(mana_bg, 163, 61, 312, 8, null);
		g.fillRect(163, 61, 312, 8);
		
		
	
		
		g.setPaint(paint_stamina);
		g.setStroke(stroke_stamina);		
		// Fill ranges from 0 to 160 (last parameter). -160 us used since angle is drawn clockwise 
		g.drawArc(12, -2, 141, 141, -53, -160);
		
		g.drawImage(radar_bg, offsetX_radar_bg, 27, null);
		
		g.drawImage(frame_left, 0, 0, null);
		g.drawImage(frame_right, offsetX_frame_right, 0, null);
		g.drawImage(frame_face, 31, 17, null);
		g.drawImage(eye, 49, 35, null);
		

		drawTrapezoid(g, 10, 191, 10, 467, 6, 6, true);
		
		drawTrapezoid(g, 191, 10, 467, 10, 6, 0, false);
		
		g.setFont(font);
		
//		g.drawString(player.getHealth()+"/"+player.getMaxHealth(), 30, 25);
//		g.drawString(player.getEnergy()/100 +"/"+player.getMaxEnergy()/100, 30, 45);
	}
	
	public void drawTrapezoid(Graphics2D g, int x, int y, int width, int height, int angle1, int angle2, boolean isVertical) {
		if(isVertical) {
			int [ ] pointX = {0+x, width+x, width+x, 0+x};
			int [ ] pointY = {0+y, angle1+y, (height-angle2)+y, height+y};
			g.fillPolygon(pointX, pointY, pointX.length);
		}
		else {
			int [ ] pointX = {0+angle1+x, (width-angle2)+x, width+angle2+x, (-angle1)+x};
			int [ ] pointY = {y, y, height+y, height+y};
			g.fillPolygon(pointX, pointY, pointX.length);
		}
	}
	
}
