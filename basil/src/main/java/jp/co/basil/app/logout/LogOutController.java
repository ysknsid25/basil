package jp.co.basil.app.logout;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.logger.LoggerSingleton;
import jp.co.basil.app.login.UserInfoBean;

/**
 * @author NOMOS) Yoshida
 * ログアウト処理を担当します。
 *
 */
@Controller
public class LogOutController {

	/**
	 * セッションを破棄して、ログイン画面の表示
	 * @param model
	 * @return
	 */
	@RequestMapping(path=ConstantPath.LOGOUT_PATH)
	private String logout(HttpSession session) {

		UserInfoBean userInfoBean = (UserInfoBean)session.getAttribute(Constant.SESSION_USER_NO);

		if(userInfoBean != null) {

			LoggerSingleton.getInstance().getLogger().info("ユーザーID:" + userInfoBean.getUsetNo() + "がログアウトしました。");

		}

		session.invalidate();

		return ConstantPath.INDEX_STR_PATH;

	}

}
