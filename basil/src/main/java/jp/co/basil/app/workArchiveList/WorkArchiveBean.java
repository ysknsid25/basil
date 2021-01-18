package jp.co.basil.app.workArchiveList;

import jp.co.basil.app.constant.Constant;
import jp.co.basil.app.constant.ConstantKbnBean;

/**
 * @author NOMOS) Yoshida
 * 作業・実績一覧で表示する項目を保持するBean
 *
 */
public class WorkArchiveBean {

	private String workStMark = "";
	private String shimei = "";
	private String workId = "";
	private String workNo = "";
	private String workNm = "";
	private String overView = "";
	private String customKbn = "";
	private String customNm = "";
	private String planEndYmd = "";
	private String endYmd = "";
	private float totalFuncp = 0;
	private float searchFuncp = 0;
	private float pgFuncp = 0;
	private float ptFuncp = 0;
	private float incidentalFuncp = 0;
	private float arcTotalFuncp = 0;
	private String updateYMD = "";
	private String updateHMS = "";


	/**
	 * コンストラクタ
	 */
	protected WorkArchiveBean() {
	}


	public String getShimei() {
		return shimei;
	}


	public void setShimei(String shimei) {
		this.shimei = shimei;
	}


	public String getWorkNo() {
		return workNo;
	}


	public void setWorkNo(String workNo) {
		this.workNo = workNo;
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
		this.setCustomNm(ConstantKbnBean.getCustomKbn(customKbn));
	}


	public String getCustomNm() {
		return customNm;
	}


	public void setCustomNm(String customNm) {
		this.customNm = customNm;
	}


	public String getPlanEndYmd() {
		return planEndYmd;
	}


	public void setPlanEndYmd(String planEndYmd) {
		this.planEndYmd = planEndYmd;
	}


	public String getEndYmd() {
		return endYmd;
	}


	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}


	public float getTotalFuncp() {
		return totalFuncp;
	}


	public void setTotalFuncp(float totalFuncp) {
		this.totalFuncp = totalFuncp;
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


	public float getArcTotalFuncp() {
		return arcTotalFuncp;
	}


	public void setArcTotalFuncp(float arcTotalFuncp) {
		this.arcTotalFuncp = arcTotalFuncp;
	}


	public String getWorkId() {
		return workId;
	}


	public void setWorkId(String workId) {
		this.workId = workId;
	}


	public float getIncidentalFuncp() {
		return incidentalFuncp;
	}


	public void setIncidentalFuncp(float incidentalFuncp) {
		this.incidentalFuncp = incidentalFuncp;
	}


	public String getWorkStMark() {
		return workStMark;
	}


	public void setWorkStMark(boolean isOverDate, boolean isOverFuncP) {

		if(isOverDate && isOverFuncP) {

			this.workStMark = Constant.ORVER_DATE_FUNCP;

		}else if(isOverDate) {

			this.workStMark = Constant.ORVER_DATE;

		}else if(isOverFuncP) {

			this.workStMark = Constant.ORVER_DATE_FUNCP;

		}else {

			this.workStMark = "";

		}

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
