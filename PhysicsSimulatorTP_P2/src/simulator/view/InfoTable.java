package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableModel;

@SuppressWarnings("serial")
public class InfoTable extends JPanel {
	
	private String _title;
	private TableModel _tableModel;
	
	public InfoTable(String title, TableModel tableModel) {
		this._title = title;
		this._tableModel = tableModel;
		this.initGUI();
		
	}
	private void initGUI() {
		//LAYOUT
		this.setLayout(new BorderLayout());

		//BORDER
		Border b = BorderFactory.createLineBorder(Color.black, 2);
		this.setBorder(BorderFactory.createTitledBorder(b, this._title));
		
		//TABLE
		JTable table = new JTable(this._tableModel);
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		this.add(scrollPane);
	}
}