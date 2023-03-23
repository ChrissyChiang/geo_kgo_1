-- -- insert Data
-- INSERT [dbo].[KGO_FUNCTION] ([Name], [Url], [F_Seq], [OrderNum], [FunctionId])
-- VALUES (N'問卷管理', N'/questManagement', 3, 16, N'questManagement')
-- GO
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('ADMIN','questManagement');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('CASE_M','questManagement');
--
-- INSERT [dbo].[KGO_FUNCTION] ([Name], [Url], [F_Seq], [OrderNum], [FunctionId])
-- VALUES ( N'府內線上服務', N'/caseOpenForOrgan', 3, 17, N'caseOpenForOrgan')
-- GO
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('ADMIN','caseOpenForOrgan');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('CASE_M','caseOpenForOrgan');
--
-- INSERT [dbo].[KGO_FUNCTION] ([Name], [Url], [F_Seq], [OrderNum], [FunctionId])
-- VALUES (N'線上預約臨櫃', N'/appointmentSetting', 3, 18, N'appointmentSetting')
-- GO
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('ADMIN','appointmentSetting');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('CASE_M','appointmentSetting');
--
-- INSERT [dbo].[KGO_FUNCTION] ( [Name], [Url], [F_Seq], [OrderNum], [FunctionId])
-- VALUES ( N'設定代理人', N'/agentSetting', 3, 19, N'agentSetting')
-- GO
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('ADMIN','agentSetting');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('CASE_M','agentSetting');
--
-- INSERT [dbo].[KGO_FUNCTION] ([Name], [Url], [F_Seq], [OrderNum], [FunctionId])
-- VALUES ( N'稽核管理表單', N'/caseInspect', 3, 20, N'caseInspect')
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('ADMIN','caseInspect');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('CASE_M','caseInspect');
--
-- --
-- INSERT INTO KGO_FUNCTION (Name, Url, F_Seq, OrderNum,FunctionId)
-- VALUES ('跨機關檢視', '/caseCrossView', 2, 7,'caseCrossView');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('ADMIN','caseCrossView');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('CASE_M','caseCrossView');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('UNIT_A','caseCrossView');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('UNIT_M','caseCrossView');
-- INSERT INTO KGO_ROLE_FUNCTION (RoleId,FunctionId)
-- VALUES ('UNIT_U','caseCrossView');

-- 跨機關檢視 備註
DROP TABLE IF EXISTS [dbo].[GEO_KGO_CASE_CROSS_REVIEW_COMMENT]
GO
CREATE TABLE [dbo].[GEO_KGO_CASE_CROSS_REVIEW_COMMENT] (
                                                           [CrossReviewCommentId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NOT NULL,
                                                           [CaseId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
                                                           [OrganId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS  NULL,
                                                           [UnitId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL,
                                                           [UserId] varchar(50) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL,
                                                           [Comment] nvarchar(500) COLLATE Chinese_Taiwan_Stroke_CI_AS NULL,
                                                           CONSTRAINT [PK_KGO_USER_copy_1_copy_1_copy_1] PRIMARY KEY CLUSTERED ([CrossReviewCommentId])
                                                               WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON)
                                                               ON [PRIMARY]
)
GO

ALTER TABLE [dbo].[GEO_KGO_CASE_CROSS_REVIEW_COMMENT] SET (LOCK_ESCALATION = TABLE)
GO

EXEC sp_addextendedproperty
     'MS_Description', N'備註id',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT',
     'COLUMN', N'CrossReviewCommentId'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'服務id',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT',
     'COLUMN', N'CaseId'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'機關id',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT',
     'COLUMN', N'OrganId'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'科室id',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT',
     'COLUMN', N'UnitId'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'填寫人員id',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT',
     'COLUMN', N'UserId'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'備註內容',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT',
     'COLUMN', N'Comment'
GO

EXEC sp_addextendedproperty
     'MS_Description', N'跨機關檢視 備註
',
     'SCHEMA', N'dbo',
     'TABLE', N'GEO_KGO_CASE_CROSS_REVIEW_COMMENT'
GO