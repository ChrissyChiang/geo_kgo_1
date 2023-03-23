package gov.kcg.kgo.geoviewmodel.frontend.rq;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import gov.kcg.kgo.viewModel.base.request.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * GEO 20221020 add_Jim
 * MyData Gen Data rq
 */

@ApiModel(description = "MyData Gen Data rq")
public class MyDataGenDataRq extends ApiRequest {
    @ApiModelProperty(value = "caseSetId")
    private String caseSetId;

    @ApiModelProperty(value = "name")
    private String name;

    @ApiModelProperty(value = "pid")
    private String pid;

    @ApiModelProperty(value = "birthday")
    private String birthday;


    public String getCaseSetId() {
        return caseSetId;
    }

    public void setCaseSetId(String caseSetId) {
        this.caseSetId = caseSetId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}