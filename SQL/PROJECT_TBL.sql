#プロジェクトテーブル
CREATE TABLE PROJECT_TBL (
	PROJECT_ID    INT PRIMARY KEY,
	PROJECT_NM    VARCHAR(100) NOT NULL,
	BEGIN_YMD     VARCHAR(10) NOT NULL,
	END_YMD       VARCHAR(10) NOT NULL,
	LEADER_NO     INT NOT NULL,
	SUB_LEADER_NO INT,
	UPDATE_YMD    VARCHAR(10) NOT NULL,
	UPDATE_HMS    VARCHAR(12) NOT NULL
);

#プロジェクトのテストデータ
INSERT INTO PROJECT_TBL VALUES(nextval('PROJECT_ID'), 'テストプロジェクト', '2020/04/05', '2021/04/05', 1, NULL, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
