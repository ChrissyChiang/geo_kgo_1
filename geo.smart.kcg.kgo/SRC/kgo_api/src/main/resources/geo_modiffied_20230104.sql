---第二階段開發匯入正式站sql總整理

--20220729.sql
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

--20220812.sql
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

---20220901.sql
/******機關優惠折扣 *****/
CREATE TABLE [dbo].[GEO_KGO_ORGAN_DISCOUNT_RATIO](
    [organ_discount_ratio_id] [bigint] IDENTITY(1,1) NOT NULL,
    [organ_id] [varchar](50) NOT NULL,
    [discount_ratio] [int] NULL
    ) ON [PRIMARY]


/**** 修改[KGO_CASESET]欄位 ***/
ALTER TABLE [KGO_CASESET] DROP COLUMN isPay;
ALTER TABLE [KGO].[dbo].[KGO_CASESET] ADD needPay bit NULL;


--20220906.sql
/******場地附件 *****/
CREATE TABLE [dbo].[GEO_KGO_CASESET_SITE_MAIN_FILE](
    [site_file_id] [bigint] IDENTITY(1,1) NOT NULL,
    [site_main_id] [varchar](50) NULL,
    [file_name] [varchar](50) NULL,
    [isDelete] bit NULL
    ) ON [PRIMARY]

/**** 場地主檔 ***/
CREATE TABLE [dbo].[GEO_KGO_CASESET_SITE_MAIN](
    [site_main_id] [varchar](50) PRIMARY KEY NOT NULL,
    [organ_id] [varchar](50) NULL,
    [unit_id][varchar](50) NOT NULL,
    [site_name] [nvarchar](100) NOT NULL,
    [site_amount] [int] NULL,
    [site_status] [int] NULL,
    [serviceHtml] [nvarchar](max)NULL,
    [edit_user] [varchar](50)NOT NULL,
    [edit_time] [datetime2](0) NOT NULL,
    [create_user][varchar](50)NOT NULL,
    [create_time][datetime2](0) NOT NULL,
    [is_delete] [int] NULL,
    [site_type] [varchar](10) NOT NUll
    ) ON [PRIMARY]

---1005.sql
------折扣標準 : 折扣標準--------------------------
    INSERT INTO KGO_FUNCTION (Name,Url,F_Seq,OrderNum,FunctionId)VALUES('折扣標準','/discountStandard',2,24,'discountStandard')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('ADMIN','discountStandard')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('CASE_M','discountStandard')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_A','discountStandard')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_M','discountStandard')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_U','discountStandard')


------身份切換 : 身份切換--------------------------
    INSERT INTO KGO_FUNCTION (Name,Url,F_Seq,OrderNum,FunctionId)VALUES('身份切換','/idSwitch',3,25,'idSwitch')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('ADMIN','idSwitch')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('CASE_M','idSwitch')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_A','idSwitch')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_M','idSwitch')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_U','idSwitch')


------查詢服務管理 : 查詢服務管理--------------------------
    INSERT INTO KGO_FUNCTION (Name,Url,F_Seq,OrderNum,FunctionId)VALUES('查詢服務管理','/searchExCaseMgt',3,26,'searchExCaseMgt')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('ADMIN','searchExCaseMgt')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('CASE_M','searchExCaseMgt')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_A','searchExCaseMgt')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_M','searchExCaseMgt')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_U','searchExCaseMgt')

------場地案件檢視  : 場地案件檢視--------------------------
    INSERT INTO KGO_FUNCTION (Name,Url,F_Seq,OrderNum,FunctionId)VALUES('場地活動案件檢視 ','/siteCaseView',3,28,'siteCaseView')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('ADMIN','siteCaseView')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('CASE_M','siteCaseView')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_A','siteCaseView')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_M','siteCaseView')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_U','siteCaseView')

------搜尋引擎開關 : 搜尋引擎開關--------------------------
    INSERT INTO KGO_FUNCTION (Name,Url,F_Seq,OrderNum,FunctionId)VALUES('全球搜尋引擎開關','/searchEngine',3,27,'searchEngine')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('ADMIN','searchEngine')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('CASE_M','searchEngine')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_A','searchEngine')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_M','searchEngine')
    INSERT INTO KGO_ROLE_FUNCTION(RoleId,FunctionId) VALUES ('UNIT_U','searchEngine')


