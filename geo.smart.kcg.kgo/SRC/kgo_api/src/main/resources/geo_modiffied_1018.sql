ALTER TABLE [dbo].[KGO_CASESET] ADD [IsOpenForOrgan] int NOT NULL Default 0;
GO

-- 欄位名isOpenForOrgan 改為 IsOpenForOrgan
-- 砍掉欄位
-- ALTER TABLE [dbo].[KGO_CASESET]
--     DROP CONSTRAINT DF__KGO_CASES__isOpe__673F4B05(需替換帶入);
-- GO
-- ALTER TABLE [dbo].[KGO_CASESET] DROP COLUMN [isOpenForOrgan]
-- GO