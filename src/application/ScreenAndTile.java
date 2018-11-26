package application;

import javafx.scene.image.Image;

public class ScreenAndTile {
	Image goldBlock = new Image(getClass().getResourceAsStream("/resources/goldBlock.png"));
	Image leftTile = new Image(getClass().getResourceAsStream("/resources/grassCenterBlock.png")); //I assigned all of these to CenterTile.
	Image centerTile = new Image(getClass().getResourceAsStream("/resources/grassCenterBlock.png"));//Can change anytime with any image
	Image rightTile = new Image(getClass().getResourceAsStream("/resources/grassCenterBlock.png"));// we want.
	
	public int TILE_WIDTH = 60;
	public int TILE_HEIGHT = 60;
	public int SCREEN_WIDTH = 600;
	public int SCREEN_HEIGHT = 360;
	
	int w = SCREEN_WIDTH / TILE_WIDTH; // How many elements will be inserted per row
	int h = SCREEN_HEIGHT / TILE_HEIGHT; // How many elements will be inserted per coloumn
	int[] map = new int[w * h]; // Array to keep track of each tile
	
	
	

}
