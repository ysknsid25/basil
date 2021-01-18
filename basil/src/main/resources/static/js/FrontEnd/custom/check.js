/**
 * 必須チェック
 * @param strValue
 * @param targetNm
 * @returns
 */
function isEmpty(strValue, targetNm){

	var isEmpty = strValue == '';

	if(isEmpty){

		alert(targetNm + 'は必須入力です。');

	}

	return isEmpty;

}


/**
 * 数字チェック
 * @param numValue
 * @param targetNm
 * @returns
 */
function isValidNumber(numValue, targetNm, pattern){

	  var isValidNumber = pattern.test(numValue);

	  if(!isValidNumber){

			alert(targetNm + 'は正の数字のみ入力可能です。');

	  }

	return isValidNumber;

}


/**
 * 日付の整合性チェック
 * @param beginDate
 * @param endDate
 * @returns
 */
function dateCheck(beginDate, endDate){

	replacedBeginDate = Number(beginDate.replace('-',''));
	replacedEndDate = Number(endDate.replace('-',''));

	return beginDate > endDate;

}


/**
 * 合計値が合致しているかを検証する
 * @param indicateNum 指標値
 * @param sumNum 合計した値
 * @returns
 */
function isEqualsSumValue(indicateNum, sumNum){

	if(indicateNum < sumNum){

		alert('合計値が指標値を超えています');
		return false;

	}else if(indicateNum > sumNum){

		alert('合計値が指標値を下回っています');
		return false;

	}

	return true;

}


/**
 * 削除チェックボックスがチェックされているか確認します
 * @param isChecked 削除チェックボックスがチェックされているか
 * @returns はいを押下するとtrue。チェックボックスがチェックされていなければ無条件でtrue。
 */
function confirmDoDelete(isChecked){

	if(isChecked){

		return confirm('削除処理を行います。よろしいですか？');

	}

	return false;

}

