package jp.co.basil.app.workUpdate;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import java.sql.PreparedStatement;

import jp.co.basil.app.constant.ConstantPath;
import jp.co.basil.app.db.DBAccess;
import jp.co.basil.app.db.DBexception;
import jp.co.basil.app.util.StringUtil;

/**
 * @author NOMOS) Yoshida
 * 作業情報登録に必要な情報を取得するためのクラス。
 *
 */
public class WorkRegistDbAccess extends DBAccess {

	public WorkRegistDbAccess() {
	}


	/**
	 * ユーザー情報の一覧を取得する。
	 * @return
	 * @throws Exception
	 */
	public List<UserInfoBean> getUserInfo() throws Exception{

		List<UserInfoBean> retList = this.getUserInfoList(this.executeSelect(this.getUserInfoSQL()));
		this.disConnect();
		return retList;

	}


	/**
	 * 作業情報を取得します。<br>
	 * 作業IDが空文字またはNullまたは初期値の0で来た場合新規登録なので、<br>
	 *検索処理は行わず、 空のインスタンスをnewして返す。
	 * @param workId 作業ID
	 * @return
	 * @throws Exception
	 */
	protected WorkInfoBean getWorkInfo(String workId) throws Exception{

		WorkInfoBean retBean = null;

		if(workId == null || workId.isEmpty() || workId.equals("0")) {

			retBean = new WorkInfoBean();

		}else {

			retBean = this.getWorkInfo(this.executeSelect(this.getSelectSQL(workId)));
			this.disConnect();

		}

		return retBean;

	}



	/**
	 * 作業情報登録における登録・更新処理を担います。
	 * @param formBean
	 * @param isRegist
	 * @param isDelete
	 * @throws Exception
	 */
	protected void execute(WorkRegistForm formBean, boolean isRegist, boolean isDelete) throws Exception{

		if(isDelete) {

			//削除
			this.executeUpdate(this.getDeleteSQL(Integer.parseInt(formBean.getWorkId())));

		}else if(isRegist) {

			//新規登録
			this.doInsert(formBean);

		}else {

			//更新
			this.doUpdate(formBean);

		}

	}


	/**
	 * 登録処理を行う
	 * @param formBean
	 * @throws Exception
	 */
	private void doInsert(WorkRegistForm formBean) throws Exception{

		this.connect();
		this.executeUpdate(this.getPreparedStatement(formBean, this.getInsertSQL()));

	}


	/**
	 * 更新処理を行う。
	 * @param formBean
	 * @throws Exception
	 */
	private void doUpdate(WorkRegistForm formBean) throws Exception{

		if(this.exclusionControl(this.getLockSQL(formBean.getWorkId()), formBean.getUpdateYMD(), formBean.getUpdateHMS())) {

			this.connect();
			this.executeUpdate(this.getPreparedStatement(formBean, this.getUpdateSQL(formBean.getWorkId())));

		}else {

			throw new DBexception("他端末にて更新済みです");

		}


	}


	/**
	 * ロック用のSQLを提供します。
	 * @param workId
	 * @return
	 */
	private String getLockSQL(String workId) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
		sb.append("UPDATE_YMD, ");
		sb.append("UPDATE_HMS ");
		sb.append("from ");
		sb.append("WORK_TBL ");
		sb.append("where ");
		sb.append("WORK_ID = " + Integer.parseInt(workId));

