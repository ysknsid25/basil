#実績テーブル
CREATE TABLE ACHIEVE_TBL (
	WORK_ID	     INT,
	USER_NO	     INT,
	REGISTED_YMD VARCHAR(10),
	FUNCP        FLOAT NOT NULL,
	CONSTRAINT PK_ACHIEVE_TBL PRIMARY KEY(WORK_ID, USER_NO, REGISTED_YMD)
);

#実績テーブルのデータ
INSERT INTO ACHIEVE_TBL VALUES(1, 1, '2020/04/22', 8);
INSERT INTO ACHIEVE_TBL VALUES(2, 1, '2020/04/22', 2);
INSERT INTO ACHIEVE_TBL VALUES(1, 1, '2020/04/23', 8);
INSERT INTO ACHIEVE_TBL VALUES(1, 1, '2020/04/24', 4.5);
INSERT INTO ACHIEVE_TBL VALUES(2, 1, '2020/05/01', 4.5);
INSERT INTO ACHIEVE_TBL VALUES(5, 2, '2020/04/22', 8);
INSERT INTO ACHIEVE_TBL VALUES(5, 2, '2020/05/01', 8);
INSERT INTO ACHIEVE_TBL VALUES(6, 2, '2020/04/22', 6);
INSERT INTO ACHIEVE_TBL VALUES(6, 2, '2020/05/02', 3);
INSERT INTO ACHIEVE_TBL VALUES(7, 3, '2020/04/22', 2);
INSERT INTO ACHIEVE_TBL VALUES(7, 3, '2020/04/23', 3);
INSERT INTO ACHIEVE_TBL VALUES(7, 3, '2020/04/24', 4);
INSERT INTO ACHIEVE_TBL VALUES(7, 3, '2020/04/25', 5);
