package jp.co.basil.app.monthlyArchivementList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.dailyArchivementList.ArchiveBean;
import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * 月別の工数をDBから取得する。
 *
 */
public class MonthlyArcDbAccess extends DBAccess {


	/**
	 * コンストラクタ
	 */
	protected MonthlyArcDbAccess() {
	}


	/**
	 * 月別工数を取得する。
	 * @param targetDay 取得対象の日付
	 * @return
	 * @throws Exception
	 */
	protected List<ArchiveBean> getDailyArcInfo(String targetYear, String targetMonth) throws Exception{

		List<ArchiveBean> retList = this.getColValues(this.executeSelect(this.getSQL(targetYear, targetMonth)));
		this.disConnect();
		return retList;

	}


	/**
	 * 月別工数の取得対象となる日付を取得して値をDBからもらいます。
	 * @param targetDay 取得対象の日付
	 * @return
	 */
	private String getSQL(String targetYear, String targetMonth) {

		String targetYM = targetYear + Constant.SLASH + targetMonth;
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("base.SHIMEI, ");
			sb.append("base.WORK_NO, ");
			sb.append("base.WORK_NM,");
			sb.append("w.TOTAL_FUNCP as \"PLAN_TOTAL_FUNCP\", ");
			sb.append("base.TOTAL_FUNCP ");
		sb.append("from ");
			sb.append("(select ");
				sb.append("WORK_ID, ");
				sb.append("SHIMEI, ");
				sb.append("WORK_NO, ");
				sb.append("WORK_NM,");
				sb.append("SUM(FUNCP) as TOTAL_FUNCP ");
			sb.append("from ");
				sb.append("DAILY_ARC_VIW ");
			sb.append("where ");
				sb.append("REGISTED_DAY like '" + targetYM + "%' ");
			sb.append("group by ");
				sb.append("WORK_ID, ");
				sb.append("USER_NO, ");
				sb.append("SHIMEI, ");
				sb.append("WORK_NO, ");
				sb.append("WORK_NM ");
			sb.append(") base ");
			sb.append("LEFT JOIN  ");
			sb.append("WORK_TBL w ");
			sb.append("ON ");
			sb.append("base.WORK_ID = w.WORK_ID ");
		sb.append("order by ");
			sb.append("base.SHIMEI ");

		return sb.toString();

	}


	/**
	 * 値を取り出す。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List<ArchiveBean> getColValues(ResultSet rs) throws Exception{

		List<ArchiveBean> retList = new ArrayList<ArchiveBean>();
		ArchiveBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new ArchiveBean();

			tmpBean.setShimei(StringUtil.getString(rs.getString("SHIMEI")));
			tmpBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			tmpBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NM")));
			tmpBean.setFuncp(rs.getFloat("TOTAL_FUNCP"));
			tmpBean.setPlanFuncp(rs.getFloat("PLAN_TOTAL_FUNCP"));

			retList.add(tmpBean);

		}

		this.disConnect();
		return retList;

	}


	/**
	 * 案件別の総工数を取得するための入り口
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<ArcByWorkNoBean> getArcByWorkNo() throws Exception{

		return this.getArcByWorkNo(this.executeSelect(this.getArcByWorkNoSQL()));

	}


	/**
	 * 案件別の総工数を取得するためのSQL
	 * @return
	 */
	private String getArcByWorkNoSQL() {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("base.WORK_NO, ");
			sb.append("base.TOTAL_FUNCP, ");
			sb.append("tp.TOTAL_FUNCP as \"PLAN_TOTAL_FUNCP\", ");
			sb.append("truncate((base.TOTAL_FUNCP/tp.TOTAL_FUNCP)*100, 2) as 'SHINTYOKURITU' ");
		sb.append("from ");
			sb.append("(select ");
				sb.append("WORK_NO, ");
				sb.append("SUM(FUNCP) as TOTAL_FUNCP ");
			sb.append("from ");
				sb.append("DAILY_ARC_VIW ");
			sb.append("group by ");
				sb.append("WORK_NO");
			sb.append(")base ");
			sb.append("INNER JOIN ");
			sb.append("TOTAL_FUNCP_VIW tp ");
			sb.append("ON ");
			sb.append("base.WORK_NO = tp.WORK_NO ");
		sb.append("order by ");
			sb.append("base.WORK_NO");

		return sb.toString();

	}


