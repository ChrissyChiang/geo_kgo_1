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


