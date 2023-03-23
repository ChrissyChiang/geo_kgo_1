package gov.kcg.kgo.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.enums.front.CityApi;
import gov.kcg.kgo.service.PushAPICommonService;
import gov.kcg.kgo.util.DateUtil;
import gov.kcg.kgo.util.RandomUtil;
import gov.kcg.kgo.viewModel.pushApi.push.push0001.rq.bean.PushRqHeader;

/**
 * 推播API基層
 * 
 * @author TPIuser
 *
 */
public class PushAPICommonServiceImpl extends KgoFrontEndServiceImpl implements PushAPICommonService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(PushAPICommonServiceImpl.class);

	/**
	 * parameters of header
	 */
	private static final String HEADER_PARAMETER_TXSN = "txSN";
	private static final String HEADER_PARAMETER_TXDATE = "txDate";
	private static final String HEADER_PARAMETER_TXID = "txID";
	private static final String HEADER_PARAMETER_DEPT = "dept"; // TODO 待確認規格

	private static final String URL_SLASH = "/";

	/**
	 * 
	 * @param cityApi
	 * @return
	 */
	protected PushRqHeader getHeader(CityApi cityApi, String organCode) {
		PushRqHeader header = new PushRqHeader();

		// 交易序號，格式為HHmmss+8碼亂數(自行產生不重複即可)
		String txSN = DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_ONLY_TIME_NO_HYPHEN) + RandomUtil.getRandomNumberWithDigits(7);
		// 交易時間 "yyyyMMddHHmmss"
		String txDate = DateUtil.timestampToString(DateUtil.getCurrentTimestamp(), DateUtil.PATTEN_FULL_TIME_NO_HYPHEN);
		// 交易代碼 API編號，ex. USER0001
		String txID = StringUtils.upperCase(cityApi.getType()) + cityApi.getNumber();
		// 發布局處
		// TODO:待與主平台-組織系統確認並作定義
		String dept = organCode;

		header.setTxSn(txSN);
		header.setTxDate(txDate);
		header.setTxID(txID);
		header.setDept(dept);

		return header;
	}
}
