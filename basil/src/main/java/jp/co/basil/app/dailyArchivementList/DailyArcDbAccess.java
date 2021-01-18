package jp.co.basil.app.dailyArchivementList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * DBへの問い合わせを提供します
 *
 */
public class DailyArcDbAccess extends DBAccess {


	/**
	 * コンストラクタ
	 */
	protected DailyArcDbAccess() {
	}


	/**
	 * 日別工数を取得する。
	 * @param targetDay 取得対象の日付
	 * @return
	 * @throws Exception
	 */
	protected List<ArchiveBean> getDailyArcInfo(String targetDay) throws Exception{

		List<ArchiveBean> retList = this.getColValues(this.executeSelect(this.getSQL(targetDay)));
		this.disConnect();
		return retList;

	}


	/**
	 * 日別工数の取得対象となる日付を取得して値をDBからもらいます。
	 * @param targetDay 取得対象の日付
	 * @return
	 */
	private String getSQL(String targetDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("SHIMEI, ");
		sb.append("WORK_NO, ");
		sb.append("WORK_NM,");
		sb.append("FUNCP ");
		sb.append("from ");
		sb.append("DAILY_ARC_VIW ");
		sb.append("where ");
		sb.append("REGISTED_DAY = '" + targetDay + "'");
		sb.append("order by ");
		sb.append("SHIMEI ");

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
			tmpBean.setFuncp(rs.getFloat("FUNCP"));

			retList.add(tmpBean);

		}

