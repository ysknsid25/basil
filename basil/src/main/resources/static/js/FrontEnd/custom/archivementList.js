function sendData(obj){

	obj.submit();

}

function specialSendData(obj){

	document.archiveRegistButton.specialFlg.value='1';
	obj.submit();

}


function changeDisp(){

	if(document.getElementById('dispChkBox').checked){

		document.getElementById('tantoWorkTable').style.display = "";

	}else{

		document.getElementById('tantoWorkTable').style.display = "none";

	}

}


