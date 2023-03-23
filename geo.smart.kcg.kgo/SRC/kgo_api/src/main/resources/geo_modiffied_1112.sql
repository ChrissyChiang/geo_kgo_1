--新增是否啟用機關審核表單
ALTER TABLE [dbo].[KGO_CASE_MAIN] ADD [IsOpenOrganForm] int NULL;
GO

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
    [CommentId] nvarchar(64),
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
    'COLUMN', N'CommentId'
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