package saku;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * プロパティクラス
 *
 * @version 1.0
 */
public final class MainProperties  extends BaseProperties {
	
	/**
	 * プロパティファイル名
	 */
	public static final String fileName = "saku.ini";
	
	/**
	 * 最上位表示
	 */
	private boolean alwaysOnTop = true;
	
	/**
	 * 最上位表示のプロパティ名
	 */
	public static final String ALWAYS_ON_TOP = "alwaysOnTop";
	
	/**
	 * 自動時刻更新
	 */
	private boolean autoRefresh = true;
	
	/**
	 * 自動時刻更新のプロパティ名
	 */
	public static final String AUTO_REFRESH = "autoRefresh";
	
	/**
	 * 不透明度
	 */
	private int opacity = OPACITY_100;
	
	/**
	 * 不透明度のプルパティ名
	 */
	public static final String OPACITY = "opacity";
	
	/**
	 * 事前通知(秒)
	 */
	private int priorNotice = PRIOR_MIN1;
	
	/**
	 * 事前通知のプロパティ名
	 */
	public static final String PRIOR_NOTICE = "priorNotice";
	
	/**
	 * アラート音
	 */
	private boolean alertSound = true;
	
	/**
	 * アラートモードのプロパティ名
	 */
	public static final String ALERT_SOUND = "alertSound";
	
	/**
	 * サーフェイスタイプ
	 */
	private int surfaceStyle = STYLE_NORMAL;
	
	/**
	 * サーフェイスタイプのプロパティ名
	 */
	public static final String SURFACE_STYLE = "surfaceStyle";
	
	/**
	 * カウントダウンタイマ(秒)
	 */
	private Map<String, Integer> countDown = new HashMap<String, Integer>();
	
	/**
	 * アクションキー
	 */
	private Map<String, String> actionKeys = new HashMap<String, String>();
	
	/**
	 * カウントダウンタイマのプロパティ名
	 */
	public static final String COUNT_DOWN = "countDown";

	/**
	 * アクションキーのプロパティ名
	 */
	public static final String ACTION_KEY = "actionKey";

	/**
	 * タイムラインの表示数
	 */
	private int timeLines = 10;
	
	/**
	 * タイムライン表示数のプロパティ名
	 */
	public static final String T_LINES = "timeLines";
	
	/**
	 * ウィンドウ X位置
	 */
	private int locX = 0;
	
	/**
	 * ウィンドウX位置のプロパティ名
	 */
	public static final String LOC_X = "locX";
	
	/**
	 * ウィンドウ Y位置
	 */
	private int locY = 0;
	
	/**
	 * ウィンドウY位置のプロパティ名
	 */
	public static final String LOC_Y = "locY";
	
	/**
	 * ウィンドウ幅
	 */
	private int frameWidth = -1;
	
	/**
	 * ウィンドウ幅のプロパティ名
	 */
	public static final String FRAME_WIDTH = "frameWidth";
	
	/**
	 * ウィンドウ高さ
	 */
	private int frameHeight = -1;
	
	/**
	 * ウィンドウ高さのプロパティ名
	 */
	private static final String FRAME_HEIGHT = "frameHeight";
	
	/**
	 * メニューバーの非表示
	 */
	private boolean hideMenuBar = false;
	
	/**
	 * メニューバー非表示のプロパティ名
	 */
	private static final String HIDE_MENUBAR = "hideMenuBar";
		
	/**
	 * 不透明度: 100%
	 */
	public static final int OPACITY_100 = 0;

	/**
	 * 不透明度: 80%
	 */
	public static final int OPACITY_80 = 1;
	
	/**
	 * 不透明度: 50%
	 */
	public static final int OPACITY_50 = 2;
	
	/**
	 * 事前通知: なし
	 */
	public static final int PRIOR_NONE = 0;
	
	/**
	 * 事前通知: 60秒前
	 */
	public static final int PRIOR_MIN1 = 60;

	/**
	 * 事前通知: 180秒前
	 */
	public static final int PRIOR_MIN3 = 180;
	
	/**
	 * 事前通知: 300秒前
	 */
	public static final int PRIOR_MIN5 = 300;
	
	/**
	 * サーフェイススタイル: 通常
	 */
	public static final int STYLE_NORMAL = 0;
	
	/**
	 * サーフェイススタイル: 反転
	 */
	public static final int STYLE_REVERSE = 1;
	
	/**
	 * カウントダウンのデフォルト値(秒)
	 */
	public static final int COUNTDOWN = 60;
	
	/**
	 * インスタンス
	 */
	private static MainProperties me = null;
	
	/**
	 * コンストラクタ
	 */
	private MainProperties() {
		try {
			load(fileName);
		} catch (FileNotFoundException e) {
			System.err.println("INI file not found. Creating...");
		}
	}
	
