package gov.kcg.kgo.viewModel.compoent.radioBox.bean;

import gov.kcg.kgo.viewModel.compoent.SelectListItem;

/**
 * RadioBox元件
 * 
 */
public class RadioBoxItem extends SelectListItem{
	
	public RadioBoxItem() {}

	public RadioBoxItem(String sLabel, String sValue) {
		super(sLabel, sValue);
	}
	
	public RadioBoxItem(String sLabel, String sValue, Boolean selected) {
		super(sLabel, sValue, selected);
	}
	
	public RadioBoxItem(String sLabel, String sValue, String groupKey, Boolean selected) {
		super(sLabel, sValue, groupKey, selected);
	}
}
