package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	public NewtonUniversalGravitationBuilder() {
		this("nlug","NewtonUniversalGravitation");
	}

	public NewtonUniversalGravitationBuilder(String typeTag, String desc) {
		super(typeTag, desc);
	}

	@Override
	protected ForceLaws createInstance(JSONObject data) {
		double g = (data.has("G") ? data.getDouble("G") : 6.67E-11);
		return new NewtonUniversalGravitation(g);
	}
	public JSONObject getInfo() {
		JSONObject info = new JSONObject();
		info.put("type", "nlug");
		info.put("desc", "NewtonUniversalGravitation");
		JSONObject aux = new JSONObject();
		aux.put("G", "Garavitational constant, e.g., 6.67E-11" );
		info.put("data", aux);
		return info;
	}
}
