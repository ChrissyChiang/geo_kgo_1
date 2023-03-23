-- -- 服務申辦統計名次排序條件
DROP TABLE IF EXISTS[dbo].[GEO_KGO_CASESET_APPLY_COUNT_RULE]
    GO

CREATE TABLE [dbo].[GEO_KGO_CASESET_APPLY_COUNT_RULE] (
    [rule_id] int identity NOT NULL,
    [search_range_type] int NULL,
    [search_rank] int NULL,
    [date_start] varchar(50),
    [date_end] varchar(50) NULL,
    [case_set_status] varchar(50),
    CONSTRAINT [_copy_1_copy_2_copy_3_copy_2_copy_2] PRIMARY KEY CLUSTERED ([rule_id])
    WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
    )
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'條件id',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE',
    'COLUMN', N'rule_id'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'搜尋區間',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE',
    'COLUMN', N'search_range_type'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'搜尋前幾名，預設10',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE',
    'COLUMN', N'search_rank'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'起始日 yyyy-MM-dd，搜尋區間=4：必填',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE',
    'COLUMN', N'date_start'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'結束日 yyyy-MM-dd，搜尋區間=4：必填',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE',
    'COLUMN', N'date_end'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'案件啟用狀態 (開啟-On,關閉-Off)，不填時包含所有狀態',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE',
    'COLUMN', N'case_set_status'
    GO

    EXEC sp_addextendedproperty
    'MS_Description', N'服務申辦統計名次排序條件',
    'SCHEMA', N'dbo',
    'TABLE', N'GEO_KGO_CASESET_APPLY_COUNT_RULE'
    GO