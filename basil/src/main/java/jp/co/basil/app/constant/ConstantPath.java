package jp.co.basil.app.constant;

/**
 * @author NOMOS) Yoshida
 * Controllerのパスを一元管理するための定数を集めたクラス。
 *
 */
public class ConstantPath {

	public static final String INDEX_STR_PATH = "index";
	public static final String INDEX_PATH = "/";
	public static final String LOGIN_PATH = "login";
	public static final String LOGOUT_PATH = "logout";
	public static final String ERROR_PATH = "error";
	public static final String WORKARCHIVELIST_PATH = "workArchiveList";
	public static final String DAILYARCHIVELIST_PATH = "dailyArchivementList";
	public static final String DOWNLOADPAGE_PATH = "downloadPage";
	public static final String MONTHLYARCHIVELIST_PATH = "monthlyArchivementList";
	public static final String WORKREGIST_PATH = "workRegist";
	public static final String DO_WORKREGIST_PATH = "doWorkRegist";
	public static final String ARCHIVEMENTLIST_PATH = "archivementList";
	public static final String ARCHIVEMENTREGIST_PATH = "archivementRegist";
	public static final String DO_ARCHIVEMENTREGIST_PATH = "doArchivementRegist";

	public static final String DOWNLOAD_PATH = "C:\\workSpace\\basil\\basil\\src\\main\\resources\\static\\download\\";


	/**
	 * ダウンロード用の相対パスを返す
	 * @param absolutePath
	 * @return
	 */
	public static String getRelaticePath(String fileName) {

		return ConstantPath.DOWNLOAD_PATH + fileName;

	}

}
