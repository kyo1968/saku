package saku;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * デートフォーマッタ
 *
 * @version 1.00
 */
public final class LocalDateFormat {
	
	/**
	 * ファイル保存フォーマット
	 */
	public static final String FILE_DATEFORMAT = "yyyy-MM-dd HH:mm:ss zzz";
	
	/**
	 * 表示フォーマット
	 */
	public static final String UI_DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
	//public static final String UI_DATEFORMAT = "yyyy年MM月dd日 HH時mm分ss秒"; /* 日本語表示 */
	
	/**
	 * ファイルフォーマッタ
	 */
	private DateFormat fileDateFormat;
	
	/**
	 * 表示フォーマッタ
	 */
	private DateFormat uiDateFormat;

	/**
	 * コンストラクタ
	 */
	public LocalDateFormat() {
		/* フォーマッタを生成 */
		fileDateFormat = new SimpleDateFormat(FILE_DATEFORMAT);
		uiDateFormat = new SimpleDateFormat(UI_DATEFORMAT);
	}
	
	/**
	 * 日時オブジェクトをファイル形式にフォーマットする。
	 * 
	 * @param date 日時オブジェクト 
	 * @return ファイル形式の文字列
	 */
	public String formatToFile(Date date) {
		return (fileDateFormat.format(date));
	}
	
	/**
	 * 日時オブジェクトを表示形式にフォーマットする。
	 * 
	 * @param date 日時オブジェクト
	 * @return 表示形式の文字列
	 */
	public String formatToUI(Date date) {
		return (uiDateFormat.format(date));
	}
	
	/**
	 * ファイル形式の文字列を日時オブジェクトに変換する。
	 * 
	 * @param source ファイル形式の文字列
	 * @return 日時オブジェクト
	 * @throws ParseException 変換エラー
	 */
	
	public Date parseFromFile(String source) throws ParseException {
		return (fileDateFormat.parse(source));
	}

	/**
	 * 表示形式の文字列を日時オブジェクトに変換する。
	 * 
	 * @param source 表示形式の文字列
	 * @return 日時オブジェクト
	 * @throws ParseException 変換エラー
	 */
	public Date parseFromUI(String source) throws ParseException {
		return (uiDateFormat.parse(source));
	}
}