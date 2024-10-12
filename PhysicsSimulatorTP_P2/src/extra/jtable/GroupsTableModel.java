package extra.jtable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	String[] _header = { "Id", "Force Laws", "Bodies" };
	List<BodiesGroup> _groups;
	GroupsTableModel(Controller ctrl) {
		this._groups = new ArrayList<>();
		ctrl.addObserver(this);
	}
	// TODO el resto de métodos van aquí …
	@Override
	public int getRowCount() {
		return this._groups.size();
	}
	@Override
	public int getColumnCount() {
		return this._header.length;
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return this._groups.get(rowIndex).getId();
		case 1:
			return this._groups.get(rowIndex).getForceLawsInfo();
		case 2:
			return this._groups.get(rowIndex);
		}
		return null;
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._groups.clear();
//		this._groups = groups.values();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
//		this._groups.add(groups.get(groups));
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		// TODO Auto-generatode methos stub
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

