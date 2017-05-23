package saku;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;
import javax.swing.ListSelectionModel;

/**
 * タイムベース設定ダイアログ
 *
 * @version 1.00
 */
public final class TimebaseDialog extends JDialog {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 451634717514151897L;
	
	/**
	 * デートフォーマッタ
	 */
	private static final DateFormat df = LocalDateFormat.getInstance(LocalDateFormat.FORMAT_UI_DATETIME);
	
	/**
	 * コンポーネント類
	 */
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * テーブル
	 */
	private JTable table;

	/**
	 * メインメソッド
	 * 
	 * @param args コマンド引数
	 */
	public static void main(String[] args) {
		try {
			TimebaseDialog dialog = new TimebaseDialog(null);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ (Eclipse WDTで生成)
	 */
	public TimebaseDialog(JFrame parent) {
		super(parent, true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Timebase");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 510, 300);
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
						/* 設定内容を更新して保存 */
						if(update()) {
							dispose();
						}
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();	/* キャンセル */
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
							addRow();	/* タイムベース設定の追加 */
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
			/* 設定ファイルの読み込み */
			if (mgr.load(MainProperties.getInstance().getTimeLines())) {
				Map<String, Timebase> tml = mgr.getTimebase();
				/* テーブルモデルに設定 */
				for (Entry<String, Timebase> e : tml.entrySet()) {
					Object[] row = new Object[3];
					
					row[0] = e.getKey();									// ロケーション
					row[1] = df.format(e.getValue().getBaseTime());		// 基準時間
					row[2] = Integer.toString(e.getValue().getRespawn());	// リポップ間隔
										
					model.addRow(row);
				}
				
				fixColumnWidth();	/* 列幅の自動調整 */
			}
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 設定内容を保存する。
	 */
	private boolean update() {

		TimebaseManager mgr = TimebaseManager.getInstance();
		mgr.clear();	/* マネージャを初期化 */

		/* テーブルモデルを保存 */
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
				
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> v = (Vector<Vector<Object>>)model.getDataVector();
		int i = 1; /* テーブル行カウンタ  */
		for (Vector<Object> e : v) {

			String loc = (String)e.get(0);		/* ロケーション */
			String date = (String)e.get(1);		/* 基準時間 */
			String respawn = (String)e.get(2);	/* リポップ時間 */
			
			try {
				/* ロケーション名は必須 */
				if (loc == null || loc.isEmpty() || loc.trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Location name required at line " + i + ".", 
							"Error", JOptionPane.ERROR_MESSAGE);
					return (false);
				}
				
				loc = loc.trim();	/* ロケーションを整形 */
				
				/* 設定項目が未設定のものはエラー */
				if (date == null || date.isEmpty()
						|| respawn == null || respawn.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Emtpy value is not allowed at line " + i + ".",
							"Error", JOptionPane.ERROR_MESSAGE);
					return (false);
				}
				
				Timebase tb = new Timebase();
				tb.setBaseTime(df.parse(date));
				tb.setRespawn(Integer.parseInt(respawn));
				mgr.putTimebase(loc, tb);
				
				i++;
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this, "Invalid timebase setting at line " + i + ".",
						"Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
				return (false);
			}
		}
		
		try {
			mgr.save();		/* ファイルに保存 */
			return (true);
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Cannot save basetime", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return (false);
		}
	}
	
	/**
	 * テーブルにタイムベースを追加する。
	 */
	private void addRow () {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.addRow(new Object[3]);
	}
	
	/**
	 * 列幅を調整する。
	 */
	private void fixColumnWidth () {
		/* 列ごとに幅を計算 */
		for (int i = 0; i < table.getColumnCount(); i++) {
			TableColumn tc = table.getColumnModel().getColumn(i);
			
			/* 各行の幅を計算 */
			int max = 0;
			int vrows = table.getRowCount();
			for (int j = 0; j < vrows; j++) {
				TableCellRenderer r = table.getCellRenderer(j, i);
				Object value = table.getValueAt(j,  i);
				Component c = r.getTableCellRendererComponent(table, value, false,  false, j, i);
				int w = c.getPreferredSize().width;
				
				/* 最大幅を記録 */
				if (max < w) {
					max = w;
				}
			}
			
			/* 最大幅を列幅に設定 */
			tc.setPreferredWidth(max + 1);
		}
	}
}
