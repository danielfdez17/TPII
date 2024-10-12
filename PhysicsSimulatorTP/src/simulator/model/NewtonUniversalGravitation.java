package simulator.model;

import java.util.List;

import simulator.misc.Vector2D;

public class NewtonUniversalGravitation implements ForceLaws {

	private double g;
	private Vector2D d;
	
	public NewtonUniversalGravitation(double G) {
		if (G <= 0) {
			throw new IllegalArgumentException();
		}
		this.g = G;
		this.d = new Vector2D();
	}
	
	@Override
	public void apply(List<Body> bodies) {
		Body bi, bj;
		for (int i = 0; i < bodies.size(); i++) {
			this.d= new Vector2D();
			bi = bodies.get(i);
			for (int j = 0; j < bodies.size(); j++) {
				if (i != j) {
					bj = bodies.get(j);
					double force = 0;
					double distance = bj.getPosition().distanceTo(bi.getPosition());
					Vector2D dis = bj.getPosition().minus(bi.getPosition());
					dis = dis.direction();
					if (distance == 0) {
						force = 0;
					}
					else {
						double mass = bi.getMass() * bj.getMass();
						double pos = distance * distance;
						force = (this.g * mass) / pos;
						this.d = dis.scale(force).plus(d);
					}
				}
			}
			bi.resetForce();
			bi.addForce(d);
		}
	}
	
}