	/**
	 * DBから取得した値を取り出す。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private ArrayList<ArcByWorkNoBean> getArcByWorkNo(ResultSet rs) throws Exception{

		ArrayList<ArcByWorkNoBean> retList = new ArrayList<ArcByWorkNoBean>();
		ArcByWorkNoBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new ArcByWorkNoBean();

			tmpBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			tmpBean.setPlanTotalFuncp(StringUtil.getString(rs.getString("PLAN_TOTAL_FUNCP")));
			tmpBean.setArcTotalFuncp(StringUtil.getString(rs.getString("TOTAL_FUNCP")));
			tmpBean.setShintyokuritu(StringUtil.getString(rs.getString("SHINTYOKURITU")));

			retList.add(tmpBean);

		}

		this.disConnect();

		return retList;

	}


	/**
	 * 完了済みの作業情報を取得してreturnするための入り口
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<ArcByWorkNoBean> getFinishedWorkInfo() throws Exception{

		return this.getFinishedWorkInfo(this.executeSelect(this.getFinishedWorkInfoSQL()));

	}


	/**
	 * 終了済みの案件情報一覧を取得する。<br>
	 * @return
	 */
	private String getFinishedWorkInfoSQL() {

		StringBuffer sb = new StringBuffer();

		sb.append("SELECT ");
			sb.append("arc.WORK_NO, ");
			sb.append("arc.WORK_NM, ");
			sb.append("arc.SHIMEI, ");
			sb.append("wk.PLAN_END_YMD, ");
			sb.append("arcSt.END_YMD, ");
			sb.append("wk.TOTAL_FUNCP, ");
			sb.append("arc.ARC_FUNCP, ");
			sb.append("truncate((arc.ARC_FUNCP/wk.TOTAL_FUNCP)*100, 2) as 'SHINTYOKURITU' ");
		sb.append("FROM ");
			sb.append("(SELECT ");
				sb.append("dav.USER_NO, ");
				sb.append("dav.SHIMEI, ");
				sb.append("dav.WORK_ID, ");
				sb.append("dav.WORK_NO, ");
				sb.append("dav.WORK_NM, ");
				sb.append("SUM(dav.FUNCP) AS 'ARC_FUNCP' ");
			sb.append("FROM ");
				sb.append("DAILY_ARC_VIW dav ");
			sb.append("GROUP BY ");
				sb.append("dav.USER_NO, ");
				sb.append("dav.SHIMEI, ");
				sb.append("dav.WORK_ID, ");
				sb.append("dav.WORK_NO, ");
				sb.append("dav.WORK_NM ");
			sb.append(") arc ");
			sb.append("INNER JOIN ");
			sb.append("(SELECT ");
				sb.append("WORK_ID, ");
				sb.append("PLAN_END_YMD, ");
				sb.append("TOTAL_FUNCP ");
			sb.append("FROM ");
				sb.append("WORK_TBL ");
			sb.append(") wk ");
			sb.append("ON ");
			sb.append("arc.WORK_ID = wk.WORK_ID ");
			sb.append("INNER JOIN ");
			sb.append("(SELECT ");
				sb.append("WORK_ID, ");
				sb.append("END_YMD ");
			sb.append("FROM ");
				sb.append("achieve_day_tbl ");
			sb.append("WHERE ");
				sb.append("FINISH_KBN = '2' ");
			sb.append(") arcSt ");
		sb.append("ON ");
		sb.append("wk.WORK_ID = arcSt.WORK_ID ");
		sb.append("ORDER BY ");
		sb.append("arcSt.END_YMD desc, ");
		sb.append("arc.SHIMEI");

		return sb.toString();

	}


	/**
	 * DBから取得した完了済みの実績を取り出して格納する。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private ArrayList<ArcByWorkNoBean> getFinishedWorkInfo(ResultSet rs) throws Exception{

		ArrayList<ArcByWorkNoBean> retList = new ArrayList<ArcByWorkNoBean>();
		ArcByWorkNoBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new ArcByWorkNoBean();

			tmpBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			tmpBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NM")));
			tmpBean.setTantosha(StringUtil.getString(rs.getString("SHIMEI")));
			tmpBean.setPlanEndYmd(StringUtil.getString(rs.getString("PLAN_END_YMD")));
			tmpBean.setEndYmd(StringUtil.getString(rs.getString("END_YMD")));
			tmpBean.setPlanTotalFuncp(StringUtil.getString(rs.getString("TOTAL_FUNCP")));
			tmpBean.setArcTotalFuncp(StringUtil.getString(rs.getString("ARC_FUNCP")));
			tmpBean.setShintyokuritu(StringUtil.getString(rs.getString("SHINTYOKURITU")));

			retList.add(tmpBean);

		}

		this.disConnect();

		return retList;

	}


}
