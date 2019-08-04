package Objects;

import Utils.Vector2D;
import javafx.scene.canvas.GraphicsContext;

public class GameObject {
	protected Vector2D position;
	protected Vector2D velocity;

	public GameObject() {
	}

	public void update(Double seconds) {

	}

	public void draw(GraphicsContext context) {

	}

	public Vector2D getPosition() {
		return position;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public Vector2D getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2D velocity) {
		this.velocity = velocity;
	}

}
