package PlayerStates;

import Objects.PlayerObject;

public class StandingState implements PlayerState {
	private PlayerObject player;

	public StandingState(PlayerObject player) {
		this.player = player;
		this.player.setStandingSprite();
	}

	@Override
	public void rightPressed() {
		player.setIsRight(true);
		player.setState(new RunningState(player));
	}

	@Override
	public void rightReleased() {
		// TODO Auto-generated method stub

	}

	@Override
	public void leftPressed() {
		player.setIsRight(false);
		player.setState(new RunningState(player));
	}

	@Override
	public void leftReleased() {
		// TODO Auto-generated method stub

	}

	@Override
	public void jumpPressed() {
		player.setState(new JumpingState(player));

	}

	@Override
	public void update(Double seconds) {
		player.updateY(seconds);
		this.player.setStandingSprite();
	}
}
