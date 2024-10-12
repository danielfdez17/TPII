package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class BodiesGroup implements Iterable<Body> {

	private String id;
	private ForceLaws fl;
	private List<Body> bodies;
	private List<Body> bodiesRO;
	
	public BodiesGroup(String id, ForceLaws fl) {
		if (id == null || fl == null || id.trim().length() <= 0) {
			throw new IllegalArgumentException("Invalid argument in BodiesGroup builder");
		}
		this.id = id;
		this.fl = fl;
		this.bodies = new ArrayList<Body>();
		this.bodiesRO = Collections.unmodifiableList(bodies);
	}
	
	public String getId() {
		return this.id;
	}
	void setForceLaws(ForceLaws fl) {
		if (fl == null) {
			throw new IllegalArgumentException("Invalid argument in BodiesGroup.setForceLaws()");
		}
		this.fl = fl;
	}
	void addBody(Body b) {
		if (b == null) {
			throw new IllegalArgumentException("Invalid argument in BodiesGroup.addBody()");
		}
		if (this.bodies.contains(b)) {
			throw new IllegalArgumentException("Invalid argument in BodiesGroup.addBody(); the body already exists");
		}
		this.bodies.add(b);
	}
	void advance(double dt) {
		if (dt <= 0) {
			throw new IllegalArgumentException("Invalid argument in BodiesGroup.advance()");
		}
		for (Body b : this.bodies) {
			b.resetForce();
		}
		this.fl.apply(this.bodies);
		for (Body b : this.bodies) {
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
	public String getForceLawsInfo() {
		return this.fl.toString();
	}
	@Override
	public Iterator<Body> iterator() {
		return this.bodiesRO.iterator();
	}

	
}