---1007.sql 這裡ReturnUrl、SpApiUrl 資料要再改
    INSERT INTO KGO_MYDATA_SERVICE (ClientId,ClientSecret,CbcIv,ReturnUrl,SpApiUrl,ServiceName)
    VALUES
('CLI.H7VVnyGYFs','aLphdjxq0It2vim2','InhDhqXDZGxyEEn6','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','特定地區三輪以上慢車證照申請'),
('CLI.OFXfkaeMqf','1PhmQI9GREwdfRnV','INKb72Hl54vBjAte','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','車輛行車事故鑑定覆議線上申請'),
('CLI.3SjKIPr4u4','HaVC1IkJTojSANXe','8dSaPoEh2sX3cNOR','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','中低收入老人特別照顧津貼申請'),
('CLI.fHm6jKhbvH','wAKkuHJvpKMdXrGU','wQZnusH0weLDSJxp','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','老人健保自付額核退申請'),
('CLI.ucQruZSATH','4Ta1II5YX5CQ3rJl','HWSR2cWbXXWprtSF','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','高雄市公共托育機構線上報名申請'),
('CLI.9rx807Rpkd','WIHuSfogDKzVGQlT','q7fypn4oLlJUxzEv','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','發展遲緩兒童早期療育費用補助申請'),
('CLI.I9AvN1jDEG','ifD9d9jrNzpVN7nE','k9YmcYjwPgYRevIg','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','低收入戶孕產婦及嬰幼兒營養補助'),
('CLI.V6uUfSDqqJ','rlVUkVz5bMpOJK45','kovPKJUW75cEjO2h','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','老人修繕住屋補助申請'),
('CLI.o4PE0LGAPV','JG5AOyp7LpTaBApB','c3uHNNLc2mSo3fhq','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','市立聯合醫院志工獎勵申請'),
('CLI.HCX6qIqURU','VnxYozZxHZdKRylB','EUpgMgN4QMrNqNLv','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','市立聯合醫院經濟補助申請'),
('CLI.Wf6LN0XSn8','BApBwx4pCbmHvLIh','CgCt4hgYyqmZG4oj','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','市立民生醫院複製病歷資料線上申請'),
('CLI.vTgivmuOV7','uShExMMTvIFu6aDx','BZn2adjbIsakzqSm','https://testkgo2.kcg.gov.tw/apply_form','http://testkgobe2.kcg.gov.tw:8080/kgo/sp/apply','市立民生醫院高級健檢預約線上申請');

INSERT INTO KGO_MYDATA_RESOURCE (MyDataId,MyDataType,MyDataName,DownloadMinutes,IsEnable,RsInfoFormat,SType)
VALUES
    ('API.ccuZXSsnGv','衛生福利部社會救助及社工司','中低收入老人生活津貼資料',0,0,'',''),
    ('API.nyhnLM1fve','衛生福利部社會及家庭署','身心障礙資格',0,0,'',''),
    ('API.QOHFReaQFc','衛生福利部社會救助及社工司','身心障礙者生活補助資料	',10,0,'','');

