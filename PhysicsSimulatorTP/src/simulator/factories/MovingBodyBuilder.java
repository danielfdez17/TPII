package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.Body;
import simulator.model.MovingBody;

public class MovingBodyBuilder extends Builder<Body> {
	private String id;	
	private String gid;
	private Vector2D p;
	private Vector2D v;
	private double m;
	private MovingBody Mbody;
	// typeTag descibre el objecto que se va a crear
	// desc describe el tipo de objetos que pueden ser creados por este builder
	public MovingBodyBuilder() {
		super("mv_body","MovingBody");
	}
	
	

	@Override
	protected Body createInstance(JSONObject data) {
		// comprobar que data tiene toda la información necesaria para crear un MovingBody
		if (!data.has("id") || !data.has("gid") || !data.has("p") || !data.has("v") || !data.has("m") ||
				data.getJSONArray("p").length() != 2 || data.getJSONArray("v").length() != 2 ) {
			throw new IllegalArgumentException();
		}		
		
		this.id = data.getString("id");
		this.gid = data.getString("gid");
		
		JSONArray position = data.getJSONArray("p");
		double aux1 = position.getDouble(0);
		double aux2 = position.getDouble(1);
		this.p = new Vector2D(aux1,aux2);
		
		JSONArray velocity = data.getJSONArray("v");
		aux1= velocity.getDouble(0);
		aux2= velocity.getDouble(1);
		this.v= new Vector2D(aux1,aux2);
		
		this.m = data.getDouble("m");
		
		this.Mbody = new MovingBody(id,gid,p,v,m);
		return Mbody;
		}
}