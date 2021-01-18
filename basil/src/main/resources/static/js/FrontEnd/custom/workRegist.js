function sendData(){

	//送信処理開始時に二重送信防止のために、登録ボタンを不可視にする
	document.getElementById('registButton').style.display = "none";

	//削除チェックボックスが選択されているのであれば、必須チェックなどは不要でそのままSubmit
	var objCheckBox = document.workRegistTbl.isDelete;
	if(typeof objCheckBox !== "undefined" && confirmDoDelete(objCheckBox.checked)){

		document.workRegistTbl.submit();

	}

	//作業番号・作業名・開始予定日・修了予定日・工数の必須チェック
	if(chkEmpty()){

		document.getElementById('registButton').style.display = "";
		return;

	}

	//日付の整合性チェック
	if(dateCheck(document.workRegistTbl.beginYMD.value, document.workRegistTbl.endYMD.value)){

		alert('開始予定日に終了予定日以降が選択されています');
		document.getElementById('registButton').style.display = "";
		return;

	}

	//工数の確認
	if(!isValidFuncp()){

		document.getElementById('registButton').style.display = "";
		return;

	}

	decideWorkNo(document.workRegistTbl.workNoB.value);

	if(!confirm('処理を実行します。よろしですか？')){

		document.getElementById('registButton').style.display = "";
		return;

	}

	document.workRegistTbl.submit();

}


/**
 * 作業情報登録・更新における必須チェック
 * @returns
 */
function chkEmpty(){

	//作業番号・作業名・開始予定日・修了予定日・工数の必須チェック
	var workNoB = document.workRegistTbl.workNoB.value;
	var isEmptyVal = isEmpty(workNoB, '案件番号')
					|| isEmpty(document.workRegistTbl.workNm.value, '作業名')
					|| isEmpty(document.workRegistTbl.beginYMD.value, '開始予定日')
					|| isEmpty(document.workRegistTbl.endYMD.value, '終了予定日')
					|| isEmpty(document.workRegistTbl.planTotalFuncp.value, '予定総工数')
					|| isEmpty(document.workRegistTbl.planSearchFuncp.value, '予定調査工数')
					|| isEmpty(document.workRegistTbl.planPGFuncp.value, '予定PG工数')
					|| isEmpty(document.workRegistTbl.planPTFuncp.value, '予定PT工数')
					|| isEmpty(document.workRegistTbl.incidentalFuncp.value, '予定付帯工数');

	return isEmptyVal;

}


/**
 * 工数についてチェック
 * @returns
 */
function isValidFuncp(){

	var planTotalFuncp = document.workRegistTbl.planTotalFuncp.value;
	var planSearchFuncp = document.workRegistTbl.planSearchFuncp.value;
	var planPGFuncp = document.workRegistTbl.planPGFuncp.value;
	var planPTFuncp = document.workRegistTbl.planPTFuncp.value;
	var incidentalFuncp = document.workRegistTbl.incidentalFuncp.value;

	var pattern = /^([1-9]\d*|0)(\.\d+)?$/;

	var isValidNum = isValidNumber(planTotalFuncp, '予定総工数', pattern)
						&& isValidNumber(planSearchFuncp, '予定調査工数', pattern)
						&& isValidNumber(planPGFuncp, '予定PG工数', pattern)
						&& isValidNumber(planPTFuncp, '予定PT工数', pattern)
						&& isValidNumber(incidentalFuncp, '予定付帯工数', pattern);

	if(!isValidNum){

		return false;

	}

	var sumFuncp = Number(planSearchFuncp) + Number(planPGFuncp) + Number(planPTFuncp) + Number(incidentalFuncp);

	var isValidSum = isEqualsSumValue(planTotalFuncp, sumFuncp);

	return isValidSum;

}


/**
 * 案件番号の入力の仕方によって値を決める
 */
function decideWorkNo(){

	if(document.workRegistTbl.workNoA.value == ''){

		document.workRegistTbl.workNo.value = document.workRegistTbl.workNoB.value;

	}else{

		document.workRegistTbl.workNo.value = document.workRegistTbl.workNoA.value + '-' + document.workRegistTbl.workNoB.value;

	}

}


/**
 * 処理後のメッセージを表示する。
 * @param isUpdated
 * @returns
 */
function finUpdate(isUpdated){

	if(isUpdated){

		alert('処理が完了しました。');

	}

}


/**
 * 	入力された総工数に応じて他の工数の値を変更する。
 * @returns
 */
function calcFuncp(){

	var totalFuncP = document.workRegistTbl.planTotalFuncp.value;

	document.workRegistTbl.planSearchFuncp.value = totalFuncP*0.2;
	document.workRegistTbl.planPGFuncp.value = totalFuncP*0.5;
	document.workRegistTbl.planPTFuncp.value = totalFuncP*0.25;
	document.workRegistTbl.incidentalFuncp.value = totalFuncP*0.05;

}

