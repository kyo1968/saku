package saku;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseMotionAdapter;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;

/**
 * メインフレーム
 *
 * @version 1.00
 */
public final class MainFrame extends JFrame {

	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 7505081879058054896L;

	/**
	 * デートフォーマッタ
	 */
	private static LocalDateFormat df = new LocalDateFormat();

	/**
	 * コンポーネント類
	 */
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JTable table;
	private JMenuBar menuBar;
	private JMenu mnMain;
	private JMenuItem mntmExit;
	private JMenuItem mntmTimebase;
	private JMenuItem mntmRefresh;
	private JPopupMenu popupMenu;
	private Timer timer;
	private JMenu mnSettings;
	private JCheckBoxMenuItem chckbxmntmAlwaysOnTop;
	private JCheckBoxMenuItem chckbxmntmAutoRefresh;
	private JMenu mnOpacity;
	private JRadioButtonMenuItem radioButtonMenuItem;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButtonMenuItem radioButtonMenuItem_1;
	private JRadioButtonMenuItem radioButtonMenuItem_2;
	
	/**
	 * メインメソッド
	 * 
	 * @param args コマンド引数
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/* 複数起動の防止 */
					if (checkMutex()) {
						JFrame.setDefaultLookAndFeelDecorated(true); /* 半透明ウィンドウ対応 */
						MainFrame frame = new MainFrame();
						frame.setVisible(true);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	/**
	 * ミューテックスを設定する。
	 * 
	 * @return ミューテックスが開放されていればtrue。
	 * @throws IOException チャネルロックエラー
	 */
	private static boolean checkMutex() throws IOException {
		/* チャネル設定 */
		final FileOutputStream fos = new FileOutputStream(new File(".lock"));
		final FileChannel fc = fos.getChannel();
		final FileLock lock = fc.tryLock();
		
		/* チャネルロックをチェック */
		if (lock == null) {
			return (false);	/* すでにロックあり */
		}
		
		/* シャットダウンフックを設定 */
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				if (lock != null && lock.isValid()) {
					try {
						lock.release();
					} catch (IOException e) {
					}
				}
				try {
					fc.close();
				} catch (IOException e) {
				}
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		});
		