	/**
	 * インスタンスを取得する。
	 * @return インスタンス
	 */
	public static final MainProperties getInstance() {
		if (me == null) {
			me = new MainProperties();
		}
		return (me);
	}
	
	/**
	 * 最上位表示を確認する。
	 * 
	 * @return 最上位表示のときはtrue
	 */
	public boolean isAlwaysOnTop() {
		return alwaysOnTop;
	}
	
	/**
	 * 最上位表示を設定する。
	 * 
	 * @param alwaysOnTop 最上位表示にするならtrue
	 */
	public void setAlwaysOnTop(boolean alwaysOnTop) {
		this.alwaysOnTop = alwaysOnTop;
	}
	
	/**
	 * 自動時刻更新を確認する。
	 * 
	 * @return 自動更新時はtrue
	 */
	public boolean isAutoRefresh() {
		return autoRefresh;
	}
	
	/**
	 * 自動時刻更新を設定する。
	 * 
	 * @param autoRefresh 自動更新時はtrue
	 */
	public void setAutoRefresh(boolean autoRefresh) {
		this.autoRefresh = autoRefresh;
	}
	
	/**
	 * 不透明度レベルを取得する。
	 * 
	 * @return 不透明度レベル
	 */
	public int getOpacity() {
		return opacity;
	}

	/**
	 * 不透明度レベルを設定する。
	 * 
	 * @param opacity 不透明度レベル
	 */
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
	
	/**
	 * 事前通知(秒)を取得する。
	 * 
	 * @return 事前通知
	 */
	public int getPriorNotice() {
		return priorNotice;
	}
	
	/**
	 * 事前通知(秒)を設定する。
	 * 
	 * @param priorNotice
	 */
	public void setPriorNotice(int priorNotice) {
		this.priorNotice = priorNotice;
	}
	
	/**
	 * タイムラインの表示数を設定する．
	 * 
	 * @return タイムライン表示数
	 */
	public int getTimeLines() {
		return timeLines;
	}
	
	/**
	 * タイムラインの表示数を取得する．
	 * 
	 * @param timeLines タイムライン表示数
	 */
	public void setTimeLines(int timeLines) {
		this.timeLines = timeLines;
	}
	
	/**
	 * アラート音を確認する。
	 * 
	 * @return アラート音が有効なときはtrue
	 */
	public boolean isAlertSound() {
		return alertSound;
	}
	
	/**
	 * アラート音を設定する。
	 * 
	 * @param alertSound アラート音が有効なときはtrue
	 */
	public void setAlertSound(boolean alertSound) {
		this.alertSound = alertSound;
	}
	
	/**
	 * カウントダウン(秒)を取得する．
	 * 
	 * @return カウントダウン値
	 */
	public int getCountDown(String name) {
		Integer c = countDown.get(name + "." + COUNT_DOWN);

		/* 未登録のときはデフォルト値 */
		if (c != null) {
			return c;
		}
		
		return COUNTDOWN;
	}
	
	/**
	 * カウントダウン(秒)を設定する．
	 * 
	 * @param countDown カウントダウン
	 */
	public void setCountDown(String name, int countDown) {
		this.countDown.put(name + "." + COUNT_DOWN, countDown);
	}
	
	/**
	 * アクションキーリストを取得する．
	 * 
	 * @return アクションキーリスト
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> getActionKeys() {
		return (Map<String, String>) ((HashMap<String, String>) actionKeys).clone();
	}
	
	/**
	 * アクションキーを取得する．
	 * 
	 * @return アクションキー
	 */
	public String getActionKey(String name) {
		String c = actionKeys.get(name + "." + ACTION_KEY);

		/* 未登録のときはnull */
		if (c != null) {
			return c;
		}
		
		return null;
	}
	
	/**
	 * アクションキーを設定する．
	 * 
	 * @param key アクションキー
	 */
	public void setActionKey(String name, String key) {
		if (key == null){
			actionKeys.remove(name + "." + ACTION_KEY);
		} else {
			actionKeys.put(name + "." + ACTION_KEY, key);
		}
	}
	
	/**
	 * ウィンドウのX位置を取得する。
	 * 
	 * @return X位置
	 */
	public int getX() {
		return locX;
	}
	
	/**
	 * ウィンドウのX位置を設定する。
	 * 
	 * @param locX X位置
	 */
	public void setX(int locX) {
		this.locX = locX;
	}
	
	/**
	 *  ウィンドウのY位置を取得する。
	 *  
	 * @return Y位置
	 */
	public int getY() {
		return locY;
	}
	
	/**
	 * ウィンドウのY位置を設定する。
	 * 
	 * @param locY Y位置
	 */
	public void setY(int locY) {
		this.locY = locY;
	}
	
