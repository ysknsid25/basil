package jp.co.basil.app.workUpdate;

import jp.co.basil.app.util.DateTimeUtil;

/**
 * @author NOMOS) Yoshida
 * 作業情報を登録します
 *
 *
 */
public class WorkRegistForm {

	/** 作業ID */
	private String workId = "";

	/** 案件番号 */
	private String workNo = "";

	/** ユーザー番号 */
	private int userNo = 0;

	/** 作業名 */
	private String workNm = "";

	/** 作業概要 */
	private String overView = "";

	/** 改修区分 */
	private String customKbn = "";

	/** 開始予定日 */
	private String beginYMD = "";

	/** 終了予定日 */
	private String endYMD = "";

	/** 予定総工数 */
	private float planTotalFuncp = 0;

	/** 予定調査工数 */
	private float planSearchFuncp = 0;

	/** 予定PG工数 */
	private float planPGFuncp = 0;

	/** 予定PT工数 */
	private float planPTFuncp = 0;

	/** 予定付帯作業工数 */
	private float incidentalFuncp = 0;

	/** 削除フラグ */
	private String isDelete = "";

	/** データ取得時点での更新日 */
	private String updateYMD = "";

	/** データ取得時点での更新時 */
	private String updateHMS = "";


	/**
	 * コンストラクタ
	 */
	public WorkRegistForm () {
	}


	public String getWorkId() {
		return workId;
	}


	public void setWorkId(String workId) {
		this.workId = workId;
	}


	public String getWorkNo() {
		return workNo;
	}


	public void setWorkNo(String workNo) {
		this.workNo = workNo;
	}


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	public String getWorkNm() {
		return workNm;
	}


	public void setWorkNm(String workNm) {
		this.workNm = workNm;
	}


	public String getOverView() {
		return overView;
	}


	public void setOverView(String overView) {
		this.overView = overView;
	}


	public String getCustomKbn() {
		return customKbn;
	}


	public void setCustomKbn(String customKbn) {
		this.customKbn = customKbn;
	}


	public String getBeginYMD() {
		return beginYMD;
	}


	public void setBeginYMD(String beginYMD) {
		this.beginYMD = DateTimeUtil.convDate(beginYMD);
	}


	public String getEndYMD() {
		return endYMD;
	}


	public void setEndYMD(String endYMD) {
		this.endYMD = DateTimeUtil.convDate(endYMD);
	}


	public float getPlanTotalFuncp() {
		return planTotalFuncp;
	}


	public void setPlanTotalFuncp(float planTotalFuncp) {
		this.planTotalFuncp = planTotalFuncp;
	}


	public float getPlanSearchFuncp() {
		return planSearchFuncp;
	}


	public void setPlanSearchFuncp(float planSearchFuncp) {
		this.planSearchFuncp = planSearchFuncp;
	}


	public float getPlanPGFuncp() {
		return planPGFuncp;
	}


	public void setPlanPGFuncp(float planPGFuncp) {
		this.planPGFuncp = planPGFuncp;
	}


	public float getIncidentalFuncp() {
		return incidentalFuncp;
	}


	public void setIncidentalFuncp(float incidentalFuncp) {
		this.incidentalFuncp = incidentalFuncp;
	}


	public float getPlanPTFuncp() {
		return planPTFuncp;
	}


	public void setPlanPTFuncp(float planPTFuncp) {
		this.planPTFuncp = planPTFuncp;
	}


	public String getIsDelete() {
		return isDelete;
	}


	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}


	public String getUpdateYMD() {
		return updateYMD;
	}


	public void setUpdateYMD(String updateYMD) {
		this.updateYMD = updateYMD;
	}


	public String getUpdateHMS() {
		return updateHMS;
	}


	public void setUpdateHMS(String updateHMS) {
		this.updateHMS = updateHMS;
	}

}
