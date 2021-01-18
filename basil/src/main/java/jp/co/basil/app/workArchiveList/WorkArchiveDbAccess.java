package jp.co.basil.app.workArchiveList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.DateTimeUtil;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * 登録された作業の一覧とそれに紐づく総工数の情報を表示します。
 *
 */
public class WorkArchiveDbAccess extends DBAccess {


	/** 終了済みのものも含めるフラグ */
	private final String CONTAIN_FINISHED = "1";

	/**
	 * コンストラクタ
	 */
	protected WorkArchiveDbAccess() {
	}


	/**
	 * DBから作業情報を抜いてくる入り口
	 * @param containFinishedFlg 完了済みのものも含めるフラグ 1:含める null:含めない
	 * @param userNo ユーザーによるソートを行う場合
	 * @return
	 */
	protected List<WorkArchiveBean> getWorkInfo(String containFinishedFlg, String userNo) throws Exception{

		List<WorkArchiveBean> retList = this.getColValues(this.executeSelect(this.getSQL(containFinishedFlg, userNo)));
		this.disConnect();
		return retList;

	}


	/**
	 * 作業情報一覧を取得するためのSQL
	 * @param containFinishedFlg
	 * @return
	 */
	protected String getSQL(String containFinishedFlg, String userNo) {

		if(userNo == null) {

			userNo = Constant.ALL_SELECTED_CD;

		}

		boolean isSelectedUser = !Constant.ALL_SELECTED_CD.equals(userNo);
		boolean isFinished = CONTAIN_FINISHED.equals(containFinishedFlg);
		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("main.SHIMEI, ");
			sb.append("main.WORK_ID, ");
			sb.append("main.WORK_NO, ");
			sb.append("main.WORK_NM, ");
			sb.append("main.ORVERVIEW, ");
			sb.append("main.CUSTOM_KBN, ");
			sb.append("main.PLAN_END_YMD, ");
			sb.append("arcday.END_YMD, ");
			sb.append("main.TOTAL_FUNCP, ");
			sb.append("main.SERACH_FUNCP, ");
			sb.append("main.PG_FUNCP, ");
			sb.append("main.PT_FUNCP, ");
			sb.append("main.INCIDENTAL_FUNCP, ");
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

				if(isSelectedUser) {

					sb.append("where ");
					sb.append("w.USER_NO = " + userNo);

				}

			sb.append(" ) main ");
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

		if(!isFinished) {

			sb.append("where ");
			sb.append("arcday.FINISH_KBN is null or arcday.FINISH_KBN in ('0', '1') or arcday.FINISH_KBN = '' ");

		}

		sb.append(" order by ");
			sb.append("main.PLAN_END_YMD desc, ");
			sb.append("main.WORK_ID");

		return sb.toString();

	}


	/**
	 * 値を取り出す。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List<WorkArchiveBean> getColValues(ResultSet rs) throws Exception{

		List<WorkArchiveBean> retList = new ArrayList<WorkArchiveBean>();
		WorkArchiveBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new WorkArchiveBean();

			tmpBean.setShimei(StringUtil.getString(rs.getString("SHIMEI")));
			tmpBean.setWorkId(StringUtil.getString(rs.getString("WORK_ID")));
			tmpBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			tmpBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NM")));
			tmpBean.setOverView(StringUtil.getString(rs.getString("ORVERVIEW")));
			tmpBean.setCustomKbn(StringUtil.getString(rs.getString("CUSTOM_KBN")));
			tmpBean.setPlanEndYmd(StringUtil.getString(rs.getString("PLAN_END_YMD")));
			tmpBean.setEndYmd(StringUtil.getString(rs.getString("END_YMD")));
			tmpBean.setTotalFuncp(rs.getFloat("TOTAL_FUNCP"));
			tmpBean.setSearchFuncp(rs.getFloat("SERACH_FUNCP"));
			tmpBean.setPgFuncp(rs.getFloat("PG_FUNCP"));
			tmpBean.setPtFuncp(rs.getFloat("PT_FUNCP"));
			tmpBean.setIncidentalFuncp(rs.getFloat("INCIDENTAL_FUNCP"));
			tmpBean.setArcTotalFuncp(rs.getFloat("ARC_TOTAL_FUNCP"));
			tmpBean.setWorkStMark(DateTimeUtil.compareDate(tmpBean.getEndYmd(), tmpBean.getPlanEndYmd()), tmpBean.getArcTotalFuncp() > tmpBean.getTotalFuncp());

			retList.add(tmpBean);

		}

		this.disConnect();

		return retList;

	}

}
