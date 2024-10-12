package extra.jtable;

import java.awt.Dimension;
import java.awt.Frame;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

public class ForceLawsDialog extends JDialog implements SimulatorObserver{

	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	// TODO en caso de ser necesario, añadir los atributos aquí…
	ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		this._ctrl = ctrl;
		this.initGUI();
		this._ctrl.addObserver(this);
	}
	private void initGUI() {
		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		// _forceLawsInfo se usará para establecer la información en la tabla
		this._forceLawsInfo = _ctrl.getForceLawsInfo();
		// TODO crear un JTable que use _dataTableModel, y añadirla al panel
		this._dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				// TODO hacer editable solo la columna 1
				return (column == 1);
			}
		};
		//mainPanel.add(this._dataTableModel);
		this._dataTableModel.setColumnIdentifiers(this._headers);
		this._lawsModel = new DefaultComboBoxModel<>();
		// TODO añadir la descripción de todas las leyes de fuerza a _lawsModel
		for (int i = 0; i < this._forceLawsInfo.size(); i++) {
			this._lawsModel.addElement(this._forceLawsInfo.get(i).toString());
		}
		// TODO crear un combobox que use _lawsModel y añadirlo al panel
		DefaultComboBoxModel comboBox = new DefaultComboBoxModel<>();
		this._groupsModel = new DefaultComboBoxModel<>();
		//mainPanel.add(comboBox);
		// TODO crear un combobox que use _groupsModel y añadirlo al panel
		// TODO crear los botones OK y Cancel y añadirlos al panel
		JButton OK = new JButton("OK");
		JButton Cancel = new JButton("Cancel");
		mainPanel.add(OK);
		mainPanel.add(Cancel);
		this.setPreferredSize(new Dimension(700, 400));
		this.pack();
		this.setResizable(false);
		this.setVisible(false);
		}
	public void open() {
		if (this._groupsModel.getSize() == 0)
//			return _status;
			// TODO Establecer la posición de la ventana de diálogo de tal manera que se
			// abra en el centro de la ventana principal
			this.pack();
			this.setVisible(true);
//			return _status;
			}
	// TODO el resto de métodos van aquí…
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

