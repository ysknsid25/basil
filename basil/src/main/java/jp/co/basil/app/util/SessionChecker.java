package jp.co.basil.app.util;

import javax.servlet.http.HttpSession;

import jp.co.basil.app.constant.Constant;

/**
 * @author NOMOS) Yoshida
 * セッションチェックを行う。
 *
 */
public class SessionChecker {

	public static final String BOOL_IS_ERROR = "hadErrorMessage";
	public static final String ERR_MESSAGE = "errMessage";

	public static void existSession(HttpSession session) throws Exception{

		if(session.getAttribute(Constant.SESSION_USER_NO) == null) {

			throw new Exception("セッション切れです。");

		}

	}

}
