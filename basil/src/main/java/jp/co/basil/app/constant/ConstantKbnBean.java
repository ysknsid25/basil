package jp.co.basil.app.constant;

import java.util.HashMap;

/**
 * @author NOMOS) Yoshida
 * 区分定義を記載した定数クラス
 *
 */
public class ConstantKbnBean {

	private static HashMap<String, String> customKbnMap = null;

	public static final String KBN_CD_CUSTOM_KAISYU = "2";
	public static final String KBN_CD_CUSTOM_SHINKI = "1";

	public static final String KBN_NM_CUSTOM_KAISYU = "改修";
	public static final String KBN_NM_CUSTOM_SHINKI = "新規";


	private static HashMap<String, String> finishKbnMap = null;

	public static final String KBN_CD_FINISH_BEFORE = "0";
	public static final String KBN_CD_FINISH_NOW = "1";
	public static final String KBN_CD_FINISH_AFTER = "2";

	public static final String KBN_NM_FINISH_BEFORE = "作業前";
	public static final String KBN_NM_FINISH_NOW = "作業中";
	public static final String KBN_NM_FINISH_AFTER = "完了";


	/**
	 * 改修区分を格納したMapを定義する。
	 * @return
	 */
	public static String getCustomKbn(String customKbn){

		if(customKbnMap == null) {

			customKbnMap = new HashMap<String, String>();
			customKbnMap.put(KBN_CD_CUSTOM_KAISYU, KBN_NM_CUSTOM_KAISYU);
			customKbnMap.put(KBN_CD_CUSTOM_SHINKI, KBN_NM_CUSTOM_SHINKI);

		}

		return customKbnMap.get(customKbn);

	}


	/**
	 * 終了区分を格納したMapを定義する。
	 * @param finishKbn
	 * @return
	 */
	public static String getFinishKbn(String finishKbn) {

		if(finishKbnMap == null) {

			finishKbnMap = new HashMap<String, String>();
			finishKbnMap.put(KBN_CD_FINISH_BEFORE, KBN_NM_FINISH_BEFORE);
			finishKbnMap.put(KBN_CD_FINISH_NOW, KBN_NM_FINISH_NOW);
			finishKbnMap.put(KBN_CD_FINISH_AFTER, KBN_NM_FINISH_AFTER);

		}

		return finishKbnMap.get(finishKbn);

	}

}
