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