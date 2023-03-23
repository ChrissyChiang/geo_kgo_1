package gov.kcg.kgo.security.filter;

import gov.kcg.kgo.util.KgoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class IpBlacklistFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(IpBlacklistFilter.class);

    @Value("${kgo.ip.blacklist}")
    private String blacklist;

    @Value("${kgo.ip.blacklist.enable}")
    private boolean isBlacklistEnable;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 20210823 GEO add for testing --> 強制在 response 回傳 cookie 給前端以維繫不同機器的 session 無效(browser 已不支援)
        /*if (Boolean.parseBoolean(SpringUtil.getProperty("dev.mode")) && response.getHeader("Set-Cookie")==null)
            response.setHeader("Set-Cookie", "JSESSIONID="+request.getSession().getId()+"; Path=/kgo; Httponly; SameSite=None; Secure");*/


        String clientIp = KgoUtil.getClientIp();
        LOGGER.info("Enable blacklist: {} , Ip [{}] is accessing...", isBlacklistEnable, clientIp);
        List<String> blockedIps = Arrays.stream(blacklist.split(",")).filter(ip -> ip.length() > 0)
                .collect(Collectors.toList());
        if (isBlacklistEnable && !blockedIps.isEmpty()) { // 啟用IP黑名單且黑名單不為空

            BiPredicate<String, String> testBlockedIp = (blockedIp, remoteIp) -> {
                IpAddressMatcher matcher = new IpAddressMatcher(blockedIp);
                return matcher.matches(remoteIp);
            };

            boolean isIpInBlacklist =
                    blockedIps.stream().anyMatch(blockIp -> testBlockedIp.test(blockIp, clientIp));
            if (isIpInBlacklist) {
                LOGGER.error("Ip [{}] is in the blacklist.", clientIp);
                throw new AccessDeniedException("Access denied.");
            } else {
                filterChain.doFilter(request, response);
            }
        } else { // 停用IP黑名單或黑名單為空
            filterChain.doFilter(request, response);
        }
    }
}
