package extra.jtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	List<Body> _bodies;
	BodiesTableModel(Controller ctrl) {
		this._bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	// TODO el resto de métodos van aquí…
	@Override
	public int getRowCount() {
		return this._bodies.size();
	}
	@Override
	public int getColumnCount() {
		return this._header.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this._bodies.get(rowIndex).getId();
		case 1:
			return this._bodies.get(rowIndex).getgId();
		case 2:
			return this._bodies.get(rowIndex).getMass();
		case 3:
			return this._bodies.get(rowIndex).getVelocity();
		case 4:
			return this._bodies.get(rowIndex).getPosition();
		case 5:
			return this._bodies.get(rowIndex).getForce();
		}
		return null;
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onDeltaTimeChanged(double dt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		// TODO Auto-generated method stub
		
	}
}
