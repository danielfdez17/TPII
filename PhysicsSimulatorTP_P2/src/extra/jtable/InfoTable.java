package extra.jtable;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class InfoTable extends JPanel {
	String _title;
	TableModel _tableModel;
	public InfoTable(String title, TableModel tableModel) {
		this._title = title;
		this._tableModel = tableModel;
		this.initGUI();
		
	}
	private void initGUI() {
		// TODO cambiar el layout del panel a BorderLayout()
		this.setLayout(new BorderLayout());
		// TODO añadir un borde con título al JPanel, con el texto _title

		// TODO añadir un JTable (con barra de desplazamiento vertical) que use _tableModel
		JTable table = new JTable();
		JScrollPane scrollPane = new JScrollPane();
		JScrollBar scrollBar = new JScrollBar();
		
	}
}