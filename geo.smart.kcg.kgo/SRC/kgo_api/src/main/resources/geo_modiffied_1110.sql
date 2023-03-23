-- 機關審核表單 案件資料檔
DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASE_DETAIL_ORGAN]
    GO

CREATE TABLE [dbo].[GEO_KGO_CASE_DETAIL_ORGAN] (
    [CaseId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [ColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [Version] int  NOT NULL,
    [ColumnValue] nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [IsSource] bit  NULL,
    [IsCorrect] bit  NULL,
    [CorrectMemo] nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [CColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [CorrectBValue] nvarchar(max) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [comment_id] nvarchar(64),
    [CaseFormVersion] int NOT NULL,
    CONSTRAINT [PK_KGO_CASE_DETAIL_copy_1] PRIMARY KEY CLUSTERED ([CaseId], [ColumnId], [Version], [CColumnId], [CaseFormVersion])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_CASE_DETAIL_ORGAN] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'CaseId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'ColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位設定值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'ColumnValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'來源',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'IsSource'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'需要補正',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'IsCorrect'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'補正說明',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'CorrectMemo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'複合欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'CColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'補正值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'CorrectBValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'簽核意見id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'comment_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'一般表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN',
    'COLUMN', N'CaseFormVersion'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單 案件資料檔',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASE_DETAIL_ORGAN'
    GO
-- 機關審核表單 服務設定子欄位
DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASESET_COLUMN_CHILD_ORGAN]
    GO
CREATE TABLE [dbo].[GEO_KGO_CASESET_COLUMN_CHILD_ORGAN] (
    [CColumnId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [CaseSetId] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
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
    [CaseFormVersion] int NOT NULL,
    CONSTRAINT [PK_KGO_CASESET_COLUMN_CHILD_copy_1] PRIMARY KEY CLUSTERED ([CColumnId], [CaseSetId], [Version], [ColumnId], [OrderNum], [CaseFormVersion])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_CASESET_COLUMN_CHILD_ORGAN] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'複合欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'CColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件種類ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'CaseSetId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'ColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位種類',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'ColumnType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'ColumnValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位長度',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'Length'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否必填',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'IsMustKey'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位備註',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'Memo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'父欄位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'PColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'前文字',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'FText'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'後文字',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'BText'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'群組編號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'VGroup'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'顯示順序',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'OrderNum'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'所在行數',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'Row'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否重複檢核0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'IsCheckFrequency'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N' 欄位勾選0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'IsFieldCheck'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'一般表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN',
    'COLUMN', N'CaseFormVersion'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單 服務設定子欄位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_CHILD_ORGAN'
    GO
-- 機關審核表單 服務設定欄位
DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASESET_COLUMN_ORGAN]
    GO
CREATE TABLE [dbo].[GEO_KGO_CASESET_COLUMN_ORGAN] (
    [CaseSetId] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
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
    [CaseFormVersion] int NOT NULL,
    CONSTRAINT [PK_KGO_CASESET_COLUMN_copy_1] PRIMARY KEY CLUSTERED ([CaseSetId], [Version], [ColumnId], [CaseFormVersion])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_CASESET_COLUMN_ORGAN] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件種類ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'CaseSetId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'群組序號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'GroupSeq'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'ColumnId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位名稱',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'ColumnName'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位型態',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'ColumnType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位設定值',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'ColumnValue'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'顯示順序',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'OrderNum'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位長度',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'Length'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否必填',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'IsMustKey'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'欄位備註',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'Memo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'附件,上傳檔案限制副檔名',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'FileType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否重複檢核0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'IsCheckFrequency'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N' 欄位勾選0-否，1-是',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'IsFieldCheck'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'一般表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN',
    'COLUMN', N'CaseFormVersion'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單 服務設定欄位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_COLUMN_ORGAN'
    GO
-- 機關審核表單 案件設定群組
DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASESET_GROUP_ORGAN]
    GO
CREATE TABLE [dbo].[GEO_KGO_CASESET_GROUP_ORGAN] (
    [CaseSetId] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [GroupSeq] int  IDENTITY(1,1) NOT NULL,
    [Version] int  NOT NULL,
    [Memo] nvarchar(200) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [OrderNum] int  NULL,
    [IsShow] int,
    [CheckFrequencyPeriod] varchar(30) NULL,
    [CaseFormVersion] int NOT NULL,
    CONSTRAINT [PK_KGO_CASESET_GROUP_copy_1] PRIMARY KEY CLUSTERED ([CaseSetId], [GroupSeq], [Version], [CaseFormVersion])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_CASESET_GROUP_ORGAN] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件種類ID',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'CaseSetId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'群組序號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'GroupSeq'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'Version'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'備註',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'Memo'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'顯示順序',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'OrderNum'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否顯示',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'IsShow'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'重複檢核時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'CheckFrequencyPeriod'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'一般表單版本號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN',
    'COLUMN', N'CaseFormVersion'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關審核表單 案件設定群組',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_GROUP_ORGAN'
    GO

--新增是否啟用機關審核表單
ALTER TABLE [dbo].[KGO_CASESET] ADD [IsOpenOrganForm] int NULL;
GO