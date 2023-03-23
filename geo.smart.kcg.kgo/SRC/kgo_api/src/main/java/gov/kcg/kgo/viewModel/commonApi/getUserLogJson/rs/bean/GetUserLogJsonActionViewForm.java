package gov.kcg.kgo.viewModel.commonApi.getUserLogJson.rs.bean;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import gov.kcg.kgo.service.bean.excel.KgoLogExportExcelVo;
import gov.kcg.kgo.viewModel.base.response.BaseViewForm;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 提供操作軌跡紀錄json log ViewForm
 */
@ApiModel(description = "提供操作軌跡紀錄json log  ViewForm")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserLogJsonActionViewForm extends BaseViewForm {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "軌跡紀錄")
	private List<KgoLogExportExcelVo> logList ;

	public List<KgoLogExportExcelVo> getLogList() {
		return logList;
	}

	public void setLogList(List<KgoLogExportExcelVo> logList) {
		this.logList = logList;
	}
}
