package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.StationaryBody;

public class StationaryBodyBuilder extends Builder<Body> {
	
	public StationaryBodyBuilder() {
		this("st_body","StationaryBody");
	}

	public StationaryBodyBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected Body createInstance(JSONObject data) {
		if (!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("m") || data.getJSONArray("p").length() != 2) {
			throw new IllegalArgumentException("Invalid arguments in StationaryBodyBuilder.createInstance()");
		}
		JSONArray ja = data.getJSONArray("p");
		String id = data.getString("id");
		String gid = data.getString("gid");
		Vector2D position = new Vector2D(ja.getDouble(0), ja.getDouble(1));
		double mass = data.getDouble("m");
		return new StationaryBody(id, gid, position, mass);
	}

}
