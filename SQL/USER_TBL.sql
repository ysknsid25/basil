#���[�U�[�e�[�u��
CREATE TABLE USER_TBL (
	USER_NO	      int PRIMARY KEY,
	USER_ID	      varchar(10) NOT NULL,
	PASSWORD      varchar(10) NOT NULL,
	SHIMEI	      varchar(20) NOT NULL,
	AUTHORITY_KBN varchar(1) NOT NULL,
	NOTIFICATION varchar(1),
    UNIQUE idPass_index (USER_ID, PASSWORD)
);

#���[�U�[�e�[�u���̃e�X�g�f�[�^
INSERT INTO USER_TBL values (1, 'satou', 'tarou', '����', '1', '1');
INSERT INTO USER_TBL values (2, 'suzuki', 'jiro', '���', '0', '1');
INSERT INTO USER_TBL values (3, 'tanaka', 'saburo', '�c��', '0', '');
