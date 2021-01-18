/**
 * 完了済みのものも含めるチェックボックスが変更されたときの処理
 * @returns
 */
function dispRefresh(){

	if(document.workArcChkBoxTbl.containFinishedFlg.checked){

		document.workArcChkBoxTbl.hdContainFinishedFlg.value = document.workArcChkBoxTbl.containFinishedFlg.value;

	}

	document.workArcChkBoxTbl.submit();

}

/**
 * 更新するために列をクリックした際の処理
 * @param WorkId
 * @returns
 */
function submitUpdateInfo(workId){

	document.workArchiveListTbl.workId.value = workId;
    document.workArchiveListTbl.submit();

}

