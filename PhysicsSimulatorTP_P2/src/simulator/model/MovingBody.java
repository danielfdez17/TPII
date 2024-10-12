package simulator.model;

import simulator.misc.Vector2D;

public class MovingBody extends Body {
	public MovingBody(String id, String gid, Vector2D position, Vector2D velocity, double mass) {
		super(id, gid, position, velocity, mass);
	}

	@Override
	void advance(double dt) {
		Vector2D a;
		if (this.mass == 0) {
			a = new Vector2D();
		}
		else {
			a = new Vector2D(this.force.scale(1 / this.mass));
		}
		this.position = this.position.plus(this.velocity.scale(dt).plus(a.scale(0.5*dt * dt))); // p = p + 1/2 * a * dt * dt
		//this.position = this.position.plus(this.velocity.scale(dt)); // p = p + v * t
		this.velocity = a.scale(dt).plus(velocity); // v = v + a * dt
	}
}
