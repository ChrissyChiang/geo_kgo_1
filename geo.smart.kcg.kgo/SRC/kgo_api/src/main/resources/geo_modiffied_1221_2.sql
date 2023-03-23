
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_GROUP]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_COLUMN]
GO
DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_COLUMN_CHILD]
GO

DROP TABLE IF EXISTS [dbo].[GEO_KGO_APPOINTMENT_FORM_DETAIL]

GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_GROUP] (
    [AppointmentMainId] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [GroupSeq] int  IDENTITY(1,1) NOT NULL,
    [Version] int  NOT NULL,
    [Memo] nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [OrderNum] int  NULL,
    [IsShow] int,
    [CheckFrequencyPeriod] varchar(30) NULL,
    CONSTRAINT [PK_KGO_CASESET_GROUP_copy_1_copy_1] PRIMARY KEY CLUSTERED ([AppointmentMainId], [GroupSeq], [Version])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_GROUP] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約主檔ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'AppointmentMainId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'群組序號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'GroupSeq'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'備註',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'Memo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'顯示順序',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'OrderNum'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否顯示',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'IsShow'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'重複檢核時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP',
    'COLUMN', N'CheckFrequencyPeriod'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單 設定群組',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_GROUP'
    GO

    CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_COLUMN] (
    [AppointmentMainId] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [GroupSeq] int  NOT NULL,
    [Version] int  NOT NULL,
    [ColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [ColumnName] nvarchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [ColumnType] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [ColumnValue] nvarchar(4000) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [OrderNum] int  NULL,
    [Length] int  NULL,
    [IsMustKey] bit  NULL,
    [Memo] nvarchar(500) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [FileType] varchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [IsCheckFrequency] int NULL,
    [IsFieldCheck] int NULL,
    CONSTRAINT [PK_KGO_CASESET_COLUMN_copy_1_copy_1] PRIMARY KEY CLUSTERED ([AppointmentMainId], [Version], [ColumnId])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_COLUMN] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約主黨ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'AppointmentMainId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'群組序號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'GroupSeq'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'ColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位名稱',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'ColumnName'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位型態',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'ColumnType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位設定值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'ColumnValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'顯示順序',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'OrderNum'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位長度',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'Length'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否必填',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'IsMustKey'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位備註',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'Memo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'附件,上傳檔案限制副檔名',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'FileType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否重複檢核0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'IsCheckFrequency'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N' 欄位勾選0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN',
    'COLUMN', N'IsFieldCheck'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單 主欄位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN'
    GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_COLUMN_CHILD] (
    [CColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [AppointmentMainId] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [Version] int  NOT NULL,
    [ColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [ColumnType] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [ColumnValue] nvarchar(4000) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [Length] int  NULL,
    [IsMustKey] bit  NULL,
    [Memo] nvarchar(500) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [PColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [FText] nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [BText] nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [VGroup] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [OrderNum] int  NOT NULL,
    [Row] int  NULL,
    [IsCheckFrequency] int NULL,
    [IsFieldCheck] int NULL,
    CONSTRAINT [PK_KGO_CASESET_COLUMN_CHILD_copy_1_copy_1] PRIMARY KEY CLUSTERED ([CColumnId], [AppointmentMainId], [Version], [ColumnId], [OrderNum])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_COLUMN_CHILD] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'複合欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'CColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約主檔ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'AppointmentMainId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'ColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位種類',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'ColumnType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'ColumnValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位長度',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'Length'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否必填',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'IsMustKey'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位備註',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'Memo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'父欄位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'PColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'前文字',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'FText'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'後文字',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'BText'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'群組編號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'VGroup'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'顯示順序',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'OrderNum'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'所在行數',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'Row'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否重複檢核0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'IsCheckFrequency'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N' 欄位勾選0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD',
    'COLUMN', N'IsFieldCheck'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單子欄位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_COLUMN_CHILD'
    GO

CREATE TABLE [dbo].[GEO_KGO_APPOINTMENT_FORM_DETAIL] (
    [AppointmentId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [ColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [Version] int  NOT NULL,
    [ColumnValue] nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [IsSource] bit  NULL,
    [IsCorrect] bit  NULL,
    [CorrectMemo] nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [CColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [CorrectBValue] nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [CommentId] nvarchar(64),
    CONSTRAINT [PK_KGO_CASE_DETAIL_copy_1_copy_1] PRIMARY KEY CLUSTERED ([AppointmentId], [ColumnId], [Version], [CColumnId])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_APPOINTMENT_FORM_DETAIL] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約單ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'AppointmentId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'ColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位設定值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'ColumnValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'來源',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'IsSource'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'需要補正',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'IsCorrect'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'補正說明',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'CorrectMemo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'複合欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'CColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'補正值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'CorrectBValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'簽核意見id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL',
    'COLUMN', N'CommentId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'預約表單 預約資料檔',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_APPOINTMENT_FORM_DETAIL'
    GO