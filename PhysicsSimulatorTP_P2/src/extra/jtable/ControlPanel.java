package extra.jtable;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
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
import simulator.view.Utils;

class ControlPanel extends JPanel implements SimulatorObserver {
	private Controller _ctrl;
	private JToolBar _toolaBar;
	private JFileChooser _fc;
	private boolean _stopped = true; // utilizado en los botones de run/stop
	private JButton _quitButton;
	// TODO añade más atributos aquí
	private JButton openButton;
	private JButton physicsButton;
	private JButton viewerButton;
	private JButton runButton;
	private JButton stopButton;
	private JButton exitButton;
	private JLabel stepsLabel;
	private JSpinner steps;
	private JLabel dtLabel;
	private JTextField deltaTime;
	private ForceLawsDialog forceLawsDialog;
	
	ControlPanel(Controller ctrl) {
		_ctrl = ctrl;
		initGUI();
		this._ctrl.addObserver(this);
	}
	private void initGUI() {
		setLayout(new BorderLayout());
		_toolaBar = new JToolBar();
		add(_toolaBar, BorderLayout.PAGE_START);
		this.stepsLabel = new JLabel("Steps:");
		this.steps = new JSpinner(new SpinnerNumberModel(10, 1, 10000, 1));
		this.dtLabel = new JLabel("Delta-Time:");
		this.deltaTime = new JTextField();
		this.openButton = new JButton();
		this.physicsButton = new JButton();
		this.viewerButton = new JButton();
		this.runButton = new JButton();
		this.stopButton = new JButton();
		this.exitButton = new JButton();
		this.forceLawsDialog = null;
		
		this._toolaBar.add(this.openButton);
		this._toolaBar.addSeparator();
		this._toolaBar.add(this.physicsButton);
		this._toolaBar.add(this.viewerButton);
		this._toolaBar.addSeparator();
		this._toolaBar.add(this.runButton);
		this._toolaBar.add(this.stopButton);
		this._toolaBar.add(stepsLabel);
		this._toolaBar.add(steps);
		this._toolaBar.add(dtLabel);
		this._toolaBar.add(deltaTime);
		this._toolaBar.add(Box.createGlue());
		this._toolaBar.addSeparator();
		this._quitButton = new JButton();
		this._quitButton.setToolTipText("Quit");
		this._quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		this._quitButton.addActionListener((e) -> Utils.quit(this));
		this._toolaBar.add(this._quitButton);
		this._toolaBar.addSeparator();
		this._toolaBar.add(exitButton);
		
		// Action Listeners
		openButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//_fc.showOpenDialog(Utils.getWindow(this));
				// si se selecciona un fichero, se resetea el simulador y carga el fichero
			}
		});
		physicsButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (forceLawsDialog == null) {
					forceLawsDialog = new ForceLawsDialog(null, _ctrl);
					// comprobar que es null, si no lo es se utiliza esa instancia
				}
				forceLawsDialog.open();
			}
		});
		viewerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ViewerWindow vw = new ViewerWindow(null, _ctrl);
			}
		});
		runButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = false;
				openButton.setEnabled(false);
				physicsButton.setEnabled(false);
				viewerButton.setEnabled(false);
				runButton.setEnabled(false);
				exitButton.setEnabled(false);
				// actualiza el valor actual de dt 
				int deltaTimeAux = Integer.parseInt(deltaTime.getText());
				int stepsAux = 0;
				onDeltaTimeChanged(deltaTimeAux);
				run_sim(stepsAux);
			}
		});
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_stopped = true;
			}
		});
		// Quit Button
		_toolaBar.add(Box.createGlue()); // this aligns the button to the right
		_toolaBar.addSeparator();
		_quitButton = new JButton();
		_quitButton.setToolTipText("Quit");
		_quitButton.setIcon(new ImageIcon("resources/icons/exit.png"));
		_quitButton.addActionListener((e) -> Utils.quit(this));
		_toolaBar.add(_quitButton);
		// TODO crear el selector de ficheros
//		_fc = �
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
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				_ctrl.run(1);
			} catch (Exception e) {
				// TODO llamar a Utils.showErrorMsg con el mensaje de error que corresponda
				Utils.showErrorMsg("");
				// TODO activar todos los botones
				this.openButton.setEnabled(true);
				this.physicsButton.setEnabled(true);
				this.viewerButton.setEnabled(true);
				this.runButton.setEnabled(true);
				this.exitButton.setEnabled(true);
				this._stopped = true;
				return;
			}
			SwingUtilities.invokeLater(() -> run_sim(n - 1));
		} else {
			// TODO llamar a Utils.showErrorMsg con el mensaje de error que corresponda
			Utils.showErrorMsg("");
			// TODO activar todos los botones
			this.openButton.setEnabled(true);
			this.physicsButton.setEnabled(true);
			this.viewerButton.setEnabled(true);
			this.runButton.setEnabled(true);
			this.exitButton.setEnabled(true);
			this._stopped = true;
		}
	}
}
