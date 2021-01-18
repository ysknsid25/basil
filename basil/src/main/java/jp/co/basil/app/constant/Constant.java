package jp.co.basil.app.constant;

/**
 * @author NOMOS) Yoshida
 * 変数などの定数名を管理する。
 *
 */
public class Constant {

	/** DB接続情報 */
	public static final String DEV_DB_URL = "jdbc:mysql://localhost/basil?useUnicode=true&characterEncoding=utf8";
	public static final String DEV_DB_IDPASS = "basil";
//	public static final String DEV_DB_URL = "jdbc:mysql://192.168.1.246/basilTest?useUnicode=true&characterEncoding=utf8";
//	public static final String DEV_DB_IDPASS = "basilTest";
	public static final String PROC_DB_URL = "jdbc:mysql://192.168.1.246/basil?useUnicode=true&characterEncoding=utf8";
	public static final String PROC_DB_IDPASS = "basil";


	/** 設定を開発・本番と切り分ける */
	public static final boolean IS_DEVELOP = true;

	/** セッション変数格納名 */
	public static final String SESSION_USER_NO = "userNo";

	/** MenuBarの状態を管理する変数名 */
	public static final String MENUBAR_STATUS = "menuStatus";

	/** 登録・更新処理の有無を表示管理を行う変数 */
	public static final String ISUPDATED = "isUpdated";
	public static final String ISREGIST = "isRegist";

	/** メッセージを送信したいHangout ChatのURL */
	public static final String CHATROOM_URL = "https://chat.googleapis.com/v1/spaces/AAAAAwRTf0k/messages?key=AIzaSyDdI0hCZtE6vySjMm-WEfRq3CPzqKqqsHI&token=YygmRIixJzyKFeyW1ASlhABFv8muS0M8VkPr9ctVo9k%3D";

	/** チェックボックスON */
	public static final String CHECKBOX_ON = "1";

	/** 日付書式 */
	public static final String DATE_FORMAT = "yyyy/MM/dd";
	public static final String DATE_FORMAT_HAIHUN = "yyyy-MM-dd";
	public static final String DATE_FORMAT_YEAR = "yyyy";
	public static final String DATE_FORMAT_MONTH = "MM";
	public static final String DATETIME_FOAMRT =  "yyyyMMddhhmmssSSS";

	/** ハイフン */
	public static final String HAIHUN = "-";

	/** スラッシュ */
	public static final String SLASH = "/";

	/** 空文字 */
	public static final String EMPTY_STR = "";

	/** 終了日が開始予定日よりも後かつ総工数の実績が予定総工数を超えている */
	public static final String ORVER_DATE_FUNCP = "★";

	/** 終了日が開始予定日よりも後 */
	public static final String ORVER_DATE = "○";

	/** 総工数の実績が予定総工数を超えている */
	public static final String ORVER_FUNCP = "●";

	/** FoamtId */
	public static final String F_REPORT_FORMAT = "XLS01"; //富士通進捗報告用

	/** 実績登録カードヘッダ */
	public static final String JISSEKI_TOUROKU = "実績登録";
	public static final String TOKUSYU_TOUROKU = "特殊登録";

	/** セレクトボックスに「すべて」の項目を追加する用 */
	public static final String ALL_SELECTED_CD = "999";
	public static final int ALL_SELECTED_VAL = 999;
	public static final String ALL_SELECTED_NAME = "全て";


	/**
	 * パラメーターから判断してチェックボックスの値を返す。<br>
	 * パラメーターに合わせて初期値をセットする。
	 * @param parameterVal リクエストパラメーターで受けた値
	 * @param initVal リクエストパラメーターがnullの場合の初期値
	 * @return
	 */
	public static String branchFlg(String parameterVal, String initVal) {

		if(parameterVal == null || parameterVal.isEmpty()) {

			return initVal;

		}

		return parameterVal;

	}


	/**
	 * 完了済みのものは含めない実績登録か、完了済みのもののみ選択できる特殊登録かでカードヘッダを切り分ける。
	 * @param isSpecial
	 * @return
	 */
	public static String getJissekiRegistHeager(boolean isSpecial) {

		if(isSpecial) {

			return TOKUSYU_TOUROKU;

		}

		return JISSEKI_TOUROKU;

	}


}
