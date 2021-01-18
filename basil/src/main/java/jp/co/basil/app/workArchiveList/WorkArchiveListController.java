package jp.co.basil.app.workArchiveList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.dailyArchivementList.ArchiveBean;
import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.monthlyArchivementList.MonthlyArcDbAccess;
import jp.co.basil.app.util.DateTimeUtil;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.util.SessionChecker;
import jp.co.basil.app.util.StringUtil;
import jp.co.basil.app.workUpdate.UserInfoBean;
import jp.co.basil.app.workUpdate.WorkRegistDbAccess;
import jp.co.basil.app.workUpdate.WorkUpdateController;

/**
 * @author NOMOS) Yoshida
 * to, from作業実績一覧のControllerです。
 *
 */
@Controller
public class WorkArchiveListController {

	@RequestMapping(path=ConstantPath.WORKARCHIVELIST_PATH)
	public String workArchiveList(HttpSession session, Model model, String hdContainFinishedFlg, String userNo) {

		try {

			SessionChecker.existSession(session);
			this.initDispWorkArchive(model, hdContainFinishedFlg, userNo);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.WORKARCHIVELIST_PATH;

	}


	/**
	 * 作業情報・実績一覧画面を表示するための準備を行う。
	 * @param model
	 * @param hdContainFinishedFlg
	 * @throws Exception
	 */
	public void initDispWorkArchive(Model model, String hdContainFinishedFlg, String userNo) throws Exception{

		String selectedUserNo = userNo;
		String chkBoxSt = Constant.branchFlg(hdContainFinishedFlg, Constant.CHECKBOX_ON);
		List<UserInfoBean> userInfoList = new WorkRegistDbAccess().getUserInfo();
		this.setAllUserAtFirst(userInfoList);
		List<WorkArchiveBean> list = new WorkArchiveDbAccess().getWorkInfo(chkBoxSt, selectedUserNo);
		model.addAttribute(WorkUpdateController.USERINFO, userInfoList);

		if(userNo != null) {

			model.addAttribute("selectedUsrNo", Integer.parseInt(selectedUserNo));

		}else {

			model.addAttribute("selectedUsrNo", Constant.ALL_SELECTED_VAL);

		}

		model.addAttribute("isContainFinished", chkBoxSt);
		model.addAttribute("workInfo", list);
		model.addAttribute("workInfoSize", list.size());
		model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(true, false, false, false, false));

	}


	/**
	 * ユーザーの選択肢の先頭に、「すべて」の選択肢を追加する。
	 * @param userInfoList
	 */
	private void setAllUserAtFirst(List<UserInfoBean> userInfoList) {

		UserInfoBean allUser = new UserInfoBean();

		allUser.setUserNo(Constant.ALL_SELECTED_VAL);
		allUser.setShimei(Constant.ALL_SELECTED_NAME);

		userInfoList.add(0, allUser);

	}


}
