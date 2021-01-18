function sendData(obj, buttonNo){

	if(!chkDatePicker()){

		return;

	}

	document.dailyArcTbl.buttonNo.value = buttonNo;

	obj.submit();

}


function outPutFormat(){

	if(!chkDatePicker()){

		return;

	}

	document.formatTbl.targetDate.value = document.getElementById('flatpickrDate').value;
	document.formatTbl.submit();

}


function chkDatePicker(){

	if(document.getElementById('flatpickrDate').value == ''){

		alert('日付は必須入力です。');
		return false;

	}

	return true;

}
