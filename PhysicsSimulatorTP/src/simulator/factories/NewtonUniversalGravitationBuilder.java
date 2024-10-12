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
		if (!data.has("G")) {
			return new NewtonUniversalGravitation(6.67E-11);
		}
		return new NewtonUniversalGravitation(data.getDouble("G"));
	}

}
