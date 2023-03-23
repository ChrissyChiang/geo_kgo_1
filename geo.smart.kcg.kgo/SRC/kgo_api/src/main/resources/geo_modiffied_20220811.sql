------0811 線上預約臨櫃身份驗證---------

create table GEO_KGO_APPOINTMENT_CHECK
(
    appointment_main_id varchar(30) not null,
    check_type   varchar(30) not null,
    constraint PK_GEO_KGO_APPOINTMENT_CHECK_id_type
        primary key (appointment_main_id, check_type)
)
    go

exec sp_addextendedproperty 'MS_Description', '線上預約臨櫃身分驗證', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_APPOINTMENT_CHECK'
go

exec sp_addextendedproperty 'MS_Description', '線上預約主檔id', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_APPOINTMENT_CHECK', 'COLUMN',
     'appointment_main_id'
go

exec sp_addextendedproperty 'MS_Description', '身份驗證', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_APPOINTMENT_CHECK', 'COLUMN',
     'check_type'
go


------0811 增加啟用同意、說明內容---------
ALTER TABLE GEO_KGO_APPOINTMENT_MAIN ADD is_service_html Integer , service_html nvarchar(max)