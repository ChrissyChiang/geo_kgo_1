package gov.kcg.kgo.viewModel.backend.kgoLog.query.rs.bean;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import gov.kcg.kgo.dto.CaseMainCaseSetCountDto;
import gov.kcg.kgo.dto.KgoUseLogFunctionCodeCountDto;
import gov.kcg.kgo.enums.backend.BackendFunctionCodeEnum;
import gov.kcg.kgo.enums.common.SystemTypeEnum;
import gov.kcg.kgo.enums.front.FrontendFunctionCodeEnum;
import gov.kcg.kgo.util.MessageUtil;
import gov.kcg.kgo.util.SpringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 *  使用者軌跡紀錄 - 功能使用分析.
 * 
 */
@ApiModel(description = "後台 - 軌跡紀錄 - 查詢 - 功能使用分析 後台: 功能使用分析、前台: 案件申辦統計(TOP10)")
public class LogFuncOrServiceCount implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value="功能/服務名稱")
	private String name = StringUtils.EMPTY;
	
	@ApiModelProperty(value="數量")
	private Integer count;


	public LogFuncOrServiceCount() {
	}
	
	/**
	 * 後台建構子.
	 *
	 * @param dto the dto
	 * @param systemType the system type
	 */
	public LogFuncOrServiceCount(KgoUseLogFunctionCodeCountDto dto) {
		MessageUtil messageUtil = SpringUtil.getBean(MessageUtil.class);
		String funcCode = dto.getFunctionCode();
		if(StringUtils.isBlank(funcCode)) {
			this.name = StringUtils.EMPTY;
		} else {
			String functionNameI18n = BackendFunctionCodeEnum.getBackendFunctionEnum(funcCode).getFunctionNameI18n();
			this.name = messageUtil.getMessage(functionNameI18n);
		}
		this.count = dto.getCount();
	}
	
	/**
	 * 前台建構子.
	 *
	 * @param dto the dto
	 */
	public LogFuncOrServiceCount(CaseMainCaseSetCountDto dto) {
		this.name = dto.getCaseSetName();
		this.count = dto.getCount();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}