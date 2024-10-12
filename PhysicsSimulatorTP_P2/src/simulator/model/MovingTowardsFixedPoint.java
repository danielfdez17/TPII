package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		if (c == null || g <= 0) {
			throw new IllegalArgumentException("Invalid argument in MovingTowardsFixedPoint builder");
		}
		this.c = c;
		this.g = g;
	}

	@Override
	public void apply(List<Body> bodies) {
		Vector2D d;
		for (Body b : bodies) {
			d = new Vector2D(this.c.direction().minus(b.getPosition().direction()));
			b.addForce(d.scale(this.g * b.getMass()));
		}
	}
	@Override
	public String toString() {
		return "Moving towards " + this.c + " with constant acceleration " + this.g;
	}
}
