package saku;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * プロパティクラス
 *
 * @version 1.0
 */
public final class MainProperties {
	
	/**
	 * プロパティ
	 */
	private Properties properties = new Properties();
	
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
	 * コンストラクタ
	 */
	public MainProperties() {
		load(fileName);
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
	 * プロパティファイルを読み込む。
	 * 
	 * @param file プロパティファイル名
	 */
	public void load(String file) {
		try {
			load(new FileInputStream(new File(file))); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * プロパティファイルを読み込む。
	 * 
	 * @param is 入力ストリーム
	 */
	public void load(InputStream is) {
		
		try {
			properties.load(is);
			
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
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * プロパティファイルを保存する。
	 */
	public void save() {
		save(fileName);
	}

	/**
	 * プロパティファイルを保存する。
	 * 
	 * @param file プロパティファイル名
	 */
	public void save(String file) {
		try {
			save(new FileOutputStream(new File(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * プロパティファイルを保存する。
	 * 
	 * @param os 出力ストリーム
	 */
	public void save(OutputStream os) {
		try {
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
			
			properties.store(os, "saku settings");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * プロパティ値(文字列)を取得する。
	 * 
	 * @param key プロパティ名
	 * @param defaultValue デフォルト値
	 * @return プロパティ値
	 */
	public String getString(String key, String defaultValue) {
		try {
			String v = properties.getProperty(key);
			if (v != null) {
				return (v);
			}
			return (defaultValue);
		} catch (Exception e) {
			return (defaultValue);
		}
	}
	
	/**
	 * プロパティ値(論理値)を取得する。
	 * 
	 * @param key プロパティ名
	 * @param defaultValue デフォルト値
	 * @return プロパティ値
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		
		try {
			String v = properties.getProperty(key);

			if (v != null) {
				return (Boolean.parseBoolean(v));
			}
			return (defaultValue);
		} catch (Exception e) {
			return (defaultValue);
		}
	}
	
	/**
	 * プロパティ値(浮動小数点)を取得する。
	 * 
	 * @param key プロパティ名
	 * @param defaultValue デフォルト値
	 * @return プロパティ値
	 */
	public float getFloat(String key, float defaultValue) {
		
		try {
			String v = properties.getProperty(key);

			if (v != null) {
				return (Float.parseFloat(v));
			}
			return (defaultValue);
		} catch (Exception e) {
			return (defaultValue);
		}
	}
	
	/**
	 * プロパティ値(整数値)を取得する。
	 * 
	 * @param key プロパティ名
	 * @param defaultValue デフォルト値
	 * @return プロパティ値
	 */
	public int getInt(String key, int defaultValue) {
		
		try {
			String v = properties.getProperty(key);

			if (v != null) {
				return (Integer.parseInt(v));
			}
			return (defaultValue);
		} catch (Exception e) {
			return (defaultValue);
		}
	}
}