		return (true);
	}

	/**
	 * コンストラクタ (Eclipse WDTで生成)
	 */
	public MainFrame() {
		setTitle("Saku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setAlwaysOnTop(true);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		mntmTimebase = new JMenuItem("Timebase");
		mntmTimebase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TimebaseDialog().setVisible(true);
				reload();
			}
		});
		mnMain.add(mntmTimebase);
		
		mntmRefresh = new JMenuItem("Refresh");
		mntmRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		mnMain.add(mntmRefresh);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		mnMain.add(mntmExit);
		
		mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		chckbxmntmAlwaysOnTop = new JCheckBoxMenuItem("Always on Top");
		chckbxmntmAlwaysOnTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem c = (JCheckBoxMenuItem)e.getSource();
				MainFrame.this.setAlwaysOnTop(c.isSelected());
			}
		});
		chckbxmntmAlwaysOnTop.setSelected(true);
		mnSettings.add(chckbxmntmAlwaysOnTop);
		
		chckbxmntmAutoRefresh = new JCheckBoxMenuItem("Auto Refresh");
		chckbxmntmAutoRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem c = (JCheckBoxMenuItem)e.getSource();
				
				if (c.isSelected()) {
					MainFrame.this.startTimer();
				} else {
					MainFrame.this.stopTimer();
				}
			}
		});
		chckbxmntmAutoRefresh.setSelected(true);
		mnSettings.add(chckbxmntmAutoRefresh);
		
		mnOpacity = new JMenu("Opacity");
		mnSettings.add(mnOpacity);
		
		radioButtonMenuItem = new JRadioButtonMenuItem("None");
		radioButtonMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOpacity(1.0f);
			}
		});
		buttonGroup.add(radioButtonMenuItem);
		mnOpacity.add(radioButtonMenuItem);
		
		radioButtonMenuItem_1 = new JRadioButtonMenuItem("80%");
		radioButtonMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOpacity(.8f);
			}
		});
		buttonGroup.add(radioButtonMenuItem_1);
		mnOpacity.add(radioButtonMenuItem_1);
		
		radioButtonMenuItem_2 = new JRadioButtonMenuItem("50%");
		radioButtonMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOpacity(.5f);
			}
		});
		buttonGroup.add(radioButtonMenuItem_2);
		mnOpacity.add(radioButtonMenuItem_2);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		popupMenu = new JPopupMenu();
		popupMenu.addPopupMenuListener(new PopupMenuListener() {
			public void popupMenuCanceled(PopupMenuEvent e) {
			}
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				popupMenu.removeAll();
			}
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				populateMenuItem();
			}
		});
		//contentPane.add(popupMenu, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				table.setRowSelectionInterval(row, row);
			}
		});
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getTableHeader().setReorderingAllowed(false);
		table.setComponentPopupMenu(popupMenu);
		scrollPane.setViewportView(table);
		
		/* 時刻更新タイマ */
		timer = new Timer(10000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
				System.out.println("refreshing");
			}
		});
		
		/* テーブル初期化 */
		setUp();
		
		/* 時刻更新タイマの開始 */
		startTimer();
	}
	
	/**
	 * 時刻更新タイマを開始する。
	 */
	private void startTimer() {
		/* 更新オプションのチェック */
		if (chckbxmntmAutoRefresh.isSelected()) {
			if (timer != null && !timer.isRunning()) {
				timer.start();
			}
		}
	}
	
	/**
	 * 時刻更新タイマを停止する。
	 */
	private void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
	}
	
	/**
	 * テーブルを初期化する。
	 */
	private void setUp (){
		try {
			TimebaseManager mgr = TimebaseManager.getInstance();

			/* 設定をロード */
			if (mgr.load()) {
				Map<String, Timebase> m = mgr.getTimebase();
				Object[][] data = new Object[m.size()][];
				
				int i = 0;
				for (Entry<String, Timebase> e : m.entrySet()) {
					Object[] dd = new Object[2];
					dd[0] = e.getKey();
					dd[1] = df.format(e.getValue().getTimeLine().get(0));
					data[i] = dd;
					i++;
				}
				
				/* モデルを設定*/
				DefaultTableModel model = new DefaultTableModel(data, new String[] {"Location", "Next Spawn"}) {
					private static final long serialVersionUID = -8462179571190270614L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return (false);
					}
				};
				table.setModel(model);
				
				/* カラムソートの設定 */
				RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
				table.setRowSorter(sorter);
			}
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * テーブルを更新する。
	 */
	@SuppressWarnings("unchecked")
	private void refresh() {
		
		/* モデルを取得 */
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		
		/* 現時刻でタイムラインを再計算 */
		Date c = new Date();
		TimebaseManager mgr = TimebaseManager.getInstance();
		for (Vector<Object> e : (Vector<Vector<Object>>)model.getDataVector()) {
			String key = (String)e.get(0);
			Timebase tb = mgr.getTimebase(key);
			tb.refresh(c);
			e.set(1, df.format(tb.getTimeLine().get(0)));
		}
		
		/* テーブル更新 */
		model.fireTableDataChanged();
	}
	
	/**
	 * タイムラインメニューを構築する。
	 */
	private void populateMenuItem() {
		
		/* 選択した行を取得 */
		int row = table.getSelectedRow();
		String key = (String)table.getValueAt(row, 0);
		
		/* ポップアップメニューに項目を設定 */
		if (key != null && !key.isEmpty()) {
			/* 選択したロケーションをラベルに設定 */
			JLabel label = new JLabel("<html><b>" + key + "</b></html>");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			popupMenu.add(label);
			popupMenu.addSeparator();
			
			/* タイムラインを設定 */
			TimebaseManager mgr = TimebaseManager.getInstance();
			Timebase tb = mgr.getTimebase(key);
			if (tb != null) {
				List<Date> line = tb.getTimeLine();
				
				int i = 1;
				for (Date date : line) {
					JMenuItem item = new JMenuItem(i + ": " + df.format(date));
					popupMenu.add(item);
					i++;
				}
			}
		}
	}
	
	/**
	 * タイムベース設定を再読み込みする。
	 */
	public void reload() {
		/* 時刻更新タイマを停止 */
		stopTimer();
		
		/* テーブルの初期化 */
		setUp();
		
		/* 時刻更新タイマを再会 */
		startTimer();
	}
}