package PlayerStates;

import Objects.PlayerObject;

public class FallingState implements PlayerState {
	private PlayerObject player;

	public FallingState(PlayerObject player) {
		this.player = player;
		//this.player.getVelocity().setValueY(JumpingState.JUMP_VELOCITY);
		
	}

	@Override
	public void rightPressed() {

	}

	@Override
	public void rightReleased() {
	}

	@Override
	public void leftPressed() {
	}

	@Override
	public void leftReleased() {
	}

	@Override
	public void jumpPressed() {
	}

	@Override
	public void update(Double seconds) {
		if (player.isLeftPressed() || player.isRightPressed()) {
			player.updateX(seconds);
		}

		player.updateY(seconds);
		player.setJumpingSprite();
	}

}
