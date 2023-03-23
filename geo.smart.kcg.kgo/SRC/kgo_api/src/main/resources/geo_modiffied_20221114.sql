
CREATE TABLE [dbo].[GEO_KGO_CASE_PAYMENT_RECORD](
    [pay_record_id] [BIGINT] IDENTITY(1,1) NOT NULL,
    [case_id] [VARCHAR](50) NOT NULL,
    [payment_status] [VARCHAR](10) NOT NULL,
    [payment_type] [VARCHAR](10) NOT NULL,
    [create_date] [datetime] NULL,
    [amount] [NUMERIC](12,3) NULL,
    [is_external] [VARCHAR](8) NOT NULL
)