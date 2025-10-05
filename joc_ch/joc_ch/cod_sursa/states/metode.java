package states;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface metode {

	public void draw(Graphics2D g);
	public void render();
	public void mouseClicked(MouseEvent e);
	public void mousePressed(MouseEvent e);
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
	void keyTyped(KeyEvent e);
}
