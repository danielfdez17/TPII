package simulator.model;

import org.json.JSONObject;
import simulator.misc.Vector2D;

public abstract class Body {
	
	// ATRIBURES
	protected String id;
	protected String gid;
	protected Vector2D velocity;
	protected Vector2D force;
	protected Vector2D position;
	protected double mass;
	
	// BUILDERS
	public Body(String id, String gid, Vector2D position, Vector2D velocity, double mass) {
		if (id == null || gid == null || velocity == null || position == null || mass <= 0 || id.trim().length() <= 0 || gid.trim().length() <= 0) { 
			throw new IllegalArgumentException();
		}
			this.id = id;
			this.gid = gid;
			this.velocity = velocity;
			this.force = new Vector2D();
			this.position = position;
			this.mass = mass;			
	}
	
	// METHODS
	public String getId() {
		return this.id;
	}
	public String getgId() {
		return this.gid;
	}
	public Vector2D getVelocity() {
		return this.velocity;
	}
	public Vector2D getForce() {
		return this.force;
	}
	public Vector2D getPosition() {
		return this.position;
	}
	public double getMass() {
		return this.mass;
	}
	void addForce(Vector2D f) {
		this.force = this.force.plus(f);
	}
	void resetForce() {
		this.force = new Vector2D();
	}
	abstract void advance(double dt);
	public JSONObject getState() {
		JSONObject jo = new JSONObject();
		jo.put("id", this.id);
		jo.put("m", this.mass);
		jo.put("p", this.position.asJSONArray());
		jo.put("v", this.velocity.asJSONArray());
		jo.put("f", this.force.asJSONArray());
		return jo;
	}
	public String toString() {
		return this.getState().toString();
	}
}
