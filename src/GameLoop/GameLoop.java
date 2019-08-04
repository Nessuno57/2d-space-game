package GameLoop;

import GameApp.GameApp;
import Objects.PlayerObject;
import Utils.Sprite;
import Utils.Vector2D;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

public class GameLoop extends AnimationTimer {

	public static final double TIME_STEP = 0.0166f;
	private double accumulatedTime = 0;
	private long previousTimeInNanos = 0;
	private static final double MAX_STEP = 0.0166f;

	private double secondsElapsedSinceLastFpsUpdate = 0f;
	private int framesSinceLastFpsUpdate = 0;
	private int fps = 0;
	//private final static Paint skyPaint = Paint.valueOf("#99ffff");
	private final static Paint textPaint = Paint.valueOf("#ffffff");
	private Image background = Sprite.BACKGROUND2.getSpriteImage();
	
	private ScrollingView scrollingView;
	private GraphicsContext context;

	private static final GameLoop gameLoop = new GameLoop();

	private GameLoop() {

	}

	public static GameLoop getInstance() {
		return gameLoop;
	}

	public void setGraphicsContext(GraphicsContext context) {
		this.context = context;
	}

	public void update(Double seconds) {

		player.update(seconds);
		scrollingView.updateScrollingView(seconds);

	}

	public void render() {
		/*
		context.setFill(skyPaint);
		context.setLineWidth(2);
		context.fillRect(0, 0, GameApp.WIDTH, GameApp.HEIGHT);
		*/
		context.drawImage(background, 0, 0);
		scrollingView.drawScrollingView(context);

		context.setFill(textPaint);
		context.fillText(Integer.toString(fps), 20, 40);
		player.draw(context);

	}

	@Override
	public void handle(long currentTimeInNanos) {
		if (previousTimeInNanos == 0) {
			previousTimeInNanos = currentTimeInNanos;
			return;
		}

		double elapsedTimeInSeconds = (currentTimeInNanos - previousTimeInNanos) / 1e9f;
		double elapsedTimeCappedInSeconds = Math.min(elapsedTimeInSeconds, MAX_STEP);

		accumulatedTime += elapsedTimeCappedInSeconds;
		previousTimeInNanos = currentTimeInNanos;

		boolean isUpdated = false;
		while (accumulatedTime >= TIME_STEP) {
			this.update(TIME_STEP);
			accumulatedTime -= TIME_STEP;
			isUpdated = true;
		}

		if (isUpdated) {
			this.render();
			framesSinceLastFpsUpdate++;
		}

		secondsElapsedSinceLastFpsUpdate += elapsedTimeInSeconds;

		if (secondsElapsedSinceLastFpsUpdate >= 1f) {
			fps = (int) Math.round(framesSinceLastFpsUpdate / secondsElapsedSinceLastFpsUpdate);
			secondsElapsedSinceLastFpsUpdate = 0;
			framesSinceLastFpsUpdate = 0;
		}
	}
	

	// *** Inizializzazione

	@Override
	public void stop() {
	    previousTimeInNanos = 0;
	    accumulatedTime = 0;
	    secondsElapsedSinceLastFpsUpdate = 0f;
	    framesSinceLastFpsUpdate = 0;
	    super.stop();
	}


	private PlayerObject player;

	public void initGame() {
		player = new PlayerObject();
		player.setPosition(new Vector2D(80.f, 100));
		this.scrollingView = new ScrollingView(player);
		player.setScrollingView(scrollingView);
	}

	public PlayerObject getPlayer() {
		return player;
	}
	
	private final double menuX = GameApp.WIDTH/2 - Sprite.MENU.getSpriteImage().getWidth()/2;
	private final double menuY = GameApp.HEIGHT/2 - Sprite.MENU.getSpriteImage().getHeight()/2;
	
	public void drawMenu() {
		context.drawImage(Sprite.MENU.getSpriteImage(), menuX, menuY);
	}

}
