package Objects;

import GameApp.GameApp;
import GameLoop.ScrollingView;
import PlayerStates.DeathState;
import PlayerStates.FallingState;
import PlayerStates.JumpingState;
import PlayerStates.PlayerState;
import PlayerStates.RunningState;
import PlayerStates.StandingState;
import PlayerStates.VictoryState;
import Utils.Sprite;
import Utils.Vector2D;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;

public class PlayerObject extends GameObject implements EventHandler<KeyEvent> {
	private ScrollingView view;
	public static final int CHANGE_FRAME_AFTER_TICK = 6;
	public static final double INITIAL_POSITION = 80;
	public final static double FLOOR_HEIGHT = 400;
	public final static double MIN_DRAW_POSITION = 80;
	public final static double MAX_DRAW_POSITION = 280;
	public final static double BASE_X_VELOCITY = 200;
	public  double MIN_PLAYER_POSITION = MIN_DRAW_POSITION;
	public  double MAX_PLAYER_POSITION = MAX_DRAW_POSITION;
	public final static double PADDING = 10;
	public static final double PLAYER_WIDTH = Sprite.ASTROSTANDINGLEFT.getSpriteImage().getWidth();
	public static final double PLAYER_HEIGHT = Sprite.ASTROSTANDINGLEFT.getSpriteImage().getHeight();
	public static final double GRAVITY = 3;
	
	private double absolutePositionX = INITIAL_POSITION + 50;

	private boolean isRightPressed = false;
	private boolean isLeftPressed = false;
	private boolean isFlying = false;
	
	

	private Sprite spriteToDraw;
	private PlayerState state = new StandingState(this);
	private boolean isRight;
	public Vector2D drawPosition = new Vector2D(INITIAL_POSITION, 0);
	
	public final static int IMMUNITY_TICK = 100;
	private int elapsedImmunityTicks = 0;
	private int lifePoints = 5;
	private boolean isDead = false;
	private boolean isWin = false;
	private boolean isImmune = false;
	private int elapsedChangeOpacityTicks = 0;
	private boolean printTrasparent = false;
	

	public PlayerObject() {
		super();
		this.isRight = true;
		this.spriteToDraw = Sprite.ASTROSTANDINGRIGHT;
		velocity = new Vector2D(BASE_X_VELOCITY, 0);
	}

	private final static Paint textPaint = Paint.valueOf("#ffffff");
	@Override
	public void draw(GraphicsContext context) {
		
		
		double lifeX = 0;
		for(int i = 0; i < lifePoints ; i++) {
			context.drawImage(Sprite.LIFE.getSpriteImage(), lifeX + PADDING , PADDING);
			lifeX += Sprite.LIFE.getSpriteImage().getWidth();
		}
			
		if (printTrasparent) {
			context.setGlobalAlpha(0.5);
			context.drawImage(spriteToDraw.getSpriteImage(), drawPosition.getValueX(), this.position.getValueY());
			context.setGlobalAlpha(1);

		} else {
			context.drawImage(spriteToDraw.getSpriteImage(), drawPosition.getValueX(), this.position.getValueY());
		}
		
		/* Utili per debug
		context.setFill(textPaint);
		String pos = "Camera Position " + position.getValueX();
		String absolutePos = "Absolute " + absolutePositionX;
		String posY = "PositionY: " + position.getValueY();
		context.fillText( pos , 20, 55);
		context.fillText( absolutePos , 20, 65);
		context.fillText( posY , 20, 75);*/
		
	}

