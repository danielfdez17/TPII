package simulator.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
class BodiesTableModel extends AbstractTableModel implements SimulatorObserver {
	private String[] _header = { "Id", "gId", "Mass", "Velocity", "Position", "Force" };
	private List<Body> _bodies;
	BodiesTableModel(Controller ctrl) {
		this._bodies = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return this._bodies.size();
	}
	@Override
	public int getColumnCount() {
		return this._header.length;
	}
	@Override
	public String getColumnName(int col) {
		return this._header[col];
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0: return this._bodies.get(rowIndex).getId();
		case 1: return this._bodies.get(rowIndex).getgId();
		case 2: return this._bodies.get(rowIndex).getMass();
		case 3: return this._bodies.get(rowIndex).getVelocity();
		case 4: return this._bodies.get(rowIndex).getPosition();
		case 5: return this._bodies.get(rowIndex).getForce();
		default: return null;
		}
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		 this.fireTableDataChanged();
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._bodies.clear();
        this.fireTableStructureChanged();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (BodiesGroup g : groups.values()) {
			for (Body b : g) { this._bodies.add(b); }
		}
		this.fireTableStructureChanged();
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
        for (Body b : g) { this._bodies.add(b); }
        this.fireTableStructureChanged();
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		this._bodies.add(b);
        this.fireTableStructureChanged();
	}
	@Override
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}
