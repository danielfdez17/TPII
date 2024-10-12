package simulator.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class PhysicsSimulator implements Observable<SimulatorObserver> {
	
	private double dt;
	private double t;
	private ForceLaws fl;
	private Map<String, BodiesGroup> map;
	private Map<String, BodiesGroup> unmodifiable_map;
	private List<String> keys;
	private List<SimulatorObserver> observers;
	
	public PhysicsSimulator(ForceLaws fl, double dt) {
		if (dt < 0 || fl == null) {
			throw new IllegalArgumentException("Invalid arguments in PhysicsSimulator builder");
		}
		this.dt = dt;
		this.t = 0.0;
		this.fl = fl;
		this.map = new HashMap<String, BodiesGroup>();
		this.unmodifiable_map = Collections.unmodifiableMap(this.map);
		this.keys = new LinkedList<>();
		this.observers = new LinkedList<>();
		
	}
	public void advance() {
		for (Map.Entry<String, BodiesGroup> m : map.entrySet()) {
			m.getValue().advance(dt);
		}
		this.t += dt;
		for (SimulatorObserver o : this.observers) {
			o.onAdvance(map, t);
		}
	}
	public void addGroup(String id) {
		if (this.map.containsKey(id)) {
			throw new IllegalArgumentException("Group with id : " + id + "already exists in the Map");
		}
		BodiesGroup g = new BodiesGroup(id, this.fl);
		this.map.put(id, g);
		this.keys.add(id);
		for (SimulatorObserver o : this.observers) {
			o.onGroupAdded(this.unmodifiable_map, g);
		}
	}
	public void addBody(Body b) {
		if (!this.map.containsKey(b.getgId())) {
			throw new IllegalArgumentException("Invalid argument in PhysicsSimulator.addBody(); the body already exists");
		}
		this.map.get(b.getgId()).addBody(b);
		for (SimulatorObserver o : this.observers) {
			o.onBodyAdded(this.unmodifiable_map, b);
		}
	}
	public void setForceLaws(String id, ForceLaws f) {
		if (this.map.containsKey(id)) {
			this.map.get(id).setForceLaws(f);
		}
		else {
			throw new IllegalArgumentException("The map does not contains the group with id " + id);
		}
		for (SimulatorObserver o : this.observers) {
			o.onForceLawsChanged(new BodiesGroup(id, f));
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
	public void reset() {
		this.map.clear();
		this.keys.clear();
		this.t = 0.0;
		for (SimulatorObserver o : this.observers) {
			o.onReset(this.unmodifiable_map, this.t, this.dt);
		}
	}
	public void setDeltaTime(double dt) {
		if (dt <= 0) {
			throw new IllegalArgumentException("Invalid argument in PhysicsSimulator.setDeltaTime()");
		}
		this.dt = dt;
		for (SimulatorObserver o : this.observers) {
			o.onDeltaTimeChanged(this.dt);
		}
	}
	@Override
	public void addObserver(SimulatorObserver o) {
		if (!this.observers.contains(o)) {
			this.observers.add(o);
			o.onRegister(this.unmodifiable_map, this.t, this.dt);
		}
	}
	@Override
	public void removeObserver(SimulatorObserver o) {
		if (this.observers.contains(o)) {
			this.observers.remove(o);
		}
	}
 }
