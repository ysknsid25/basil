package jp.co.basil.app.dailyArchivementList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.fileManager.FileDownloader;
import jp.co.basil.app.menuBar.MenuBarStatusBean;
import jp.co.basil.app.notifier.Notifier;
import jp.co.basil.app.util.DateTimeUtil;
import jp.co.basil.app.util.ErrorProcess;
import jp.co.basil.app.util.SessionChecker;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * 日別工数のController
 *
 */

@Controller
public class DailyArchivementController extends FileDownloader{

	private static final String BUTTON_NOTIFICATION = "02";

	private DailyArcDbAccess dbAccesser = new DailyArcDbAccess();

	@Autowired
    ResourceLoader resourceLoader;


	@RequestMapping(path=ConstantPath.DAILYARCHIVELIST_PATH)
	private String dailyArchivementList(HttpSession session, Model model, String flatpickrDate, String buttonNo) {

		try {

			SessionChecker.existSession(session);
			String date = DateTimeUtil.convDate(flatpickrDate);

			//通知ボタン押下時の処理
			if(BUTTON_NOTIFICATION.equals(buttonNo)) {

				this.notice(date);

			}

			this.initDispDailyArc(model, date);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

		return ConstantPath.DAILYARCHIVELIST_PATH;

	}


	@RequestMapping(path=ConstantPath.DOWNLOADPAGE_PATH, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	private String dailyArchivementList(HttpSession session, HttpServletResponse response, Model model, String targetDate) {

		try {

			SessionChecker.existSession(session);
			String date = DateTimeUtil.convDate(targetDate);
			String fileName = this.outPutFormat(model, date);
			String outPutPath = ConstantPath.getRelaticePath(fileName);

			this.download(response, outPutPath, fileName);

		}catch(Exception e) {

			return ErrorProcess.doErrorProcess(model, e.toString());

		}

	        return null;

	}


	/**
	 * 画面表示の準備をする。
	 * @param model
	 * @param flatpickrDate
	 * @throws Exception
	 */
	private void initDispDailyArc(Model model, String date) throws Exception{

		boolean isMinyuryoku = this.dbAccesser.isMinyuryoku(date);
		List<ArchiveBean> list = this.dbAccesser.getDailyArcInfo(date);
		model.addAttribute("minyuryokuAri", isMinyuryoku);
		model.addAttribute("selectedDay", date);
		model.addAttribute("dailyInfo", list);
		model.addAttribute("dailyInfoSize", list.size());
		model.addAttribute(Constant.MENUBAR_STATUS, new MenuBarStatusBean(false, true, false, false, false));

	}


	/**
	 * 通知処理を行う。
	 * @param date
	 * @throws Exception
	 */
	private void notice(String date) throws Exception{

		String noticeMessage = this.dbAccesser.getNoticeMessage(date);

		if(!noticeMessage.isEmpty()) {

			new Notifier(noticeMessage, Constant.CHATROOM_URL).notice();

		}

	}


	/**
	 *	報告フォーマットの出力を行う
	 * @param date
	 * @throws Exception
	 */
	private String outPutFormat(Model model, String date) throws Exception{

		DailyReportMaker formatMaker = new DailyReportMaker(Constant.F_REPORT_FORMAT, date);

		ArrayList<HashMap<String, String>> totalValueList = this.dbAccesser.getFormatData(date);
		ArrayList<HashMap<String, String>> dailyArcMapList = this.dbAccesser.getDailyTotalFuncp(date);

		return formatMaker.execute(totalValueList, dailyArcMapList);

	}


}
