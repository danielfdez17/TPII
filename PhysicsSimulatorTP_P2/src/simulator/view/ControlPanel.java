package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
class ControlPanel extends JPanel implements SimulatorObserver {
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true;
	private JButton quitButton;
	private JButton physicsButton;
	private JButton runButton;
	private JButton loadButton;
	private JButton stopButton;
	private JButton viewerButton;
	private JLabel stepsLabel;
	private JSpinner stepsSpinner;
	private JLabel deltaLabel;
	private JTextField deltaTime;
	private ForceLawsDialog flDialog;


	
	ControlPanel(Controller ctrl) {
		this._ctrl = ctrl;
		initGUI();
		this._ctrl.addObserver(this);
	}
	private void initGUI() {
		this.setLayout(new BorderLayout());

		//TOOLABAR
		this._toolaBar = new JToolBar();
		this.add(this._toolaBar, BorderLayout.PAGE_START);
		
		//LOAD BUTTON
		this.loadButton = new JButton();
		this._fc = new JFileChooser("resources/examples/input");
		this.loadButton.setToolTipText("Load");
		this.loadButton.setIcon(new ImageIcon("resources/icons/open.png"));
		this.loadButton.addActionListener((e) -> {
			int v = this._fc.showOpenDialog(Utils.getWindow(this));
			if (v == JFileChooser.APPROVE_OPTION) {
				File file = this._fc.getSelectedFile();
				this._ctrl.reset();
				InputStream is;
				try {
					is = new FileInputStream(file);
					this._ctrl.loadData(is);
				} catch (FileNotFoundException e1) {
					Utils.showErrorMsg(e1.getMessage());
				}
			}
		});
		this._toolaBar.add(this.loadButton);
		this._toolaBar.addSeparator();
		
		//PHYSICS BUTTON
		this.physicsButton = new JButton();
		this.flDialog = null;
		this.physicsButton.setToolTipText("Physics");
		this.physicsButton.setIcon(new ImageIcon("resources/icons/physics.png"));
		this.physicsButton.addActionListener((e) -> {
			if (this.flDialog == null) {
				this.flDialog = new ForceLawsDialog((Frame) SwingUtilities.getWindowAncestor(this), this._ctrl);
			}
			this.flDialog.open();
		});
		this._toolaBar.add(this.physicsButton);
		
		//VIEWER BUTTON
		this.viewerButton = new JButton();
		this.viewerButton.setToolTipText("Viewer");
		this.viewerButton.setIcon(new ImageIcon("resources/icons/viewer.png"));
		this.viewerButton.addActionListener((e) -> {
			new ViewerWindow((JFrame) SwingUtilities.getWindowAncestor(this), this._ctrl);
		});
		this._toolaBar.add(this.viewerButton);
		
		this._toolaBar.addSeparator();
		
		//RUN BUTTON
		this.runButton = new JButton();
		this.runButton.setToolTipText("Run");
		this.runButton.setIcon(new ImageIcon("resources/icons/run.png"));
		this.runButton.addActionListener((e) -> {
			double delta = Double.parseDouble(this.deltaTime.getText());
			this._ctrl.setDeltaTime(delta);
			int steps = (Integer) this.stepsSpinner.getValue();
			this._stopped = false;
			this.setButtons(false); 
			this.run_sim(steps);
		});
		this._toolaBar.add(this.runButton);
		
		//STOP BUTTON
		this.stopButton = new JButton();
		this.stopButton.setToolTipText("Stop");
		this.stopButton.setIcon(new ImageIcon("resources/icons/stop.png"));
		this.stopButton.addActionListener((e) -> {
			this._stopped = true;
		});
		this._toolaBar.add(this.stopButton);
		
		//STEPS LABEL
		this.stepsLabel = new JLabel("Steps: ");
		this._toolaBar.add(this.stepsLabel);

		//STEPS SPINNER
		this.stepsSpinner = new JSpinner(new SpinnerNumberModel(10000, 1, 10000, 100));
		this.stepsSpinner.setMaximumSize(new Dimension(80, 40));
		this.stepsSpinner.setMinimumSize(new Dimension(80, 40));
		this.stepsSpinner.setPreferredSize(new Dimension(80, 40));
		this._toolaBar.add(this.stepsSpinner);
		
		//DELTA LABLE
		this.deltaLabel = new JLabel("Delta-Time: ");
		this._toolaBar.add(this.deltaLabel);
		
		//DELTA JTEXTFIELD
		this.deltaTime = new JTextField(10);
		this.deltaTime.setMinimumSize(new Dimension(80,40));
		this.deltaTime.setMaximumSize(new Dimension(80,40));
		this._toolaBar.add(this.deltaTime);
		
		this._toolaBar.add(Box.createGlue());
		this._toolaBar.addSeparator();
		
		//QUIT BUTTON
		this.quitButton = new JButton();
		this.quitButton.setToolTipText("Quit");
		this.quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		this.quitButton.addActionListener((e) -> Utils.quit(this));
		this._toolaBar.add(this.quitButton);
	}
	private void run_sim(int n) {
		if (n > 0 && !this._stopped) {
			try {
				this._ctrl.run(1);
			} catch (Exception e) {
				Utils.showErrorMsg(e.getMessage());
				setButtons(true);
				this._stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
			
			} else {
				setButtons(true);
				this._stopped = true;
			}
	}
	private void setButtons(boolean ok) {
		this.runButton.setEnabled(ok);
		this.loadButton.setEnabled(ok);
		this.quitButton.setEnabled(ok);
		this.viewerButton.setEnabled(ok);
		this.physicsButton.setEnabled(ok);
	}
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		this.deltaTime.setText("" + dt);
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		this.deltaTime.setText("" + dt);
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}
	@Override
	public void onDeltaTimeChanged(double dt) {
		this.deltaTime.setText("" + dt);
	}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {}

}
