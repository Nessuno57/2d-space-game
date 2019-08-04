package GameLoop;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import GameApp.GameApp;
import Objects.PlayerObject;
import Objects.Rocket;
import Objects.SkeletonObject;
import Utils.Point;
import Utils.Sprite;
import Utils.Vector2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
public class ScrollingView {
	//0 = vuoto, 1/2 = terreno , 3 = scheletri , 4 = goal
	private int[] level = { 
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1,   0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 3, 0, 0, 1, 0, 0,   0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 2, 1, 2, 2, 0, 0,   0, 0, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 0, 2, 2, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   1, 1, 1, 1, 1, 2, 2, 1, 1, 0, 0, 2, 2, 2, 0, 0, 0, 2, 0, 0,   0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 1, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 2, 0, 0, 2, 0, 0,   0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0,   0,
			0,   0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0,   1, 2, 2, 2, 2, 2, 1, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0,   0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0,   3, 0, 0, 3, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0,   0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0,   0,
			0,   1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,   1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,   0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1,   1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 2, 2, 2, 1, 1,   0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 3, 0, 0, 0, 1, 1, 1, 1, 1,   0,
			0,   2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,   2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,   0, 2, 0, 2, 0, 2, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 2, 2, 2,   2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,   0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2,   0 
			};
	
	private double startX = -50;
	//Offset per lo spostamento dei blocchi!
	private double offsetX = 0;
	//Indice del primo blocco in alto a sinistra da disegnare
	private int leftBlockIndex = 0;
	private PlayerObject player;
	private double lastPlayerPosition = 80;
	private boolean isCollidedTop = false;
	private boolean isCollidedBot = false;
	private boolean isCollidedX = false;
	private double collidedPosition;
	private ArrayList<SkeletonObject> skeletons;
	private ArrayList<SkeletonObject> deadSkeletons = new ArrayList<>();
	private boolean isHitSkeletonHead = false;
	private ArrayList<Rocket> rockets = new ArrayList<>();
	public final static int TICK_FOR_NEW_ROCKET = 150;
	private int ticksForRocketElapsed = 0;
	private double goalX = GameApp.BLOCKS_IN_A_ROW * GameApp.BLOCK_SIZE;
	
