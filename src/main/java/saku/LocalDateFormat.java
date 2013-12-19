package saku;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * デートフォーマッタ
 *
 * @version 1.00
 */
public final class LocalDateFormat {
	
	/**
	 * ファイル保存フォーマット
	 */
	public static final String FORMAT_FILE_DATETIME = "yyyy-MM-dd HH:mm:ss zzz";
	
	/**
	 * 表示フォーマット
	 */
	public static final String FORMAT_UI_DATETIME = "yyyy-MM-dd HH:mm:ss";
	//public static final String UI_DATEFORMAT = "yyyy年MM月dd日 HH時mm分ss秒"; /* 日本語表示 */
	
	/**
	 * 表示フォーマット (時刻のみ)
	 */
	public static final String FORMAT_UI_TIME = "HH:mm:ss";
	//public static final String UI_SIMPLE_DATEFORMAT = "HH時mm分ss秒"; /* 日本語表示 */
	
	/**
	 * デートフォーマッタのインスタンスを取得する。
	 * 
	 * @param format フォーマット
	 * @return インスタンス
	 */
	public static DateFormat getInstance(String format) {
		return (new SimpleDateFormat(format));
	}
}