package gov.kcg.kgo.service.bean.scheduled.viewModel.holiday.rs;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "日期")
	@JsonProperty("date")
	private String date;

	@ApiModelProperty(notes = "節日或紀念日名稱")
	@JsonProperty("name")
	private String name;

	@ApiModelProperty(notes = "是否放假")
	@JsonProperty("isHoliday")
	private String isHoliday;

	@ApiModelProperty(notes = "放假類別")
	@JsonProperty("holidayCategory")
	private String holidayCategory;

	@ApiModelProperty(notes = "說明")
	@JsonProperty("description")
	private String description;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsHoliday() {
		return isHoliday;
	}

	public void setIsHoliday(String isHoliday) {
		this.isHoliday = isHoliday;
	}

	public String getHolidayCategory() {
		return holidayCategory;
	}

	public void setHolidayCategory(String holidayCategory) {
		this.holidayCategory = holidayCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
