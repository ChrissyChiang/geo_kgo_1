package gov.kcg.kgo.controller.test;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.MediaType;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import gov.kcg.kgo.controller.base.BaseController;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.inProgress.rs.InProgressRs;
import gov.kcg.kgo.viewModel.cityCoinApi.mission.search.rs.SearchRs;
import gov.kcg.kgo.viewModel.testApi.homeAction.rs.TestHomeActionRs;
import gov.kcg.kgo.viewModel.testApi.homeAction.rs.bean.TestHomeActionRsViewForm;

/**
 * controller 範例.
 */
@Controller
@RequestMapping("/api2")
public class APIPushTestController extends BaseController {

	@RequestMapping(value = { "/push/push0001" }, method = { RequestMethod.POST }, consumes = {
			MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public TestHomeActionRsViewForm user0001Action(HttpServletRequest request, HttpServletResponse response) {
		// 取得登入者資訊.
		TestHomeActionRs rs = new TestHomeActionRs();

		String txSN = request.getHeader("txSN");
		String txDate = request.getHeader("txDate");
		String txID = request.getHeader("txID");
		String dept = request.getHeader("dept");
		String bodyStr = "";
		String str = "";

		try {
			BufferedReader br = request.getReader();
			while ((str = br.readLine()) != null) {
				bodyStr += str;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("request header set = [ " + txSN + ", " + txDate + ", " + txID + ", " + dept + " ]");

//		JSONObject jo = new JSONObject(bodyStr);
//		System.out.println("request body set = " + new JSONObject(bodyStr));

//		viewForm.setAppPushConf(appPushConf);
//		viewForm.setSysUID("1111");
//		viewForm.setUserSEQ("2222");
		TestHomeActionRsViewForm viewForm = new TestHomeActionRsViewForm();
		return viewForm;
	}

	/**
	 * test
	 * 
	 * @param seq
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	@RequestMapping(value = { "/api/Mission/Search/{seq}" }, method = { RequestMethod.GET }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public SearchRs testApiMissionSearchAction(@PathVariable("seq") String seq, HttpServletResponse response)
			throws JsonMappingException, JsonProcessingException {
		String bodyStr = "{\r\n" + "    \"rtnCode\": \"0000\",\r\n" + "    \"rtnMessage\": \"回應成功\",\r\n"
				+ "    \"result\": {\r\n" + "                \"seq\": 2,\r\n" + "\"organizer_Seq\": 2,\r\n"
				+ "\"appKey\": \"fWr23DgUB2452DWvtj07\",\r\n" + "\"type\": \"R\",\r\n"
				+ "                \"title\": \"政令宣導\",\r\n" + "                \"content\": \"宣導禮讓行人\",\r\n"
				+ "                \"readingUrl\": \"https://www.mtaxi.com.tw/taxi/handicapped/\",\r\n"
				+ "                \"questionnaireUrl\": null,\r\n" + "                \"sendMode\": \"F\",\r\n"
				+ "                \"rewardAmount\": 50,\r\n" + "                \"rewardCount\": 1000,\r\n"
				+ "                \"totalAmount\": 50000,\r\n"
				+ "                \"targetMinBirthday\": \"1960/01/01\",\r\n"
				+ "                \"targetMaxBirthday\": \"2010/12/31\",\r\n"
				+ "                \"targetDistrictCode\": \"6401200, 6401100\",\r\n"
				+ "                \"targetGender\": \"1\",\r\n"
				+ "                \"startTime\": \"2020/09/01 08:00:00\",\r\n"
				+ "                \"endTime\": \"2020/10/01 00:00:00\",\r\n" + "                \"creator\": null,\r\n"
				+ "                \"createTime\": \"2020-09-08T14:13:31.827\",\r\n"
				+ "                \"lastModifier\": null,\r\n" + "                \"lastModifiedTime\": null\r\n"
				+ "    }\r\n" + "}\r\n" + "";
		try {
			System.out.println(">>> Search request body set = " + new JSONObject(bodyStr));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
		}
		System.out.println(">>> Search seq= " + seq);
		SearchRs rs = new ObjectMapper().readValue(bodyStr, SearchRs.class);
		return rs;
	}

	/**
	 * test
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = { "/api/Mission/InProgress" }, method = { RequestMethod.POST }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public InProgressRs testApiMissionInProgressAction(HttpServletRequest request, HttpServletResponse response) {
		String bodyStr = "{\r\n" + "    \"rtnCode\": \"0000\",\r\n" + "    \"rtnMessage\": \"回應成功\",\r\n"
				+ "    \"result\": {\r\n" + "        \"data\": [\r\n" + "            {\r\n"
				+ "                \"seq\": 2,\r\n" + "\"organizer_Seq\": 2,\r\n"
				+ "\"appKey\": \"fWr23DgUB2452DWvtj07\",\r\n" + "\"type\": \"R\",\r\n"
				+ "                \"title\": \"政令宣導\",\r\n" + "                \"content\": \"宣導禮讓行人\",\r\n"
				+ "                \"readingUrl\": \"https://www.mtaxi.com.tw/taxi/handicapped/\",\r\n"
				+ "                \"questionnaireUrl\": null,\r\n" + "                \"sendMode\": \"F\",\r\n"
				+ "                \"rewardAmount\": 50.00,\r\n" + "                \"rewardCount\": 1000,\r\n"
				+ "                \"totalAmount\": 50000,\r\n"
				+ "                \"targetMinBirthday\": \"1960/01/01\",\r\n"
				+ "                \"targetMaxBirthday\": \"2010/12/31\",\r\n"
				+ "                \"targetDistrictCode\": \"6401200, 6401100\",\r\n"
				+ "                \"targetGender\": \"1\",\r\n"
				+ "                \"startTime\": \"2020/09/01 08:00:00\",\r\n"
				+ "                \"endTime\": \"2020/10/01 00:00:00\",\r\n" + "                \"creator\": null,\r\n"
				+ "                \"createTime\": \"2020-09-08T14:13:31.827\",\r\n"
				+ "                \"lastModifier\": null,\r\n" + "                \"lastModifiedTime\": null\r\n"
				+ "            },\r\n" + "            {\r\n" + "                \"seq\": 3,\r\n"
				+ "\"organizer_Seq\": 5,\r\n" + "\"appKey\": \"fWr23DgUB2452DWvtj07\",\r\n" + "\"type\": \"Q\",\r\n"
				+ "                \"title\": \"交通大調查\",\r\n" + "                \"content\": \"對於目前交通的建議\",\r\n"
				+ "                \"readingUrl\": null,\r\n"
				+ "                \"questionnaireUrl\": \"https://www.mtaxi.com.tw/taxi/handicapped/\",\r\n"
				+ "                \"sendMode\": \"L\",\r\n" + "                \"rewardAmount\": 40.012,\r\n"
				+ "                \"rewardCount\": 1000,\r\n" + "                \"totalAmount\": 50000,\r\n"
				+ "                \"targetMinBirthday\": null,\r\n"
				+ "                \"targetMaxBirthday\": null,\r\n"
				+ "                \"targetDistrictCode\": null,\r\n" + "                \"targetGender\": null,\r\n"
				+ "                \"startTime\": \"2020/09/01 08:00:00\",\r\n"
				+ "                \"endTime\": \"2020/10/01 00:00:00\",\r\n" + "                \"creator\": null,\r\n"
				+ "                \"createTime\": \"2020-09-08T14:13:31.827\",\r\n"
				+ "                \"lastModifier\": null,\r\n" + "                \"lastModifiedTime\": null\r\n"
				+ "            }        ],\r\n" + "        \"totalCount\": 2\r\n" + "    }\r\n" + "}\r\n";
		try {
			System.out.println(">>> InProgress request body set = " + new JSONObject(bodyStr));
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
		}

		InProgressRs rs = null;
		try {
			rs = new ObjectMapper().readValue(bodyStr, InProgressRs.class);
			System.out
					.println(">>> InProgress request body set - title = " + rs.getResult().getData().get(0).getTitle());

		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
}
