#��ƃe�[�u��
CREATE TABLE WORK_TBL (
	WORK_ID	         INT PRIMARY KEY,
	USER_NO	         INT NOT NULL,
	PROJECT_ID	     INT NOT NULL,
	WORK_NO	         VARCHAR(10) NOT NULL,
	WORK_NM	         VARCHAR(100) NOT NULL,
	ORVERVIEW	       VARCHAR(500) NOT NULL,
	WORK_KBN	       VARCHAR(1),
	CUSTOM_KBN	     VARCHAR(1) NOT NULL,
	PROCESS_KBN	     VARCHAR(1),
	PLAN_BEGIN_YMD	 VARCHAR(10) NOT NULL,
	PLAN_END_YMD	   VARCHAR(10) NOT NULL,
	TOTAL_FUNCP	     FLOAT NOT NULL,
	SERACH_FUNCP	   FLOAT NOT NULL,
	PG_FUNCP	       FLOAT NOT NULL,
	PT_FUNCP	       FLOAT NOT NULL,
	INCIDENTAL_FUNCP FLOAT NOT NULL,
	UPDATE_YMD	     VARCHAR(10) NOT NULL,
	UPDATE_HMS	     VARCHAR(12) NOT NULL
);

#��ƃe�[�u���̃f�[�^
INSERT INTO WORK_TBL VALUES(0, 1, 1, '01', '�e�X�g���0', '�����ς݂̃e�X�g', '2', '2', '0', '2020/04/10' , '2020/04/30',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, '01', '�e�X�g���1', '�e�X�g�p�̍�ƈ�ڂł��B', '0', '2', '0', '2020/04/10' , '2020/04/30',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, '02', '�e�X�g���2', '�e�X�g�p�̍��2�ڂł��B', '0', '2', '1','2020/04/30' , '2020/05/01',
60, 5, 30, 20, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, '03', '�e�X�g���3', '�e�X�g�p�̍��3�ڂł��B', '0', '1', '0','2020/05/01' , '2020/05/20',
100, 5, 60, 30, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 1, 1, 'XXXX-01', '�e�X�g���4', '�e�X�g�p�̍��4�ڂł��B', '0', '2', '1','2020/05/21' , '2020/05/31',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 2, 1, '04', '�e�X�g���5', '�e�X�g�p�̍��5�ڂł��B', '0', '2', '0', '2020/04/10' , '2020/04/20',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 2, 1, 'XXXX-02', '�e�X�g���6', '�e�X�g�p�̍��6�ڂł��B', '0', '1', '1', '2020/04/21' , '2020/04/30',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
INSERT INTO WORK_TBL VALUES(nextval('WORK_ID'), 3, 1, '05', '�e�X�g���7', '�e�X�g�p�̍��7�ڂł��B', '0', '2', '0', '2020/05/01' , '2020/05/05',
40, 5, 20, 10, 5, date_format(CURDATE(), '%Y/%m/%d'), SUBSTRING(date_format(CURTIME(), '%H:%i:%S.%f'), 1, 12));
