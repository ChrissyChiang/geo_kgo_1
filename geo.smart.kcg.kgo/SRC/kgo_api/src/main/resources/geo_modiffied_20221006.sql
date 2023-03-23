select * from KGO_ORGAN

--塞入[KGO_ORGAN]機關
insert into [dbo].[KGO_ORGAN](OrganId,OrganName,CreateTime,BelongKgo)  values ('0902080013','阮綜合醫療社團法人阮綜合醫院',GETDATE(),0);
--塞入[GEO_KGO_CASESET_ALLOW_ORGAN] 允許申請的機關名單
insert into [dbo].[GEO_KGO_CASESET_ALLOW_ORGAN](caseset_id,organ_id)  values ('S2020112500004','0902080013');
--塞入[GEO_KGO_HCA_ORGAN] 非市府機關人員 如護士
insert into [dbo].[GEO_KGO_HCA_ORGAN](hca_card_number,organ_id,hca_name,hca_idn)  values ('000085709313','0902080013','陳淑梅','E220822787');




