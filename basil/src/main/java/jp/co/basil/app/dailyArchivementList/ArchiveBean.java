package jp.co.basil.app.dailyArchivementList;

/**
 * @author NOMOS) Yoshida
 * DAILY_ARC_VIWに対するエンティティ
 *
 */
public class ArchiveBean {

	private String registedDay = "";
	private String shimei = "";
	private String workId = "";
	private String workNo = "";
	private String workNm = "";
	private float planFuncp = 0;
	private float funcp = 0;


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


	public float getFuncp() {
		return funcp;
	}


	public void setFuncp(float funcp) {
		this.funcp = funcp;
	}


	public String getRegistedDay() {
		return registedDay;
	}


	public void setRegistedDay(String registedDay) {
		this.registedDay = registedDay;
	}


	public String getWorkId() {
		return workId;
	}


	public void setWorkId(String workId) {
		this.workId = workId;
	}


	public float getPlanFuncp() {
		return planFuncp;
	}


	public void setPlanFuncp(float planFuncp) {
		this.planFuncp = planFuncp;
	}

}
