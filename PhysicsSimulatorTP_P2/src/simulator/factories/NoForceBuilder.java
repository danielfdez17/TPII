package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {
	
	public NoForceBuilder() {
		this("nf","No Force");
	}

	public NoForceBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		return new NoForce();
	}
	@Override
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		info.put("type", "nf");
		info.put("desc", "No Force");
		info.put("data", new JSONObject());
		return info;
	}
}
