package application;
	

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {
	// Global variables
	// flagX = X key is pressed
	ScreenAndTile myScreen = new ScreenAndTile();
	List<ImageView> imageViewList = new ArrayList<>();
	List<Sprite> spriteList = new ArrayList<>();
	Boolean flagA = false;
	Boolean flagD = false;
	Boolean flagW = false;
	Boolean flagS = false;
	Sprite player = new Sprite(100, 100, 60, 60, "player", Color.BLACK);
	Sprite player2 = new Sprite(200, 200, 60, 60, "player2", Color.BLUE);
	Thread t1;
	Thread t2;
	Thread t3;
	BorderPane root;
	Scene scene;
	//JavaFX start method
	@Override
	public void start(Stage primaryStage) {
		try {
			// Root and Scene
			root = new BorderPane();
			scene = new Scene(root,myScreen.SCREEN_WIDTH,myScreen.SCREEN_HEIGHT);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    
			//Creating tiles (Map background)
			createGraphics();
			
			//Add nodes to root here
			root.getChildren().add(player);
			root.getChildren().add(player2);
			root.getChildren().addAll(1, imageViewList);
			
			//JavaFX Set Scene
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			//Stopping all the threads after closing game window
			/*primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			       @Override
			       public void handle(WindowEvent e) {
			          Platform.exit();
			          System.exit(0);
			       }
			    });*/
			primaryStage.show();

			//Thread Run Methods & Movement
			
			
			//TEST 23.11.2018 (MadProgrammer)
			
			keyPressListener(scene);
			keyReleaseListener(scene);
			
			//AnimationTimer for setTranslateX and Y
			timerLoop(scene).start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Main.launch(args);
	}
	
	// FUNCTION DEFINITIONS
	
	
	private void createGraphics() {
		//Assign values to the map array based on tile position.
		for(int i = 0; i < myScreen.map.length; i++){
			ImageView imgView = new ImageView();
			if (i % myScreen.w == 0) {  // // At the end of our coloumn
				myScreen.map[i] = 0; // 0 -> Left Block
				imgView.setImage(myScreen.leftTile);
				imgView.setPreserveRatio(true);
				imgView.setFitHeight(myScreen.TILE_HEIGHT);
				imgView.setFitWidth(myScreen.TILE_WIDTH);
				imgView.setX((i % myScreen.w) * myScreen.TILE_WIDTH);
				imgView.setY(Math.floor(i / myScreen.w) * myScreen.TILE_HEIGHT);
				//imageViewList.add(i, imgView);
				root.getChildren().add(imgView);
			} else if (i % myScreen.w == (myScreen.w - 1)) {  // At the end of our row
				myScreen.map[i] = 1; // 1 -> Right Block
				imgView.setImage(myScreen.rightTile);
				imgView.setPreserveRatio(true);
				imgView.setFitHeight(myScreen.TILE_HEIGHT);
				imgView.setFitWidth(myScreen.TILE_WIDTH);
				imgView.setX((i % myScreen.w) * myScreen.TILE_WIDTH);
				imgView.setY(Math.floor(i / myScreen.w) * myScreen.TILE_HEIGHT);
				//imageViewList.add(i, imgView);
				root.getChildren().add(imgView);
			} else {  // Everything else
				myScreen.map[i] = 2; // 2 -> Center Block
				imgView.setImage(myScreen.centerTile);
				imgView.setPreserveRatio(true);
				imgView.setFitHeight(myScreen.TILE_HEIGHT);
				imgView.setFitWidth(myScreen.TILE_WIDTH);
				imgView.setX((i % myScreen.w) * myScreen.TILE_WIDTH);
				imgView.setY(Math.floor(i / myScreen.w) * myScreen.TILE_HEIGHT);
				//imageViewList.add(i, imgView);
				root.getChildren().add(imgView);
			}
		}
	}
	
	public AnimationTimer timerLoop(Scene myScene) {
		AnimationTimer timer = new AnimationTimer() {
			
			@Override
			public void handle(long now) {
				
				//Flags A & D
				if(flagD && !flagA) {
					player.velocityX = 1;
				} else if(flagA && !flagD) {
					player.velocityX = -1;
				} else if(flagD && flagA) {
					player.velocityX = 1;
				} else if(!flagA && !flagD) {
					player.velocityX = 0;
				}
				//Flags W & S
				if(flagS && !flagW) {
					player.velocityY = 1;
				} else if(flagW && !flagS) {
					player.velocityY = -1;
				} else if(flagS && flagW) {
					player.velocityY = 1;
				} else if(!flagW && !flagS) {
					player.velocityY = 0;
				} 
				else {
					player.velocityX = 0;
					player.velocityY = 0;
				}
				
				player.setTranslateX(player.getTranslateX() + player.velocityX);
				player.setTranslateY(player.getTranslateY() + player.velocityY);
				
				if(collision(player, player2)) {
					player.setFill(Color.YELLOW);
				}
				else {
					player.setFill(Color.BLACK);
				}
				
			}
			
		};
		return timer;
	}
	
	
	private boolean collision(Sprite box1, Sprite box2) {
		double x1, x2, y1, y2;
		
		x1 = box1.getTranslateX();
		y1 = box1.getTranslateY();
		x2 = box2.getTranslateX();
		y2 = box2.getTranslateY();
		
		if((x1 + 60 < x2) || (x2 + 60 < x1)) {
			return false;
		}
		
		else if((y1 + 60) < y2 || (y2 + 60) < y1) {
			return false;
		}
		else {
			return true;
		}
	}
	

	private void keyPressListener(Scene myScene) {
		myScene.setOnKeyPressed(e -> {
	    	switch(e.getCode()) {
			case A:
				flagA = true;
				break;
			case D:
				flagD = true;
				break;
			case W:
		    	flagW = true;
				player.velocityY = -1;
				break;
			case S:
		    	flagS = true;
				break;
			}
});
		
	}

	private void keyReleaseListener(Scene myScene) {
		myScene.setOnKeyReleased(e -> {
	    	switch(e.getCode()) {
			case A:
				flagA = false;
				break;
			case D:
		    	flagD = false;
				break;
			case W:
		    	flagW = false;
				break;
			case S:
		    	flagS = false;
				break;
			}
});
		
	}

	
}
