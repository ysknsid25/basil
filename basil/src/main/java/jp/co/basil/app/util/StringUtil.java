package jp.co.basil.app.util;

/**
 * @author NOMOS) Yoshida
 * String系の汎用クラスを提供
 *
 */
public class StringUtil {

	/**
	 * 空文字判定をして値を返す。
	 * @param str nullならば空文字<br>
	 * @return
	 */
	public static String getString(String str) {

		if(str == null || str.isEmpty()) {

			return "";

		}

		return str;

	}


	/**
	 * 例外の内容をメッセージ化して返します。
	 * @param errMessage
	 * @return
	 */
	public static String makeErrorMessage(String errMessage) {

		return "エラー内容: " + errMessage;

	}

}
