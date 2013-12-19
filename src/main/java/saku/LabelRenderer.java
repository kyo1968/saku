package saku;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * ラベルレンダラ
 *
 * @version 1.0
 */
public final class LabelRenderer extends DefaultTableCellRenderer {
	
	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 2541244983984348673L;
	

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		/* アイコン表示 */
		if (value instanceof JLabel) {
			JLabel label = (JLabel)value;
			label.setIcon(new ImageIcon("Calendar.png"));
			return (label);
		}
		
		return (super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column));
	}

}
