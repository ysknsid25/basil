package jp.co.basil.app.workUpdate;

/**
 * @author NOMOS) Yoshida
 * ユーザー情報を管理するBean
 */
public class UserInfoBean {

	private int userNo = 0;
	private String shimei = "";


	public int getUserNo() {
		return userNo;
	}


	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}


	public String getShimei() {
		return shimei;
	}


	public void setShimei(String shimei) {
		this.shimei = shimei;
	}


}
