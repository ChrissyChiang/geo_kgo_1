package gov.kcg.kgo.service.operationmemo;

import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.kcg.kgo.enums.common.SysLogExeType;

/**
 * 共通操作紀錄.
 */
public class OperationApiMemo extends OperationMemo {


	private static final long serialVersionUID = -7731215069043034900L;
	private static final Logger LOGGER = LoggerFactory.getLogger(OperationApiMemo.class);

	/**
	 * 作業內容(新增、刪除、修改 各功能實作List內容).
	 *
	 * @return the display memo
	 */
	@Override
	public String getDisplayMemo() {
		if(CollectionUtils.isEmpty(memoList)) {
			return "";
		}
		String disMemo = StringUtils.EMPTY;
		SysLogExeType type = super.getSysLogType();
		if(type != null) {
			disMemo = messageUtil.getMessage(type.getCodeMsgKey()) + "，";
		}
		
		disMemo += memoList.stream().map(col -> StringUtils.isBlank(col.getColLabel()) && StringUtils.isBlank(col.getColValue()) ? StringUtils.EMPTY :StringUtils.isBlank(col.getColLabel())? col.getColValue():col.getColLabel() + "：" + col.getColValue()).collect(Collectors.joining("，"));
		LOGGER.info(">>>>> 儲存操作紀錄: {}", disMemo);
		return disMemo;
	}
}
