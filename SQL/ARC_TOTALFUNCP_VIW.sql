#�Č��E���[�U�[�ʂɑ��H�����擾���邽�߂�VIWE
CREATE VIEW ARC_TOTALFUNCP_VIW (

	WORK_ID,
    USER_NO,
    ARC_TOTAL_FUNCP

)
AS
select
	WORK_ID,
	USER_NO,
    SUM(FUNCP) as ARC_TOTAL_FUNCP
from
	ACHIEVE_TBL
group by
	WORK_ID,
    USER_NO;
