package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import gov.kcg.kgo.viewModel.backend.hotSearch.query.rq.HotSearchChangeRq;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.HotSearchService;
import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rq.HotSearchDeleteRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.delete.rs.HotSearchDeleteRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rq.HotSearchSaveRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.save.rs.HotSearchSaveRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rq.HotSearchSaveAllRq;
import gov.kcg.kgo.viewModel.backend.hotSearch.saveAll.rs.HotSearchSaveAllRs;
import gov.kcg.kgo.viewModel.backend.hotSearch.home.rs.HotSearchHomeRs;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/hotSearch")
@Api(tags = {"backend-hot-search-controller 熱門搜尋管理"})
public class HotSearchController extends BaseController {

	@Autowired
	HotSearchService hotSearchService;

	/**
	 * 熱門搜尋-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "熱門搜尋-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public HotSearchHomeRs hotSearchHomeAction() {
		HotSearchHomeRs rs = hotSearchService.hotSearchHome();
		return rs;
	}

	/**
	 * 熱門搜尋-熱門設定刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "熱門搜尋-熱門設定刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public HotSearchDeleteRs hotSearchDeleteAction(@RequestBody HotSearchDeleteRq rq, HttpServletRequest request) {
		HotSearchDeleteRs rs = hotSearchService.hotSearchDelete(rq);
		return rs;
	}

	/**
	 * 熱門搜尋-熱門設定-儲存
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "熱門搜尋-熱門設定-儲存")
	@RequestMapping(value = { "/saveAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public HotSearchSaveRs hotSearchSaveAction(@RequestBody HotSearchSaveRq rq, HttpServletRequest request) {
		HotSearchSaveRs rs = hotSearchService.hotSearchSave(rq);
		return rs;
	}

	/**
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "熱門搜尋-整頁儲存")
	@RequestMapping(value = { "/saveAllAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public HotSearchSaveAllRs hotSearchSaveAllAction(@RequestBody HotSearchSaveAllRq rq, HttpServletRequest request) {
		HotSearchSaveAllRs rs = hotSearchService.hotSearchSaveAll(rq);
		return rs;
	}

	/**
	 * Geo 20220729 add 切換前台搜尋引擎
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "熱門搜尋-切換前台搜尋引擎")
	@RequestMapping(value = { "/changeHotSrarch" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public void hotSearchChangeAction(@RequestBody HotSearchChangeRq rq, HttpServletRequest request) {
		hotSearchService.hotSearchChangeAction(rq);
	}
}
