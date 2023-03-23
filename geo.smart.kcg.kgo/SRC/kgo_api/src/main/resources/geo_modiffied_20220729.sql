----搜尋引擎切換---
create table GEO_KGO_HOT_SEARCH
(
    hot_search_seq  int not null  identity primary key ,
    isOpenKgo            int not null,
    edit_user            varchar(50),
    edit_time           datetime2(0)
)
    go

exec sp_addextendedproperty 'MS_Description', '搜尋引擎切換', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_HOT_SEARCH'
go

exec sp_addextendedproperty 'MS_Description', '搜尋引擎序號', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_HOT_SEARCH', 'COLUMN',
     'hot_search_seq'
go

exec sp_addextendedproperty 'MS_Description', '切換市府引擎', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_HOT_SEARCH', 'COLUMN',
     'isOpenKgo'
go

exec sp_addextendedproperty 'MS_Description', '異動者', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_HOT_SEARCH',
     'COLUMN', 'edit_user'
go

exec sp_addextendedproperty 'MS_Description', '異動時間', 'SCHEMA', 'dbo', 'TABLE', 'GEO_KGO_HOT_SEARCH',
     'COLUMN', 'edit_time'
go
