//package gov.kcg.kgo.security.filter;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.AntPathMatcher;
//import org.springframework.web.util.WebUtils;
//
//import gov.kcg.kgo.enums.error.KgoCommonApiError;
//import gov.kcg.kgo.exception.ErrorResult;
//import gov.kcg.kgo.exception.KgoApiException;
//import gov.kcg.kgo.util.SsoUtil;
//
///**
// * API使用權限filter.
// */
//
//public class AuthFilter implements Filter {
//
//	/** Logger. */
//	private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);
//    
//    /** 不過濾白名單. */
//    private static final String[] AUTH_WHITE_LIST = {
//    		"/",
//    		// 前後臺登入授權相關
//    		"/backend/auth/**",
//    		"/frontend/auth/**",
//    		// 城市資料平台
//    		"/common/**",
//    		// My data call back
//    		"/sp/apply",
//    		"/myDataService/**",
//    		
//            // -- swagger ui
//            "/v2/api-docs",
//            "/swagger-resources",
//            "/swagger-resources/**",
//            "/configuration/ui",
//            "/configuration/ui/**",
//            "/configuration/security",
//            "/swagger-ui.html",
//            "/swagger-ui.html/**",
//            // "/swagger-ui.html/swagger-resources/configuration/ui",
//            // "/swagger-resources/configuration/ui",
//            "/webjars/**"
//            // other public endpoints of your API may be appended to this array
//    };
//    
//    /** exclude url. */
//    private static final List<String> EXCLUDE_URL = Arrays.asList(AUTH_WHITE_LIST);
//
//    /**
//     * Default constructor.
//     */
//    public AuthFilter() {
//    }
//
//    /**
//     * @see Filter#destroy()
//     */
//    @Override
//    public void destroy() {
//    }
//
//    /**
//     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
//     */
//    @Override
//	public void doFilter(ServletRequest reqst, ServletResponse resps, FilterChain chain)
//			throws IOException, ServletException {
//		HttpServletRequest httpServletRequest = (HttpServletRequest) reqst;
//			// 不過濾的url、靜態資源
//		 if (shouldNotFilter(httpServletRequest)) {
//			chain.doFilter(reqst, resps);
//
//		 } else if {
//			 
//			// 未登入本系統
//		} else if (httpServletRequest.getSession() == null || WebUtils.getSessionAttribute(httpServletRequest,
//				SsoUtil.SESSION_KEY_USER_LIGIN_INO_OBJECT) == null) {
//
//			HttpServletRequest request = (HttpServletRequest) reqst;
//			HttpServletResponse response = (HttpServletResponse) resps;
//
//			// 使用者未登入.
//			throw new KgoApiException(new ErrorResult(KgoCommonApiError.USER_NOT_LOGIN));
//
////################################################################################################################			
//		} else {
//			if (LOGGER.isDebugEnabled())
//				LOGGER.debug("filter by pass.");
//			chain.doFilter(reqst, resps);
//		}
//	}
//    
//    /**
//     * 不過濾的url、靜態資源.
//     *
//     * @param request the request
//     * @return true, if successful
//     * @throws ServletException the servlet exception
//     */
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//   	 String url = request.getRequestURL().toString();
//   	 AntPathMatcher pathMatcher = new AntPathMatcher();
//   	 // 是否跳過filter 路徑匹配
//   	 boolean isPathExc = EXCLUDE_URL.stream().anyMatch(exclude -> pathMatcher.match(exclude, request.getServletPath()));
//        return isResourceUrl(url) || isPathExc;
//   }
//    
//    /**
//     * 是否為靜態資源.
//     *
//     * @param url the url
//     * @return true, if is resource url
//     */
//    private boolean isResourceUrl(String url) {
//        boolean isResourceUrl = false;
//        List<String> resourceRequests = Arrays.asList(
//            "/css/", "/js/", "/scss/", "/fonts/", "/emails/",
//            ".css", ".js", ".scss", ".eot", ".svg", ".ttf", ".woff", ".otf", ".ico", ".jpg", ".jpeg", ".gif",".png", ".html");
//        for (String resourceRequest : resourceRequests) {
//            if (StringUtils.containsIgnoreCase(url, resourceRequest)) {
//                isResourceUrl = true;
//            }
//        }
//        return isResourceUrl;
//    }
//}
