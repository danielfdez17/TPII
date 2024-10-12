package simulator.factories;

import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {
	
	public MovingTowardsFixedPointBuilder() {
		this("mtfp","MovingTowardsFixedPoint");
	}

	public MovingTowardsFixedPointBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		Vector2D c = (data.has("c") ? new Vector2D(data.getJSONArray("c").getDouble(0), data.getJSONArray("c").getDouble(1)) : new Vector2D());
		double g = (data.has("g") ? data.getDouble("g") : 9.81);
		return new MovingTowardsFixedPoint(c, g);
	}
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		info.put("type", "mtfp");
		info.put("desc", "MovingTowardsFixedPoint");
		JSONObject aux = new JSONObject();
		aux.put("c", "A 2D Vector, e.g., [1e14,1-4e10]");
		aux.put("g", "Acceleration towards the point ,e.g., 9.8");
		info.put("data", aux);
		return info;
	}
}