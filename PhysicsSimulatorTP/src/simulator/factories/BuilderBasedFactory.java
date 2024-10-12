package simulator.factories;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

public class BuilderBasedFactory<T> implements Factory<T> {

	private Map<String, Builder<T>> _builders;
	private List<JSONObject> _buildersInfo;
	
	public BuilderBasedFactory() {
		// Create a HashMap for _builders, a LinkedList _buildersInfo
		this._builders = new HashMap<String, Builder<T>>();
		this._buildersInfo = new LinkedList<>();
	}
	public BuilderBasedFactory(List<Builder<T>> b) {
		this();
		// call addBuilder(b) for each builder b in builder
		for (int i = 0; i < b.size(); i++) {
			this.addBuilder(b.get(i));
		}
		
	}
	public void addBuilder(Builder<T> b) {
		// add and entry ‘‘ b.getTag() −> b’’ to _builders.
		this._builders.put(b.getTypeTag(), b);
		// add b.getInfo () to _buildersInfo
		this._buildersInfo.add(b.getInfo());
	}
	
	
	@Override
	public T createInstance(JSONObject info) {
		if (info == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: null");
		}
		// Search for a builder with a tag equals to info . getString ("type"), call its
		// createInstance method and return the result if it is not null . The value you
		// pass to createInstance is :
		Iterator<JSONObject> it = this._buildersInfo.iterator();
		boolean found = false;
		while (it.hasNext() && !found) {
			found = it.next().getString("type").equals(info.getString("type"));
		}
		
		JSONObject jo = info.has("data") ? info.getJSONObject("data") : new JSONObject();
		// If no builder is found or thr result is null ...
		if (!found || jo == null) {
			throw new IllegalArgumentException("Invalid value for createInstance: " + info.toString());			
		}
		
		return this._builders.get(info.get("type")).createInstance(jo);
	}

	@Override
	public List<JSONObject> getInfo() {
		return Collections.unmodifiableList(_buildersInfo);
	}

}
