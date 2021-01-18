package jp.co.basil.app.archivementUpdate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.constant.ConstantKbnBean;
import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 *実績情報登録周りのDB操作を扱う。
 */
public class ArchivementDbAccess extends DBAccess {

	private static final String ENDDAY_KEY = "endday";
	private static final String FINKBN_KEY = "finkbn";

	/** 特殊登録処理か？ */
	private boolean isSpecial = false;

	/**
	 * コンストラクタ
	 */
	protected ArchivementDbAccess() {
	}


	/**
	 * コンストラクタ<br>
	 * 特殊登録処理か否かを指定する。
	 */
	protected ArchivementDbAccess(String specialFlg) {

		this.isSpecial = Constant.CHECKBOX_ON.equals(specialFlg);

	}


	/**
	 * 作業情報を取得する。
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	protected List<WorkInfoBean> getWorkInfo(String userNo) throws Exception{

		return this.getWorkInfoVal(this.executeSelect(this.getWorkInfoSQL(userNo)));

	}


	/**
	 * 実績情報の登録・更新・削除のための入り口
	 * @param formBean
	 */
	protected int execute(ArchiveForm formBean, String userNo) throws Exception{

		int resultCd = 0;

		//登録・更新削除を判別する。
		if(formBean.isDelete()) {

			resultCd = this.doInsOrUpdOrDel(this.getDeleteSQL(formBean, userNo), formBean, userNo, true);

		}else {

			resultCd = this.executeUpdate(formBean, userNo);

		}

		return resultCd;

	}


	/**
	 * 作業実績テーブルに登録された特定のレコードを削除するSQLを提供します。
	 * @param formBean
	 * @param userNo
	 * @return
	 */
	private String getDeleteSQL(ArchiveForm formBean, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("delete from ");
			sb.append("ACHIEVE_TBL ");
		sb.append("where ");
			sb.append("WORK_ID = " + formBean.getWorkId());
			sb.append(" and ");
			sb.append("USER_NO = " + userNo);
			sb.append(" and ");
			sb.append("REGISTED_YMD = '" + formBean.getTargetDay() + "'");

		return sb.toString();

	}


	/**
	 * 作業実績状態テーブルに登録された特定のレコードを削除するSQLを提供します。
	 * @param formBean
	 * @param userNo
	 * @return
	 */
	private String getDeleteStSQL(ArchiveForm formBean, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("delete from ");
		sb.append("ACHIEVE_DAY_TBL ");
		sb.append("WORK_ID = " + formBean.getWorkId());
		sb.append(" and ");
		sb.append("USER_NO = " + userNo);

		return sb.toString();

	}


	/**
	 * 既に値が登録されているか否かで登録と更新を切り分ける
	 * @param formBean
	 * @param userNo
	 * @throws Exception
	 */
	private int executeUpdate(ArchiveForm formBean, String userNo) throws Exception{

		boolean isRegistedRecord = this.isRegistedFuncp(this.executeSelect(this.getArcSQL(formBean.getWorkId(), userNo, formBean.getTargetDay())));
		String sql = "";

		if(!isRegistedRecord) {

			sql = this.getInsArcTbl(formBean, userNo);

		}else {

			sql = this.getUpdArcTbl(formBean, userNo);

		}

		return this.doInsOrUpdOrDel(sql, formBean, userNo, false);

	}


	/**
	 * 実績テーブルへの新規登録処理を行う。
	 * @param formBean
	 * @param userNo
	 */
	private int doInsOrUpdOrDel(String sql, ArchiveForm formBean, String userNo, boolean isDelete) throws Exception{

		int retCd = 0;
		this.connect();
		retCd = this.executeQuery(sql);

		try {

			//実績テーブルへの登録失敗時はロールバックして値を返す
			if(retCd < 0) {

				this.doTransAction(retCd);
				this.disConnect();
				return retCd;

			}

			retCd = this.doCRUD(formBean, userNo, isDelete);

			this.doTransAction(retCd);

		}catch(Exception e) {

			this.doTransAction(-1);

		}

		return retCd;

	}


	/**
	 * 作業実績テーブルへの登録・更新・削除処理を担当する。
	 * @param formBean
	 * @param userNo
	 * @param isDelete
	 * @return
	 * @throws Exception
	 */
	private int doCRUD(ArchiveForm formBean, String userNo, boolean isDelete) throws Exception{

		int retCd = 0;

		if(isDelete) {

			retCd = this.doupdWorkStatusWhenDel(formBean, userNo);

		}else{

			retCd = this.doInsOrUpdWorkStatus(formBean, userNo);

		}

		return retCd;

	}


