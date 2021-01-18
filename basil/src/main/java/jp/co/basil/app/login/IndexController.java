package jp.co.basil.app.login;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.logger.LoggerSingleton;
import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.workArchiveList.WorkArchiveListController;

/**
 * @author Yoshida
 * to, fromログイン画面へのURLを
 *
 */
@Controller
public class IndexController {


	/** ログインの結果、ユーザーアカウントが存在する場合に遷移先ページを表示するためのコントローラー */
	private WorkArchiveListController walController = new WorkArchiveListController();


	/**
	 * ログイン画面の表示
	 * @param model
	 * @return
	 */
	@RequestMapping(path=ConstantPath.INDEX_PATH)
	private String index() {

		return ConstantPath.INDEX_STR_PATH;

	}


	/**
	 * ログイン処理
	 * @param session
	 * @param model
	 * @param userId
	 * @param pass
	 * @return
	 */
	@RequestMapping(path=ConstantPath.LOGIN_PATH)
	private String login(HttpSession session, Model model, String userId, String password) {

		try {

			UserInfoBean userInfoBean = new LoginDbAccess().doLogin(userId, password);

			if(userInfoBean == null) {

				model.addAttribute("isError", true);
				model.addAttribute("errorMessage", "ユーザーIDまたはパスワードが違います。");
				return "index";

			}

			session.setAttribute(Constant.SESSION_USER_NO, userInfoBean);

			LoggerSingleton.getInstance().getLogger().info("ユーザーID:" + userInfoBean.getUsetNo() + "がログインしました。");
			model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(true, false, false, false, false));
			return walController.workArchiveList(session, model, "", null);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

	}

}
