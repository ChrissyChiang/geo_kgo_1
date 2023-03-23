package gov.kcg.kgo.georepository.custom;

import gov.kcg.kgo.geoenum.RentStatusEnum;
import gov.kcg.kgo.geomodel.ktcpay.GeoKgoPaymentExtQueryData;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class GeoKgoPaymentReposCustom extends GeoBaseReposCustom {

  @PersistenceContext
  public EntityManager entityManager;

  public List<GeoKgoPaymentExtQueryData> getPaymentListByCaseId(String caseId) {

    String sql =
            "SELECT " +
            "    KCM.CASEID AS CASE_ID, " +
            "    GKP.CREATE_TIME AS CREATE_TIME, " +
            "    GKP.EDIT_TIME AS EDIT_TIME, " +
            "    GKP.PAY_AMOUNT AS PAY_AMOUNT, " +
            "    GKP.PAY_DEADLINE AS PAY_TIME, " +
            "    GKP.PAY_TYPE AS PAY_TYPE, " +
            "    GKP.PAYMENT_STATUS AS PAYMENT_STATUS, " +
            "    KC.OWNERORGAN AS PAY_ORIGIN_ORG, " +
            "    GKP.SUB_UUID AS SUB_UUID " +
            "FROM KGO_CASE_MAIN KCM " +
            "JOIN KGO_CASESET KC ON KCM.CASESETID = KC.CASESETID " +
            "JOIN GEO_KGO_CASE_RENT_PAYMENT GKP ON KCM.CASEID = GKP.CASE_ID " +
            "WHERE GKP.PAYMENT_STATUS IN ('YET', 'RFD') AND KCM.CASEID = :caseId ";

    Query query = entityManager.createNativeQuery(sql);
    query.setParameter("caseId", caseId);

    return (List<GeoKgoPaymentExtQueryData>) query.getResultList().stream()
        .map(data -> {
          Object[] datas = (Object[]) data;
          RentStatusEnum rentStatusEnum = RentStatusEnum.getRentStatusEnum((String) datas[6]);
          String paymentType = RentStatusEnum.YET == rentStatusEnum ? "P" : "R";

          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
          GeoKgoPaymentExtQueryData queryData = new GeoKgoPaymentExtQueryData();
          queryData.setCaseId((String) datas[0]);
          queryData.setCreateTime(this.invalidDateFormat(datas[1], dateFormat));
          queryData.setEditTime(this.invalidDateFormat(datas[2], dateFormat));
          queryData.setPayAmount(String.valueOf(datas[3]));
          queryData.setPayTime(this.invalidDateFormat(datas[4], dateFormat));
          queryData.setPayType(paymentType);
          queryData.setPaymentStatus("0");
          queryData.setPayOriginOrg(datas[7] == null ? Strings.EMPTY : this.getPayOriginOrg(datas[7]));
          queryData.setSub((String) datas[8]);
          return queryData;
        })
        .collect(Collectors.toList());
  }

  private String invalidDateFormat(Object data, SimpleDateFormat dateFormat) {
    return data == null
        ? Strings.EMPTY
        : dateFormat.format(data);
  }

  private String getPayOriginOrg(Object data) {
    return ((String) data).substring(0, 8);
  }
}