	/**
	 * 実績を新規登録するためのSQLを提供します。
	 * @param formBean
	 * @param userNo
	 * @return
	 */
	private String getInsArcTbl(ArchiveForm formBean, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("insert into ");
			sb.append("ACHIEVE_TBL ");
		sb.append("values(");
			sb.append(formBean.getWorkId() + ", ");
			sb.append(userNo + ", ");
			sb.append("'" + formBean.getTargetDay() + "', ");
			sb.append(formBean.getFuncp() + "");
		sb.append(")");

		return sb.toString();

	}


	/**
	 * 実績テーブルに対して更新を行うためのSQLを提供します。
	 * @param formBean
	 * @param userNo
	 * @return
	 */
	private String getUpdArcTbl(ArchiveForm formBean, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("update ");
			sb.append("ACHIEVE_TBL ");
		sb.append("set ");
			sb.append("FUNCP = " + formBean.getFuncp());
		sb.append("where ");
		sb.append("WORK_ID = " + formBean.getWorkId());
		sb.append(" and ");
		sb.append("USER_NO = " + userNo);
		sb.append(" and ");
		sb.append("REGISTED_YMD = '" + formBean.getTargetDay() + "'");

		return sb.toString();

	}


	/**
	 * 作業状態テーブルに対して、登録または更新処理を切り分けます。
	 * @param formBean
	 * @param userNo
	 * @return 0以上なら成功
	 * @throws Exception
	 */
	private int doInsOrUpdWorkStatus(ArchiveForm formBean, String userNo) throws Exception{

		int retCd = 0;

		ArchivementDbAccess dataGetter = new ArchivementDbAccess();
		WorkStatusBean workStatusBean = dataGetter.getWorkStatus(dataGetter.executeSelect(dataGetter.getWorkStatusSQL(formBean.getWorkId(), userNo)));

		ArchivementDbAccess executer = new ArchivementDbAccess();
		executer.connect();

		if(workStatusBean == null) {

			//insert
			retCd = executer.executeQuery(executer.doInsWorkStSQL(formBean, userNo));

		}else {

			//update
			retCd = executer.executeQuery(executer.doUpdWorkStSQL(workStatusBean, formBean, userNo));

		}

		executer.doTransAction(retCd);

		return retCd;

	}


	/**
	 * 作業状態テーブルに対して、削除または更新処理を切り分けます。
	 * @param formBean
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	private int doupdWorkStatusWhenDel(ArchiveForm formBean, String userNo) throws Exception{

		ArchivementDbAccess executer = new ArchivementDbAccess();
		ArchivementDbAccess dataGetter = new ArchivementDbAccess();
		int retCd = 0;
		boolean isRegistedData = dataGetter.isRegistedFuncp(dataGetter.executeSelect(dataGetter.getTotalArcSQL(formBean.getWorkId(), userNo)));

		executer.connect();

		//作業実績テーブルの指定日付のデータを削除後、当該作業IDに登録された実績データがまだ残っている場合で、作業終了実績日が登録されている場合は、終了実績日を更新する必要がある。
		if(isRegistedData) {

			//Update
			retCd = executer.executeQuery(executer.doUpdWorkStSQL(executer.getWorkStatus(executer.executeSelect(executer.getWorkStatusSQL(formBean.getWorkId(), userNo))), formBean, userNo));

		}else {

			//Delete
			retCd = executer.executeQuery(executer.getDeleteStSQL(formBean, userNo));

		}

		executer.doTransAction(retCd);

		return retCd;

	}


	/**
	 * 作業状態テーブルの更新用SQLを提供します。
	 * @param workStatusBean
	 * @param formBean
	 * @param userNo
	 * @return
	 * @throws Exception
	 */
	private String doUpdWorkStSQL(WorkStatusBean workStatusBean, ArchiveForm formBean, String userNo) throws Exception{

		//実績テーブルから一番古い開始実績日を取得
		String beginYMD = this.getYMD(this.executeSelect(this.getOldestDaySQL(formBean.getWorkId(), userNo)));

		//取得できない場合、当日をセット
		if(beginYMD.isEmpty()) {

			beginYMD = formBean.getTargetDay();

		}

		//終了実績日と完了フラグの値からセットする終了実績日と終了区分を決める。
		HashMap<String, String> endDayAndFinKbnMap = this.getEndDayAndFinishKbn(formBean, workStatusBean.getEndYMD(), userNo);

		StringBuffer sb = new StringBuffer();

		sb.append("update ");
			sb.append("ACHIEVE_DAY_TBL ");
		sb.append("set ");
			sb.append("BEGIN_YMD = '" + beginYMD + "', ");
			sb.append("END_YMD = '" + endDayAndFinKbnMap.get(ENDDAY_KEY) + "', ");
			sb.append("FINISH_KBN = '" + endDayAndFinKbnMap.get(FINKBN_KEY) + "' ");
		sb.append("where ");
			sb.append("WORK_ID = " + formBean.getWorkId());
			sb.append(" and ");
			sb.append("USER_NO = " + userNo);

		return sb.toString();

	}


