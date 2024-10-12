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
			throw new IllegalArgumentException();
		}
		JSONArray position = data.getJSONArray("p");
		return new StationaryBody(data.getString("id"), data.getString("gid"), new Vector2D(position.getDouble(0), position.getDouble(1)),
					data.getDouble("m"));
	}

}
