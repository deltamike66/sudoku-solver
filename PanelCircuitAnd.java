import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class PanelCircuitAnd extends JPanel{
	private static final long serialVersionUID = 1L;
	private String f;
	private String[] input;
	private int l=0;
	
	PanelCircuitAnd() {
		super();
    }

    @Override
    public void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	Graphics2D g2d = (Graphics2D)g;
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    	g2d.setColor(Color.GRAY);
    	// larghezza 335
    	// altezza 265
    	if (this.f != null) {
	    	if (this.f.length() > 2) {
	        	this.input = this.f.split("=",0)[1].split("\\+",0);        	
	        } else {
	        	this.input = new String[0];
	        }
	        this.l = this.input.length;
	    	if (this.l>0)
	    		drawCircuit(g2d);
    	}
    }
    
    public void refresh(String formula) {
    	this.f = formula;
    	repaint();
    }
    
    public void drawCircuit(Graphics2D g2d) {
    	g2d.drawString("A    B    C    D", 5, 15);
    	for (int l=0;l<4;l++) {
    		g2d.drawLine(8+l*24, 18, 8+l*24, 260);
    		g2d.drawLine(20+l*24, 28, 20+l*24, 24);
    		g2d.drawLine(20+l*24, 24, 8+l*24, 24);
    		g2d.drawArc(8+l*24-1, 23, 2,2,0,360);
    		g2d.drawLine(20+l*24, 42, 20+l*24, 260);
    		this.drawNot(g2d, 20+l*24, 28); 
    	}
    	
    	int t=0;
    	if (this.l > 0) {
	    	for (String i: input) {
	    		this.drawAnd(g2d, 140, 50+t*25, i);
	    		t++;
	    	}
    	}

    	int spaceY = 22;
    	int y;
    	
    	for (int i=0; i<this.l;i++) {
    		y = 60+i*25;
    		g2d.drawLine(170, y, 180+i*5, y);
    		g2d.drawLine(180+i*5, y, 180+i*5, y-i*spaceY);
    		g2d.drawLine(180+i*5, y-i*spaceY, 245, y-i*spaceY);
    	}
   
    	this.drawOr(g2d, 50);
    
    }
    
    private void drawOr(Graphics2D g2d, int y) {
    	int x=205;
    	g2d.drawArc(x, y, 40, 40, 45, -90);
    	g2d.setColor(Color.WHITE);
    	g2d.drawArc(x+1, y, 40, 40, 45, -90);
    	g2d.drawArc(x+2, y, 40, 40, 45, -90);
    	g2d.drawArc(x+3, y, 40, 40, 45, -90);
    	g2d.drawArc(x+4, y, 40, 40, 45, -90);
    	g2d.drawArc(x+5, y, 40, 40, 45, -90);
    	g2d.drawArc(x+6, y, 40, 40, 45, -90);
    	g2d.drawArc(x+7, y, 40, 40, 45, -90);
    	g2d.setColor(Color.GRAY);
    	g2d.drawArc(x-16, y+5, 100, 100, 90, -45);
    	g2d.drawArc(x-16, y-65, 100, 100, -90, 45);
    	g2d.drawLine(x+68, y+20, x+130, y+20);
    	g2d.drawString("f(A,B,C,D)",x+70,y+15);
    	
    }
    
    private void drawNot(Graphics2D g2d, int x, int y) {
    	g2d.drawLine(x-5, y, x+5, y);
    	g2d.drawLine(x-5, y, x, y+10);
    	g2d.drawLine(x+5, y, x, y+10);
    	g2d.drawArc(x-2, y+10, 4, 4, 0, 360);
    }
    
    private void drawAnd(Graphics2D g2d, int x, int y, String IN) {
    	g2d.drawLine(x, y, x+20, y);
    	g2d.drawLine(x, y, x, y+20);
    	g2d.drawLine(x, y+20, x+20, y+20);
    	g2d.drawArc(x+10, y, 20, 20, 90, -180);
    	
    	int space = 0;
    	if (IN.contains("A") && !IN.contains("A'")) {
    		g2d.drawLine(8+space*32,y+4, x, y+4);
    		g2d.drawArc(7+space*32, y+4-1, 2, 2, 0, 360);
       	}
    	if (IN.contains("A'")) {
    		g2d.drawLine(20+space*32,y+4, x, y+4);
    		g2d.drawArc(19+space*32, y+4-1, 2, 2, 0, 360);
       	}
    	space++;
    	if (IN.contains("B") && !IN.contains("B'")) {
    		g2d.drawLine(8+space*24,y+4+space*4, x, y+4+space*4);
    		g2d.drawArc(7+space*24, y+4+space*4-1, 2, 2, 0, 360);
       	}
    	if (IN.contains("B'")) {
    		g2d.drawLine(20+space*24,y+4+space*4, x, y+4+space*4);
    		g2d.drawArc(19+space*24, y+4+space*4-1, 2, 2, 0, 360);
       	}
    	space++;
    	if (IN.contains("C") && !IN.contains("C'")) {
    		g2d.drawLine(8+space*24,y+4+space*4, x, y+4+space*4);
    		g2d.drawArc(7+space*24, y+4+space*4-1, 2, 2, 0, 360);
       	}
    	if (IN.contains("C'")) {
    		g2d.drawLine(20+space*24,y+4+space*4, x, y+4+space*4);
    		g2d.drawArc(19+space*24, y+4+space*4-1, 2, 2, 0, 360);
       	}
    	space++;
    	if (IN.contains("D") && !IN.contains("D'")) {
    		g2d.drawLine(8+space*24,y+4+space*4, x, y+4+space*4);
    		g2d.drawArc(7+space*24, y+4+space*4-1, 2, 2, 0, 360);
       	}
    	if (IN.contains("D'")) {
    		g2d.drawLine(20+space*24,y+4+space*4, x, y+4+space*4);
    		g2d.drawArc(19+space*24, y+4+space*4-1, 2, 2, 0, 360);
       	}
    }
}
