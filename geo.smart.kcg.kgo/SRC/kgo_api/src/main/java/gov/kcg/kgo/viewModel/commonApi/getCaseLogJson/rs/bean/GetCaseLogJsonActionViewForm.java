package gov.kcg.kgo.viewModel.commonApi.getCaseLogJson.rs.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import gov.kcg.kgo.service.bean.excel.KgoCaseLogExportExcelVo;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提供案件軌跡紀錄json log ViewForm
 */
@ApiModel(description = "提供案件軌跡紀錄json log ViewForm")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCaseLogJsonActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "軌跡紀錄")
	private List<KgoCaseLogExportExcelVo> logList ;

	public List<KgoCaseLogExportExcelVo> getLogList() {
		return logList;
	}

	public void setLogList(List<KgoCaseLogExportExcelVo> logList) {
		this.logList = logList;
	}
}
