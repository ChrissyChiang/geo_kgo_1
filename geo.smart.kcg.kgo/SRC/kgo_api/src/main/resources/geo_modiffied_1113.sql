-- 前台使用者
DROP TABLE IF EXISTS [dbo].[GEO_KGO_FRONTEND_USER]
    GO

CREATE TABLE [dbo].[GEO_KGO_FRONTEND_USER] (
    [UserId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NOT NULL,
    [Email] varchar(80) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [Name] nvarchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [UserIdentity] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL,
    [Phone] varchar(30) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
    [UserLoginType] varchar(50) NULL,
    [UserValidate] varchar(255),
    [EditUser] varchar(50) NULL,
    [EditTime] datetime2(0) NULL,
    CONSTRAINT [PK_KGO_USER_copy_1] PRIMARY KEY CLUSTERED ([UserId])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    ON [PRIMARY]
    )
    GO

ALTER TABLE [dbo].[GEO_KGO_FRONTEND_USER] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'使用者id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'UserId'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'信箱',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'Email'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'姓名',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'Name'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'身分證號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'UserIdentity'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'手機',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'Phone'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'登入方式',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'UserLoginType'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'驗證資訊',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'UserValidate'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯人員',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'EditUser'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER',
    'COLUMN', N'EditTime'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'使用者帳號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_FRONTEND_USER'
    GO