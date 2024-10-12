package simulator.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator {
	
	private double dt;
	private double t;
	private ForceLaws fl;
	private Map<String, BodiesGroup> map;
	private List<String> keys;

	public PhysicsSimulator(ForceLaws fl, double dt) throws IllegalArgumentException {
		if (dt < 0 || fl == null) {
			throw new IllegalArgumentException();
		}
		this.dt = dt;
		this.t = 0.0;
		this.fl = fl;
		this.map = new HashMap<String, BodiesGroup>();
		this.keys = new LinkedList<>();
		
	}
	public void advance() {
		for (Map.Entry<String, BodiesGroup> m : map.entrySet()) { 
			m.getValue().advance(dt);
		}
		this.t += dt;
	}
	public void addGroup(String id) throws IllegalArgumentException {
		for (Map.Entry<String, BodiesGroup> m : map.entrySet()) {
			if (m.getKey().equals(id)) {
				throw new IllegalArgumentException();
			}
		}
		BodiesGroup bg = new BodiesGroup(id, this.fl);
		map.put(id, bg);
		keys.add(id);
	}
	public void addBody(Body b) throws IllegalArgumentException {
		if(!this.map.containsKey(b.getgId())) {
			throw new IllegalArgumentException();
		}
		this.map.get(b.getgId()).addBody(b);
	}
	public void setForceLaws(String id, ForceLaws f) throws IllegalArgumentException {
		boolean found = false;
		for (Map.Entry<String, BodiesGroup> m : map.entrySet()) {
			if (m.getKey().equals(id)) {
				m.getValue().setForceLaws(f);
				found = true;
			} 
		}
		if (!found) {
			throw new IllegalArgumentException();
		}
	}
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("time", this.t);
		
		JSONArray ja = new JSONArray();
		for (String key : keys) {
			ja.put(map.get(key).getState());
		}
		jo.put("groups", ja);
		return jo;
	}
	public String toString() {
		return getState().toString();
	}
 }
