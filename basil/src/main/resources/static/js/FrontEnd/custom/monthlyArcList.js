/**
 * 案件別実績一覧の表示を切り替える
 */
function dispTotalFuncTable(){

	if(document.getElementById('dispChanger').checked){

		document.getElementById('totalTable').style.display = "";

	}else{

		document.getElementById('totalTable').style.display = "none";

	}

}


/**
 * 完了済み案件一覧の表示を切り替える
 * @returns
 */
function dispFinFuncTable(){

	if(document.getElementById('dispChangerFin').checked){

		document.getElementById('totalFinTable').style.display = "";

	}else{

		document.getElementById('totalFinTable').style.display = "none";

	}

}


