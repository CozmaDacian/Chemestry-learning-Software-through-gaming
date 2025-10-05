package utilz;

import java.awt.Button;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Butoane_Game extends Component{

    private boolean inside = false;
    private BufferedImage press, not_press;

    public Butoane_Game(BufferedImage press, BufferedImage not_press) {
       
        this.press = press;
        this.not_press = not_press;
     
    }
    public void removeFocus() {
        requestFocusInWindow(false);
    }
    
    public void draw(Graphics g) {
    	
        if (inside) {
            g.drawImage(press, getX(), getY(), getWidth(), getHeight(), this);
        } else {
            g.drawImage(not_press, getX(), getY(), getWidth(), getHeight(), this);
        }
    }

   

    public void setInside(boolean inside) {
        this.inside = inside;
        repaint();
    }

    public BufferedImage getNot_press() {
        return not_press;
    }

    public void setNot_press(BufferedImage not_press) {
        this.not_press = not_press;
        repaint();
    }

    public BufferedImage getPress() {
        return press;
    }

    public void setPress(BufferedImage press) {
        this.press = press;
        repaint();
    }
}