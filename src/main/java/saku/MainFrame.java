package saku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Vector;
import javax.swing.JPopupMenu;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.ButtonGroup;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JToolBar;

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
	private static DateFormat df = LocalDateFormat.getInstance(LocalDateFormat.FORMAT_UI_TIME);

	/**
	 * デートフォーマッタ (タイトルバー用)
	 */
	private static DateFormat ldf = LocalDateFormat.getInstance(LocalDateFormat.FORMAT_UI_DATETIME);
	
	/**
	 * プロパティ
	 */
	private MainProperties properties = MainProperties.getInstance();
	
	/**
	 * タイマ間隔 (ミリ秒)
	 */
	private final int delay = 1000;

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
	private JMenu mnAlert;
	private JCheckBoxMenuItem chckbxmntmBeep;
	private JRadioButtonMenuItem rdbtnmntmNone;
	private JRadioButtonMenuItem rdbtnmntmMin;
	private JRadioButtonMenuItem rdbtnmntmMin_1;
	private JRadioButtonMenuItem rdbtnmntmMin_2;
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JMenu mnSurface;
	private JRadioButtonMenuItem rdbtnmntmNormal;
	private JRadioButtonMenuItem rdbtnmntmReverse;
	private final ButtonGroup buttonGroup_2 = new ButtonGroup();
	private JPanel panel;
	private JButton btnCountDown;
	private JButton btnCountDown1;
	private JButton btnCountDown2;
	private JPopupMenu popupMenu_1;
	private JCheckBoxMenuItem chckbxmntmHideMenuItem;
	private JToolBar toolBar;
	
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
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				tearDown();
			}
		});
		setTitle("Saku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setAlwaysOnTop(properties.isAlwaysOnTop());
		
		popupMenu_1 = new JPopupMenu();
		addPopup(this, popupMenu_1);
		
		chckbxmntmHideMenuItem = new JCheckBoxMenuItem("Hide Menubar");
		chckbxmntmHideMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem c = (JCheckBoxMenuItem)e.getSource();
				if (c.isSelected()) {
					toolBar.setVisible(false);	/* メニューバーを非表示 */
				} else {
					toolBar.setVisible(true);		/* メニューバーを表示 */
				}
			}
		});
		popupMenu_1.add(chckbxmntmHideMenuItem);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
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
		contentPane.setLayout(new BorderLayout(0, 0));
		
		toolBar = new JToolBar();
		toolBar.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.add(toolBar, BorderLayout.NORTH);
		toolBar.setFloatable(false);
		
		menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		menuBar.setBorder(null);
		toolBar.add(menuBar);
		
		mnMain = new JMenu("Main");
		menuBar.add(mnMain);
		
		mntmTimebase = new JMenuItem("Timebase");
		mntmTimebase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TimebaseDialog(MainFrame.this).setVisible(true);
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
				tearDown();
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
				properties.setAlwaysOnTop(c.isSelected());
				MainFrame.this.setAlwaysOnTop(c.isSelected());
			}
		});
		chckbxmntmAlwaysOnTop.setSelected(properties.isAlwaysOnTop());
		mnSettings.add(chckbxmntmAlwaysOnTop);
		
		chckbxmntmAutoRefresh = new JCheckBoxMenuItem("Auto Refresh");
		chckbxmntmAutoRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem c = (JCheckBoxMenuItem)e.getSource();
				
				properties.setAutoRefresh(c.isSelected());
				if (c.isSelected()) {
					MainFrame.this.startTimer();
				} else {
					MainFrame.this.stopTimer();
				}
			}
		});
		chckbxmntmAutoRefresh.setSelected(properties.isAutoRefresh());
		mnSettings.add(chckbxmntmAutoRefresh);
		
		chckbxmntmBeep = new JCheckBoxMenuItem("Alert Sound");
		chckbxmntmBeep.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JCheckBoxMenuItem c = (JCheckBoxMenuItem)e.getSource();
				properties.setAlertSound(c.isSelected());
			}
		});
		chckbxmntmBeep.setSelected(properties.isAlertSound());
		mnSettings.add(chckbxmntmBeep);
		
		mnAlert = new JMenu("Prior Notice");
		mnSettings.add(mnAlert);
		
		rdbtnmntmNone = new JRadioButtonMenuItem("None");
		rdbtnmntmNone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				properties.setPriorNotice(MainProperties.PRIOR_NONE);
			}
		});
		buttonGroup_1.add(rdbtnmntmNone);
		mnAlert.add(rdbtnmntmNone);
		
		rdbtnmntmMin = new JRadioButtonMenuItem("1 min");
		rdbtnmntmMin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				properties.setPriorNotice(MainProperties.PRIOR_MIN1);
			}
		});
		buttonGroup_1.add(rdbtnmntmMin);
		mnAlert.add(rdbtnmntmMin);
		
		rdbtnmntmMin_1 = new JRadioButtonMenuItem("3 min");
		rdbtnmntmMin_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				properties.setPriorNotice(MainProperties.PRIOR_MIN3);
			}
		});
		buttonGroup_1.add(rdbtnmntmMin_1);
		mnAlert.add(rdbtnmntmMin_1);
		
		rdbtnmntmMin_2 = new JRadioButtonMenuItem("5 min");
		rdbtnmntmMin_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				properties.setPriorNotice(MainProperties.PRIOR_MIN5);
			}
		});
		buttonGroup_1.add(rdbtnmntmMin_2);
		mnAlert.add(rdbtnmntmMin_2);
		
		mnOpacity = new JMenu("Opacity");
		mnSettings.add(mnOpacity);
		
		radioButtonMenuItem = new JRadioButtonMenuItem("100%");
		radioButtonMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				properties.setOpacity(MainProperties.OPACITY_100);
				setOpacity(1.0f);
			}
		});
		buttonGroup.add(radioButtonMenuItem);
		mnOpacity.add(radioButtonMenuItem);
		
				radioButtonMenuItem_1 = new JRadioButtonMenuItem("80%");
				radioButtonMenuItem_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						properties.setOpacity(MainProperties.OPACITY_80);
						setOpacity(.8f);
					}
				});
				buttonGroup.add(radioButtonMenuItem_1);
				mnOpacity.add(radioButtonMenuItem_1);
				
				radioButtonMenuItem_2 = new JRadioButtonMenuItem("50%");
				radioButtonMenuItem_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						properties.setOpacity(MainProperties.OPACITY_50);
						setOpacity(.5f);
					}
				});
				buttonGroup.add(radioButtonMenuItem_2);
				mnOpacity.add(radioButtonMenuItem_2);
				
				mnSurface = new JMenu("Surface");
				mnSettings.add(mnSurface);
				
				rdbtnmntmNormal = new JRadioButtonMenuItem("Normal");
				rdbtnmntmNormal.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						properties.setSurfaceStyle(MainProperties.STYLE_NORMAL);
						table.setForeground(Color.BLACK);
						table.setBackground(Color.WHITE);
					}
				});
				buttonGroup_2.add(rdbtnmntmNormal);
				mnSurface.add(rdbtnmntmNormal);
				
				rdbtnmntmReverse = new JRadioButtonMenuItem("Reverse");
				rdbtnmntmReverse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						properties.setSurfaceStyle(MainProperties.STYLE_REVERSE);
						table.setForeground(Color.WHITE);
						table.setBackground(Color.BLACK);
					}
				});
				buttonGroup_2.add(rdbtnmntmReverse);
				mnSurface.add(rdbtnmntmReverse);
		//contentPane.add(popupMenu, BorderLayout.NORTH);
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane);
		
		table = new JTable() {
			private static final long serialVersionUID = -2592351809384294718L;
			@Override
			public Component prepareRenderer(TableCellRenderer tcr, int row, int column) {
				Component c = super.prepareRenderer(tcr, row, column);
				String v = (String) getValueAt(row, 1);
				if (v.endsWith("[Ready!]")) {
					c.setForeground(Color.RED);
				} else {
					c.setForeground(getForeground());
				}
				return (c);
			}
		};
		
		/* クリックでタイムライン表示 */
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (table.getSelectedColumn() == 2) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
		
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
		table.getTableHeader().setReorderingAllowed(false);
		//table.setComponentPopupMenu(popupMenu);
		table.setFont(new Font("MS PGothic", Font.BOLD, 12));
		table.setDefaultRenderer(JLabel.class, new LabelRenderer());
		scrollPane.setViewportView(table);
		panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);

		btnCountDown = new TimerButton("timer1");
		panel.setLayout(new GridLayout(0, 3, 0, 0));
		panel.add(btnCountDown);
		
		btnCountDown1 = new TimerButton("timer2");
		panel.add(btnCountDown1);
		
		btnCountDown2 = new TimerButton("timer3");
		panel.add(btnCountDown2);
		
		/* 時刻更新タイマ */
		timer = new Timer(delay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		
		/* 不透明度の初期設定 */
		switch (properties.getOpacity()) {
		case MainProperties.OPACITY_100:
			radioButtonMenuItem.setSelected(true);
			setOpacity(1.0f);
			break;
		case MainProperties.OPACITY_80:
			radioButtonMenuItem_1.setSelected(true);
			setOpacity(.8f);
			break;
		case MainProperties.OPACITY_50:
			radioButtonMenuItem_2.setSelected(true);
			setOpacity(.5f);
			break;
		}

		/* 事前通知の初期設定 */
		switch (properties.getPriorNotice()) {
		case MainProperties.PRIOR_NONE:
			rdbtnmntmNone.setSelected(true);
			break;
		case MainProperties.PRIOR_MIN1:
			rdbtnmntmMin.setSelected(true);
			break;
		case MainProperties.PRIOR_MIN3:
			rdbtnmntmMin_1.setSelected(true);
			break;
		case MainProperties.PRIOR_MIN5:
			rdbtnmntmMin_2.setSelected(true);
			break;
		}
		
		/* スタイルの初期設定 */
		switch (properties.getSurfaceStyle()) {
		case MainProperties.STYLE_NORMAL:
			rdbtnmntmNormal.setSelected(true);
			table.setForeground(Color.BLACK);
			table.setBackground(Color.WHITE);
			break;
		case MainProperties.STYLE_REVERSE:
			rdbtnmntmReverse.setSelected(true);
			table.setForeground(Color.WHITE);
			table.setBackground(Color.BLACK);
			break;
		}
		
		/* メニューバー非表示の初期設定 */
		if (properties.isHideMenuBar()) {
			chckbxmntmHideMenuItem.setSelected(true);
		}

		/* テーブル初期化 */
		initTable();
		
		/* 時刻更新タイマの開始 */
		startTimer();
		
		/* ウィンドウ初期化 */
		setUp();
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
	private void initTable (){
		try {
			TimebaseManager mgr = TimebaseManager.getInstance();

			/* 設定をロード */
			if (mgr.load()) {
				Map<String, Timebase> m = mgr.getTimebase();
				Object[][] data = new Object[m.size()][];
				
				int i = 0;
				for (Entry<String, Timebase> e : m.entrySet()) {
					Object[] dd = new Object[3];
					dd[0] = e.getKey();
					dd[1] = df.format(e.getValue().getTimeLine().get(0));
					dd[2] = new JLabel(e.getKey());
					data[i] = dd;
					i++;
				}
				
				/* モデルを設定*/
				DefaultTableModel model = new DefaultTableModel(data, new String[] {"Location", "Estimated Spawn Time", ""}) {
					private static final long serialVersionUID = -8462179571190270614L;

					@Override
					public boolean isCellEditable(int row, int column) {
						return (false);
					}
					
					@Override
					public Class<?> getColumnClass(int col) {
						return (getValueAt(0, col).getClass());
					}
				};
				table.setModel(model);
				
				/* カラムソートの設定 */
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
				sorter.setSortable(2, false);
				table.setRowSorter(sorter);
				
				/* アイコンカラムの固定 */
				TableColumn col = table.getColumnModel().getColumn(2);
				col.setMaxWidth(16);
				col.setMinWidth(16);
				col.setResizable(false);

				/* カラム幅の調整 */
				fixColumnWidth();
				
			} else {
				JOptionPane.showMessageDialog(this, "Invalid timebase settings:" +  TimebaseManager.fileName, 
						"Error", JOptionPane.ERROR_MESSAGE);
			}

		} catch (IOException | ParseException e) {
			JOptionPane.showMessageDialog(this, "Cannot load timebase settings: " +  TimebaseManager.fileName, 
					"Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
	
	/**
	 * 初期化処理する。
	 */
	private void setUp() {
		/* ウィンドウの位置とサイズを再現 */
		int x = properties.getX();
		int y = properties.getY();
		int width = properties.getWidth();
		int height = properties.getHeight();
		
		/* 現在のウィンドウ位置を取得 */
		Rectangle r = getBounds();
		
		/* 保存している値があれば適用 */
		if (width > 0 && height > 0) {
			r.setSize(width, height);
		}
		
		if (x >= 0 && y >= 0) {
			r.setLocation(x, y);
		}
		
		/* 位置とサイズ変更 */
		setBounds(r);
	}
	
	/**
	 * 終了処理する。
	 */
	private void tearDown() {
		/* ウィンドウの位置とサイズを記録 */
		Rectangle r = getBounds();
		properties.setX((int)r.getX());
		properties.setY((int)r.getY());
		properties.setWidth((int)r.getWidth());
		properties.setHeight((int)r.getHeight());
		
		/* プロパティを保存 */
		properties.save();
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
		long alertTime = properties.getPriorNotice() * 1000;
		TimebaseManager mgr = TimebaseManager.getInstance();
		for (Vector<Object> e : (Vector<Vector<Object>>)model.getDataVector()) {
			String key = (String)e.get(0);
			Timebase tb = mgr.getTimebase(key);
			tb.refresh(c);
			
			/* アラート表示 */
			Date co = tb.getTimeLine().get(0);
			if (alertTime == 0 || co.getTime() - c.getTime() > alertTime) {
				e.set(1, df.format(co));
				/* アラート音の準備 */
				tb.setAlert(true);
			} else {
				e.set(1, df.format(co) + " [Ready!]");
				
				/* アラート音 */
				if (tb.isAlert()) {
					if (properties.isAlertSound()) {
						Toolkit.getDefaultToolkit().beep();
					}
					/* 繰り返し通知の防止 */
					tb.setAlert(false);
				}
			}
		}
		
		/* テーブル更新 */
		model.fireTableDataChanged();
		
		/* 最終更新時間を表示 */
		setTitle("Saku: [" + ldf.format(c) + "]");
	}
	
	/**
	 * タイムラインメニューを構築する。
	 */
	private void populateMenuItem() {
		
		/* 選択した行を取得 */
		int row = table.getSelectedRow();
		if (row >= 0) {
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
						JMenuItem item = new JMenuItem(i + ") " + df.format(date));
						popupMenu.add(item);
						i++;
					}
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
		initTable();
		
		/* 時刻更新タイマを再会 */
		startTimer();
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
	
	/**
	 * ポップアップメニューを登録する。
	 * 
	 * @param component 登録先コンポーネント
	 * @param popup ポップアップメニュー
	 */
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}