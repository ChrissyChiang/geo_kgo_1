
/******服務退費比率 *****/
CREATE TABLE [dbo].[GEO_KGO_CASESET_REFUND_RATIO](
    [service_refund_ratio_id] [bigint] IDENTITY(1,1) NOT NULL,
    [case_set_id] [varchar](50) NOT NULL,
    [from_days] [int] NULL,
    [end_days] [int] NULL,
    [refund_ratio] [int] NULL
    ) ON [PRIMARY]

/******機關退費比率 *****/
CREATE TABLE [dbo].[GEO_KGO_ORGAN_DISCOUNT_RATIO](
    [organ_discount_ratio_id] [bigint] IDENTITY(1,1) NOT NULL,
    [organ_id] [varchar](50) NOT NULL,
    [discount_ratio] [int] NULL,
    [edit_user] [varchar](30) not null,
    [edit_time] [datetime2] (0) not null
    ) ON [PRIMARY]


/******增加[KGO_CASESET]的欄位 *****/
ALTER TABLE [KGO].[dbo].[KGO_CASESET] ADD pay_deadline int NULL;
ALTER TABLE [KGO].[dbo].[KGO_CASESET] ADD refund_deadline int  NULL;
ALTER TABLE [KGO].[dbo].[KGO_CASESET] ADD caseset_category [nvarchar](50) NULL;
ALTER TABLE [KGO].[dbo].[KGO_CASESET] ADD activeFlow bit NULL;
ALTER TABLE [KGO].[dbo].[KGO_CASESET] ADD needPay bit NULL;




 




