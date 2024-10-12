package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class StatusBar extends JPanel implements SimulatorObserver {
	
	private static final String GROUPS = "Groups: ";
	private static final String TIME = "Time: ";
	private JLabel timeLabel;
	private JLabel groupsLabel;
	
	StatusBar(Controller ctrl) {
		this.initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBorder(BorderFactory.createBevelBorder(1));
		this.setVisible(true);
		// TIME LABLE
		this.timeLabel = new JLabel(TIME + 0);
		this.add(timeLabel);
		
		JSeparator s = new JSeparator(JSeparator.VERTICAL);
		s.setPreferredSize(new Dimension(10, 20));
		this.add(s);
		
		// GROUPS LABEL
		this.groupsLabel = new JLabel(GROUPS + 0);
		this.add(groupsLabel);
		
		JSeparator s2 = new JSeparator(JSeparator.VERTICAL);
		s2.setPreferredSize(new Dimension(10, 20));
		this.add(s2);
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		this.timeLabel.setText(TIME + time);
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this.groupsLabel.setText(GROUPS + groups.size());
		this.timeLabel.setText(TIME + time);
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		this.groupsLabel.setText(GROUPS + groups.size());
		this.timeLabel.setText(TIME + time);
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this.groupsLabel.setText(GROUPS + groups.size());
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}
	@Override
	public void onDeltaTimeChanged(double dt) {
		this.timeLabel.setText(TIME + dt);
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}