	public void update(Double seconds) {
		//INTERAZIONE CON L'AMBIENTE:viene fatto dalla mappa, tutti i flag necessari verranno settati!
		if(isDead || isWin) {
			return;
		}
		
		//UPDATE DELLO STATO
		state.update(seconds);
		
		//Collisione con gli oggetti(scheletri)
		boolean collidedWithSkel = view.isCollidingWithSkeleton(absolutePositionX, position.getValueY(), PLAYER_WIDTH,PLAYER_HEIGHT);
		if(collidedWithSkel) {
			if(view.isHitSkeletonHead()) {
				setState(new JumpingState(this));
			} else if(!isImmune) {
				lifePoints--;
				isImmune = true;
			}
		}
		if( !isImmune && view.isCollidingWithRockets(absolutePositionX, position.getValueY(), PLAYER_WIDTH,PLAYER_HEIGHT)) {
			lifePoints--;
			isImmune = true;
		}
		
		//CHECK DEAD
		if(lifePoints <= 0) {
			isDead = true;
			printTrasparent = false;
			setState(new DeathState(this));
		}
		
		//Se non sono morto ho un po di tempo di immunita'
		if(isImmune) {
			elapsedChangeOpacityTicks++;
			if(elapsedChangeOpacityTicks == CHANGE_FRAME_AFTER_TICK) {
				printTrasparent = !printTrasparent;
				elapsedChangeOpacityTicks = 0;
			}
			elapsedImmunityTicks++;
			if(elapsedImmunityTicks >= IMMUNITY_TICK) {
				elapsedImmunityTicks = 0;
				isImmune = false;
				printTrasparent = false;
			}
		}
		
		if(position.getValueY() + PLAYER_HEIGHT >= GameApp.HEIGHT) {
			isDead = true;
			lifePoints = 0;
			setState(new DeathState(this));
			return;
		}
		
		//CHECK GOAL
		if(view.isGoalReached(absolutePositionX)) {
			isWin = true;
			setState(new VictoryState(this));
		}
	}

	// WASD per muoversi!
	@Override
	public void handle(KeyEvent event) {

		if (event.getCode() == KeyCode.D && event.getEventType() == KeyEvent.KEY_PRESSED) {
			isRightPressed = true;
			if (!isRight) {
				setIsRight(true);
			}
			state.rightPressed();
		} else if (event.getCode() == KeyCode.D && event.getEventType() == KeyEvent.KEY_RELEASED) {
			isRightPressed = false;
			if (isLeftPressed) {
				setIsRight(false);
			}
			state.rightReleased();
		} else if (event.getCode() == KeyCode.A && event.getEventType() == KeyEvent.KEY_PRESSED) {
			isLeftPressed = true;
			if (isRight) {
				setIsRight(false);
			}
			state.leftPressed();
		} else if (event.getCode() == KeyCode.A && event.getEventType() == KeyEvent.KEY_RELEASED) {
			isLeftPressed = false;
			if (isRightPressed) {
				setIsRight(true);
			}
			state.leftReleased();
		} else if (event.getCode() == KeyCode.SPACE && event.getEventType() == KeyEvent.KEY_PRESSED) {
			state.jumpPressed();
		}
	}

	public void setNextSprite() {
		if (isRight) {
			if (spriteToDraw == Sprite.ASTROSTANDINGRIGHT || spriteToDraw == Sprite.ASTROSTANDINGLEFT) {
				spriteToDraw = Sprite.ASTRORUNRIGHT1;
			} else if (spriteToDraw == Sprite.ASTRORUNRIGHT1) {
				spriteToDraw = Sprite.ASTRORUNRIGHT12;
			}else if (spriteToDraw == Sprite.ASTRORUNRIGHT12) {
				spriteToDraw = Sprite.ASTRORUNRIGHT2;
			} else {
				this.setStandingSprite();
			}
		} else {
			if (spriteToDraw == Sprite.ASTROSTANDINGLEFT || spriteToDraw == Sprite.ASTROSTANDINGRIGHT) {
				spriteToDraw = Sprite.ASTRORUNLEFT1;
			} else if (spriteToDraw == Sprite.ASTRORUNLEFT1) {
				spriteToDraw = Sprite.ASTRORUNLEFT12;
			}else if (spriteToDraw == Sprite.ASTRORUNLEFT12) {
				spriteToDraw = Sprite.ASTRORUNLEFT2;
			} else {
				this.setStandingSprite();
			}
		}
	}

	public void setStandingSprite() {
		if (isRight) {
			spriteToDraw = Sprite.ASTROSTANDINGRIGHT;
		} else {
			spriteToDraw = Sprite.ASTROSTANDINGLEFT;
		}
	}

	public void setJumpingSprite() {
		if (isRight) {
			spriteToDraw = Sprite.ASTROJUMPRIGHT;
		} else {
			spriteToDraw = Sprite.ASTROJUMPLEFT;
		}
	}

	public void setState(PlayerState state) {
		System.out.println("Transizione Stato: " + this.state.getClass() + "  ---> " + state.getClass());
		this.state = state;
		if(state.getClass() == JumpingState.class || state.getClass() == FallingState.class) {
			isFlying = true;
		} else {
			isFlying = false;
		}
	}

	public void setIsRight(boolean bool) {
		if (isRight != bool) {
			this.velocity.setValueX(this.velocity.getValueX() * (-1));
			this.isRight = bool;
		}
	}

