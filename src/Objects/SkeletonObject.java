package Objects;

import GameApp.GameApp;
import Utils.Sprite;
import Utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class SkeletonObject extends GameObject {
	private Sprite skeletonSprite = Sprite.RUNRIGHT1;
	private final int CHANGE_VELOCITY_TICKS = 10;
	private final int CHANGE_FRAME_TICK = 8;
	private int elapsed_velocity_ticks = 0;
	private int elapsed_frame_ticks = 0;
	
	private boolean isRight = true;
	private final double SKEL_HEIGHT = skeletonSprite.getSpriteImage().getHeight();
	private final double SKEL_WIDTH = skeletonSprite.getSpriteImage().getWidth();
	
	private double cameraPositionX = 0;
	private boolean isDead = false;
	private boolean isDeadAnimationEnded = false;
	
	public SkeletonObject(Vector2D position) {
		this.position = position;
		position.setValueY(position.getValueY() + (GameApp.BLOCK_SIZE - SKEL_HEIGHT));
		velocity = new Vector2D(100, 100);
	}
	
	public void update(Double seconds) {
		if(!isDead) {
			this.position.setValueX(position.getValueX() + (velocity.getValueX() * seconds));
		}
		elapsed_frame_ticks++;
		if(elapsed_frame_ticks >= CHANGE_FRAME_TICK) {
			setNextSprite();
			elapsed_frame_ticks = 0;
			elapsed_velocity_ticks++;
			if(elapsed_velocity_ticks == CHANGE_VELOCITY_TICKS) {
				elapsed_velocity_ticks = 0;
				velocity.setValueX(-velocity.getValueX());
				isRight = !isRight;
			}
		}
		
	}

	@Override
	public void draw(GraphicsContext context) {
		if(!isDead) {
			context.drawImage(skeletonSprite.getSpriteImage(), position.getValueX() - cameraPositionX, this.position.getValueY());
		} else {
			context.drawImage(skeletonSprite.getSpriteImage(), position.getValueX() - cameraPositionX, this.position.getValueY() + (SKEL_HEIGHT - skeletonSprite.getSpriteImage().getHeight()));
		}
	}
	
	
	public void setNextSprite() {
		if(isDead) {
			if(skeletonSprite == Sprite.DEAD5) {
				skeletonSprite = Sprite.DEAD6;
				isDeadAnimationEnded = true;
			} else if(skeletonSprite == Sprite.DEAD4) {
				skeletonSprite = Sprite.DEAD5;
			} else if(skeletonSprite == Sprite.DEAD3) {
				skeletonSprite = Sprite.DEAD4;
			} else if(skeletonSprite == Sprite.DEAD2) {
				skeletonSprite = Sprite.DEAD3;
			} else if(skeletonSprite == Sprite.DEAD1) {
				skeletonSprite = Sprite.DEAD2;
			} else {
				skeletonSprite = Sprite.DEAD1;
			}
		} else {
			if (isRight) {
				if (skeletonSprite == Sprite.STANDINGRIGHT || skeletonSprite == Sprite.STANDINGLEFT) {
					skeletonSprite = Sprite.RUNRIGHT1;
				} else if (skeletonSprite == Sprite.RUNRIGHT1) {
					skeletonSprite = Sprite.RUNRIGHT2;
				} else {
					skeletonSprite = Sprite.STANDINGRIGHT;
				}
			} else {
				if (skeletonSprite == Sprite.STANDINGRIGHT || skeletonSprite == Sprite.STANDINGLEFT) {
					skeletonSprite = Sprite.RUNLEFT1;
				} else if (skeletonSprite == Sprite.RUNLEFT1) {
					skeletonSprite = Sprite.RUNLEFT2;
				} else {
					skeletonSprite = Sprite.STANDINGLEFT;
				}
			}
		}
	}
	
	public void setCameraPosition(double cameraPosition) {
		this.cameraPositionX = cameraPosition;
	}

	public double getSKEL_HEIGHT() {
		return SKEL_HEIGHT;
	}

	public double getSKEL_WIDTH() {
		return SKEL_WIDTH;
	}

	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public boolean deadAnimationEnded() {
		return isDeadAnimationEnded;
	}
	
	
	
	
	
}
