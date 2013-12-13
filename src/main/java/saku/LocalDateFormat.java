package saku;

import java.text.SimpleDateFormat;

/**
 * デートフォーマッタ
 *
 * @version 1.00
 */
public final class LocalDateFormat extends SimpleDateFormat {
	
	/**
	 * シリアルバージョンID
	 */
	private static final long serialVersionUID = 6713516195024587466L;

	/**
	 * コンストラクタ
	 */
	public LocalDateFormat() {
		super("yyyy-MM-dd HH:mm:ss");  /* 統一形式 */
	}
}