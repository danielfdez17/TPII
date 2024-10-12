package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	private Vector2D c;
	private double g;
	private MovingTowardsFixedPoint MTbody;
	
	
	public MovingTowardsFixedPointBuilder() {
		this("mtfp","MovingTowardsFixedPoint");
	}

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		if (!data.has("c") && !data.has("g")) {
			this.c = new Vector2D();
			this.g = 9.81;
			this.MTbody = new MovingTowardsFixedPoint(c, g);
			return this.MTbody;
		}
		else if (!data.has("c")) {
			this.c = new Vector2D();
			this.MTbody = new MovingTowardsFixedPoint(c, data.getDouble("g"));
			return this.MTbody;
		}
		else if (!data.has("g")) {
			this.g = 9.81;
			JSONArray c = data.getJSONArray("c");
			double aux1 =c.getDouble(0);
			double aux2 =c.getDouble(1);
			this.c = new Vector2D(aux1,aux2);
			this.MTbody = new MovingTowardsFixedPoint(this.c, g);
			return this.MTbody;
		}
		JSONArray c = data.getJSONArray("c");
		double aux1 = c.getDouble(0);
		double aux2 = c.getDouble(1);
		this.c = new Vector2D(aux1,aux2);
		
		this.g = data.getDouble("g");
		this.MTbody = new MovingTowardsFixedPoint(this.c, g);
		return this.MTbody;
	}

}