package saku;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.Dialog.ModalityType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

public final class TimebaseDialog extends JDialog {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 451634717514151897L;
	
	/**
	 * デートフォーマッタ
	 */
	private static final LocalDateFormat df = new LocalDateFormat();
	
	/**
	 * コンポーネント類
	 */
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	/**
	 * メインメソッド
	 * 
	 * @param args コマンド引数
	 */
	public static void main(String[] args) {
		try {
			TimebaseDialog dialog = new TimebaseDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ (Eclipse WDTで生成)
	 */
	public TimebaseDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Timebase");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(new BorderLayout(0, 0));
		{
			JScrollPane scrollPane = new JScrollPane();
			contentPanel.add(scrollPane, BorderLayout.CENTER);
			{
				table = new JTable();
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
				table.getTableHeader().setReorderingAllowed(false);
				table.setModel(new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"Location", "Timebase", "Respawn (sec)"
					}
				));
				scrollPane.setViewportView(table);

			}
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Save");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						update();
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		{
			JMenuBar menuBar = new JMenuBar();
			setJMenuBar(menuBar);
			{
				JMenu mnEdit = new JMenu("Edit");
				menuBar.add(mnEdit);
				{
					JMenuItem mntmAddTimebase = new JMenuItem("Add");
					mntmAddTimebase.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							addRow();
						}
					});
					mnEdit.add(mntmAddTimebase);
				}
				{
					JMenuItem mntmRemove = new JMenuItem("Remove");
					mnEdit.add(mntmRemove);
				}
			}
		}
		
		/* テーブルの初期化 */
		setUp();
	}
	
	/**
	 * テーブルを初期化する。
	 */
	private void setUp() {
		
		TimebaseManager mgr = TimebaseManager.getInstance();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		try {
			if (mgr.load()) {
				Map<String, Timebase> tml = mgr.getTimebase();
				
				for (Entry<String, Timebase> e : tml.entrySet()) {
					Object[] row = new Object[3];
					
					row[0] = e.getKey();									// ロケーション
					row[1] = df.format(e.getValue().getBaseTime());			// 基準時間
					row[2] = Integer.toString(e.getValue().getRespawn());	// リポップ間隔
										
					model.addRow(row);
				}
				
				fixColumnWidth();
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void update() {

		TimebaseManager mgr = TimebaseManager.getInstance();

		mgr.clear();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		Vector<Vector<Object>> v = model.getDataVector();
		for (Vector<Object> e : v) {
			
			String loc = (String)e.get(0);
			String date = (String)e.get(1);
			String respawn = (String)e.get(2);
			
			if (loc != null && !loc.isEmpty()
					&& date != null && !date.isEmpty()
					&& respawn != null && !respawn.isEmpty()) {
				
				try {
					loc = loc.trim();
					
					if (!loc.isEmpty()) {
						Timebase tb = new Timebase();
						tb.setBaseTime(df.parse(date));
						tb.setRespawn(Integer.parseInt(respawn));
						mgr.putTimebase(loc, tb);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		try {
			mgr.save();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void addRow () {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[3]);
	}
	
	private void fixColumnWidth () {
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn tc = table.getColumnModel().getColumn(i);
			
			int max = 0;
			int vrows = table.getRowCount();
			for (int j = 0; j < vrows; j++) {
				TableCellRenderer r = table.getCellRenderer(j, i);
				Object value = table.getValueAt(j,  i);
				Component c = r.getTableCellRendererComponent(table, value, false,  false, j, i);
				int w = c.getPreferredSize().width;
				if (max < w) {
					max = w;
				}
			}
			
			tc.setPreferredWidth(max + 1);
		}
	}
}
