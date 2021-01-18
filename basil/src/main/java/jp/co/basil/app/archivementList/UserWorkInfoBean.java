package jp.co.basil.app.archivementList;

/**
 * @author NOMOS) Yoshida
 * ログインユーザーに登録された作業情報を保持します。
 *
 */
public class UserWorkInfoBean {

	private String workNo = "";
	private String workNm = "";
	private String planBeginDay = "";
	private String planEndDay = "";
	private String beginDay = "";
	private String endDay = "";
	private float planTotalFunpc = 0;
	private float arcTotalFuncp = 0;

	/**
	 * コンストラクタ
	 */
	public UserWorkInfoBean() {
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

	public String getPlanBeginDay() {
		return planBeginDay;
	}

	public void setPlanBeginDay(String planBeginDay) {
		this.planBeginDay = planBeginDay;
	}

	public String getPlanEndDay() {
		return planEndDay;
	}

	public void setPlanEndDay(String planEndDay) {
		this.planEndDay = planEndDay;
	}

	public String getBeginDay() {
		return beginDay;
	}

	public void setBeginDay(String beginDay) {
		this.beginDay = beginDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public float getPlanTotalFunpc() {
		return planTotalFunpc;
	}

	public void setPlanTotalFunpc(float planTotalFunpc) {
		this.planTotalFunpc = planTotalFunpc;
	}

	public float getArcTotalFuncp() {
		return arcTotalFuncp;
	}

	public void setArcTotalFuncp(float arcTotalFuncp) {
		this.arcTotalFuncp = arcTotalFuncp;
	}

}
