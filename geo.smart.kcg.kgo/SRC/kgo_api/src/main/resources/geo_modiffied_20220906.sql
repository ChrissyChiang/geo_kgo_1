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


