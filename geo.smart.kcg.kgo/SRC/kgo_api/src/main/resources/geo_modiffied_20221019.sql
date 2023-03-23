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
    [create_time] datetime2(0) not null,
    [sys_pay_amount] int null,
    [payment_discount] int null,
    [pay_assurer] varchar(30) null,
    [deduct_percent]  int null,
    [refund_discount] int null,
    [actual_refund_amount] int null,
    [refund_assurer] varchar(30) null,
    [refund_time] datetime2(0) null ,
    [sub_uuid] varchar(255) null
) ON [PRIMARY]
