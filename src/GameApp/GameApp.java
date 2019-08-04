package GameApp;

import GameLoop.GameLoop;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GameApp extends Application {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = 500;
	
	public static final int LEVEL_WIDTH = 5000;
	public static final int BLOCKS_IN_A_ROW = 102;
	public static final int BLOCKS_IN_A_VIEW = 22;
	public static final int BLOCKS_IN_A_COL = 10;
	public static final double BLOCK_SIZE = 50;
	public static final int NUM_PADDING_BLOCKS_PER_ROW = 2;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("PI Project");
		Group root = new Group();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);

		canvas.setFocusTraversable(true);
		canvas.addEventHandler(KeyEvent.ANY, new InputController());

		GameLoop gameLoop = GameLoop.getInstance();
		gameLoop.setGraphicsContext(canvas.getGraphicsContext2D());
		gameLoop.initGame();
		gameLoop.start();

		stage.setScene(scene);
		//stage.setResizable(false);
		stage.show();

	}
	
}
