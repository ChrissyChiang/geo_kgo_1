package gov.kcg.kgo.repository.custom.condition.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;


/**
 * <p>
 * SQL查詢條件.
 * </p>
 */
public class QueryParamSql {

    /** 查詢SQL. */
    private String sql;

    /** Map<param name, param value>. */
    private Map<String, Object> paramMap = new ConcurrentHashMap<>();

    /**
     * Constructor.
     */
    public QueryParamSql() {
        super();
    }

    /**
     * Constructor.
     * @param sql
     *            the sql.
     */
    public QueryParamSql(String sql) {
        this.sql = sql;
    }

    /**
     * 加入一筆SQL參數.
     * @param paramName
     *            the param name.
     * @param paramValue
     *            the param value.
     */
    public void addParam(String paramName, Object paramValue) {
        paramMap.put(StringUtils.trimToEmpty(paramName), paramValue);
    }

    /**
     * @return sql。
     * @see #sql
     */
    public String getSql() {
        return sql;
    }

    /**
     * @param sql
     *            the new sql
     * @see #sql
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return {@link #paramMap}
     */
    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    /**
     * @param paramMap
     *            {@link #paramMap}
     */
    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

}