		return retList;

	}


	/**
	 * 通知用のメッセージを取得します。
	 * @param date 集計対象日
	 * @return 通知用のメッセージ。通知対象がいなければ空文字を返す。
	 * @throws Exception
	 */
	protected String getNoticeMessage(String date) throws Exception{

		String retMessage = this.getNoticeMessage(this.executeSelect(this.getNoticeTargetSQL(date)), date);
		this.disConnect();
		return retMessage;

	}


	/**
	 * 通知対象者が存在するかを取得します。
	 * @param date
	 * @return
	 * @throws Exception
	 */
	protected boolean isMinyuryoku(String date) throws Exception{

		return this.isMinyuryokusya(this.executeSelect(this.getNoticeTargetSQL(date)));

	}


	/**
	 * 実績を入力していないユーザーの氏名を取得するためのSQL
	 * @param date
	 * @return
	 */
	private String getNoticeTargetSQL(String date) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("u.SHIMEI ");
		sb.append("from ");
			sb.append("USER_TBL u ");
				sb.append("LEFT JOIN ");
			sb.append("(");
				sb.append("select ");
					sb.append("USER_NO ");
				sb.append("from ");
					sb.append("DAILY_ARC_VIW ");
				sb.append("where ");
					sb.append("REGISTED_DAY = '" + date + "'");
			sb.append(") a ");
				sb.append("ON ");
					sb.append("a.USER_NO = u.USER_NO ");
		sb.append("where ");
			sb.append("a.USER_NO is null ");
		sb.append("and ");
			sb.append("u.NOTIFICATION = '1'");

		return sb.toString();

	}


	/**
	 * 未入力者がいるかいないか
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private boolean isMinyuryokusya(ResultSet rs) throws Exception{

		boolean isMinyuryoku = !rs.next();
		this.disConnect();
		return isMinyuryoku;

	}


	/**
	 * DBから取得した情報をもとに、通知用のメッセージを作成します。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private String getNoticeMessage(ResultSet rs, String date) throws Exception{

		StringBuffer sb = new StringBuffer();
		String retMessage = "";

		while(rs.next()) {

			sb.append(StringUtil.getString(rs.getString("SHIMEI")));
			sb.append("さん");
			sb.append(System.lineSeparator());

		}

		if(sb.length() > 0) {

			sb.append(date + "の実績が未入力のようですので、お手すきの際に入力をお願いします。");
			retMessage = sb.toString();

		}

		return retMessage;

	}


	/**
	 * F報告用フォーマットデータを取得するための入り口
	 * @param targetDay
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<HashMap<String, String>> getFormatData(String targetDay) throws Exception{

		HashMap<String, String> totalArcByFuncpList = this.getTotalArcByWorkNo(this.executeSelect(this.getTotalArcByWorkNo(targetDay)));

		return this.getFormatDataColVal(this.executeSelect(this.getFormatDataSQL(targetDay)), totalArcByFuncpList);

	}


	/**
	 * 指定した日付までの案件番号ごとの実績工数を集計する。
	 * @param targetDay
	 * @return
	 */
	private String getTotalArcByWorkNo(String targetDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("main.WORK_NO, ");
		sb.append("sum(main.TOTAL_FUNCP) as \"TOTAL_FUNCP\"");
		sb.append("from ");
		sb.append("(select a. * ,");
		sb.append("w.WORK_NO ");
		sb.append("from (select WORK_ID, ");
		sb.append("USER_NO, ");
		sb.append("SUM(FUNCP) as TOTAL_FUNCP ");
		sb.append("from ACHIEVE_TBL ");
		sb.append("where REGISTED_YMD <= '" + targetDay + "' ");
		sb.append("group by WORK_ID, USER_NO) a ");
		sb.append("INNER JOIN WORK_TBL w ");
		sb.append("ON a.WORK_ID = w.WORK_ID) main ");
		sb.append("where ");
		sb.append("WORK_NO NOT LIKE 'WORK%' ");
		sb.append("group by main.WORK_NO");

		return sb.toString();

	}


	/**
	 * DBから取得した案件番号ごとの総工数を取り出してMapに格納
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private HashMap<String, String> getTotalArcByWorkNo(ResultSet rs) throws Exception{

		HashMap<String, String> totalArcByWorkNoMap = new HashMap<String, String>();
		String tmpWorkNo = "";
		String tmpTotalFuncp = "";

		while(rs.next()) {

			tmpWorkNo = StringUtil.getString(rs.getString("WORK_NO"));
			tmpTotalFuncp = StringUtil.getString(rs.getString("TOTAL_FUNCP"));
			totalArcByWorkNoMap.put(tmpWorkNo, tmpTotalFuncp);

		}

		this.disConnect();
		return totalArcByWorkNoMap;

	}


	/**
	 * 指定日のユーザー別合計作業工数を取得するための入り口
	 * @param targetDay
	 * @return
	 * @throws Exception
	 */
	protected ArrayList<HashMap<String, String>> getDailyTotalFuncp(String targetDay) throws Exception{

		return this.getDailyTotalFuncpColVal(this.executeSelect(this.getDailyTotalSQL(targetDay)));

	}


	/**
	 * 報告用の実績データを取得する。
	 * @param targetDay
	 * @return
	 */
	private String getFormatDataSQL(String targetDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("u.SHIMEI, ");
			sb.append("w.WORK_NO, ");
			sb.append("dayArc.FUNCP, ");
			sb.append("tp.TOTAL_FUNCP as P_TOTAL_FUNCP ");
		sb.append("from ");
			sb.append("(");
				sb.append("select ");
					sb.append("WORK_ID, ");
					sb.append("USER_NO, ");
					sb.append("FUNCP ");
				sb.append("from ");
					sb.append("ACHIEVE_TBL ");
				sb.append("where ");
					sb.append("REGISTED_YMD = '" + targetDay + "' ");
			sb.append(") dayArc ");
				sb.append("INNER JOIN ");
			sb.append("(");
				sb.append("select ");
					sb.append("WORK_ID, ");
					sb.append("USER_NO, ");
					sb.append("WORK_NO, ");
					sb.append("TOTAL_FUNCP ");
				sb.append("from ");
					sb.append("WORK_TBL ");
			sb.append(") w ");
				sb.append("ON ");
					sb.append("dayArc.WORK_ID = w.WORK_ID ");
					sb.append("and ");
					sb.append("dayArc.USER_NO = w.USER_NO ");
				sb.append("INNER JOIN ");
					sb.append("TOTAL_FUNCP_VIW tp ");
					sb.append("ON ");
					sb.append("w.WORK_NO = tp.WORK_NO ");
				sb.append("INNER JOIN ");
			sb.append("(");
				sb.append("select ");
					sb.append("USER_NO, ");
					sb.append("SHIMEI ");
				sb.append("from ");
					sb.append("USER_TBL");
			sb.append(") u ");
				sb.append("ON ");
					sb.append("w.USER_NO = u.USER_NO ");
		sb.append("WHERE ");
		sb.append("w.WORK_NO NOT LIKE 'WORK%' ");

		return sb.toString();

	}


	/**
	 * DBから取得したフォーマット用のデータを取り出して格納する。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private ArrayList<HashMap<String, String>> getFormatDataColVal(ResultSet rs, HashMap<String, String> totalArcByFuncpList) throws Exception{

		ArrayList<String> workNoList = new ArrayList<String>();
		HashMap<String, String> workNoCache = new HashMap<String, String>();
		HashMap<String, String> shimeiCache = new HashMap<String, String>();
		HashMap<String, String> totalFuncpCache = new HashMap<String, String>();
		HashMap<String, Float> dailyFuncpCache = new HashMap<String, Float>();
		HashMap<String, Float> planTptalFuncpCache = new HashMap<String, Float>();

		String tmpShimei = "";
		String tmpWorkNo = "";
		float tmpDailyFuncp = 0;
		String tmpTotalFuncp = "";
		float tmpPlanTptalFuncp = 0;

		while(rs.next()) {

			tmpShimei = StringUtil.getString(rs.getString("SHIMEI"));
			tmpWorkNo = StringUtil.getString(rs.getString("WORK_NO"));
			tmpDailyFuncp = rs.getFloat("FUNCP");
			tmpTotalFuncp = totalArcByFuncpList.get(tmpWorkNo);
			tmpPlanTptalFuncp = rs.getFloat("P_TOTAL_FUNCP");

			//既に取得済みの案件番号の場合、マージしてキャッシュにセットする。
			if(workNoCache.get(tmpWorkNo) != null) {

				tmpShimei = shimeiCache.get(tmpWorkNo) +  ", " + tmpShimei;
				tmpDailyFuncp = dailyFuncpCache.get(tmpWorkNo) + tmpDailyFuncp;

			}else {

				workNoList.add(tmpWorkNo);

			}

			workNoCache.put(tmpWorkNo, tmpWorkNo);
			shimeiCache.put(tmpWorkNo, tmpShimei);
			dailyFuncpCache.put(tmpWorkNo, tmpDailyFuncp);
			totalFuncpCache.put(tmpWorkNo, tmpTotalFuncp);
			planTptalFuncpCache.put(tmpWorkNo, tmpPlanTptalFuncp);

		}

		this.disConnect();

		return this.getRowData(workNoList, shimeiCache, dailyFuncpCache, totalFuncpCache, planTptalFuncpCache);

	}


	/**
	 * キャッシュにため込んだデータを案件番号単位でリストに格納しなおす。
	 * @param workNoList
	 * @param shimeiCache
	 * @param dailyFuncpCache
	 * @param totalFuncpCache
	 * @param planTptalFuncpCache
	 * @return
	 */
	private ArrayList<HashMap<String, String>> getRowData(ArrayList<String> workNoList, HashMap<String, String> shimeiCache,
			HashMap<String, Float> dailyFuncpCache, HashMap<String, String> totalFuncpCache, HashMap<String, Float> planTptalFuncpCache){

		ArrayList<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> recordMap = null;

		for(String workNo : workNoList) {

			recordMap = new HashMap<String, String>();

			recordMap.put(DailyReportMaker.KEY_SHIMEI, shimeiCache.get(workNo));
			recordMap.put(DailyReportMaker.KEY_WORKNO, workNo);
			recordMap.put(DailyReportMaker.KEY_DAILYFUNCP, String.valueOf(dailyFuncpCache.get(workNo)));
			recordMap.put(DailyReportMaker.KEY_ARC_TOTALFUNCP, totalFuncpCache.get(workNo));
			recordMap.put(DailyReportMaker.KEY_PLAN_TOTALFUNCP, String.valueOf(planTptalFuncpCache.get(workNo)));

			retList.add(recordMap);

		}

		return retList;

	}


	/**
	 * 日別工数の取得対象となる日付を取得して値をDBからもらいます。<br>
	 * ここでは指定日のユーザー別合計工数を取得します
	 * @param targetDay 取得対象の日付
	 * @return
	 */
	private String getDailyTotalSQL(String targetDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("WORK_NO, ");
			sb.append("SHIMEI, ");
			sb.append("sum(FUNCP) as DAILY_TOTAL_FUNCP ");
		sb.append("from ");
			sb.append("DAILY_ARC_VIW ");
		sb.append("where ");
			sb.append("REGISTED_DAY = '" + targetDay + "' ");
			sb.append("AND ");
			sb.append("WORK_NO NOT LIKE 'WORK%' ");
		sb.append("group by ");
			sb.append("USER_NO, SHIMEI ");
		sb.append("order by ");
			sb.append("WORK_NO, SHIMEI ");

		return sb.toString();

	}


	/**
	 * DBから取得したフォーマット用のデータを取り出して格納する。<br>
	 * ここでは指定日のユーザー別合計工数を取得します
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private ArrayList<HashMap<String, String>> getDailyTotalFuncpColVal(ResultSet rs) throws Exception{

		ArrayList<HashMap<String, String>> retList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> tmpMap = null;

		while(rs.next()) {

			tmpMap = new HashMap<String, String>();

			tmpMap.put(DailyReportMaker.KEY_WORKNO, StringUtil.getString(rs.getString("WORK_NO")));
			tmpMap.put(DailyReportMaker.KEY_SHIMEI, StringUtil.getString(rs.getString("SHIMEI")));
			tmpMap.put(DailyReportMaker.KEY_USER_DAILY_FUNCP, StringUtil.getString(rs.getString("DAILY_TOTAL_FUNCP")));

			retList.add(tmpMap);

		}

		this.disConnect();

		return retList;

	}


}
