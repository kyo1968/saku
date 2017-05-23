package saku;

import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * キー割り当て
 * 
 * @version 1.0
  */
public class KeyMapper {
	/**
	 * キーテーブル
	 */
	private static final int [] VK_TABLE =  {
			/* 数字キー */
			KeyEvent.VK_0,		KeyEvent.VK_1,		KeyEvent.VK_2,		KeyEvent.VK_3,		KeyEvent.VK_4,
			KeyEvent.VK_5,		KeyEvent.VK_6,		KeyEvent.VK_7,		KeyEvent.VK_8,		KeyEvent.VK_9,

			/* 数字キー */
			KeyEvent.VK_NUMPAD0,	KeyEvent.VK_NUMPAD1,	KeyEvent.VK_NUMPAD2,	KeyEvent.VK_NUMPAD3,	KeyEvent.VK_NUMPAD4,
			KeyEvent.VK_NUMPAD5,	KeyEvent.VK_NUMPAD6,	KeyEvent.VK_NUMPAD7,	KeyEvent.VK_NUMPAD8,	KeyEvent.VK_NUMPAD9,

//			/* アルファベットキー */
//			KeyEvent.VK_A,		KeyEvent.VK_B,		KeyEvent.VK_C,		KeyEvent.VK_D,		KeyEvent.VK_E,
//			KeyEvent.VK_F,		KeyEvent.VK_G,		KeyEvent.VK_H,		KeyEvent.VK_I,		KeyEvent.VK_J,
//			KeyEvent.VK_K,		KeyEvent.VK_L,		KeyEvent.VK_M,		KeyEvent.VK_N,		KeyEvent.VK_O,
//			KeyEvent.VK_P,		KeyEvent.VK_Q,		KeyEvent.VK_R,		KeyEvent.VK_S,		KeyEvent.VK_T,
//			KeyEvent.VK_U,		KeyEvent.VK_V,		KeyEvent.VK_W,		KeyEvent.VK_X,		KeyEvent.VK_Y,
//			KeyEvent.VK_Z,
//			
//			/* ファンクションキー */
//			KeyEvent.VK_F1,		KeyEvent.VK_F2,		KeyEvent.VK_F3,		KeyEvent.VK_F4,		KeyEvent.VK_F5,
//			KeyEvent.VK_F6,		KeyEvent.VK_F7,		KeyEvent.VK_F8,		KeyEvent.VK_F9,		KeyEvent.VK_F10,
//			KeyEvent.VK_F11,	KeyEvent.VK_F12
		};
	
	/**
	 * キーマップ
	 */
	private Map<String, Integer> keyMap = new LinkedHashMap<String, Integer>();
	
	/**
	 * インスタンス
	 */
	private static KeyMapper me = new KeyMapper();
	
	/**
	 * コンストラクタ
	 */
	private KeyMapper() {
		/* キーマップの初期化 */
		for (int i = 0; i < VK_TABLE.length; i++) {
			/* キーとコードで紐づけ */
			keyMap.put(KeyEvent.getKeyText(VK_TABLE[i]), VK_TABLE[i]);
		}
	}

	/**
	 * インスタンスを取得する。
	 * 
	 * @return インスタンス
	 */
	public static final KeyMapper getInstance() {
		return me;
	}
	
	/**
	 * キーマップトを取得する。
	 * 
	 * @return キーマップ
	 */
	public Set<String>getKeyText() {
		return keyMap.keySet();
	}
	
	/**
	 * キーテキストを取得する。
	 * 
	 * @param keyCode キーコード
	 * @return キーテキスト
	 */
	public String getKeyText(int keyCode) {
		return KeyEvent.getKeyText(keyCode);
	}
	
	/**
	 * キーコードを取得する．
	 * 
	 * @param text キーテキスト
	 * @return キーコード
	 */
	public int getKeyCode(String text) {
		Integer i = keyMap.get(text);
		if (i == null) {
			return -1;
		}
		return i;
	}
}
