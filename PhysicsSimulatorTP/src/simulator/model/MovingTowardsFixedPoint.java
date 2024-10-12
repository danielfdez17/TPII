package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class MovingTowardsFixedPoint implements ForceLaws {
	
	private Vector2D c;
	private double g;
	
	public MovingTowardsFixedPoint(Vector2D c, double g) {
		if (c == null || g <= 0) {
			throw new IllegalArgumentException();
		}
		this.c = c; // a lo mejor debemos usar un constructor de vector
		this.g = g;
	}

	@Override
	public void apply(List<Body> bodies) {
		Vector2D d;
		for (int i = 0; i < bodies.size(); i++) {
			Body bi = bodies.get(i);
			d = new Vector2D(this.c.direction().minus(bi.getPosition().direction()));
			bi.addForce(d.scale(this.g * bi.mass));
		}
	}

}
