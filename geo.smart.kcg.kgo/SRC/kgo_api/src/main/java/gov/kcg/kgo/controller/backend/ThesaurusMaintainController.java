package gov.kcg.kgo.controller.backend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.service.ThesaurusMaintainService;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rq.ThesaurusMaintainDeleteRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.delete.rs.ThesaurusMaintainDeleteRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rq.ThesaurusMaintainEditRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.edit.rs.ThesaurusMaintainEditRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rq.ThesaurusMaintainEditHomeRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.editHome.rs.ThesaurusMaintainEditHomeRs;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rq.ThesaurusMaintainQueryRq;
import gov.kcg.kgo.viewModel.backend.thesaurusMaintain.query.rs.ThesaurusMaintainQueryRs;
import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/backend/thesaurusMaintain")
public class ThesaurusMaintainController extends BaseController {

	@Autowired
	ThesaurusMaintainService thesaurusMaintainService;

	/**
	 * 常見詞庫維護-初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見詞庫維護-初始畫面")
	@RequestMapping(value = { "/homeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ThesaurusMaintainQueryRs thesaurusMaintainHomeAction() {
		ThesaurusMaintainQueryRs rs = thesaurusMaintainService.thesaurusMaintainHome();
		return rs;
	}

	/**
	 * 常見詞庫維護-問題查詢
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見詞庫維護-問題查詢")
	@RequestMapping(value = { "/queryAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ThesaurusMaintainQueryRs thesaurusMaintainQueryAction(@RequestBody ThesaurusMaintainQueryRq rq,
			HttpServletRequest request) {
		ThesaurusMaintainQueryRs rs = thesaurusMaintainService.thesaurusMaintainQuery(rq);
		return rs;
	}

	/**
	 * 常見詞庫維護-問題維護(新增/修改)初始畫面
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見詞庫維護-問題維護(新增/修改)初始畫面")
	@RequestMapping(value = { "/editHomeAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ThesaurusMaintainEditHomeRs thesaurusMaintainEditHomeAction(@RequestBody ThesaurusMaintainEditHomeRq rq,
			HttpServletRequest request) {
		ThesaurusMaintainEditHomeRs rs = thesaurusMaintainService.thesaurusMaintainEditHome(rq);
		return rs;
	}

	/**
	 * 常見詞庫維護-問題維護(新增/修改)
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見詞庫維護-問題維護(新增/修改)")
	@RequestMapping(value = { "/editAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ThesaurusMaintainEditRs thesaurusMaintainEditAction(@RequestBody ThesaurusMaintainEditRq rq,
			HttpServletRequest request) {
		ThesaurusMaintainEditRs rs = thesaurusMaintainService.thesaurusMaintainEdit(rq);
		return rs;
	}

	/**
	 * 常見詞庫維護-問題刪除
	 * 
	 * @param rq
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "常見詞庫維護-問題刪除")
	@RequestMapping(value = { "/deleteAction" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public ThesaurusMaintainDeleteRs thesaurusMaintainEditAction(@RequestBody ThesaurusMaintainDeleteRq rq,
			HttpServletRequest request) {
		ThesaurusMaintainDeleteRs rs = thesaurusMaintainService.thesaurusMaintainDelete(rq);
		return rs;
	}

}
