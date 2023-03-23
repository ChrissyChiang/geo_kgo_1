package gov.kcg.kgo.geoutil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * GEO 20210814 add
 *
 * 用component注入方式從geoapi1999.properties取得API相關設定
 */
@Component
@PropertySource("classpath:geoapi1999.properties")
public class GeoApi1999Properties {

	/** 派工 **/
	@Value("${api.kcg.1999.kd.url}")
	private String kcg1999KdServiceUrl;

	@Value("${id.kgo.1999.kd.key}")
	private String kcg1999KdCaseSetName;

	/** 陳情 **/
	@Value("${api.kcg.1999.new.url}")
	private String kcg1999NewServiceUrl;

	@Value("${id.kgo.1999.new.key}")
	private String kcg1999NewCaseSetName;

	/** 1999縣市 **/
	@Value("${api.kcg.1999.city.url}")
	private String kcg1999CityUrl;

	/** 1999鄉鎮市區 **/
	@Value("${api.kcg.1999.area.url}")
	private String kcg1999AreaUrl;

	/** 1999村里別 **/
	@Value("${api.kcg.1999.village.url}")
	private String kcg1999VillageUrl;

	/** 1999url後綴 **/
	@Value("${api.kcg.1999.url.suffix}")
	private String kcg1999UrlSuffix;

	/** 1999高雄市代碼 **/
	@Value("${api.kcg.1999.area.kgo.code}")
	private String kcg1999AreaKgoCode;

	/** 建議地點(路燈) **/
	@Value("${api.kcg.1999.light.url}")
	private String kcg1999AddrAdviceUrl;

	/** 附件 **/
	@Value("${name.kgo.1999.column.attachment}")
	private String kcg1999Attachment;

	/** ColumnId **/
	@Value("${name.kgo.1999.columnid.mainsub}")
	private String kcg1999MainSub;

	@Value("${name.kgo.1999.columnid.advicelocation}")
	private String kcg1999AdviceLocation;

	@Value("${name.kgo.1999.columnid.addrcity1999}")
	private String kcg1999AddrCity;

	@Value("${api.kgo.1999.search.aes_key}")
	private String kgo1999AesKey;

	/** 派工查詢結果連結網址 **/
	@Value("${api.kgo.1999.kd.search.url}")
	private String kgo1999KdSearchUrl;

	/** 陳情查詢結果連結網址 **/
	@Value("${api.kgo.1999.new.search.url}")
	private String kgo1999NewSearchUrl;

	/** 陳情查詢結果限定時間 **/
	@Value("${api.kgo.1999.search.valid_until}")
	private String kgo1999ValidUntil;

	/** 陳情查詢結果原始字串格式化 **/
	@Value("${api.kgo.1999.new.search.original_format_str}")
	private String kgo1999OriginalFormatStr;

	/** 陳情查詢結果網址前綴格式化 **/
	@Value("${api.kgo.1999.new.search.url_format_str}")
	private String kgo1999UrlFormatStr;

//	@Value("${name.kgo.1999.menu.main}")
//	private String kcg1999MenuMain;
//
//	@Value("${name.kgo.1999.menu.sub}")
//	private String kcg1999MenuSub;


	public String getKcg1999KdServiceUrl() {
		return kcg1999KdServiceUrl;
	}

	public void setKcg1999KdServiceUrl(String kcg1999KdServiceUrl) {
		this.kcg1999KdServiceUrl = kcg1999KdServiceUrl;
	}

	public String getKcg1999KdCaseSetName() {
		return kcg1999KdCaseSetName;
	}

	public void setKcg1999KdCaseSetName(String kcg1999KdCaseSetName) {
		this.kcg1999KdCaseSetName = kcg1999KdCaseSetName;
	}

	public String getKcg1999NewServiceUrl() {
		return kcg1999NewServiceUrl;
	}

	public void setKcg1999NewServiceUrl(String kcg1999NewServiceUrl) {
		this.kcg1999NewServiceUrl = kcg1999NewServiceUrl;
	}

	public String getKcg1999NewCaseSetName() {
		return kcg1999NewCaseSetName;
	}

