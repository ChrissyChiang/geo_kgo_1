CREATE TABLE KGO.dbo.GEO_KGO_CITY_MEMBER
(
    Id         uniqueidentifier DEFAULT newid() NOT NULL,
    HashId  text NULL,
    RealName    bit NULL,
    UpdateTime datetime         DEFAULT getdate() NULL,
    CONSTRAINT PK_GEO_KGO_CITY_MEMBER PRIMARY KEY (Id)
);