package saku;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * タイムベース
 * 
 * @version 1.00
 */
public final class Timebase {
	/**
	 * 基準時間
	 */
	private Date base;
	
	/**
	 * リポップ間隔(秒)
	 */
	private int respawn;
	
	/**
	 * アラート通知
	 */
	private boolean alert = false;
	
	/**
	 * 現在時刻からのタイムライン
	 */
	List<Date> timeLine;
	
	/**
	 * タイムラインを計算する。
	 * 
	 * @param current 現在時刻
	 * @param timeLines 計算数
	 */
	public void refresh(Date current, int timeLines) {
		long s = base.getTime();		/* 基準時刻 */
		long c = current.getTime();		/* 現在時刻 */
		long r = respawn * 1000;		/* リポップ間隔 */
		long t;	/* 直近のリポップ時刻 */

		/* 基準時刻と現在時刻を比較 */
		if (s < c) {
			/* 最後のリポップ時刻 */
			long a = ((c - s) / r) * r + s;
			
			if (a < c) {
				t = a + r;
			} else {
				t = a;
			}
		} else {
			/* 基準時 >= 現在時刻のときは基準時が起点 */
			t = s;
		}
		
		/* タイムラインの設定 */
		List<Date> tl = new ArrayList<Date>();
		if (timeLines > 0) { 
			for (int i = 0; i < timeLines; i++) {
				tl.add(new Date(t));
				t += r;
			}
		}

		timeLine = tl;
	}
	
	/**
	 * 基準時間を取得する。
	 * 
	 * @return基準時間
	 */
	public Date getBaseTime() {
		return base;
	}
	
	/**
	 * 基準時間を設定する。
	 * 
	 * @param base 基準時間
	 */
	public void setBaseTime(Date base) {
		this.base = base;
	}
	
	/**
	 * リポップ間隔(秒)を取得する。
	 * 
	 * @return リポップ間隔
	 */
	public int getRespawn() {
		return respawn;
	}
	
	/**
	 * リポップ間隔(秒)を設定する。
	 * 
	 * @param interval リポップ間隔
	 */
	public void setRespawn(int respawn) {
		this.respawn = respawn;
	}
	
	/**
	 * タイムラインを取得する。
	 * 
	 * @return タイムライン
	 */
	public List<Date> getTimeLine() {
		return timeLine;
	}
	
	/**
	 * アラート通知フラグを設定する。
	 * 
	 * @param alert アラート通知フラグ
	 */
	public void setAlert(boolean alert) {
		this.alert = alert;
	}
	
	/**
	 * アラート通知フラグを取得する。
	 * 
	 * @return アラート通知フラグ
	 */
	public boolean isAlert() {
		return alert;
	}
}