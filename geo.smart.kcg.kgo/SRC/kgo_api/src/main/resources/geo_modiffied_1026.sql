-- 代理人

DROP TABLE IF EXISTS [dbo].[GEO_KGO_AGENT]
    GO

CREATE TABLE [dbo].[GEO_KGO_AGENT] (
    [agent_id] varchar(50) NOT NULL,
    [principal_user_id] varchar(50),
    [agent_user_id] varchar(50) NULL,
    [start_time] datetime2(0) NULL,
    [end_time] datetime2(0) NULL,
    [is_signed] int NULL,
    [is_delete] int NULL,
    [edit_organ] varchar(50) NULL,
    [edit_user] varchar(50) NULL,
    [edit_time] datetime2(0) NULL,
    CONSTRAINT [_copy_3_copy_1_copy_2_copy_1_copy_1_copy_1] PRIMARY KEY CLUSTERED ([agent_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    )
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'代理人機制id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'agent_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'被代理人id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'principal_user_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'代理人id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'agent_user_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'代理起始時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'start_time'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'代理結束時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'end_time'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否已簽核',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'is_signed'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'是否已刪除',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'is_delete'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯單位',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'edit_organ'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯人員',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'edit_user'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'編輯時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT',
    'COLUMN', N'edit_time'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'代理人機制',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_AGENT'
    GO