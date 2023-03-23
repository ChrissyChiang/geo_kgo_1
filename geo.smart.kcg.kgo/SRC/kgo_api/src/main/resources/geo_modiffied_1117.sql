-- 新增 人員 是否開啟跨機關檢視權限
ALTER TABLE [dbo].[KGO_USER] ADD [IsAvailableCrossReview] bit NULL;
GO

-- 新增 非市府員工登入後台功能所需欄位
ALTER TABLE [dbo].[KGO_USER] ADD [FromGov] bit NULL;
GO

ALTER TABLE [dbo].[KGO_USER] ADD [UserValidate] varchar(255) NULL ;
GO

ALTER TABLE [dbo].[KGO_USER] ADD [UserLoginType] varchar(50) NULL ;
GO

-- 新增 服務 是否開啟跨機關檢視權限
ALTER TABLE [dbo].[KGO_CASESET] ADD [IsAvailableCrossReview] bit NULL;
GO

-- 跨機關檢視權限 機關服務對應
CREATE TABLE [dbo].[GEO_KGO_CASESET_CROSS_REVIEW] (
    [CaseSetId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [OrganId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    CONSTRAINT [PK_KGO_USER_copy_1_copy_1] PRIMARY KEY CLUSTERED ([CaseSetId], [OrganId])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_CASESET_CROSS_REVIEW] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'服務id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_CROSS_REVIEW',
    'COLUMN', N'CaseSetId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'機關id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_CROSS_REVIEW',
    'COLUMN', N'OrganId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'跨機關檢視權限 間關服務對應',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_CROSS_REVIEW'
    GO

-- 前廠商調整的資料庫調整
-- alter table KGO_CASE_MAIN ALTER COLUMN statusDesc NVARCHAR(MAX);

