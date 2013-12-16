package saku;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import au.com.bytecode.opencsv.CSVReader;

/**
 * タイムベースマネージャ
 * 
 * @version 1.00
 */
public class TimebaseManager {

	/**
	 * 保存ファイル名
	 */
	public static final String fileName = "timebase.dat";
	
	/**
	 * タイムベースのマップ
	 */
	private Map<String, Timebase> timebaseMap = new LinkedHashMap<String, Timebase>();
	
	/**
	 * インスタンス
	 */
	private static TimebaseManager me = new TimebaseManager();
	
	/**
	 * 日時フォーマッタ
	 */
	private DateFormat df = new LocalDateFormat();

	/**
	 * コンストラクタ
	 */
	private TimebaseManager() {
	}
	
	/**
	 * インスタンスを取得する。
	 * 
	 * @return インスタンス
	 */
	public static final TimebaseManager getInstance() {
		return (me);
	}
	
	/**
	 * ロケーションのタイムベースを取得する。
	 * 
	 * @param name ロケーション名
	 * @return タイムベース
	 */
	public Timebase getTimebase(String name) {
		return timebaseMap.get(name);
	}
	
	/**
	 * ロケーションのタイムベースを設定する。
	 * 
	 * @param name ロケーション名
	 * @param timebase タイムベース
	 */
	public void putTimebase(String name, Timebase timebase) {
		timebaseMap.put(name, timebase);
	}
	
	/**
	 * 全ロケーションのタイムベースを取得する。
	 * 
	 * @return タイムベースマップ
	 */
	public Map<String, Timebase> getTimebase() {
		return (timebaseMap);
	}
	
	/**
	 * マネージャを初期化する。
	 */
	public void clear () {
		timebaseMap.clear();
	}

	/**
	 * タイムベース設定を読み込む。
	 * 
	 * @return 成功時はtrue
	 * @throws IOException 入出力エラー
	 * @throws ParseException ファイル解析エラー
	 */
	public boolean load() throws IOException, ParseException {
		return(load(fileName));
	}
	
	/**
	 * タイムベース設定を読み込む。
	 * 
	 * @param file 保存ファイル名
	 * @return 成功時はtrue
	 * @throws IOException 入出力エラー
	 * @throws ParseException ファイル解析エラー
	 */
	private boolean load(String file) throws IOException, ParseException {
		
		/* マネージャを初期化 */
		clear();

		/* 設定したタイムベースを読み込み */
		CSVReader r = null;
		Date c = new Date();
		try {
			String[] li;
			r = new CSVReader(new FileReader(file));
			while ((li = r.readNext()) != null) {
				if (li.length < 3) {
					return (false);
				}
				
				String name = li[0];		/* ロケーション */
				String respawn = li[1];		/* リポップ間隔 */
				String timebase = li[2];	/* 基準時刻 */ 
				
				/* ロケーションのタイムベースを設定 */
				Timebase tb = new Timebase();
				tb.setRespawn(Integer.parseInt(respawn));
				tb.setBaseTime(df.parse(timebase));
				tb.refresh(c);
				
				putTimebase(name, tb);
			}
			
			return (true);
		} finally {
			if (r != null) {
				r.close();
			}
		}
	}
	
	/**
	 * タイムベース設定を保存する。
	 * 
	 * @throws IOException 入出力エラー
	 */
	public void save() throws IOException {
		save(fileName);
	}
	
	/**
	 * タイムベース設定を保存する。
	 * 
	 * @param file 保存ファイル名
	 * @throws IOException 入出力エラー
	 */
	private void save(String file) throws IOException {
		
		PrintWriter w = null;
		try {
			/* 全ロケーションのタイムベースを保存 */
			w = new PrintWriter(new FileWriter(file));
			for (Entry<String, Timebase> e : timebaseMap.entrySet()) {
				StringBuffer s = new StringBuffer();
				s.append("\"" + e.getKey() + "\",");				/* ロケーション */
				s.append("\"" + e.getValue().getRespawn() + "\",");	/* リポップ間隔 */
				s.append("\"" + df.format(e.getValue().getBaseTime()) + "\"");	/* 基準時間 */
				w.println(s);
			}
		} finally {
			if (w != null) {
				w.close();
			}
		}
	}
}
