package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sprite extends Rectangle {
	public final String type;
	public int velocityX = 0;
	public int velocityY = 0;

	Sprite(int x, int y, int w, int h, String type, Color color){
		super(w, h, color);
		
		this.type = type;
		this.setTranslateX(x);
		this.setTranslateY(y);
	}
	
}
