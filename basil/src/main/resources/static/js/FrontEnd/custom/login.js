/**
 * チェック処理からのSubmit
 */
function chkLogin(){

	var userId = document.logintbl.userId.value;
	var pass = document.logintbl.password.value;

	if(isEmpty(userId, "ユーザーID") || isEmpty(pass, "パスワード") || !confirm("処理を実行します。よろしいですか？")){

		return;

	}

	document.logintbl.submit();

}