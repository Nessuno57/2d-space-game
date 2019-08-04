package PlayerStates;

import Objects.PlayerObject;

public class RunningState implements PlayerState {
	private PlayerObject player;
	private int timeTick = 0;

	public RunningState(PlayerObject player) {
		this.player = player;
	}

	@Override
	public void rightPressed() {
	}

	@Override
	public void rightReleased() {
		if (!player.isLeftPressed() && !player.isRightPressed()) {
			player.setState(new StandingState(player));
		}
	}

	@Override
	public void leftPressed() {
	}

	@Override
	public void leftReleased() {
		if (!player.isLeftPressed() && !player.isRightPressed()) {
			player.setState(new StandingState(player));
		}
	}

	@Override
	public void jumpPressed() {
		player.setState(new JumpingState(player));

	}

	@Override
	public void update(Double seconds) {
		timeTick++;
		if (timeTick == PlayerObject.CHANGE_FRAME_AFTER_TICK) {
			player.setNextSprite();
			timeTick = 0;
		}
		player.updateX(seconds);
		player.updateY(seconds);

	}

}
