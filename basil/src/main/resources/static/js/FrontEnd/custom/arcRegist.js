function sendData(){

	//送信処理開始時に二重送信防止のために、登録ボタンを不可視にする
	document.getElementById('registButton').style.display = "none";
	var isDelete = document.arcRegistTbl.isDelete.checked;

	if(isDelete && !confirmDoDelete(isDelete)){

		document.getElementById('registButton').style.display = "";
		return;

	}

	if(isEmpty(document.arcRegistTbl.targetDay.value, '作業日')){

		document.getElementById('registButton').style.display = "";
		return;

	}

	if(!confirm('処理を実行します。よろしいですか？')){

		document.getElementById('registButton').style.display = "";
		return;

	}

	document.arcRegistTbl.submit();

}