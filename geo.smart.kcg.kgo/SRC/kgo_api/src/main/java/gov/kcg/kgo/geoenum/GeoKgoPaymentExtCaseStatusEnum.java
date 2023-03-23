package gov.kcg.kgo.geoenum;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public enum GeoKgoPaymentExtCaseStatusEnum {

  /** 待繳費 */
  PY1(RentStatusEnum.WAIT, "0", "P"),
  /** 已繳費 */
  PY2(RentStatusEnum.FIN, "1", "P"),
  /** 待退費 */
  RF1(RentStatusEnum.WRFD, "0", "R"),
  /** 已退費 */
  RF2(RentStatusEnum.FRFD, "1", "R");

  private final RentStatusEnum rentStatusEnum;

  private final String paymentStatus;
  /** 退繳費類型 P:繳費 R:退費 */
  private final String paymentType;

  GeoKgoPaymentExtCaseStatusEnum(RentStatusEnum rentStatusEnum, String paymentStatus, String paymentType) {
    this.rentStatusEnum = rentStatusEnum;
    this.paymentStatus = paymentStatus;
    this.paymentType = paymentType;
  }

  public RentStatusEnum getRentStatusEnum() {
    return this.rentStatusEnum;
  }

  public String getStatus() {
    return this.paymentStatus;
  }

  public String getType() {
    return this.paymentType;
  }

  public static GeoKgoPaymentExtCaseStatusEnum getEnum(String status) {
    return Arrays.stream(values())
        .filter(e -> StringUtils.equalsIgnoreCase(e.name(), status))
        .findAny()
        .orElseThrow(() -> new EnumConstantNotPresentException(GeoKgoPaymentExtCaseStatusEnum.class, status));
  }
}
