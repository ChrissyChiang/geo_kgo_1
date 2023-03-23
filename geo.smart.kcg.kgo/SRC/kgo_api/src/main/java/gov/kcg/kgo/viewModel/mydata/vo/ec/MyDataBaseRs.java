package gov.kcg.kgo.viewModel.mydata.vo.ec;

import org.springframework.http.HttpStatus;

public class MyDataBaseRs {

    private Integer httpStatus;

    private String JsonData;

    public MyDataBaseRs(){
        this.httpStatus = HttpStatus.OK.value();
    }

    public Integer getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getJsonData() {
        return JsonData;
    }

    public void setJsonData(String jsonData) {
        JsonData = jsonData;
    }

}
