package simulator.view;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;


@SuppressWarnings("serial")
class ViewerWindow extends JFrame implements SimulatorObserver {
	private Controller _ctrl;
	private SimulationViewer _viewer;
	private JFrame _parent;
	ViewerWindow(JFrame parent, Controller ctrl) {
		super("Simulation Viewer");
		this._ctrl = ctrl;
		this._parent = parent;
		this.intiGUI();
		setLocationRelativeTo(null);
		this._ctrl.addObserver(this);

	}
	private void intiGUI() {
		JPanel mainPanel = new JPanel(new BorderLayout());
		JPanel contentPanel = new JPanel();
		_viewer = new Viewer();
		JScrollPane scrollPane=new JScrollPane();
		contentPanel.add(scrollPane);
		this.setContentPane(contentPanel);
	
		mainPanel.add(this._viewer,BorderLayout.CENTER);
		add(mainPanel);
		addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) { _ctrl.removeObserver(ViewerWindow.this); }
			@Override
			public void windowClosed(WindowEvent e) {}
			@Override
			public void windowActivated(WindowEvent e) {}
		});
		
		this.pack();
		if (_parent != null)
			this.setLocation(
					_parent.getLocation().x + _parent.getWidth()/2 - getWidth()/2,
					_parent.getLocation().y + _parent.getHeight()/2 - getHeight()/2);
		this.setVisible(true);
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {
		this._viewer.update();
	}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this._viewer.reset();
		for(BodiesGroup bg : groups.values()) {
			_viewer.addGroup(bg);
		}
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for(BodiesGroup bg : groups.values()) {
			_viewer.addGroup(bg);
		}
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		this._viewer.addGroup(g);
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {
		this._viewer.addBody(b);
	}
	@Override
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}