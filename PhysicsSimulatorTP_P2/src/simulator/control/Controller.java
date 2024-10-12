package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.model.SimulatorObserver;

public class Controller {
	private PhysicsSimulator ps;
	private Factory<Body> bf;
	private Factory<ForceLaws> flf;
	public Controller(PhysicsSimulator ps, Factory<Body> bodies, Factory<ForceLaws> fl) {
		this.ps = ps;
		this.bf = bodies;
		this.flf = fl;
	}
	
	public void loadData(InputStream in) {
		JSONObject ji = new JSONObject(new JSONTokener(in));
		JSONArray jg = ji.getJSONArray("groups");
		JSONArray jb = ji.getJSONArray("bodies");
		for (int i = 0; i < jg.length(); i++) {
			this.ps.addGroup(jg.getString(i));
		}
		if (ji.has("laws")) {
			JSONArray jl = ji.getJSONArray("laws");
			for (int i = 0; i < jl.length(); i++) {
				JSONObject aux = jl.getJSONObject(i);
				ForceLaws forces = this.flf.createInstance(aux.getJSONObject("laws"));
				this.ps.setForceLaws(aux.getString("id"), forces);
			}
		}
		for (int i = 0; i < jb.length(); i++) {
			Body b = bf.createInstance(jb.getJSONObject(i));
			this.ps.addBody(b);
		}
	}
	public void run(int n, OutputStream out) {
		PrintStream p = new PrintStream(out);
		int aux = n - 1;
		boolean pintar = false;
		p.println("{");
		p.println("\"states\": [");
		// run the sumulation n steps, etc.
		for (int i = 0; i <= n; i++) {
			p.println(this.ps.getState());
			if (!pintar) {
				p.print(",");
			}
			pintar = (aux == i);
			this.ps.advance();
		}
		p.println("]");
		p.println("}");
	}
	// CAMBIOS A LA P2
	public void run(int n) {
		for (int i = 0; i < n; i++) {
			this.ps.advance();
		}
	}
	public void reset() {
		this.ps.reset();
	}
	public void setDeltaTime(double dt) {
		this.ps.setDeltaTime(dt);
	}
	public void addObserver(SimulatorObserver o) {
		this.ps.addObserver(o);
	}
	public void removeObserver(SimulatorObserver o) {
		this.ps.removeObserver(o);
	}
	public List<JSONObject> getForceLawsInfo(){
		return this.flf.getInfo();
	}
	public void setForceLaws(String gId, JSONObject info) {
		this.ps.setForceLaws(gId, this.flf.createInstance(info));
	}
}