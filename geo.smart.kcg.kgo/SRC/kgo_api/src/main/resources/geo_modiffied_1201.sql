-- 新增 myDataFile 增加修改時間 for 刪除紀錄
ALTER TABLE [dbo].[KGO_MYDATA_FILE] ADD [UpdateTime] datetime2(0) ;
GO

ALTER TABLE [dbo].[KGO_MYDATA_FILE] ADD [UpdateUser]  varchar(50) NULL ;
GO