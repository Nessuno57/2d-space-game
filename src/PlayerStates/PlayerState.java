package PlayerStates;

public interface PlayerState {

	void rightPressed();

	void rightReleased();

	void leftPressed();

	void leftReleased();

	void jumpPressed();

	void update(Double seconds);
}
