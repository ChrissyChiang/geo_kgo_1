-- 寄出Email Log
DROP TABLE IF EXISTS [dbo].[GEO_KGO_EMAIL_LOG]
GO

CREATE TABLE [dbo].[GEO_KGO_EMAIL_LOG] (
    [seq_guid] uniqueidentifier default  NEWID() not null primary key ,
    [case_id] varchar(20)  NOT NULL,
    [status] varchar(10)  NOT NULL,
    [send_time] datetime2(0)  NOT NULL
)
GO

ALTER TABLE [dbo].[GEO_KGO_EMAIL_LOG] SET (LOCK_ESCALATION = TABLE)
    GO

    EXEC sp_addextendedproperty
     'MS_Description', N'seq_guid',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_EMAIL_LOG',
     'COLUMN', N'seq_guid'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件編號',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_EMAIL_LOG',
    'COLUMN', N'case_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件狀態',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_EMAIL_LOG',
    'COLUMN', N'status'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'寄信時間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_EMAIL_LOG',
    'COLUMN', N'send_time'
    GO