	public void setKcg1999NewCaseSetName(String kcg1999NewCaseSetName) {
		this.kcg1999NewCaseSetName = kcg1999NewCaseSetName;
	}

	public String getKcg1999CityUrl() {
		return kcg1999CityUrl;
	}

	public void setKcg1999CityUrl(String kcg1999CityUrl) {
		this.kcg1999CityUrl = kcg1999CityUrl;
	}

	public String getKcg1999AreaUrl() {
		return kcg1999AreaUrl;
	}

	public void setKcg1999AreaUrl(String kcg1999AreaUrl) {
		this.kcg1999AreaUrl = kcg1999AreaUrl;
	}

	public String getKcg1999VillageUrl() {
		return kcg1999VillageUrl;
	}

	public void setKcg1999VillageUrl(String kcg1999VillageUrl) {
		this.kcg1999VillageUrl = kcg1999VillageUrl;
	}

	public String getKcg1999UrlSuffix() {
		return kcg1999UrlSuffix;
	}

	public void setKcg1999UrlSuffix(String kcg1999UrlSuffix) {
		this.kcg1999UrlSuffix = kcg1999UrlSuffix;
	}

	public String getKcg1999AreaKgoCode() {
		return kcg1999AreaKgoCode;
	}

	public void setKcg1999AreaKgoCode(String kcg1999AreaKgoCode) {
		this.kcg1999AreaKgoCode = kcg1999AreaKgoCode;
	}

	public String getKcg1999AddrAdviceUrl() {
		return kcg1999AddrAdviceUrl;
	}

	public void setKcg1999AddrAdviceUrl(String kcg1999AddrAdviceUrl) {
		this.kcg1999AddrAdviceUrl = kcg1999AddrAdviceUrl;
	}

	public String getKcg1999Attachment() {
		return kcg1999Attachment;
	}

	public void setKcg1999Attachment(String kcg1999Attachment) {
		this.kcg1999Attachment = kcg1999Attachment;
	}

	public String getKcg1999MainSub() {
		return kcg1999MainSub;
	}

	public void setKcg1999MainSub(String kcg1999MainSub) {
		this.kcg1999MainSub = kcg1999MainSub;
	}

	public String getKcg1999AdviceLocation() {
		return kcg1999AdviceLocation;
	}

	public void setKcg1999AdviceLocation(String kcg1999AdviceLocation) {
		this.kcg1999AdviceLocation = kcg1999AdviceLocation;
	}

	public String getKcg1999AddrCity() {
		return kcg1999AddrCity;
	}

	public void setKcg1999AddrCity(String kcg1999AddrCity) {
		this.kcg1999AddrCity = kcg1999AddrCity;
	}

	public String getKgo1999AesKey() {
		return kgo1999AesKey;
	}

	public void setKgo1999AesKey(String kgo1999AesKey) {
		this.kgo1999AesKey = kgo1999AesKey;
	}

	public String getKgo1999KdSearchUrl() {
		return kgo1999KdSearchUrl;
	}

	public void setKgo1999KdSearchUrl(String kgo1999KdSearchUrl) {
		this.kgo1999KdSearchUrl = kgo1999KdSearchUrl;
	}

	public String getKgo1999NewSearchUrl() {
		return kgo1999NewSearchUrl;
	}

	public void setKgo1999NewSearchUrl(String kgo1999NewSearchUrl) {
		this.kgo1999NewSearchUrl = kgo1999NewSearchUrl;
	}

	public String getKgo1999ValidUntil() {
		return kgo1999ValidUntil;
	}

	public void setKgo1999ValidUntil(String kgo1999ValidUntil) {
		this.kgo1999ValidUntil = kgo1999ValidUntil;
	}

	public String getKgo1999OriginalFormatStr() {
		return kgo1999OriginalFormatStr;
	}

	public void setKgo1999OriginalFormatStr(String kgo1999OriginalFormatStr) {
		this.kgo1999OriginalFormatStr = kgo1999OriginalFormatStr;
	}

	public String getKgo1999UrlFormatStr() {
		return kgo1999UrlFormatStr;
	}

	public void setKgo1999UrlFormatStr(String kgo1999UrlFormatStr) {
		this.kgo1999UrlFormatStr = kgo1999UrlFormatStr;
	}

}
