package simulator.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup {

	private String id;
	private ForceLaws fl;
	private List<Body> bodies;
	
	public BodiesGroup(String id, ForceLaws fl) {
		if (id == null || fl == null || id.trim().length() <= 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
		this.fl = fl;
		this.bodies = new ArrayList<Body>();
	}
	
	public String getId() {
		return this.id;
	}
	void setForceLaws(ForceLaws fl) throws IllegalArgumentException {
		if (fl == null) {
			throw new IllegalArgumentException();
		}
		this.fl = fl;
	}
	void addBody(Body b) {
		if (b == null) {
			throw new IllegalArgumentException();
		}
		boolean found = false;
		int i = 0;
			while (i < this.bodies.size() && !found) {
			found = (this.bodies.get(i).getId() == b.getId());
			i++;
		}
		if (found) {
			throw new IllegalArgumentException();
		}
		this.bodies.add(b);
	}
	void advance(double dt) {
		if (dt <= 0) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < this.bodies.size(); i++) {
			Body b = this.bodies.get(i);
			b.resetForce();
		}
		this.fl.apply(this.bodies);
		
		for (int i = 0; i < this.bodies.size(); i++) {
			Body b = this.bodies.get(i);	
			b.advance(dt);
		}
	}
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", this.id);
		JSONArray ja = new JSONArray();
		for (int i = 0; i < this.bodies.size(); i++) {
			ja.put(this.bodies.get(i).getState());
		}
		jo.put("bodies", ja);
		return jo;
	}
	public String toString() {
		return this.getState().toString();
	}
}
