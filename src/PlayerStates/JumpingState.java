package PlayerStates;

import Objects.PlayerObject;

public class JumpingState implements PlayerState {
	private PlayerObject player;
	public final static double JUMP_VELOCITY = 200;
	//private final int JUMP_TICK_NUMBER = 50;
	//private int elapsedJumpTick = 0;

	public JumpingState(PlayerObject player) {
		this.player = player;
		this.player.getVelocity().setValueY(-JUMP_VELOCITY);
		this.player.setJumpingSprite();
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
		if (player.getVelocity().getValueY() <= 0) {
			player.setState(new FallingState(player));
		}
		player.setJumpingSprite();
	}

}
