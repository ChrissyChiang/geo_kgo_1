package gov.kcg.kgo.geomodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 20220729 前台切換搜尋引擎
 * 市府搜尋引擎 Model
 */
@ApiModel(description = "熱門搜尋-市府搜尋引擎")
public class GeoHotSearchGovernmentModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "標題")
    private String title;

    @ApiModelProperty(notes = "連結")
    private String url;

    @ApiModelProperty(notes = "子連結")
    private String subLink;

    @ApiModelProperty(notes = "預覽內容")
    private String highlight;

    @ApiModelProperty(notes = "機關")
    private String organization;

    @ApiModelProperty(notes = "網站名稱")
    private String webName;

    @ApiModelProperty(notes = "score")
    private String score;

    @ApiModelProperty(notes = "公告時間")
    private String releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubLink() {
        return subLink;
    }

    public void setSubLink(String subLink) {
        this.subLink = subLink;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
