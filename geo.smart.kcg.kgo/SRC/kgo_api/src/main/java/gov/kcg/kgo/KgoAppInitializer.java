package gov.kcg.kgo;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class KgoAppInitializer extends SpringBootServletInitializer {
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		// disable ErrorPageFilter
		setRegisterErrorPageFilter(false);
		return application.sources(KgoApplication.class);
	}

//	@Override
//	public void onStartup(ServletContext sc) throws ServletException {
//		// super.onStartup(sc);
//		sc.getSessionCookieConfig().setHttpOnly(true);
//		sc.getSessionCookieConfig().setSecure(true);
//	}
}
