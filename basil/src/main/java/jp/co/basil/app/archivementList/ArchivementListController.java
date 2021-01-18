package jp.co.basil.app.archivementList;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.login.UserInfoBean;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.util.DateTimeUtil;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.util.SessionChecker;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS)Yoshida
 * 実績照会のコントローラ
 *
 */
@Controller
public class ArchivementListController {

	@RequestMapping(path=ConstantPath.ARCHIVEMENTLIST_PATH)
	private String workRegist(HttpSession session, Model model, String year, String month) {

		try {

			SessionChecker.existSession(session);
			this.initDisp(session, model, year, month);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.ARCHIVEMENTLIST_PATH;

	}


	/**
	 * 画面表示の準備を行います。
	 * @param model
	 * @param year
	 * @param month
	 * @throws Exception
	 */
	public void initDisp(HttpSession session, Model model, String year, String month) throws Exception{

		UserArcDbAccess dbAccess = new UserArcDbAccess();
		UserInfoBean tmpBean = (UserInfoBean)session.getAttribute(Constant.SESSION_USER_NO);
		String userNo = tmpBean.getUsetNo();

		String targetYear = DateTimeUtil.convYearOrMonth(year, Constant.DATE_FORMAT_YEAR);
		String targetMonth = DateTimeUtil.convYearOrMonth(month, Constant.DATE_FORMAT_MONTH);
		List<ArchiveBeanExt> list = dbAccess.getUserArcInfo(targetYear, targetMonth, userNo);
		List<UserWorkInfoBean> userWorkList = dbAccess.getUserWorkInfo(userNo);

		/**
		 * TODO:将来的に別プロジェクトの管理も行う必要が生じた場合、この段階でセッションに格納されたプロジェクトIDを取得し、<br>
		 * 特殊登録が許容されているかをDBに問い合わせる。
		 *
		 */
		boolean isAllowSpecialRegist = true;

		model.addAttribute("isAllowSpecialRegist", isAllowSpecialRegist);
		model.addAttribute("selectedYear", Integer.parseInt(targetYear));
		model.addAttribute("thisYear", Integer.parseInt(DateTimeUtil.convYearOrMonth("", Constant.DATE_FORMAT_YEAR)));
		model.addAttribute("selectedYear", Integer.parseInt(targetYear));
		model.addAttribute("selectedMobth", targetMonth);
		model.addAttribute("arcInfo", list);
		model.addAttribute("arcInfoSize", list.size());
		model.addAttribute("workInfo", userWorkList);
		model.addAttribute("workInfoSize", userWorkList.size());
		model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(false, false, false, false, true));

	}

}
