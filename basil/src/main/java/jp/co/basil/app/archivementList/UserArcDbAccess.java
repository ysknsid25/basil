package jp.co.basil.app.archivementList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.dailyArchivementList.ArchiveBean;
import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * DBへの問い合わせを提供します
 *
 */
public class UserArcDbAccess extends DBAccess {


	/**
	 * コンストラクタ
	 */
	protected UserArcDbAccess() {
	}


	/**
	 * ログインユーザーの月別工数を取得する。
	 * @param targetYear 取得対象の年
 	 * @param targetMonth 取得対象の月
	 * @return
	 * @throws Exception
	 */
	protected List<ArchiveBeanExt> getUserArcInfo(String targetYear, String targetMonth, String userNo) throws Exception{

		return this.getColValues(this.executeSelect(this.getSQL(targetYear, targetMonth, userNo)));

	}


	/**
	 * ユーザーに登録された作業情報一覧を取得します。
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	protected List<UserWorkInfoBean> getUserWorkInfo(String userNo) throws Exception{

		return this.getUserWorkInfo(this.executeSelect(this.getSQL(userNo)));

	}


	/**
	 * 月別工数の取得対象となる日付を取得して値をDBからもらいます。
	 * @param targetDay 取得対象の日付
	 * @return
	 */
	private String getSQL(String targetYear, String targetMonth, String userNo) {

		String targetYM = targetYear + Constant.SLASH + targetMonth;
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("REGISTED_DAY, ");
			sb.append("WORK_ID, ");
			sb.append("WORK_NO,");
			sb.append("WORK_NM, ");
			sb.append("FUNCP ");
		sb.append("from ");
			sb.append("DAILY_ARC_VIW ");
		sb.append("where ");
			sb.append("REGISTED_DAY like '" + targetYM + "%'");
			sb.append("and ");
			sb.append("USER_NO = '" + userNo + "'");
		sb.append("order by ");
			sb.append("REGISTED_DAY, ");
			sb.append("WORK_NO ");

		return sb.toString();

	}


	/**
	 * 作業情報一覧を取得するためのSQL
	 * @param containFinishedFlg
	 * @return
	 */
	protected String getSQL(String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("main.WORK_NO, ");
			sb.append("main.WORK_NM, ");
			sb.append("main.PLAN_BEGIN_YMD, ");
			sb.append("main.PLAN_END_YMD, ");
			sb.append("arcday.BEGIN_YMD, ");
			sb.append("arcday.END_YMD, ");
			sb.append("main.TOTAL_FUNCP, ");
			sb.append("total.ARC_TOTAL_FUNCP ");
		sb.append("from ");
			sb.append("(");
				sb.append("select ");
					sb.append("u.SHIMEI, ");
					sb.append("w.* ");
				sb.append("from ");
					sb.append("WORK_TBL w ");
					sb.append("INNER JOIN ");
					sb.append("USER_TBL u ");
					sb.append("ON ");
					sb.append("w.USER_NO = u.USER_NO ");
			sb.append(") main ");
			sb.append("LEFT JOIN ");
			sb.append("ARC_TOTALFUNCP_VIW total ");
			sb.append("ON ");
			sb.append("main.WORK_ID = total.WORK_ID ");
			sb.append("and ");
			sb.append("main.USER_NO = total.USER_NO ");
			sb.append("LEFT JOIN ");
			sb.append("ACHIEVE_DAY_TBL arcday ");
			sb.append("ON ");
			sb.append("main.WORK_ID = arcday.WORK_ID ");
			sb.append("and ");
			sb.append("main.USER_NO = arcday.USER_NO ");

		sb.append("where ");
			sb.append("(arcday.FINISH_KBN is null or arcday.FINISH_KBN in ('0', '1')) ");
		sb.append("and ");
			sb.append("main.USER_NO = '" + userNo + "' ");
		sb.append("order by ");
			sb.append("main.PLAN_END_YMD desc ");

		return sb.toString();

	}


	/**
	 * 値を取り出す。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List<ArchiveBeanExt> getColValues(ResultSet rs) throws Exception{

		List<ArchiveBeanExt> retList = new ArrayList<ArchiveBeanExt>();
		ArchiveBeanExt tmpBean = null;

		while(rs.next()) {

			tmpBean = new ArchiveBeanExt();

			tmpBean.setRegistedDay(StringUtil.getString(rs.getString("REGISTED_DAY")));
			tmpBean.setHiddenRegistedDate(tmpBean.getRegistedDay().replaceAll(Constant.SLASH, Constant.EMPTY_STR));
			tmpBean.setWorkId(StringUtil.getString(rs.getString("WORK_ID")));
			tmpBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			tmpBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NM")));
			tmpBean.setFuncp(rs.getFloat("FUNCP"));

			retList.add(tmpBean);

		}

		this.disConnect();
		return retList;

	}

	/**
	 * DBから取得したユーザーの作業情報を取り出します。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List<UserWorkInfoBean> getUserWorkInfo(ResultSet rs) throws Exception{

		List<UserWorkInfoBean> retList = new ArrayList<UserWorkInfoBean>();

		UserWorkInfoBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new UserWorkInfoBean();

			tmpBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			tmpBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NM")));
			tmpBean.setPlanBeginDay(StringUtil.getString(rs.getString("PLAN_BEGIN_YMD")));
			tmpBean.setPlanEndDay(StringUtil.getString(rs.getString("PLAN_END_YMD")));
			tmpBean.setBeginDay(StringUtil.getString(rs.getString("BEGIN_YMD")));
			tmpBean.setEndDay(StringUtil.getString(rs.getString("END_YMD")));
			tmpBean.setPlanTotalFunpc(rs.getFloat("TOTAL_FUNCP"));
			tmpBean.setArcTotalFuncp(rs.getFloat("ARC_TOTAL_FUNCP"));

			retList.add(tmpBean);

		}

		this.disConnect();
		return retList;

	}

}
