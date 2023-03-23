------好孕行得通 : 某服務允許特定機關進入--------------------------
CREATE TABLE [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](
	[caseset_allow_organ_id] [bigint] IDENTITY(1,1) NOT NULL,
	[caseset_id] [varchar](50) NULL,
	[organ_id] [varchar](50) NULL
) ON [PRIMARY]


------好孕行得通 : 健保卡所屬機關----------------------------------
CREATE TABLE [dbo].[GEO_KGO_HCA_ORGAN](
	[hca_organ_id] [bigint] IDENTITY(1,1) NOT NULL,
	[hca_card_number] [varchar](50) NULL,
	[organ_id] [varchar](50) NULL,
	[hca_name] [varchar](50) NULL,
	[hca_idn] [varchar](50) NULL
) ON [PRIMARY]

------好孕行得通 : KGO_ORGAN 新增欄位----------------------------------
ALTER TABLE [dbo].[KGO_ORGAN] ADD BelongKgo bit;







-----------------------------------------------------------
--************** 好孕行得通 : 儲存資料 **************
-----------------------------------------------------------
--檢查機關是否存在
--select * from [dbo].[KGO_ORGAN] where OrganId in ('1507360028','1542010141','1107350015','2342180010','1502050045','0142030019','3507310226','1502111089','1302050014','3542011775','1542062077','0902080013','1507310019','3507020192','3507020192','1102110011','3502097684','3502052149','0102070020','1502070118','3502030732','3507020030','3501290587','3507321505','1142120001','1507310037');

--塞入[KGO_ORGAN]


insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1507360028','新正薪醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1542010141','優生婦產科醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1107350015','天主教聖功醫療財團法人聖功醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('2342180010','永安區衛生所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1502050045','德謙醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('0142030019','衛生福利部旗山醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3507310226','翁仲仁婦產科',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1502111089','戴銘浚婦兒醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1302050014','高雄醫學大學附設醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3542011775','邱正義婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1542062077','樂生婦幼醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('0902080013','阮綜合醫療社團法人阮綜合醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1507310019','金安心醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3507020192','金禾美診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1102110011','高雄市立小港醫院（委託財團法人私立高雄醫學大學經營)',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3502097684','吳玉珍婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3502052149','王婦產科',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('0102070020','高雄市立大同醫院（委託財團法人私立高雄醫學大學經營)',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1502070118','健新醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3502030732','蘇婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3507020030','岡山杏生婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3501290587','張榮州婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3507321505','安安十全婦幼診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1142120001','義大醫療財團法人義大醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1507310037','高大美杏生醫院',GETDATE(),0);

--0928補
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3502021760','容婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('3507301343','悠仁婦產科診所',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1502051426','四季台安醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1107320017','義大大昌醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('0502030015','國軍高雄總醫院左營分院附設民眾診療服務處',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1507300022','博愛蕙馨醫院',GETDATE(),0);
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('1107320017','義大醫療財團法人義大大昌醫院',GETDATE(),0);


-------塞入[GEO_KGO_CASESET_ALLOW_ORGAN]
DECLARE @caseset_id varchar(50);
SET @caseset_id = 'test';

--測試
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1507360028');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397220100A');
--市府單位
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397120000J');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397120100J');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397120200J');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397120300J');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397400000I');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397140000I');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397340000A');
--區公所
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397510000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397520000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397530000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397540000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397550000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397560000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397570000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397580000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397590000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397600000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397610000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397620000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397630000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397640000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397650000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397660000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397670000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397680000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397690000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397700000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397710000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397720000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397730000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397740000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397750000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397760000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397770000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397780000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397790000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397800000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397810000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397820000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397830000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397840000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397850000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397860000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397870000A');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'397880000A');
--醫院
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1507360028');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1542010141');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1107350015');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'2342180010');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1502050045');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'0142030019');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3507310226');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1502111089');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1302050014');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3542011775');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1542062077');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'0902080013');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1507310019');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3507020192');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1102110011');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3502097684');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3502052149');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'0102070020');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1502070118');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3502030732');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3507020030');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3501290587');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3507321505');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1142120001');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1507310037');

--0928補
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3502021760');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'3507301343');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1502051426');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1107320017');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'0502030015');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1507300022');
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values (@caseset_id,'1107320017');

-------塞入[GEO_KGO_HCA_ORGAN]
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000056674600','1507360028','(測試人員)','S120648333');


insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000079223026','1507360028','黃悦淑','H221808266');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000059306931','1542010141','蔡溱庭','P220811371');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000002301378','1107350015','李佳芳','S222373304');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000014659958','2342180010','梁耀予','R223224989');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000076928502','1502050045','唐湘玲','V220845159');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000060813251','0142030019','蕭櫻華','S222421592');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000000917225','3507310226','朱乃維','S220895245');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000085392064','1502111089','李佩珊','E224253199');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000083247315','1302050014','侯淑惠','E223332093');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000082157558','3542011775','邱正義','S101657545');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000050505767','1542062077','賴依君','T222712222');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000085709313','0902080013','陳淑梅','E220822787');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000069045519','1507310019','彭宥榛','S224091145');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000068853570','3507020192','邵秝煒','S223951624');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000006963409','3507020192','陳怡卉','S224734216');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000050827670','1102110011','吳淑惠','E222124257');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000002250002','3502097684','彭美如','J221024038');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000018368882','3502052149','黃靖淳','R221268787');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000064181236','0102070020','劉詠雲','S221066980');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000001689194','1502070118','許文姿','E221548066');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000082002175','3502030732','蘇守良','D120146396');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000072652959','3507020030','王英妮','M221832201');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000051779877','3501290587','張玉佩','E221085411');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000069177388','3507321505','張美娟','E221878372');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000007301750','1142120001','姬偉貞','S223342027');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000000687198','1507310037','查憶慧','X220368326');

--0928補

insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000007320094','3502021760','陳學慧','E224125789');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000072402187','3507301343','許雅婷','E222452327');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000004889635','1502051426','郭美錦','T222689166');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000008467869','1107320017','楊秀民','T222712295');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000006465130','0502030015','劉惠容','Q222253207');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000004889539','1507300022','田景如','E220563005');
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000008468542','1107320017','鄭鈺倩','E222308806');




