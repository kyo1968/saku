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
abstract public class BaseProperties {
	
	/**
	 * プロパティ
	 */
	protected Properties properties = new Properties();
	
	/**
	 * プロパティを設定する。
	 */
	abstract protected void loadProperties();
	
	/**
	 * プロパティを保存する。
	 */
	abstract protected void storeProperties();
	
	/**
	 * プロパティファイルを読み込む。
	 * 
	 * @param file プロパティファイル名
	 * @throws FileNotFoundException INI file error.
	 */
	public void load(String file) throws FileNotFoundException {
		load(new FileInputStream(new File(file))); 
	}
	
	/**
	 * プロパティファイルを読み込む。
	 * 
	 * @param is 入力ストリーム
	 */
	public void load(InputStream is) {
		
		try {
			/* プロパティファイルの読み込み */
			properties.load(is);
			
			/* プロパティの設定 */
			loadProperties();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * プロパティファイルを保存する。
	 * 
	 * @param file プロパティファイル名
	 * @throws FileNotFoundException INI file error.
	 */
	public void save(String file) throws FileNotFoundException {
		save(new FileOutputStream(new File(file)));
	}
	
	/**
	 * プロパティファイルを保存する。
	 * 
	 * @param os 出力ストリーム
	 */
	public void save(OutputStream os) {
		try {
			/*  プロパティの保存 */
			storeProperties();
			
			/* プロパティファイルの保存 */
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
