package PlayerStates;

import Objects.PlayerObject;
import Utils.Sprite;

public class DeathState implements PlayerState {
	private PlayerObject player;
	
	public DeathState(PlayerObject player) {
		this.player = player;
		player.setSprite(Sprite.ASTRODEAD1);
	}

	@Override
	public void rightPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rightReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftReleased() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void jumpPressed() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Double seconds) {
		
	}

}
