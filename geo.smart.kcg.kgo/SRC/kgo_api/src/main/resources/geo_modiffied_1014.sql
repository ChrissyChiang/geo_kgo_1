
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'GEO_KGO_APPOINTMENT_MAIN'))
    BEGIN
        ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_CONTACT_USER] DROP CONSTRAINT IF EXISTS [fk_GEO_KGO_APPOINTMENT_CONTACT_USER_GEO_KGO_APPOINTMENT_CONTACT_USER_1]
    END
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'GEO_KGO_APPOINTMENT_BLOCK_USER'))
    BEGIN
        ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_BLOCK_USER] DROP CONSTRAINT IF EXISTS [fk_GEO_KGO_APPOINTMENT_BLOCK_USER_GEO_KGO_APPOINTMENT_BLOCK_USER_1]
    END
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'GEO_KGO_APPOINTMENT_DETAIL'))
    BEGIN
        ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL] DROP CONSTRAINT IF EXISTS [fk_GEO_KGO_APPOINTMENT_DETAIL_GEO_KGO_APPOINTMENT_DETAIL_1]
    END
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'GEO_KGO_APPOINTMENT_DETAIL_TIME'))
    BEGIN
        ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL_TIME] DROP CONSTRAINT IF EXISTS [fk_GEO_KGO_APPOINTMENT_DETAIL_TIME_GEO_KGO_APPOINTMENT_DETAIL_TIME_1]
    END
GO
IF EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'GEO_KGO_APPOINTMENT_DETAIL_NUMBERS'))
    BEGIN
        ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL_NUMBERS] DROP CONSTRAINT IF EXISTS [fk_GEO_KGO_APPOINTMENT_DETAIL_NUMBER_GEO_KGO_APPOINTMENT_DETAIL_NUMBER_1]
    END
GO

DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_MAIN]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_CONTACT_USER]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_BLOCK_USER]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_DETAIL]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_DETAIL_TIME] 
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_DETAIL_NUMBERS]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT]
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_MAIN] (
  [appointment_main_id] varchar(50) NOT NULL,
  [organ_id] varchar(50) NOT NULL,
  [unit_id] varchar(50) NOT NULL,
  [appointment_name] nvarchar(100),
  [appointment_status] varchar(30),
  [mydata_url] varchar(500),
  [edit_organ] varchar(50),
  [edit_user] varchar(50),
  [edit_time] datetime2(0),
  CONSTRAINT [_copy_3_copy_1] PRIMARY KEY CLUSTERED ([appointment_main_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃名稱主檔id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'appointment_main_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'機關id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'organ_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'科室id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'unit_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃名稱',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'appointment_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'啟用狀態（on, off, delete）',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'appointment_status'
GO

EXEC sp_addextendedproperty
'MS_Description', N'myData連結網址',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'mydata_url'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯單位',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'edit_organ'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯人員',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'edit_user'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN',
'COLUMN', N'edit_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃主檔',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_MAIN'
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_CONTACT_USER] (
  [user_id] varchar(50) NOT NULL,
  [appointment_main_id] varchar(50) NOT NULL,
  [organ_id] varchar(50),
  [unit_id] varchar(50),
  [user_name] nvarchar(50),
  [edit_organ] varchar(50),
  [edit_user] varchar(50),
  [edit_time] datetime2(0),
  CONSTRAINT [_copy_3_copy_1_copy_1] PRIMARY KEY CLUSTERED ([user_id], [appointment_main_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'人員id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃主檔id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'appointment_main_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'機關id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'organ_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'科室id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'unit_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'承辦人姓名',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'user_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯單位',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'edit_organ'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯人員',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'edit_user'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER',
'COLUMN', N'edit_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃承辦人
',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_CONTACT_USER'
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_BLOCK_USER] (
  [block_user_id] varchar(30) NOT NULL,
  [appointment_main_id] varchar(50) NOT NULL,
  [block_use_name] nvarchar(50) NULL,
  [block_user_identity] varchar(50) NULL,
  [block_start_time] datetime2(0) NULL,
  [block_end_time] datetime2(0) NULL,
  [is_trigger_block] int,
  [edit_organ] varchar(50),
  [edit_user] varchar(50),
  [edit_time] datetime2(0),
  CONSTRAINT [_copy_3_copy_1_copy_2] PRIMARY KEY CLUSTERED ([block_user_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'黑名單使用者id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'block_user_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃主檔id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'appointment_main_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'黑名單使用者姓名',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'block_use_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'黑名單使用者身分證字號',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'block_user_identity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'黑名單鎖定起始時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'block_start_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'黑名單鎖定結束時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'block_end_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否啟用封鎖(GeoBooleanType)',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'is_trigger_block'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯單位',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'edit_organ'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯人員',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'edit_user'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER',
'COLUMN', N'edit_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃名稱黑名單',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_BLOCK_USER'
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL] (
  [appointment_detail_id] varchar(50) NOT NULL,
  [appointment_main_id]  varchar(50) NOT NULL,
  [location] nvarchar(150) NULL,
  [appointment_detail_date] datetime2(0) NULL,
  [earliest_time] varchar(30) NULL,
  [latest_time] varchar(30) NULL,
  [earliest_day] int NULL,
  [latest_day] int NULL,
  [is_enable] int NULL,
  [edit_organ] varchar(50),
  [edit_user] varchar(50),
  [edit_time] datetime2(0),
  CONSTRAINT [_copy_3_copy_1_copy_2_copy_1] PRIMARY KEY CLUSTERED ([appointment_detail_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'appointment_detail_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃主檔id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'appointment_main_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃地點',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'location'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約日期',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'appointment_detail_date'
GO

EXEC sp_addextendedproperty
'MS_Description', N'最早可預約時間 HH:mm',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'earliest_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'最晚可預約時間 HH:mm',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'latest_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'最早可預約天數 ',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'earliest_day'
GO

EXEC sp_addextendedproperty
'MS_Description', N'最晚可預約天數',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'latest_day'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否提供線上預約臨櫃服務(GeoBooleanType)',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'is_enable'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯單位',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'edit_organ'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯人員',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'edit_user'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL',
'COLUMN', N'edit_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL'
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL_TIME] (
  [appointment_detail_time_id] varchar(50) NOT NULL,
  [appointment_detail_id] varchar(50) NOT NULL,
  [start_time] datetime2(0) NULL,
  [end_time] datetime2(0) NULL,
  [available_user_quota] int NULL,
  CONSTRAINT [_copy_3_copy_1_copy_2_copy_1_copy_1] PRIMARY KEY CLUSTERED ([appointment_detail_time_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-預約時段id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_TIME',
'COLUMN', N'appointment_detail_time_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_TIME',
'COLUMN', N'appointment_detail_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約時段起始',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_TIME',
'COLUMN', N'start_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約時段結束',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_TIME',
'COLUMN', N'end_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'可預約人數',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_TIME',
'COLUMN', N'available_user_quota'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-預約時段',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_TIME'
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL_NUMBERS] (
  [appointment_detail_numbers_id] varchar(50) NOT NULL,
  [appointment_detail_time_id] varchar(50) NOT NULL,
  [number_name] nvarchar(100) NULL,
  [is_used] int NULL,
  CONSTRAINT [_copy_3_copy_1_copy_2_copy_1_copy_1_copy_1_copy_6] PRIMARY KEY CLUSTERED ([appointment_detail_numbers_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
  )
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-號碼牌id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_NUMBERS',
'COLUMN', N'appointment_detail_numbers_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-預約時段id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_NUMBERS',
'COLUMN', N'appointment_detail_time_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'號碼牌名稱',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_NUMBERS',
'COLUMN', N'number_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否已使用(GeoBooleanType)',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_NUMBERS',
'COLUMN', N'is_used'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-號碼牌',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT_DETAIL_NUMBERS'
GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT] (
  [appointment_id] varchar(50) NOT NULL,
  [appointment_main_id] varchar(50) NOT NULL,
  [appointment_detail_time_id] varchar(50) NOT NULL,
  [appointment_detail_numbers_id] varchar(50) NULL,
  [appointment_identity] varchar(50) NULL,
  [appointment_name] nvarchar(100) NULL,
  [appointment_email] varchar(100) NULL,
  [appointment_phone] varchar(50) NULL,
  [is_available] int,
  [edit_time] datetime2(0),
  [is_online] int NULL,
  CONSTRAINT [_copy_3_copy_1_copy_3] PRIMARY KEY CLUSTERED ([appointment_id])
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約單id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃主檔id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_main_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-預約時段id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_detail_time_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃細節-號碼牌id',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_detail_numbers_id'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約者身分證字號',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_identity'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約者姓名',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_name'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約者電子信箱',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_email'
GO

EXEC sp_addextendedproperty
'MS_Description', N'預約者聯絡電話',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'appointment_phone'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否有效(GeoBooleanType)，取消無效',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'is_available'
GO

EXEC sp_addextendedproperty
'MS_Description', N'編輯時間',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'edit_time'
GO

EXEC sp_addextendedproperty
'MS_Description', N'是否線上預約(GeoBooleanType)',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT',
'COLUMN', N'is_online'
GO

EXEC sp_addextendedproperty
'MS_Description', N'線上預約臨櫃-預約主檔',
'SCHEMA', N'dbo',
'TABLE', N'GEO_KGO_APPOINTMENT'
GO


ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_CONTACT_USER] ADD CONSTRAINT [fk_GEO_KGO_APPOINTMENT_CONTACT_USER_GEO_KGO_APPOINTMENT_CONTACT_USER_1] FOREIGN KEY ([appointment_main_id]) REFERENCES [dbo].[GEO_KGO_APPOINTMENT_MAIN] ([appointment_main_id])
GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_BLOCK_USER] ADD CONSTRAINT [fk_GEO_KGO_APPOINTMENT_BLOCK_USER_GEO_KGO_APPOINTMENT_BLOCK_USER_1] FOREIGN KEY ([appointment_main_id]) REFERENCES [dbo].[GEO_KGO_APPOINTMENT_MAIN] ([appointment_main_id])
GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL] ADD CONSTRAINT [fk_GEO_KGO_APPOINTMENT_DETAIL_GEO_KGO_APPOINTMENT_DETAIL_1] FOREIGN KEY ([appointment_main_id]) REFERENCES [dbo].[GEO_KGO_APPOINTMENT_MAIN] ([appointment_main_id])
GO

ALTER TABLE  [dbo].[GEO_KGO_APPOINTMENT_DETAIL_TIME] ADD CONSTRAINT [fk_GEO_KGO_APPOINTMENT_DETAIL_TIME_GEO_KGO_APPOINTMENT_DETAIL_TIME_1] FOREIGN KEY ([appointment_detail_id]) REFERENCES [dbo].[GEO_KGO_APPOINTMENT_DETAIL] ([appointment_detail_id])
GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_DETAIL_NUMBERS] ADD CONSTRAINT [fk_GEO_KGO_APPOINTMENT_DETAIL_NUMBER_GEO_KGO_APPOINTMENT_DETAIL_NUMBER_1] FOREIGN KEY ([appointment_detail_time_id]) REFERENCES [dbo].[GEO_KGO_APPOINTMENT_DETAIL_TIME] ([appointment_detail_time_id])
GO