	/**
	 * 完了チェックボックスの状態と、DBの終了実績日の登録状態から終了日付と完了区分にセットする値を決めます。
	 * @param formDay
	 * @param dbEndDay
	 * @param isFinished
	 * @return
	 */
	private HashMap<String, String> getEndDayAndFinishKbn(ArchiveForm formBean, String dbEndDay, String userNo) throws Exception{

		HashMap<String, String> retMap = new HashMap<String, String>();
		String endDay = "";
		String finKbn = "";

		if(formBean.isDelete() && !dbEndDay.isEmpty()) {

			endDay = this.getYMD(this.executeSelect(this.getLatestDaySQL(formBean.getWorkId(), userNo, formBean.getTargetDay())));
			finKbn = ConstantKbnBean.KBN_CD_FINISH_AFTER;

		}else if(formBean.isDelete() && dbEndDay.isEmpty()){

			finKbn = ConstantKbnBean.KBN_CD_FINISH_NOW;

		}else  if(!dbEndDay.isEmpty() && !formBean.isFinished()) {

			finKbn = ConstantKbnBean.KBN_CD_FINISH_NOW;

		}else if(dbEndDay.isEmpty() && formBean.isFinished()) {

			endDay = formBean.getTargetDay();
			finKbn = ConstantKbnBean.KBN_CD_FINISH_AFTER;

		}else if(!dbEndDay.isEmpty() && formBean.isFinished()) {

			endDay = formBean.getTargetDay();
			finKbn = ConstantKbnBean.KBN_CD_FINISH_AFTER;

		}

		retMap.put(ENDDAY_KEY, endDay);
		retMap.put(FINKBN_KEY, finKbn);

		return retMap;

	}



	/**
	 * 開始実績日にセットする日付を取得します。<br>
	 * 作業実績テーブルに登録された、一番古い日付を返します。
	 * @param workId
	 * @param userNo
	 * @return
	 */
	private String getOldestDaySQL(String workId, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("min(REGISTED_YMD) as OLDEST ");
		sb.append("from ");
			sb.append("ACHIEVE_TBL ");
		sb.append("where ");
			sb.append("WORK_ID = " + workId);
			sb.append(" and ");
			sb.append("USER_NO = " + userNo);

		return sb.toString();

	}


	/**
	 * 終了実績日にセットする日付を取得します。<br>
	 * 作業実績テーブルに登録された一番新しい日付をかえします。
	 * @param workId
	 * @param userNo
	 * @return
	 */
	private String getLatestDaySQL(String workId, String userNo, String targetDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("max(REGISTED_YMD) as OLDEST ");
		sb.append("from ");
			sb.append("ACHIEVE_TBL ");
		sb.append("where ");
			sb.append("WORK_ID = " + workId);
			sb.append(" and ");
			sb.append("USER_NO = " + userNo);
			sb.append(" and ");
			sb.append("REGISTED_YMD <> '" + targetDay + "'");

		return sb.toString();

	}


	/**
	 * 取得した開始日付を取り出す。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private String getYMD(ResultSet rs) throws Exception{

		String retDate = "";

		if(rs.next()) {

			retDate = StringUtil.getString(rs.getString("OLDEST"));

		}

		return retDate;

	}


	/**
	 * 作業状態を登録する用のSQLを提供します。
	 * @param formBean
	 * @param userNo
	 * @return
	 */
	private String doInsWorkStSQL(ArchiveForm formBean, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("insert into ");
		sb.append("ACHIEVE_DAY_TBL ");
		sb.append("values(");
		sb.append(formBean.getWorkId() + ", ");
		sb.append(userNo + ", ");
		sb.append("date_format(CURDATE(), '%Y/%m/%d'), ");

		if(formBean.isFinished()) {

			sb.append("'" + formBean.getTargetDay() + "', ");

		}else {

			sb.append("'', ");

		}

		sb.append(ConstantKbnBean.KBN_CD_FINISH_NOW);
		sb.append(")");

		return sb.toString();

	}


