package jp.co.basil.app.monthlyArchivementList;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.dailyArchivementList.ArchiveBean;
import jp.co.basil.app.dailyArchivementList.DailyArcDbAccess;
import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.util.DateTimeUtil;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.util.SessionChecker;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * 月別実績一覧のコントローラー
 *
 */
@Controller
public class MonthlyArchivementListController {

	@RequestMapping(path=ConstantPath.MONTHLYARCHIVELIST_PATH)
	private String monthlyArchivementList(HttpSession session, Model model, String year, String month) {


		try {

			SessionChecker.existSession(session);
			String targetYear = DateTimeUtil.convYearOrMonth(year, Constant.DATE_FORMAT_YEAR);
			String targetMonth = DateTimeUtil.convYearOrMonth(month, Constant.DATE_FORMAT_MONTH);

			this.initDisp(model, targetYear, targetMonth);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.MONTHLYARCHIVELIST_PATH;

	}


	/**
	 * 画面表示の準備を行う
	 * @param model
	 * @param targetYear
	 * @param targetMonth
	 * @throws Exception
	 */
	private void initDisp(Model model, String targetYear, String targetMonth) throws Exception{

		MonthlyArcDbAccess dbAccess = new MonthlyArcDbAccess();
		List<ArchiveBean> list = dbAccess.getDailyArcInfo(targetYear, targetMonth);
		List<ArcByWorkNoBean> arcByWorkNoList = dbAccess.getArcByWorkNo();
		List<ArcByWorkNoBean> finishedWorkInfoList = dbAccess.getFinishedWorkInfo();
		model.addAttribute("thisYear", Integer.parseInt(DateTimeUtil.convYearOrMonth("", Constant.DATE_FORMAT_YEAR)));
		model.addAttribute("selectedYear", Integer.parseInt(targetYear));
		model.addAttribute("selectedMobth", targetMonth);
		model.addAttribute("arcByWorkNoInfo", arcByWorkNoList);
		model.addAttribute("arcByWorkNoInfoSize", arcByWorkNoList.size());
		model.addAttribute("finishedWorkInfo", finishedWorkInfoList);
		model.addAttribute("finishedWorkInfoSize", finishedWorkInfoList.size());
		model.addAttribute("monthlyInfo", list);
		model.addAttribute("monthlyInfoSize", list.size());
		model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(false, false, true, false, false));

	}

}