INSERT INTO KGO_MYDATA_SERVICE_RESOURCE (ClientId, MyDataId)
VALUES
    ('CLI.H7VVnyGYFs','API.idPhotoRev'),
    ('CLI.H7VVnyGYFs','API.ea0klQl5Wp'),
    ('CLI.H7VVnyGYFs','API.1yJ6s1HNn0'),
    ('CLI.OFXfkaeMqf','API.idPhotoRev'),
    ('CLI.3SjKIPr4u4','API.7QovE2Gev6'),
    ('CLI.3SjKIPr4u4','API.AbAYGIjoYw'),
    ('CLI.3SjKIPr4u4','API.ccuZXSsnGv'),
    ('CLI.3SjKIPr4u4','API.UZQkKbsOpz'),
    ('CLI.3SjKIPr4u4','API.nyhnLM1fve'),
    ('CLI.fHm6jKhbvH','API.zH584wn59r'),
    ('CLI.fHm6jKhbvH','API.1qIr0nM0BT'),
    ('CLI.fHm6jKhbvH','API.idPhotoRev'),
    ('CLI.ucQruZSATH','API.7QovE2Gev6'),
    ('CLI.ucQruZSATH','API.AbAYGIjoYw'),
    ('CLI.ucQruZSATH','API.nyhnLM1fve'),
    ('CLI.9rx807Rpkd','API.7QovE2Gev6'),
    ('CLI.9rx807Rpkd','API.AbAYGIjoYw'),
    ('CLI.9rx807Rpkd','API.QOHFReaQFc'),
    ('CLI.I9AvN1jDEG','API.idPhotoRev'),
    ('CLI.I9AvN1jDEG','API.7QovE2Gev6'),
    ('CLI.V6uUfSDqqJ','API.idPhotoRev'),
    ('CLI.V6uUfSDqqJ','API.7QovE2Gev6'),
    ('CLI.o4PE0LGAPV','API.idPhotoRev'),
    ('CLI.o4PE0LGAPV','API.Ev4zr0WpdE'),
    ('CLI.HCX6qIqURU','API.7QovE2Gev6'),
    ('CLI.HCX6qIqURU','API.UDauDOLyZg'),
    ('CLI.HCX6qIqURU','API.Mo23SDWhsn'),
    ('CLI.HCX6qIqURU','API.syWqjr4flJ'),
    ('CLI.HCX6qIqURU','API.AbAYGIjoYw'),
    ('CLI.HCX6qIqURU','API.ccuZXSsnGv'),
    ('CLI.HCX6qIqURU','API.QOHFReaQFc'),
    ('CLI.HCX6qIqURU','API.nyhnLM1fve'),
    ('CLI.Wf6LN0XSn8','API.7QovE2Gev6'),
    ('CLI.vTgivmuOV7','API.7QovE2Gev6')


--志工時數
    INSERT INTO KGO_MYDATA_COLUMN VALUES ('API.Ev4zr0WpdE','PDF','PDF','附件',0,null,null,null,null,null,0,null,null,null,null);

--商業負責人、合夥人、經理人及法定代理人之商業登記資料
INSERT INTO KGO_MYDATA_COLUMN VALUES ('API.1yJ6s1HNn0','PDF','PDF','附件',0,null,null,null,null,null,0,null,null,null,null);

--中低收入老人生活津貼資料
INSERT INTO KGO_MYDATA_COLUMN VALUES ('API.ccuZXSsnGv','PDF','PDF','附件',0,null,null,null,null,null,0,null,null,null,null);

--勞工保險被保險人投保資料（明細）
INSERT INTO KGO_MYDATA_COLUMN VALUES ('API.UZQkKbsOpz','PDF','PDF','附件',0,null,null,null,null,null,0,null,null,null,null);

--身心障礙資格
INSERT INTO KGO_MYDATA_COLUMN VALUES ('API.nyhnLM1fve','PDF','PDF','附件',0,null,null,null,null,null,0,null,null,null,null);

---20221019 sql
/******MyDataZipHash *****/
CREATE TABLE KGO.dbo.KGO_GEO_MYDATA_MODEL
(
    CaseSetId varchar(30)                      NOT NULL,
    Model     int                              NOT NULL,
    CONSTRAINT KGO_GEO_MYDATA_MODEL_PK PRIMARY KEY (CaseSetId)
);

INSERT INTO KGO.dbo.KGO_GEO_MYDATA_MODEL (CaseSetId, Model)
VALUES ('S2020111010133', 2),
       ('S2021011200005', 2),
       ('S2021011200004', 2),
       ('S2022080500005', 2),
       ('S2020111010074', 2),
       ('S2022090800002', 2),
       ('S2022091200001', 2),
       ('S2022080900001', 2),
       ('S2022082400002', 2),
       ('S2022080300003', 2),
       ('S2022080900003', 2),
       ('S2021010400004', 2);


--線上預約主檔bind場地ID
CREATE TABLE [dbo].[GEO_KGO_CASESET_RENT_MAIN] (
    [case_rent_id] varchar(30)  primary key not null,
    [case_set_id] varchar(30) not null,
    [service_id] varchar(50) not null ,
    [create_user] varchar(50) not null,
    [create_time] datetime2(0) not null
    ) ON [PRIMARY]

