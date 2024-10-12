package simulator.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.json.JSONObject;

import simulator.control.Controller;
import simulator.model.BodiesGroup;
import simulator.model.Body;
import simulator.model.SimulatorObserver;

@SuppressWarnings("serial")
public class ForceLawsDialog extends JDialog implements SimulatorObserver{
	
	private DefaultComboBoxModel<String> _lawsModel;
	private DefaultComboBoxModel<String> _groupsModel;
	private DefaultTableModel _dataTableModel;
	private Controller _ctrl;
	private List<JSONObject> _forceLawsInfo;
	private String[] _headers = { "Key", "Value", "Description" };
	private int _selectedLawsIndex;
	private JComboBox<String> _lawsComboBox;
	private JComboBox<String> _groupsComboBox;
	private JLabel _help1;
	private JLabel _help2;
	private JLabel _help3;
	private JPanel _helpPanel;
	
	public ForceLawsDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		this._ctrl = ctrl;
		this.initGUI();
		this._ctrl.addObserver(this);
	}
	
	/*  se encarga de inicializar la interfaz gráfica de la 
		ventana de diálogo, incluyendo los comboboxes para las 
		leyes de fuerza y los grupos, y los botones OK y Cancel*/
	private void initGUI() {
		
		// HELP PANEL
		this.setTitle("Force Laws Selection");
		_helpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		_helpPanel.setPreferredSize(new Dimension(300,70));
		Font bold = new Font("Arial", Font.BOLD, 13);
		Font normal = new Font("Arial", Font.PLAIN, 13);
		_help1 = new JLabel("Select a force law and provide values for the parametes in the");
		_help1.setFont(normal);
		_help2 = new JLabel("Value column");
		_help2.setFont(bold);
		_help3 = new JLabel("(default values are used for parametes with no value).");
		_help3.setFont(normal);
		
		 _helpPanel.add(_help1, BorderLayout.EAST);
		 _helpPanel.add(_help2);
		 _helpPanel.add(_help3);
		 
		// MAIN PANEL
		JPanel mainPanel = new JPanel();
		mainPanel.add(_helpPanel, BorderLayout.PAGE_START);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		// FORCE LAWS INFO
		this._forceLawsInfo = this._ctrl.getForceLawsInfo();
		
		// DATA TABLE MODEL
		this._dataTableModel = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return (column == 1);
			}
		};
		
		JTable auxTable = new JTable(this._dataTableModel);
		JScrollPane scrollPane = new JScrollPane(auxTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mainPanel.add(scrollPane, BorderLayout.CENTER);
		this._dataTableModel.setColumnIdentifiers(this._headers);
		
		// LAWS MODEL
		this._lawsModel = new DefaultComboBoxModel<>();
		for (JSONObject j : this._forceLawsInfo) {
			this._lawsModel.addElement(j.getString("desc"));
		}
		
		this._lawsComboBox = new JComboBox<String>(this._lawsModel);
		
		JSONObject info = _forceLawsInfo.get(_lawsComboBox.getSelectedIndex());
		JSONObject data = info.getJSONObject("data");
		for (String key : data.keySet()) {
			String value = data.getString(key); 
			_dataTableModel.addRow(new Object[]{key, "", value});
		}
		this._lawsComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = _lawsComboBox.getSelectedIndex();
				JSONObject info = _forceLawsInfo.get(index);
				JSONObject data = info.getJSONObject("data");
				_dataTableModel.setRowCount(0);
				for (String key : data.keySet()) {
					String value = data.getString(key); 
					_dataTableModel.addRow(new Object[]{key, "", value});
				}
				_selectedLawsIndex = index;
			}
		});
		
		// GROUPS MODEL
		this._groupsModel = new DefaultComboBoxModel<String>();
		this._groupsComboBox = new JComboBox<String>(this._groupsModel);
		mainPanel.add(this._groupsComboBox);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					JSONObject forceLaws = new JSONObject();
					String s = "", aux1 = "", aux2 = "";
					for (int i = 0; i < _dataTableModel.getRowCount(); i++) {
						if (_dataTableModel.getValueAt(i, 1).toString().equals("")) {
							aux1 = "";
							aux2 = "";
						}
						else {
							aux1 = _dataTableModel.getValueAt(i, 0).toString();
							aux2 = _dataTableModel.getValueAt(i, 1).toString();
							s += aux1 + ":" + aux2;
						}
						if (i + 1 < _dataTableModel.getRowCount()) {
							if (_dataTableModel.getValueAt(i + 1, 1).toString().equals("")) {
								aux1 = "";
								aux2 = "";
							}
							else {
								aux1 = _dataTableModel.getValueAt(i + 1, 0).toString();
								aux2 = _dataTableModel.getValueAt(i + 1, 1).toString();
								s += "," + aux1 + ":" + aux2;
							}
							i++;
						}
						if (!s.isEmpty() && s.charAt(0) == ',') {
							s = s.substring(1);
						}
					}
					JSONObject data = new JSONObject("{" + s + "}");                           
					forceLaws.put("type", _forceLawsInfo.get(_selectedLawsIndex).getString("type"));
					forceLaws.put("data", data);
					if (!forceLaws.isEmpty()) {
						_ctrl.setForceLaws(_groupsComboBox.getSelectedItem().toString(), forceLaws);
					}
					setVisible(false);
				} catch (Exception ex) {
					Utils.showErrorMsg(ex.getMessage());
				}
			}
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ForceLawsDialog.this.setVisible(false);
			}
		});
		
		// ForceLaws and groups selector panel
		JPanel southPanel = new JPanel(new GridLayout(2, 1));
		JPanel cbPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		cbPanel.add(new JLabel("Force Law:"));
		cbPanel.add(this._lawsComboBox);
		cbPanel.add(new JLabel("Group:"));
		cbPanel.add(this._groupsComboBox);
		
		
		southPanel.add(cbPanel);
		
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(okButton);
		
		southPanel.add(buttonsPanel);
		
		mainPanel.add(southPanel, BorderLayout.PAGE_END);

		setPreferredSize(new Dimension(700, 400));
		pack();
		setResizable(false);
		setVisible(false);
		
	}
	
	public void open() {
		if (this._groupsModel.getSize() == 0) {
			return;
		}
		
		this.pack();
		this.setResizable(false);
		this.setVisible(true);
	}
	
	@Override
	public void onAdvance(Map<String, BodiesGroup> groups, double time) {}
	@Override
	public void onReset(Map<String, BodiesGroup> groups, double time, double dt) {
		_groupsModel.removeAllElements();
		for (String groupName : groups.keySet()) {
			_groupsModel.addElement(groupName);
		}
		_groupsComboBox.setSelectedIndex(0);
	}
	@Override
	public void onRegister(Map<String, BodiesGroup> groups, double time, double dt) {
		for (BodiesGroup b : groups.values()) { this._groupsModel.addElement(b.getId()); }
	}
	@Override
	public void onGroupAdded(Map<String, BodiesGroup> groups, BodiesGroup g) {
		_groupsModel.addElement(g.getId());
		_groupsComboBox.setSelectedItem(g.getId());
	}
	@Override
	public void onBodyAdded(Map<String, BodiesGroup> groups, Body b) {}
	@Override
	public void onDeltaTimeChanged(double dt) {}
	@Override
	public void onForceLawsChanged(BodiesGroup g) {}
}