	/**
	 * 作業状態を取得するためのSQL
	 * @param workId
	 * @param userNo
	 * @return
	 */
	private String getWorkStatusSQL(String workId, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("BEGIN_YMD, ");
			sb.append("END_YMD, ");
			sb.append("FINISH_KBN ");
		sb.append("from ");
			sb.append("ACHIEVE_DAY_TBL ");
		sb.append("where ");
			sb.append("WORK_ID = " + workId);
			sb.append(" and ");
			sb.append("USER_NO = " + userNo);

		return sb.toString();

	}


	/**
	 * DBから取得した作業状態情報を取り出す。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private WorkStatusBean getWorkStatus(ResultSet rs) throws Exception{

		WorkStatusBean workStatusBean = new WorkStatusBean();

		if(rs.next()) {

			workStatusBean.setBeginYMD(StringUtil.getString(rs.getString("BEGIN_YMD")));
			workStatusBean.setEndYMD(StringUtil.getString(rs.getString("END_YMD")));
			workStatusBean.setFinishKbn(StringUtil.getString(rs.getString("FINISH_KBN")));

		}

		this.disConnect();

		return workStatusBean;

	}


	/**
	 * 作業実績を取得するためのSQL
	 * @param workId
	 * @param userNo
	 * @param targetDay
	 * @return
	 */
	private String getArcSQL(String workId, String userNo, String targetDay) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("count(*) as CNT ");
		sb.append("from ");
		sb.append("ACHIEVE_TBL ");
		sb.append("where ");
		sb.append("WORK_ID = " + workId);
		sb.append(" and ");
		sb.append("USER_NO = " + userNo);
		sb.append(" and ");
		sb.append("REGISTED_YMD = '" + targetDay + "' ");

		return sb.toString();

	}


	/**
	 * 作業実績を取得するためのSQL
	 * @param workId
	 * @param userNo
	 * @param targetDay
	 * @return
	 */
	private String getTotalArcSQL(String workId, String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("count(*) as CNT ");
		sb.append("from ");
		sb.append("ACHIEVE_TBL ");
		sb.append("where ");
		sb.append("WORK_ID = " + workId);
		sb.append(" and ");
		sb.append("USER_NO = " + userNo);

		return sb.toString();

	}


	/**
	 * 指定日付・指定作業IDにレコードが登録されているかを判別する
	 * @param rs
	 * @return レコードが登録されていればtrue
	 * @throws Exception
	 */
	private boolean isRegistedFuncp(ResultSet rs) throws Exception{

		boolean result = false;

		if(rs.next()) {

			result = rs.getInt("CNT") > 0;

		}

		this.disConnect();

		return result;

	}


	/**
	 * 作業情報を取得するためのSQLを提供する。
	 * @param userNo
	 * @return
	 */
	private String getWorkInfoSQL(String userNo) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("WORK_ID, ");
			sb.append("WORK_NO, ");
			sb.append("WORK_NM ");
		sb.append("from ");
			sb.append("WORK_STATUS_VIW ");
		sb.append("where ");
			sb.append("USER_NO = " + userNo);

		if(this.isSpecial) {

			sb.append(" and ");
				sb.append("FINISH_KBN = '" + ConstantKbnBean.KBN_CD_FINISH_AFTER + "' ");

		}else {

			sb.append(" and ");
				sb.append(" ( ");
					sb.append("FINISH_KBN <> '" + ConstantKbnBean.KBN_CD_FINISH_AFTER + "' ");
					sb.append(" or ");
					sb.append("FINISH_KBN is null ");
				sb.append(" ) ");

		}

		sb.append(" order by ");
			sb.append("PLAN_BEGIN_YMD desc");

		return sb.toString();

	}


	/**
	 * DBから取得した作業情報データを取り出して格納する。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private List<WorkInfoBean> getWorkInfoVal(ResultSet rs) throws Exception{

		List<WorkInfoBean> retList = new ArrayList<WorkInfoBean>();
		WorkInfoBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new WorkInfoBean();
			tmpBean.setWorkId(rs.getInt("WORK_ID"));
			tmpBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NO")) + ":" + StringUtil.getString(rs.getString("WORK_NM")));

			retList.add(tmpBean);

		}

		this.disConnect();

		return retList;

	}


	/**
	 * 特殊処理か否かを教える。
	 * @return
	 */
	public boolean isSpecial() {
		return isSpecial;
	}

}
