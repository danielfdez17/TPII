package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {

	public MovingBodyBuilder() {
		this("mv_body","MovingBody");
	}
	
	public MovingBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}
	
	@Override
	protected Body createInstance(JSONObject data) {
		if (!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m") ||
				data.getJSONArray("p").length() != 2 || data.getJSONArray("v").length() != 2 ) {
			throw new IllegalArgumentException("Invalid arguments in MovingBodyBuilder.createInstance()");
		}		
		String id = data.getString("id");
		String gid = data.getString("gid");
		JSONArray position = data.getJSONArray("p");
		double aux1 = position.getDouble(0);
		double aux2 = position.getDouble(1);
		Vector2D p = new Vector2D(aux1, aux2);
		JSONArray velocity = data.getJSONArray("v");
		aux1 = velocity.getDouble(0);
		aux2 = velocity.getDouble(1);
		Vector2D v = new Vector2D(aux1, aux2);
		double m = data.getDouble("m");
		return new MovingBody(id, gid, p, v, m);
	}
}
