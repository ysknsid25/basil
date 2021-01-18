package jp.co.basil.app.workUpdate;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.util.SessionChecker;
import jp.co.basil.app.util.StringUtil;
import jp.co.basil.app.workArchiveList.WorkArchiveListController;

/**
 * @author NOMOS) Yoshida
 * 作業情報登録のContoroller
 *
 */
@Controller
public class WorkUpdateController {

	public static final String USERINFO = "userInfo";
	private static final String WORKINFO = "workInfo";

	/**
	 * 作業情報登録の初期表示を担当する
	 * @param model
	 * @param workId
	 * @return
	 */
	@RequestMapping(path=ConstantPath.WORKREGIST_PATH)
	private String workRegist(HttpSession session, Model model, String workId) {

		//新規登録かどうか判断する
		boolean isRegist = workId == null;

		try {

			SessionChecker.existSession(session);
			this.pripareMove(model, false, isRegist, workId);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.WORKREGIST_PATH;

	}


	/**
	 * 作業情報登録の登録・更新・削除処理の入り口
	 * @param model
	 * @param formBean
	 * @return
	 */
	@RequestMapping(path=ConstantPath.DO_WORKREGIST_PATH)
	private String doWorkRegist(HttpSession session, Model model, WorkRegistForm formBean) {

		final String MITOUROKU_WORK = "0";

		//Springの仕様上、セッターを介してフォームの値をセットしてくれないため、わざわざ日付書式を書き換える必要がある。
		formBean.setBeginYMD(formBean.getBeginYMD());
		formBean.setEndYMD(formBean.getEndYMD());
		boolean isRegist = MITOUROKU_WORK.equals(formBean.getWorkId());
		boolean isDelete = formBean.getIsDelete().equals(Constant.CHECKBOX_ON);
		String retPath = "";

		try {

			SessionChecker.existSession(session);
			new WorkRegistDbAccess().execute(formBean, isRegist, isDelete);

			if(isRegist) {

				this.pripareMove(model, true, isRegist, "");
				retPath = ConstantPath.WORKREGIST_PATH;

			}else {

				WorkArchiveListController contrller = new WorkArchiveListController();
				contrller.initDispWorkArchive(model, "1", null);
				retPath = ConstantPath.WORKARCHIVELIST_PATH;

			}


		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return retPath;

	}


	/**
	 * ユーザー情報を取得する。
	 * @return
	 * @throws Exception
	 */
	private List<UserInfoBean> getUserInfo() throws Exception{

		return new WorkRegistDbAccess().getUserInfo();

	}


	/**
	 * 画面遷移の準備をする
	 * @param model
	 * @param isUpdate 更新時か？
	 * @param isRegist 新規登録か？
	 * @throws Exception
	 */
	private void pripareMove(Model model, boolean isUpdate, boolean isRegist, String workId) throws Exception{

		List<UserInfoBean> userInfoList = this.getUserInfo();
		model.addAttribute(USERINFO, userInfoList);
		model.addAttribute(WORKINFO, new WorkRegistDbAccess().getWorkInfo(workId));
		model.addAttribute(Constant.ISUPDATED, isUpdate);
		model.addAttribute(Constant.ISREGIST, isRegist);
		model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(false, false, false, true, false));

	}


}
