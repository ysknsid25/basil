package jp.co.basil.app.archivementUpdate;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.archivementList.ArchivementListController;
import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.db.DBexception;
import jp.co.basil.app.login.UserInfoBean;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.util.DateTimeUtil;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.util.SessionChecker;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * 作業実績登録のコントローラー
 *
 */
@Controller
public class ArchivementUpdateController {

	@RequestMapping(path=ConstantPath.ARCHIVEMENTREGIST_PATH)
	private String archivementRegist(HttpSession session, Model model, String specialFlg) {

		try {

			SessionChecker.existSession(session);
			UserInfoBean tmpBean = (UserInfoBean)session.getAttribute(Constant.SESSION_USER_NO);
			String userNo = tmpBean.getUsetNo();
			String date = DateTimeUtil.convYearOrMonth("", Constant.DATE_FORMAT_HAIHUN);
			ArchivementDbAccess dbAccess = new ArchivementDbAccess(specialFlg);
			List<WorkInfoBean> workInfoList = dbAccess.getWorkInfo(userNo);

			model.addAttribute("isSpecial", dbAccess.isSpecial());
			model.addAttribute("isExistData", workInfoList.size() > 0);
			model.addAttribute("title", Constant.getJissekiRegistHeager(dbAccess.isSpecial()));
			model.addAttribute("date", date);
			model.addAttribute("workInfoList", workInfoList);
			model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(false, false, false, false, true));

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.ARCHIVEMENTREGIST_PATH;

	}


	@RequestMapping(path=ConstantPath.DO_ARCHIVEMENTREGIST_PATH)
	private String archivementRegist(HttpSession session, Model model, ArchiveForm formBean, String isDelete, String isFinished) {

		try {

			SessionChecker.existSession(session);
			UserInfoBean tmpBean = (UserInfoBean)session.getAttribute(Constant.SESSION_USER_NO);
			String userNo = tmpBean.getUsetNo();

			formBean.setDelete(isDelete);
			formBean.setFinished(isFinished);
			formBean.setTargetDay(formBean.getTargetDay());

			//登録・削除・更新処理
			int resultCd = new ArchivementDbAccess().execute(formBean, userNo);

			//処理失敗時は例外を吐く
			if(resultCd < 0) {

				throw new DBexception("DB処理に失敗しました。再度ログインしてください。");

			}

			new ArchivementListController().initDisp(session, model, "", "");

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.ARCHIVEMENTLIST_PATH;

	}


}
