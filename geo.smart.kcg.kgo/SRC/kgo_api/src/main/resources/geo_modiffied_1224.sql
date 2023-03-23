-- 1224 調整CommentId為PK 語法
ALTER TABLE [GEO_KGO_CASE_DETAIL_ORGAN]  ALTER COLUMN CommentId INT NOT NULL;
GO

alter table GEO_KGO_CASE_DETAIL_ORGAN drop constraint PK_KGO_CASE_DETAIL_copy_1
    go

alter table GEO_KGO_CASE_DETAIL_ORGAN
    add constraint PK_KGO_CASE_DETAIL_copy_1
        primary key (CaseId, ColumnId, Version, CColumnId, CaseFormVersion, CommentId)
    go