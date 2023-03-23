package gov.kcg.kgo.enums.common;

/**
 * 案件設定基本欄位KGO_CASESET_COLUMN
 *
 * @author TPIuser
 */
public enum BaseColumnEnum {

    /**
     * 身分證
     */
    ID("ID", "申請人證號"),

    /**
     * 姓名
     */
    NAME("Name", "申請人姓名"),

    /**
     * 手機
     */
    CELL_PHONE("CellPhone", "申請人手機"),

    /**
     * 信箱
     */
    EMAIL("Email", "申請人Email");

    private String columnId;

    private String columnName;

    private BaseColumnEnum(String columnId, String columnName) {
        this.columnId = columnId;
        this.columnName = columnName;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

}