	public boolean isRight() {
		return this.isRight;
	}

	public Vector2D getVelocity() {
		return this.velocity;
	}

	public void updateX(Double seconds) {
		//UPDATE
		double pathDone =  seconds * velocity.getValueX();
		double currentAbsolute = absolutePositionX;
		this.absolutePositionX += pathDone;
		//Qui mi calcolo l'effettivo percorso fatto, e poi aggiorno la camera in base al risultato!
		boolean isCollided = view.isCollidingWithBlocks(absolutePositionX, position.getValueY(), PLAYER_WIDTH,PLAYER_HEIGHT, true);
		if(isCollided) {
			absolutePositionX = view.getCollidedPosition();
			pathDone = absolutePositionX - currentAbsolute;
		}
		
		
		//UPDATE CAMERA + DRAW  PERSONAGGIO!
		updateDrawPositionSpace();
		drawPosition.setValueX(drawPosition.getValueX() + pathDone);
		if(drawPosition.getValueX() <= MIN_PLAYER_POSITION ) {
			double diff = drawPosition.getValueX() - MIN_DRAW_POSITION ;
			drawPosition.setValueX(MIN_PLAYER_POSITION);
			position.setValueX(position.getValueX() + diff);
		} else if(drawPosition.getValueX() >= MAX_PLAYER_POSITION) {
			double diff = drawPosition.getValueX() - MAX_DRAW_POSITION ;
			drawPosition.setValueX(MAX_PLAYER_POSITION);
			position.setValueX(position.getValueX() +  diff);
		}

		if(position.getValueX() <= INITIAL_POSITION) {
			position.setValueX(INITIAL_POSITION);
		}
		if(absolutePositionX < 50) {
			absolutePositionX = 50;
		}
		
	}
	
	//Per allargare lo spazio di disegno del giocatore quando e' all'inizio o alla fine!
	public void updateDrawPositionSpace() {
		if(position.getValueX() <= MIN_DRAW_POSITION) {
			MIN_PLAYER_POSITION = 0;
		} else {
			MIN_PLAYER_POSITION = MIN_DRAW_POSITION;
		}
		if(position.getValueX() >= GameApp.LEVEL_WIDTH - (GameApp.WIDTH - MIN_DRAW_POSITION)) {
			MAX_PLAYER_POSITION = GameApp.WIDTH;
		} else {
			MAX_PLAYER_POSITION = MAX_DRAW_POSITION;
		}
	}

	public void updateY(Double seconds) {
		//if(velocity.getValueY() == 0)
			//return;
		velocity.setValueY(velocity.getValueY() + GRAVITY);
		this.position.setValueY(this.position.getValueY() + (seconds * this.velocity.getValueY()));
		boolean isCollided = view.isCollidingWithBlocks(absolutePositionX, position.getValueY(), PLAYER_WIDTH,PLAYER_HEIGHT, false);
		if(isCollided) {
			if(view.getAndResetIsCollidedTop()) {
				position.setValueY(view.getCollidedPosition() + 1);
				velocity.setValueY(0);
				//if(state.getClass() != FallingState.class) {
				this.setState(new FallingState(this));
				//}
			} else if(view.getAndResetIsCollidedBot()) {
				position.setValueY( view.getCollidedPosition());
				velocity.setValueY(0);
				if(this.isFlying()) {
					this.setState(getStateAfterFalling());
				}
			}
		} else {
			if(!isFlying()) {
				this.setState(new FallingState(this));
			}
		}
	}
	
	private PlayerState getStateAfterFalling() {
		if (isLeftPressed || isRightPressed) {
			return new RunningState(this);
		} else {
			return new StandingState(this);
		}
	}

	public boolean isRightPressed() {
		return isRightPressed;
	}

	public void setRightPressed(boolean isRightPressed) {
		this.isRightPressed = isRightPressed;
	}

	public boolean isLeftPressed() {
		return isLeftPressed;
	}

	public void setLeftPressed(boolean isLeftPressed) {
		this.isLeftPressed = isLeftPressed;
	}
	
	public void setScrollingView(ScrollingView scrollingView) {
		this.view = scrollingView;
	}

	public boolean isFlying() {
		return isFlying;
	}

	public void setFlying(boolean isFlying) {
		this.isFlying = isFlying;
	}
	
	public void setSprite(Sprite sprite) {
		this.spriteToDraw = sprite;
	}
	

}
