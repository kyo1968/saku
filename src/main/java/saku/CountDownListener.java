package saku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.Timer;

/**
 * カウントダウンタイマのアクションリスナ
 *
 * @verion 1.0
 */
public class CountDownListener implements ActionListener {
	
	/**
	 * 残り時間を表示するボタン
	 */
	private JButton c;
	
	/**
	 * 残り時間 (秒)
	 */
	private int count;
	
	/**
	 * コンストラクタ
	 * @param c ボタン
	 * @param count 初期時間(秒)
	 */
	public CountDownListener(JButton c, int count) {
		super();
		
		this.c  = c;
		this.count = count - 1;	/* 最初のイベント時の残り時間 */
		c.setText(getTimeString(count));	/* 初期ラベル表示*/
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (count < 0) {
			/* 残り時間がないときはタイマを停止 */
			Timer t = (Timer) e.getSource();
			t.stop();
		} else {
			/* 残り時間を表示 */
			c.setText(getTimeString(count));
			count --;
		} 
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
