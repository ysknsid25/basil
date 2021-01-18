package jp.co.basil.app.util;

import org.springframework.ui.Model;

import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.logger.LoggerSingleton;

/**
 * @author NOMOS) Yoshida
 * 例外発生時の処理を担当します
 *
 */
public class ErrorProcess {

	/**
	 * 例外時の具体的な処理
	 * @param model
	 * @param errMessage
	 * @return
	 */
	public static String doErrorProcess(Model model, String errMessage) {

		LoggerSingleton.getInstance().getLogger().error(errMessage);
		model.addAttribute(SessionChecker.BOOL_IS_ERROR, true);
		model.addAttribute(SessionChecker.ERR_MESSAGE, StringUtil.makeErrorMessage(errMessage));
		return ConstantPath.ERROR_PATH;

	}

}