	/**
	 * サーフェイススタイルを取得する。
	 * 
	 * @return サーフェイススタイル
	 */
	public int getSurfaceStyle() {
		return surfaceStyle;
	}
	
	/**
	 * サーフェイススタイルを設定する。
	 * 
	 * @param surfaceStyle サーフェイススタイル
	 */
	public void setSurfaceStyle(int surfaceStyle) {
		this.surfaceStyle = surfaceStyle;
	}
	
	/**
	 * ウィンドウ幅を取得する。
	 * 
	 * @return ウィンドウ幅
	 */
	public int getWidth() {
		return frameWidth;
	}
	
	/**
	 * ウィンドウ幅を設定する。
	 * 
	 * @param frameWidth ウィンドウ幅
	 */
	public void setWidth(int frameWidth) {
		this.frameWidth = frameWidth;
	}

	/**
	 * ウィンドウ高さを取得する。
	 * 
	 * @return ウィンドウ高さ
	 */
	public int getHeight() {
		return frameHeight;
	}
	
	/**
	 * ウィンドウ高さを設定する。
	 * 
	 * @param frameHeight ウィンドウ高さ
	 */
	public void setHeight(int frameHeight) {
		this.frameHeight = frameHeight;
	}
	
	/**
	 * メニューバーの非表示フラグを確認する。
	 * 
	 * @return メニューバー非表示フラグ
	 */
	public boolean isHideMenuBar() {
		return hideMenuBar;
	}
	
	/**
	 * メニューバーの非表示フラグを設定する。
	 * 
	 * @param hideMenuBar メニューバー非表示フラグ
	 */
	public void setHideMenuBar(boolean hideMenuBar) {
		this.hideMenuBar = hideMenuBar;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void loadProperties() {
		setAlwaysOnTop(getBoolean(ALWAYS_ON_TOP, true));
		setAutoRefresh(getBoolean(AUTO_REFRESH, true));
		setOpacity(getInt(OPACITY, OPACITY_100));
		setX(getInt(LOC_X, 0));
		setY(getInt(LOC_Y, 0));
		setWidth(getInt(FRAME_WIDTH, -1));
		setHeight(getInt(FRAME_HEIGHT, -1));
		setPriorNotice(getInt(PRIOR_NOTICE, PRIOR_MIN1));
		setAlertSound(getBoolean(ALERT_SOUND, false));
		setSurfaceStyle(getInt(SURFACE_STYLE, STYLE_NORMAL));
		setHideMenuBar(getBoolean(HIDE_MENUBAR, false));
		setTimeLines(getInt(T_LINES, 10));
		
		/* タイマーボタンプロパティの検索 */
		for (Entry<Object, Object> e : properties.entrySet()) {
			String key = (String)e.getKey();
			if (key.endsWith("." + COUNT_DOWN)) {
				countDown.put(key, getInt(key, COUNTDOWN));		
			} else if (key.endsWith("." + ACTION_KEY)) {
				actionKeys.put(key, getString(key, null));		
			}
		}
	}
	
	/**
	 * プロパティファイルを保存する。
	 */
	public void save() {
		try {
			save(fileName);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot write INI file.");
			e.printStackTrace();
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void storeProperties() {
		properties.put(ALWAYS_ON_TOP, Boolean.toString(isAlwaysOnTop()));
		properties.put(AUTO_REFRESH, Boolean.toString(isAutoRefresh()));
		properties.put(OPACITY, Integer.toString(getOpacity()));
		properties.put(LOC_X, Integer.toString(getX()));
		properties.put(LOC_Y, Integer.toString(getY()));
		properties.put(FRAME_WIDTH, Integer.toString(getWidth()));
		properties.put(FRAME_HEIGHT, Integer.toString(getHeight()));
		properties.put(PRIOR_NOTICE, Integer.toString(getPriorNotice()));
		properties.put(ALERT_SOUND, Boolean.toString(isAlertSound()));
		properties.put(SURFACE_STYLE, Integer.toString(getSurfaceStyle()));
		properties.put(HIDE_MENUBAR, Boolean.toString(isHideMenuBar()));
		properties.put(T_LINES, Integer.toString(getTimeLines()));
		
		/* タイマーボタンプロパティの保存 */
		for (Entry<String, Integer> e : countDown.entrySet()) {
			String key = e.getKey();
			Integer value = e.getValue();
			if (key != null && value != null) {
				properties.put(key, Integer.toString(value));
			}
		}
		
		for (Entry<String, String> e : actionKeys.entrySet()) {
			String key = e.getKey();
			String value = e.getValue();
			if (key != null && value != null) {
				properties.put(key, value);
			}
		}
	}
}