		return sb.toString();

	}


	/**
	 * 削除用のSQLを提供する
	 * @param workId
	 * @return
	 */
	private String getDeleteSQL(int workId) {

		StringBuffer sb = new StringBuffer();

		sb.append("delete from ");
			sb.append("WORK_TBL ");
		sb.append("where ");
		sb.append("WORK_ID = " + workId);

		return sb.toString();

	}


	/**
	 * 作業情報一覧を取得するためのSQLを提供します。
	 * @param workId
	 * @return
	 */
	private String getSelectSQL(String workId) {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("WORK_ID, ");
			sb.append("USER_NO, ");
			sb.append("PROJECT_ID, ");
			sb.append("WORK_NO, ");
			sb.append("WORK_NM, ");
			sb.append("ORVERVIEW, ");
			sb.append("CUSTOM_KBN, ");
			sb.append("PLAN_BEGIN_YMD, ");
			sb.append("PLAN_END_YMD, ");
			sb.append("TOTAL_FUNCP, ");
			sb.append("SERACH_FUNCP, ");
			sb.append("PG_FUNCP, ");
			sb.append("PT_FUNCP, ");
			sb.append("INCIDENTAL_FUNCP, ");
			sb.append("UPDATE_YMD, ");
			sb.append("UPDATE_HMS ");
		sb.append("from ");
			sb.append("WORK_TBL ");
		sb.append("where ");
			sb.append("WORK_ID = " + Integer.parseInt(workId));

		return sb.toString();

	}


	/**
	 * SQLの実行結果を取り出してBeanに格納して返します。
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	private WorkInfoBean getWorkInfo(ResultSet rs) throws Exception{

		WorkInfoBean retBean = new WorkInfoBean();

		if(rs.next()) {

			retBean.setWorkId(rs.getInt("WORK_ID"));
			retBean.setUserNo(rs.getInt("USER_NO"));
			retBean.setProjectid(rs.getInt("PROJECT_ID"));
			retBean.setWorkNo(StringUtil.getString(rs.getString("WORK_NO")));
			retBean.setWorkNm(StringUtil.getString(rs.getString("WORK_NM")));
			retBean.setOverView(StringUtil.getString(rs.getString("ORVERVIEW")));
			retBean.setCustomKbn(StringUtil.getString(rs.getString("CUSTOM_KBN")));
			retBean.setPlanBeginYMD(StringUtil.getString(rs.getString("PLAN_BEGIN_YMD")));
			retBean.setPlanEndYMD(StringUtil.getString(rs.getString("PLAN_END_YMD")));
			retBean.setTotalFuncP(rs.getFloat("TOTAL_FUNCP"));
			retBean.setSearchFuncp(rs.getFloat("SERACH_FUNCP"));
			retBean.setPgFuncp(rs.getFloat("PG_FUNCP"));
			retBean.setPtFuncp(rs.getFloat("PT_FUNCP"));
			retBean.setInicidentalFuncp(rs.getFloat("INCIDENTAL_FUNCP"));
			retBean.setUpdateYMD(StringUtil.getString(rs.getString("UPDATE_YMD")));
			retBean.setUpdateHMS(StringUtil.getString(rs.getString("UPDATE_HMS")));

		}

		return retBean;

	}


	/**
	 * プリペアードステートメントを用意する。
	 * @param formBean
	 * @return
	 * @throws Exception
	 */
	private PreparedStatement getPreparedStatement(WorkRegistForm formBean, String sql) throws Exception{

		PreparedStatement stmt = this.makePreparedStatement(sql);

	    stmt.setInt(1, formBean.getUserNo()); //USER_NO
	    stmt.setString(2, formBean.getWorkNo()); //WORK_NO
	    stmt.setString(3, formBean.getWorkNm()); //WORK_NM
	    stmt.setString(4, formBean.getOverView()); //ORVERVIEW
	    stmt.setString(5, formBean.getCustomKbn()); //CUSTOM_KBN
	    stmt.setString(6, formBean.getBeginYMD()); //PLAN_BEGIN_YMD
	    stmt.setString(7, formBean.getEndYMD()); //PLAN_END_YMD
	    stmt.setFloat(8, formBean.getPlanTotalFuncp()); //TOTAL_FUNCP
	    stmt.setFloat(9, formBean.getPlanSearchFuncp()); //SERACH_FUNCP
	    stmt.setFloat(10, formBean.getPlanPGFuncp()); //PG_FUNCP
	    stmt.setFloat(11, formBean.getPlanPTFuncp()); //PT_FUNCP
	    stmt.setFloat(12, formBean.getIncidentalFuncp()); //INCIDENTAL_FUNCP

		return stmt;

	}


	/**
	 * 作業情報テーブルへの登録用のSQLを提供します。
	 * @param formBean
	 * @return
	 */
	private String getInsertSQL() {

		StringBuffer sb = new StringBuffer();

		sb.append("INSERT INTO ");
			sb.append("WORK_TBL ");
		sb.append("VALUES(");
			sb.append("nextval('WORK_ID'), "); //WORK_ID
			sb.append("?, "); //USER_NO
			sb.append("1, "); //PROJECT_ID
			sb.append("?, "); //WORK_NO
			sb.append("?, "); //WORK_NM
			sb.append("?, "); //ORVERVIEW
			sb.append("'', "); //WORK_KBN
			sb.append("?, "); //CUSTOM_KBN
			sb.append("'', "); //PROCESS_KBN
			sb.append("?, "); //PLAN_BEGIN_YMD
			sb.append("?, "); //PLAN_END_YMD
			sb.append("?, "); //TOTAL_FUNCP
			sb.append("?, "); //SERACH_FUNCP
			sb.append("?, "); //PG_FUNCP
			sb.append("?, "); //PT_FUNCP
			sb.append("?, "); //INCIDENTAL_FUNCP
			sb.append("date_format(CURDATE(), '%Y/%m/%d'), "); //UPDATE_YMD
			sb.append("SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12) "); //UPDATE_HMS
		sb.append(")");

		return sb.toString();

	}


	/**
	 * 更新用のSQLを提供します
	 * @param workId
	 * @return
	 */
	private String getUpdateSQL(String workId) {

		StringBuffer sb = new StringBuffer();

		sb.append("update ");
			sb.append("WORK_TBL ");
		sb.append("set ");
			sb.append("USER_NO = ?, "); //USER_NO
			sb.append("PROJECT_ID = 1, "); //PROJECT_ID
			sb.append("WORK_NO = ?, "); //WORK_NO
			sb.append("WORK_NM = ?, "); //WORK_NM
			sb.append("ORVERVIEW = ?, "); //ORVERVIEW
			sb.append("WORK_KBN = '', "); //WORK_KBN
			sb.append("CUSTOM_KBN = ?, "); //CUSTOM_KBN
			sb.append("PROCESS_KBN = '', "); //PROCESS_KBN
			sb.append("PLAN_BEGIN_YMD = ?, "); //PLAN_BEGIN_YMD
			sb.append("PLAN_END_YMD = ?, "); //PLAN_END_YMD
			sb.append("TOTAL_FUNCP = ?, "); //TOTAL_FUNCP
			sb.append("SERACH_FUNCP = ?, "); //SERACH_FUNCP
			sb.append("PG_FUNCP = ?, "); //PG_FUNCP
			sb.append("PT_FUNCP = ?, "); //PT_FUNCP
			sb.append("INCIDENTAL_FUNCP = ?, "); //INCIDENTAL_FUNCP
			sb.append("UPDATE_YMD = date_format(CURDATE(), '%Y/%m/%d'), "); //UPDATE_YMD
			sb.append("UPDATE_HMS = SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12) "); //UPDATE_HMS
		sb.append("where ");
		sb.append("WORK_ID = " + Integer.parseInt(workId));

		return sb.toString();

	}


	/**
	 * ユーザー情報を取得するSQL
	 * @return
	 */
	private String getUserInfoSQL() {

		StringBuffer sb = new StringBuffer();

		sb.append("select ");
			sb.append("USER_NO, ");
			sb.append("SHIMEI ");
		sb.append("from ");
			sb.append("USER_TBL ");
		sb.append("order by ");
			sb.append("USER_NO ");

		return sb.toString();

	}


	/**
	 * DBから取得したユーザー情報を取り出す。
	 * @param rs
	 * @return
	 */
	private List<UserInfoBean> getUserInfoList(ResultSet rs) throws Exception{

		List<UserInfoBean> retList = new ArrayList<UserInfoBean>();

		UserInfoBean tmpBean = null;

		while(rs.next()) {

			tmpBean = new UserInfoBean();

			tmpBean.setUserNo(rs.getInt("USER_NO"));
			tmpBean.setShimei(rs.getString("SHIMEI"));

			retList.add(tmpBean);

		}

		this.disConnect();
		return retList;

	}

}