	public ScrollingView(PlayerObject player) {
		//se c'e' una mappa prendo quella!
		
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("customlevel.txt")));
			int mapIndex = 1;
			int numRead = 0;
			for(int i = 0; i < 1000; i++) {
				int spriteId = Integer.parseInt(reader.readLine());
				level[mapIndex] = spriteId;
				mapIndex++;
				numRead ++;
				if(numRead == 100) {
					numRead = 0;
					mapIndex += 2;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("File non trovato!");
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.player = player;
		lastPlayerPosition = player.getPosition().getValueX();
		skeletons = new ArrayList<>();
		for(int i = 0; i < level.length; i++) {
			if(level[i] == 3) {
				level[i] = 0;
				int indexX = i%GameApp.BLOCKS_IN_A_ROW;
				int indexY = i/GameApp.BLOCKS_IN_A_ROW;
				skeletons.add(new SkeletonObject(new Vector2D(indexX*GameApp.BLOCK_SIZE, indexY*GameApp.BLOCK_SIZE)));
			}
			if(level[i] == 4) {
				int indexX = i%GameApp.BLOCKS_IN_A_ROW;
				goalX = indexX*GameApp.BLOCK_SIZE;
			}
		}
	}

	public void drawScrollingView(GraphicsContext context) {
		double X = offsetX;
		double Y = GameApp.BLOCK_SIZE;
		
		for( int i=0; i < GameApp.BLOCKS_IN_A_COL; i++ ) {
			X = startX + offsetX;
			int firstBlockIndex = leftBlockIndex + (GameApp.BLOCKS_IN_A_ROW * i);
			for(int j = firstBlockIndex; j < firstBlockIndex + GameApp.BLOCKS_IN_A_VIEW ; j++) {
				drawBlock(level[j], X, Y * i, context);
				X += GameApp.BLOCK_SIZE;
			}
		}
		
		//SKELETONS
		double cameraPosition = leftBlockIndex*GameApp.BLOCK_SIZE - offsetX - startX;
		for(SkeletonObject skel: skeletons) {
			skel.setCameraPosition(cameraPosition);
			skel.draw(context);
		}
		
		ArrayList<SkeletonObject> skelToRemove = new ArrayList<>();
		for(SkeletonObject deadSkel: deadSkeletons) {
			deadSkel.setCameraPosition(cameraPosition);
			deadSkel.draw(context);
			if(deadSkel.deadAnimationEnded()) {
				skelToRemove.add(deadSkel);
			}
		}
		deadSkeletons.removeAll(skelToRemove);
		//ROCKETS
		for(Rocket rocket: rockets) {
			rocket.setCameraPosition(cameraPosition);
			rocket.draw(context);
		}
		
	}
	
	private void drawBlock(int blockType, double X , double Y, GraphicsContext context) {
		if(blockType == 1) {
			context.drawImage(Sprite.LUNARTERRAIN1.getSpriteImage(), X, Y);
		} else if (blockType == 2) {
			context.drawImage(Sprite.LUNARTERRAIN2.getSpriteImage(), X, Y);
		} else if(blockType == 4) {
			context.drawImage(Sprite.GOAL.getSpriteImage(), X, Y);
		}
	}
	
	
	public void updateScrollingView(Double seconds) {
		double currentPlayerPosition = player.getPosition().getValueX();
		double positionDiff = lastPlayerPosition - currentPlayerPosition;
		lastPlayerPosition = currentPlayerPosition;
		offsetX += positionDiff;
		if (offsetX <= -GameApp.BLOCK_SIZE
				&& leftBlockIndex < GameApp.BLOCKS_IN_A_ROW - GameApp.NUM_PADDING_BLOCKS_PER_ROW) {
			offsetX += 50;
			leftBlockIndex++;
		} else if (offsetX >= GameApp.BLOCK_SIZE && leftBlockIndex > 0) {
			offsetX -= 50;
			leftBlockIndex--;
		}
		
		//SKELETONS
		for (SkeletonObject skel : skeletons) {
			skel.update(seconds);
		}

		for (SkeletonObject deadSkel : deadSkeletons) {
			deadSkel.update(seconds);
		}
		
		//ROCKETS
		ticksForRocketElapsed++;
		if(ticksForRocketElapsed >= TICK_FOR_NEW_ROCKET && currentPlayerPosition < 4600) {
			ticksForRocketElapsed = 0;
			rockets.add(new Rocket(new Vector2D(currentPlayerPosition + 1000 , player.getPosition().getValueY() + 20)));
		}
		double cameraPosition = leftBlockIndex*GameApp.BLOCK_SIZE - offsetX - startX;
		if(rockets.size() > 0 && rockets.get(0).getPosition().getValueX() + 40 < cameraPosition ) {
			rockets.remove(0);
		}
		for(Rocket rocket: rockets) {
			rocket.update(seconds);
		}
	}
	
	
	//isX indica se vogliamo le collisioni nell'asse delle X o delle Y!
	public boolean isCollidingWithBlocks(double playerX, double playerY, double playerWidth, double playerHeight, boolean isX) {
		ArrayList<Point> points;
		if(isX) {
			points = getCollidingPointX(playerX, playerY, playerWidth, playerHeight);			
		} else {
			points = getCollidingPointY(playerX, playerY, playerWidth, playerHeight);	
		}
		for(Point p: points) {
			
			try {
				int index = p.getX() + GameApp.BLOCKS_IN_A_ROW * p.getY();
				if(level[index] != 0 && level[index] != 4 ) {
					collide(playerX,playerY,playerWidth, playerHeight, p, isX);
				}
				
			} catch(ArrayIndexOutOfBoundsException e) {
			}
		}
		
		if (isX) {
			if (isCollidedX) {
				isCollidedX = false;
				return true;
			}
		} else {
			return isCollidedBot || isCollidedTop;
		}
		return false;
	}
	
	private void collide(double playerX, double playerY, double playerWidth, double playerHeight, Point point, boolean isX) {
		Rectangle2D rect = new Rectangle2D(point.getAbsoluteX(), point.getAbsoluteY(), GameApp.BLOCK_SIZE, GameApp.BLOCK_SIZE);
		boolean isColl = isColliding(playerX, playerY, playerWidth, playerHeight, rect);
		if (isColl) {
			//CASO COLLISIONI X
			if (isX) {
				isCollidedX = true;
				if (playerX < point.getAbsoluteX()) {
					collidedPosition = point.getAbsoluteX() - playerWidth;
				} else {
					collidedPosition = point.getAbsoluteX() + GameApp.BLOCK_SIZE;
				}
				// CASO COLLISIONI Y
			} else {
				// Caso collisione in top
				if ( playerY > point.getAbsoluteY() ) {
					isCollidedTop = true;
					collidedPosition = point.getAbsoluteY() + GameApp.BLOCK_SIZE;
				} else {
					// Caso collisione in bottom
					isCollidedBot = true;
					collidedPosition = point.getAbsoluteY() - playerHeight;
				}
			}
		}
	}
	
	private boolean isColliding(double playerX, double playerY, double playerWidth, double playerHeight, Rectangle2D rect) {
		return !( playerX+playerWidth <= rect.getMinX()||
	            //caso box troppo a sinistra
	            playerX > rect.getMaxX() ||
	            //caso box troppo in basso
	            playerY + playerHeight < rect.getMinY() ||
	            //caso box troppo in alto
	            playerY > rect.getMaxY());
	}
	
	public double getCollidedPosition() {
		return collidedPosition;
	}
	
	private ArrayList<Point> getCollidingPointX(double playerX, double playerY, double playerWidth, double playerHeight){
		double halfWidht = playerWidth/2;
		double halfHeight = playerHeight/2;
		//Centro del pg
		int blockXIndex = (int) (( (playerX + halfWidht) / GameApp.BLOCK_SIZE));
		int blockYIndex = (int) (( (playerY + halfHeight) / GameApp.BLOCK_SIZE));
		
		//int blockXIndex = (int) (( (playerX) / GameApp.BLOCK_SIZE));
		//int blockYIndex = (int) (( (playerY) / GameApp.BLOCK_SIZE));
		
		//System.out.println("Block position in array: " + blockXIndex + " / " + blockYIndex + " absolutePosition: " + playerX);
		
		ArrayList<Point> points = new ArrayList<>();
		points.add(new Point(blockXIndex - 1, blockYIndex));
		points.add(new Point(blockXIndex + 1, blockYIndex));
		if(player.isFlying()) {
			points.add(new Point(blockXIndex + 1, blockYIndex + 1));
			points.add(new Point(blockXIndex - 1, blockYIndex + 1));
			points.add(new Point(blockXIndex + 1, blockYIndex - 1));
			points.add(new Point(blockXIndex - 1, blockYIndex - 1));
		}
		return points;	
	}
	
	private ArrayList<Point> getCollidingPointY(double playerX, double playerY, double playerWidth, double playerHeight){
		
		//Blocco del punto in basso a sinistra del personaggio
		int blockXIndex = (int) (( (playerX) / GameApp.BLOCK_SIZE));
		int blockYIndex = (int) (( (playerY + playerWidth) / GameApp.BLOCK_SIZE));
		//Blocco del punto in basso a destra del personaggio
		int blockXIndex2 = (int) (( (playerX + playerWidth) / GameApp.BLOCK_SIZE));
		int blockYIndex2 = (int) (( (playerY + playerWidth) / GameApp.BLOCK_SIZE));
		
		//System.out.println("Block position in array: " + blockXIndex + " / " + blockYIndex + " PositionY with height: " + (playerY + playerHeight) + " AbsoluteX: " + playerX);
		ArrayList<Point> points = new ArrayList<>();
		
		if(blockXIndex == blockXIndex2) {
			points.add(new Point(blockXIndex, blockYIndex + 1));
		} else {
			points.add(new Point(blockXIndex, blockYIndex + 1));
			points.add(new Point(blockXIndex2, blockYIndex2 + 1));
		}
		if(player.isFlying()) {
			points.add(new Point(blockXIndex, blockYIndex - 1));
			points.add(new Point(blockXIndex , blockYIndex - 1));
			points.add(new Point(blockXIndex + 1, blockYIndex - 1));
		}
		return points;	
	}
	
	public boolean isCollidingWithSkeleton(double playerX, double playerY, double playerWidth, double playerHeight) {
		boolean isColliding = false;
		int i = 0;
		Rectangle2D rect = null;
		for(i = 0; i < skeletons.size(); i++) {
			rect = new Rectangle2D(skeletons.get(i).getPosition().getValueX(),skeletons.get(i).getPosition().getValueY(),skeletons.get(i).getSKEL_WIDTH(),skeletons.get(i).getSKEL_HEIGHT());
			isColliding = isColliding(playerX, playerY, playerWidth, playerHeight,rect);
			if(isColliding) {
				break;
			}
			
		}
		
		if(isColliding) {
			//double var = GameLoop.TIME_STEP * player.getVelocity().getValueY();
			//double var2 = skeletons.get(i).getPosition().getValueY() + GameLoop.TIME_STEP * player.getVelocity().getValueY();
			//se lo prendo in testa uccido lo scheletro, viceversa prendo danni
			if(playerY + playerHeight <= skeletons.get(i).getPosition().getValueY() + GameLoop.TIME_STEP * player.getVelocity().getValueY()) {
				SkeletonObject skelDead = skeletons.remove(i);
				skelDead.setDead(true);
				deadSkeletons.add(skelDead);
				isHitSkeletonHead = true;
			}
		}
		return isColliding;
	}
	
	public boolean isCollidingWithRockets(double playerX, double playerY, double playerWidth, double playerHeight) {
		boolean isColliding = false;
		int i = 0;
		Rectangle2D rect = null;
		for(i = 0; i < rockets.size(); i++) {
			Rocket rocket = rockets.get(i);
			if(!rocket.isDidAlreadyHit()) {
				rect = new Rectangle2D(rocket.getPosition().getValueX(),rocket.getPosition().getValueY(),rocket.getROCKET_WIDTH(),rocket.getROCKET_HEIGHT());
				isColliding = isColliding(playerX, playerY, playerWidth, playerHeight,rect);
				if(isColliding) {
					rocket.setDidAlreadyHit(true);
					break;
				}		
			}
			
		}
		return isColliding;
	}

	public boolean getAndResetIsCollidedTop() {
		boolean collided = isCollidedTop;
		isCollidedTop = false;
		return collided;
	}

	public void setCollidedTop(boolean isCollidedTop) {
		this.isCollidedTop = isCollidedTop;
	}

	public boolean getAndResetIsCollidedBot() {
		boolean collided = isCollidedBot;
		isCollidedBot = false;
		return collided;
	}

	public void setCollidedBot(boolean isCollidedBot) {
		this.isCollidedBot = isCollidedBot;
	}
	
	public boolean isHitSkeletonHead() {
		boolean tmp = isHitSkeletonHead;
		isHitSkeletonHead = false;
		return tmp;
	}
	
	public boolean isGoalReached(double playerX) {
		if(goalX <= playerX)
			return true;
		else 
			return false;
	}
	
	
}
