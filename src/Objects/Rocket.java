package Objects;

import Utils.Sprite;
import Utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class Rocket extends GameObject {
	private Sprite sprite = Sprite.ROCKET1;
	public final static int ROCKET_SPEED_X = -150;
	private final double ROCKET_HEIGHT = sprite.getSpriteImage().getHeight();
	private final double ROCKET_WIDTH = sprite.getSpriteImage().getWidth();
	private double cameraPositionX;
	private boolean didAlreadyHit = false;
	public final int CHANGE_SPRITE_TICKS = 5;
	private int elapsed_tick = 0;
	

	public Rocket(Vector2D position) {
		this.position = position;
	}
	
	
	@Override
	public void update(Double seconds) {
		this.position.setValueX(position.getValueX() + (ROCKET_SPEED_X * seconds));
		elapsed_tick++;
		if(elapsed_tick >= CHANGE_SPRITE_TICKS) {
			setNextSprite();
			elapsed_tick = 0;
		}
	}

	@Override
	public void draw(GraphicsContext context) {
		context.drawImage(sprite.getSpriteImage(), position.getValueX() - cameraPositionX, this.position.getValueY());
	}
	
	public void setCameraPosition(double cameraPosition) {
		this.cameraPositionX = cameraPosition;
	}


	public double getROCKET_HEIGHT() {
		return ROCKET_HEIGHT;
	}


	public double getROCKET_WIDTH() {
		return ROCKET_WIDTH;
	}
	
	
	private void setNextSprite() {
		if(sprite == Sprite.ROCKET1) {
			sprite = Sprite.ROCKET2;
		} else if(sprite == Sprite.ROCKET2) {
			sprite = Sprite.ROCKET3;
		} else {
			sprite = Sprite.ROCKET1;
		}
	}


	public boolean isDidAlreadyHit() {
		return didAlreadyHit;
	}


	public void setDidAlreadyHit(boolean didAlreadyHit) {
		this.didAlreadyHit = didAlreadyHit;
	}
	
	


	
}
