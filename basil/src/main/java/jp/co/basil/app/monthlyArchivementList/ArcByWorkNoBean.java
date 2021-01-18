package jp.co.basil.app.monthlyArchivementList;

/**
 * @author NOMOS) Yoshida
 * 案件別の作業予実情報を保持するためのBeanです。
 *
 */
public class ArcByWorkNoBean {

	private String workNo = "";
	private String workNm = "";
	private String tantosha = "";
	private String arcTotalFuncp = "";
	private String planTotalFuncp = "";
	private String planEndYmd = "";
	private String endYmd = "";
	private String shintyokuritu = "";

	/**
	 * コンストラクタ
	 */
	public ArcByWorkNoBean() {
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

	public String getTantosha() {
		return tantosha;
	}

	public void setTantosha(String tantosha) {
		this.tantosha = tantosha;
	}

	public String getArcTotalFuncp() {
		return arcTotalFuncp;
	}

	public void setArcTotalFuncp(String arcTotalFuncp) {
		this.arcTotalFuncp = arcTotalFuncp;
	}

	public String getPlanTotalFuncp() {
		return planTotalFuncp;
	}

	public void setPlanTotalFuncp(String planTotalFuncp) {
		this.planTotalFuncp = planTotalFuncp;
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

	public String getShintyokuritu() {
		return shintyokuritu;
	}

	public void setShintyokuritu(String shintyokuritu) {

		if(shintyokuritu == null || shintyokuritu.isEmpty()) {

			this.shintyokuritu = shintyokuritu;

		}else {

			this.shintyokuritu = shintyokuritu + "%";

		}

	}

}
