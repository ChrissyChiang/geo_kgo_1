package gov.kcg.kgo.geomodel;

import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetDetail;
import gov.kcg.kgo.geoentity.GeoKgoQuestionnaireCasesetTopic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * GEO 20211012 add
 * Model for 問卷結果子題目檔檔
 */
@Component
@ApiModel(description = "問卷結果子題目檔檔")
public class GeoKgoQuestionnaireResultQueryDetailModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(notes = "子題目id")
    private String detailCasesetId;

    @ApiModelProperty(notes = "子題目名稱")
    private String detailName;

    @ApiModelProperty(notes = "子題目類型(1-單選不含配分,2-單選含配分,3-複選,4-問答)")
    private Integer detailType;

    @ApiModelProperty(notes = "子題目排序(必填)")
    private Integer detailSort;

    @ApiModelProperty(notes = "選項資料(ex.A-滿意,B-不滿意)")
    private String detailChoose;

    @ApiModelProperty(notes = "是否為必填題(1表必填題, 0表非必填題)")
    private Integer isMust;

    @ApiModelProperty(notes = "配分制，回答總統計列表")
    private List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> detailAnswerList;

    @ApiModelProperty(notes = "回答")
    private GeoKgoQuestionnaireResultQueryAnswerDetailModel detailAnswer;

    public String getDetailCasesetId() {
        return detailCasesetId;
    }

    public void setDetailCasesetId(String detailCasesetId) {
        this.detailCasesetId = detailCasesetId;
    }

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }

    public Integer getDetailType() {
        return detailType;
    }

    public void setDetailType(Integer detailType) {
        this.detailType = detailType;
    }

    public Integer getDetailSort() {
        return detailSort;
    }

    public void setDetailSort(Integer detailSort) {
        this.detailSort = detailSort;
    }

    public String getDetailChoose() {
        return detailChoose;
    }

    public void setDetailChoose(String detailChoose) {
        this.detailChoose = detailChoose;
    }

    public Integer getIsMust() {
        return isMust;
    }

    public void setIsMust(Integer isMust) {
        this.isMust = isMust;
    }

    public List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> getDetailAnswerList() {
        return detailAnswerList;
    }

    public void setDetailAnswerList(List<GeoKgoQuestionnaireResultQueryAnswerDetailModel> detailAnswerList) {
        this.detailAnswerList = detailAnswerList;
    }

    public GeoKgoQuestionnaireResultQueryAnswerDetailModel getDetailAnswer() {
        return detailAnswer;
    }

    public void setDetailAnswer(GeoKgoQuestionnaireResultQueryAnswerDetailModel detailAnswer) {
        this.detailAnswer = detailAnswer;
    }

    public static GeoKgoQuestionnaireResultQueryDetailModel changeToModel(GeoKgoQuestionnaireCasesetDetail entity) {
        GeoKgoQuestionnaireResultQueryDetailModel model = new GeoKgoQuestionnaireResultQueryDetailModel();
        model.setDetailCasesetId(entity.getDetailCasesetId());
        model.setDetailName(entity.getDetailName());
        model.setDetailChoose(entity.getDetailChoose());
        model.setDetailSort(entity.getDetailSort());
        model.setDetailType(entity.getDetailType());
        model.setIsMust(entity.getIsMust());
        return model;
    } //changeToModel

    public static List<GeoKgoQuestionnaireResultQueryDetailModel> changeListToModel(List<GeoKgoQuestionnaireCasesetDetail> entityList) {
        List<GeoKgoQuestionnaireResultQueryDetailModel> modelList = null;
        if (entityList!=null) {
            modelList = new ArrayList<>();
            for (int i=0; i<entityList.size(); i++) {
                GeoKgoQuestionnaireResultQueryDetailModel model = changeToModel(entityList.get(i));
                modelList.add(model);
            }
        } //if (entityList!=null)
        return modelList;
    } //changeListToModel
}
