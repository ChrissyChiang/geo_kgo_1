package gov.kcg.kgo.geoenum;

public enum RentalFuncEnum {

    ONLINEPAY("線上繳費","/payment","payment"),
    CANCEL("取消預約","/caseSetSite/cancelAppointment","cancel"),
    VIEW("檢視",null,"view");

    private String label;
    private String url;
    private String value;

    RentalFuncEnum(String name, String url, String value) {
        this.label = name;
        this.url = url;
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {return value;}

    public void setValue(String value) {this.value = value;}
}
