package gov.kcg.kgo.enums.backend;

import java.util.stream.Stream;

public enum PushCaseStatusEnum {
    W("W", "民眾入案通知", "高雄市便民一路通訊息通知", "{0} 入案通知", "<p>你好，您於高雄市便民一路通申請{0}已入案案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>"),
    P("P", "承辦簽收通知", "高雄市便民一路通訊息通知", "{0} 簽收通知", "<p>你好，您於高雄市便民一路通申請{0}已簽收案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>"),
    C("C", "民眾補正通知", "高雄市便民一路通訊息通知", "{0} 補正通知", "<p>你好，您於高雄市便民一路通申請{0}需補正案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>"),
    F3("F3", "結案通過通知", "高雄市便民一路通訊息通知", "{0} 結案通過通知", "<p>你好，您於高雄市便民一路通申請{0}已結案案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>"),
    J3("J3", "結案不通過通知", "高雄市便民一路通訊息通知", "{0} 結案不通過通知", "<p>你好，您於高雄市便民一路通申請{0}已結案案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>"),
    //    F("F", "結案通知", "高雄市便民一路通訊息通知", "{0} 結案通知", "<p>你好，您於高雄市便民一路通申請{0}已結案案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>"),
    B("B", "補正完成通知", "高雄市便民一路通訊息通知", "{0} 補正完成通知", "<p>你好，您於高雄市便民一路通申請{0}已補正完成案件編號{1}申請時間{2}請登入系統，以案件編號進入案件查詢可查詢案件資訊。</p>");

    private String value;
    private String label;
    private String defaultMsgTitle;
    private String defaultMsgBody;
    private String defaultClickDetail;

    private PushCaseStatusEnum(String value, String label, String defaultMsgTitle, String defaultMsgBody, String defaultClickDetail) {
        this.value = value;
        this.label = label;
        this.defaultMsgTitle = defaultMsgTitle;
        this.defaultMsgBody = defaultMsgBody;
        this.defaultClickDetail = defaultClickDetail;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDefaultMsgTitle() {
        return defaultMsgTitle;
    }

    public void setDefaultMsgTitle(String defaultMsgTitle) {
        this.defaultMsgTitle = defaultMsgTitle;
    }

    public String getDefaultMsgBody() {
        return defaultMsgBody;
    }

    public void setDefaultMsgBody(String defaultMsgBody) {
        this.defaultMsgBody = defaultMsgBody;
    }

    public String getDefaultClickDetail() {
        return defaultClickDetail;
    }

    public void setDefaultClickDetail(String defaultClickDetail) {
        this.defaultClickDetail = defaultClickDetail;
    }

    public static Boolean isLegalStatus(String val) {
        return Stream.of(PushCaseStatusEnum.values()).anyMatch(x -> val.equals(x.getValue()));
    }

    public static String getDefaultMsgTitleByValue(String value) {
        return Stream.of(PushCaseStatusEnum.values())
                .filter(x -> value.equals(x.getValue()))
                .findAny()
                .get()
                .getDefaultMsgTitle();
    }

    public static String getDefaultMsgBodyByValue(String value) {
        return Stream.of(PushCaseStatusEnum.values())
                .filter(x -> value.equals(x.getValue()))
                .findAny()
                .get()
                .getDefaultMsgBody();
    }

    public static String getDefaultClickDetailByValue(String value) {
        return Stream.of(PushCaseStatusEnum.values())
                .filter(x -> value.equals(x.getValue()))
                .findAny()
                .get()
                .getDefaultClickDetail();
    }

    public static String getLabelByValue(String value) {
        return Stream.of(PushCaseStatusEnum.values())
                .filter(x -> value.equals(x.getValue()))
                .findAny()
                .get()
                .getLabel();
    }

}
