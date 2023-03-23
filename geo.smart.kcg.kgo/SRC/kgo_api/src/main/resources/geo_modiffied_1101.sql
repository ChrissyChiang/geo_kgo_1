-- 簽核意見

DROP TABLE IF EXISTS [dbo].[GEO_KGO_TASK_COMMENT]
    GO

CREATE TABLE [dbo].[GEO_KGO_TASK_COMMENT] (
    [comment_id] nvarchar(64) NOT NULL,
    [comment_text] nvarchar(500),
    [edit_organ] varchar(50) NULL,
    [edit_user] varchar(50) NULL,
    [edit_time] datetime2(0) NULL,
    PRIMARY KEY CLUSTERED ([comment_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    )
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'簽核意見id(ACT_HI_COMMENT:ID_)',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_TASK_COMMENT',
    'COLUMN', N'comment_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'簽核意見',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_TASK_COMMENT',
    'COLUMN', N'comment_text'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯單位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_TASK_COMMENT',
    'COLUMN', N'edit_organ'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯人員',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_TASK_COMMENT',
    'COLUMN', N'edit_user'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_TASK_COMMENT',
    'COLUMN', N'edit_time'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'簽核意見',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_TASK_COMMENT'
    GO