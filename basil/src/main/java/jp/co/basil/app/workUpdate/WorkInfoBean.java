package jp.co.basil.app.workUpdate;

import jp.co.basil.app.util.DateTimeUtil;

/**
 * @author NOMOS) Yoshida
 * 作業情報テーブルの情報を保持するためのBean
 *
 */
public class WorkInfoBean {

	private int workId = 0;
	private int userNo = 0;
	private int projectid = 0;
	private String workNo = "";
	private String workNoA = "";
	private String workNoB = "";
	private String workNm = "";
	private String overView = "";
	private String finishKbn = "";
	private String customKbn = "";
	private String processKbn = "";
	private String planBeginYMD = "";
	private String planEndYMD = "";
	private String beginYMD = "";
	private String endYMD = "";
	private float totalFuncP = 0;
	private float searchFuncp = 0;
	private float pgFuncp = 0;
	private float ptFuncp = 0;
	private float inicidentalFuncp = 0;
	private String updateYMD = "";
	private String updateHMS = "";

	public WorkInfoBean() {
	}

	public int getWorkId() {
		return workId;
	}

	public void setWorkId(int workId) {
		this.workId = workId;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public int getProjectid() {
		return projectid;
	}

	public void setProjectid(int projectid) {
		this.projectid = projectid;
	}

	public String getWorkNo() {
		return workNo;
	}

	public void setWorkNo(String workNo) {
		this.workNo = workNo;
		this.isContainHaihun(workNo);
	}

	/**
	 * -形式の案件番号かどうかを判定する。<br>
	 * その結果に応じて案件番号をセットする。
	 * @param workNo
	 */
	private void isContainHaihun(String workNo) {

		int result = workNo.indexOf("-");

		if(result > -1) {

			String[] tmp = workNo.split("-");
			this.workNoA = tmp[0];
			this.workNoB = tmp[1];

		}else {

			this.workNoA = "";
			this.workNoB = workNo;

		}

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

	public String getFinishKbn() {
		return finishKbn;
	}

	public void setFinishKbn(String finishKbn) {
		this.finishKbn = finishKbn;
	}

	public String getCustomKbn() {
		return customKbn;
	}

	public void setCustomKbn(String customKbn) {
		this.customKbn = customKbn;
	}

	public String getProcessKbn() {
		return processKbn;
	}

	public void setProcessKbn(String processKbn) {
		this.processKbn = processKbn;
	}

	public String getPlanBeginYMD() {
		return planBeginYMD;
	}

	public void setPlanBeginYMD(String planBeginYMD) {
		this.planBeginYMD = DateTimeUtil.convDate(planBeginYMD);
	}

	public String getPlanEndYMD() {
		return planEndYMD;
	}

	public void setPlanEndYMD(String planEndYMD) {
		this.planEndYMD = DateTimeUtil.convDate(planEndYMD);
	}

	public String getBeginYMD() {
		return beginYMD;
	}

	public void setBeginYMD(String beginYMD) {
		this.beginYMD = beginYMD;
	}

	public String getEndYMD() {
		return endYMD;
	}

	public void setEndYMD(String endYMD) {
		this.endYMD = endYMD;
	}

	public float getTotalFuncP() {
		return totalFuncP;
	}

	public void setTotalFuncP(float totalFuncP) {
		this.totalFuncP = totalFuncP;
	}

	public float getSearchFuncp() {
		return searchFuncp;
	}

	public void setSearchFuncp(float searchFuncp) {
		this.searchFuncp = searchFuncp;
	}

	public float getPgFuncp() {
		return pgFuncp;
	}

	public void setPgFuncp(float pgFuncp) {
		this.pgFuncp = pgFuncp;
	}

	public float getPtFuncp() {
		return ptFuncp;
	}

	public void setPtFuncp(float ptFuncp) {
		this.ptFuncp = ptFuncp;
	}

	public float getInicidentalFuncp() {
		return inicidentalFuncp;
	}

	public void setInicidentalFuncp(float inicidentalFuncp) {
		this.inicidentalFuncp = inicidentalFuncp;
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

	public String getWorkNoA() {
		return workNoA;
	}

	public void setWorkNoA(String workNoA) {
		this.workNoA = workNoA;
	}

	public String getWorkNoB() {
		return workNoB;
	}

	public void setWorkNoB(String workNoB) {
		this.workNoB = workNoB;
	}


}
