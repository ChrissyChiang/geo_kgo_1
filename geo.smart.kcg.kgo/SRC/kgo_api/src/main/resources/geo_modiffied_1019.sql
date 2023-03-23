-- 案件關聯服務

DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASESET_ASSOCIATE]
    GO

CREATE TABLE [dbo].[GEO_KGO_CASESET_ASSOCIATE] (
    [caseset_id] varchar(30) NOT NULL,
    [associate_caseset_id] varchar(30) NOT NULL,
    CONSTRAINT [geo_kgo_caseset_associate_id] PRIMARY KEY CLUSTERED ([caseset_id], [associate_caseset_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    )
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'服務id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_ASSOCIATE',
    'COLUMN', N'caseset_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'關聯服務id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_ASSOCIATE',
    'COLUMN', N'associate_caseset_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件關聯服務',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_ASSOCIATE'
    GO

-- 重複檢核頻率
ALTER TABLE [dbo].[KGO_CASESET_TEMPLATE] ADD [CheckFrequencyPeriod] varchar(30);
GO

ALTER TABLE [dbo].[KGO_CASESET_COLUMN_TEMPLATE] ADD [IsCheckFrequency] int NOT NULL Default 0;
GO

ALTER TABLE [dbo].[KGO_CASESET_COLUMN_CHILD] ADD [IsCheckFrequency] int NOT NULL Default 0;
GO

ALTER TABLE [dbo].[KGO_CASESET_COLUMN_CHILD_TEMPLATE] ADD [IsCheckFrequency] int NOT NULL Default 0;
GO

ALTER TABLE [dbo].[KGO_CASESET_GROUP] ADD [CheckFrequencyPeriod] varchar(30);
GO

ALTER TABLE [dbo].[KGO_CASESET_COLUMN] ADD [IsCheckFrequency] int NOT NULL Default 0;
GO