--場地預約單日table
CREATE TABLE [dbo].[GEO_KGO_CASESET_RENT_DATE](
    [rent_date_id] varchar(30) primary key NOT NULL,
    [case_rent_id] varchar (30) not null,
    [detail_date] datetime2(0) null,
    [earliest_day] int null,
    [earliest_time] varchar(30)null,
    [lastiest_day] int null,
    [lastiest_time] varchar(30) null,
    [is_enable] int null,
    [is_all_day] int null,
    [edit_time] datetime2(0) not null
    ) ON [PRIMARY]


--場地預約單日預約時段table
CREATE TABLE [dbo].[GEO_KGO_CASESET_RENT_TIME](
    [rent_time_id] varchar(30) primary key NOT NULL,
    [rent_date_id]  varchar(30) NOT NULL,
    [unit_price] int null,
    [start_time] datetime2(0) null,
    [end_time]  datetime2(0) null ,
    [available_user_qouta] int null,
    [used_users_num] int null,
    [is_locked] int null,
    [is_abandon] int null,
    [edit_time] datetime2(0) not null,
    [edit_user] varchar(50) not null
    ) ON [PRIMARY]

--預約關聯時間table
CREATE TABLE [dbo].[GEO_KGO_CASE_RENT_RELATION](
    [case_rent_relation_id] [bigint] IDENTITY(1,1) primary key  not null,
    [case_id] varchar(50)  not null,
    [caseset_id]varchar(50) not null,
    [rent_time_id] varchar(3500) not null,
    [start_time] datetime2(0) not null,
    [end_time] datetime2(0) not null,
    [rent_status] varchar(10) not null,
    [site_main_id] [varchar](50) NOT NULL,
    [edit_time] datetime2(0) not null,
    [create_time] datetime2(0) not null
    )ON [PRIMARY]


CREATE TABLE [dbo].[GEO_KGO_CASE_RENT_PAYMENT](
    [rent_payment_id] [varchar](30) primary key NOT NULL,
    [case_id] [varchar](50) NULL,
    [payment_status] [varchar](10) NULL,
    [pay_type] [varchar](10) NULL,
    [pay_time] [datetime] NULL,
    [pay_amount] [int] NULL,
    [pay_deadLine] datetime2(0) null,
    [receipt_num] varchar(30) null,
    [edit_user] varchar(20) null,
    [edit_time] datetime2(0) not null,
    [create_time] datetime2(0) not null
    ) ON [PRIMARY]

----20221101 city coin
ALTER TABLE KGO.dbo.KGO_CASESET ADD CityCoin bit DEFAULT 1 NULL;
UPDATE KGO.dbo.KGO_CASESET SET CityCoin=1;

---20221108
-- switch user log
CREATE TABLE KGO.dbo.GEO_KGO_SWITCH_USER_LOG
(
    Id         uniqueidentifier DEFAULT newid() NOT NULL,
    SessionId  text NULL,
    FunctionName    text NULL,
    SwitchFrom text NULL,
    SwitchTo   text NULL,
    Ip         text NULL,
    SwitchTime datetime         DEFAULT getdate() NULL,
    CONSTRAINT PK_GEO_KGO_SWITCH_USER_LOG PRIMARY KEY (id)
);

---20221110
ALTER TABLE kgo.dbo.KGO_CASE_MAIN ADD CityCoin int NULL;

---20221114

CREATE TABLE [dbo].[GEO_KGO_CASE_PAYMENT_RECORD](
    [pay_record_id] [BIGINT] IDENTITY(1,1) NOT NULL,
    [case_id] [VARCHAR](50) NOT NULL,
    [payment_status] [VARCHAR](10) NOT NULL,
    [payment_type] [VARCHAR](10) NOT NULL,
    [create_date] [datetime] NULL,
    [amount] [NUMERIC](12,3) NULL,
    [is_external] [VARCHAR](8) NOT NULL
    )

---20221124 為了上架公告推播用
CREATE TABLE KGO.dbo.GEO_KGO_CITY_MEMBER
(
    Id         uniqueidentifier DEFAULT newid() NOT NULL,
    HashId  text NULL,
    RealName    bit NULL,
    UpdateTime datetime         DEFAULT getdate() NULL,
    CONSTRAINT PK_GEO_KGO_CITY_MEMBER PRIMARY KEY (Id)
);


