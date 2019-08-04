package PlayerStates;

import Objects.PlayerObject;
import Utils.Sprite;

public class VictoryState implements PlayerState {
	private PlayerObject player;
	
	public VictoryState(PlayerObject player) {
		this.player = player;
		player.setSprite(Sprite.ASTROWIN1);
	}
	
	@Override
	public void rightPressed() {
		
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
		// TODO Auto-generated method stub
		
	}

}
