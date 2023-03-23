DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASESET_APPLY_COUNT]
GO

CREATE TABLE [dbo].[GEO_KGO_CASESET_APPLY_COUNT] (
                                                     [caseset_id] varchar(30) NOT NULL,
                                                     [caseset_rank] int NOT NULL,
                                                     CONSTRAINT [_copy_1_copy_2_copy_3_copy_2] PRIMARY KEY CLUSTERED ([caseset_id])
                                                         WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
)
GO

EXEC sp_addextendedproperty
     'MS_Description', N'服務id',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT',
     'COLUMN', N'caseset_id'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'名次',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT',
     'COLUMN', N'caseset_rank'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'服務申辦統計名次',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT'
GO