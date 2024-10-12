package simulator.model;

import simulator.misc.Vector2D;

public class StationaryBody extends Body {

	public StationaryBody(String id, String gid, Vector2D position, double mass) {
		super(id, gid, position, new Vector2D(), mass);
	}

	@Override
	void advance(double dt) {}

}
