package simulator.model;

import java.util.List;


import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private double g;
	
	public NewtonUniversalGravitation(double G) {
		if (G <= 0) {
			throw new IllegalArgumentException("Invalid argument in NewtonUniversalGravitation builder");
		}
		this.g = G;
	}
	
	@Override
	public void apply(List<Body> bodies) {
		Vector2D d = new Vector2D();
		for (Body bi : bodies) {
			for (Body bj : bodies) {
				if (bi.getId() != bj.getId()) {
					double force = 0;
					double distance = bj.getPosition().distanceTo(bi.getPosition());
					Vector2D dis = bj.getPosition().minus(bi.getPosition()).direction();
					if (distance != 0) {
						double mass = bi.getMass() * bj.getMass();
						double pos = distance * distance;
						force = (this.g * mass) / pos;
						d = dis.scale(force).plus(d);
					}
				}
			}
			bi.resetForce();
			bi.addForce(d);
		}
	}
	@Override
	public String toString() {
		return "Newton's Universal Gravitation whit G = " + this.g;
	}
}