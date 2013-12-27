package saku;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.Timer;

/**
 * カウントダウンタイマ
 *
 * @version 1.0
 */
public final class TimerButton extends JButton {
	
	/**
	 * シリアルバージョンID 
	 */
	private static final long serialVersionUID = 798989664689965119L;
	
	/**
	 * タイマオブジェクト
	 */
	private Timer timer = null;
	
	/**
	 * タイマ名
	 */
	private String name = null;
	
	/**
	 * デフォルト表示色
	 */
	private Color defaultColor;
	
	/**
	 * 赤色表示にする残り時間(秒)
	 */
	private int MINLEFT = 10;
	
	/**
	 * コンストラクタ
	 * 
	 * @param name タイマ名
	 */
	public TimerButton(final String name) {
		super("00:00");
		
		this.name = name;
		
		setFocusable(false);
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/* カウントダウンタイマの設定 */
				if (timer == null || !timer.isRunning()) {
					startTimer();	 /* スタート */
				} else {
					stopTimer();	/* ストップ */
				}
			}
		});
		
		/* ポップアップメニューの設定 */
		JPopupMenu popupMenu = new JPopupMenu();
		JLabel label = new JLabel("<html><b>" + name + "</b></html>");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		popupMenu.add(label);
		popupMenu.addSeparator();
		
		/* カウンタリセット */
		JMenuItem item0 = new JMenuItem("Reset Timer");
		item0.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				resetTimer();
			}
		});
		popupMenu.add(item0);

		/* 時間設定ダイアログ */
		JMenuItem item1 = new JMenuItem("Setting");
		item1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new TimerDialog(name).setVisible(true);
			}
		});
		popupMenu.add(item1);
		addPopup(popupMenu);
		
		defaultColor = getForeground();
		
		/* タイマをリセット */
		resetTimer();
	}

	/**
	 * カウントダウンを開始する。
	 */
	private void startTimer() {
		final int count = MainProperties.getInstance().getCountDown(name) - 1;
		
		/* タイマの生成 */
		timer = new Timer(1000, new ActionListener() {
			int c = count - 1; 

			@Override
			public void actionPerformed(ActionEvent e) {
				if (c < 0) {
					/* 残り時間がないときはタイマを停止 */
					Timer t = (Timer) e.getSource();
					t.stop();
				} else {
					/* 残り時間を表示 */
					if (c < MINLEFT) {	/* 10秒切ると赤色 */
						setForeground(Color.red);
					}
					setText(getTimeString(c));
					c --;
				} 
			}
		});
		
		/* 残り時間を表示 */
		setText(getTimeString(count));
		timer.start();
	}
	
	/**
	 * タイマを停止する。
	 */
	private void stopTimer() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
		timer = null;
	}

	/**
	 * タイマをリセットする。
	 */
	private void resetTimer() {
		/* タイマを停止して表示をクリア */
		stopTimer();
		setForeground(defaultColor);
		setText(getTimeString(MainProperties.getInstance().getCountDown(name)));
	}
		
	/**
	 * ポップアップメニューを登録する。
	 * 
	 * @param popup ポップアップメニュー
	 */
	private void addPopup( final JPopupMenu popup) {
		addMouseListener(new MouseAdapter() {
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
	
	/**
	 * 残り時間の表示形式を取得する．
	 * 
	 * @param count 残り時間(秒)
	 * @return 残り時間の文字列
	 */
	private String getTimeString(int count) {
		int min = count / 60;
		int sec = count % 60;
		return (String.format("%02d:%02d", min, sec));
	}
}
