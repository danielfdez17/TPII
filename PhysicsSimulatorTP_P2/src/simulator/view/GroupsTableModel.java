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
class GroupsTableModel extends AbstractTableModel implements SimulatorObserver {
	private String[] _header = { "Id", "Force Laws", "Bodies" };
	private List<BodiesGroup> _groups;
	GroupsTableModel(Controller ctrl) {
		this._groups = new ArrayList<>();
		ctrl.addObserver(this);
	}
	@Override
	public int getRowCount() {
		return this._groups.size();
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
		case 0: return this._groups.get(rowIndex).getId();
		case 1: return this._groups.get(rowIndex).getForceLawsInfo();
		case 2: 
			String s = "";
			for(Body b: this._groups.get(rowIndex)) {
				s += b.getId() + " ";
			}
			return s;
		default:
	        return null;
		}
		
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._groups.clear();
		this._groups.addAll(groups.values());
		this.fireTableStructureChanged();
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		this._groups.clear();
        this._groups.addAll(groups.values());
        this.fireTableStructureChanged();
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this._groups.add(g);
		this.fireTableStructureChanged();
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}
	@Override
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {
		this.fireTableDataChanged();
	}
}

