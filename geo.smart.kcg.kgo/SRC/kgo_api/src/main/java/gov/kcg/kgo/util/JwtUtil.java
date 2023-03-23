package gov.kcg.kgo.util;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import gov.kcg.kgo.config.Constants;
import gov.kcg.kgo.enums.error.KgoCommonApiError;
import gov.kcg.kgo.exception.ErrorResult;
import gov.kcg.kgo.exception.KgoApiException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * The Class JwtTokenUtil.
 */
@Component
public class JwtUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

	private static final String CLAIM_SUB = "sub";
    
	@Value("${kgo.jwt.secret}")
	private String jwtSecret;

	@Value("${kgo.jwt.expirationMs}")
	private int jwtExpirationMs;
	
	@Value("${kgo.jwt.refresh.expirationMs}")
	private int jwtRefreshExpirationMs;


    /**
     * 產生token
     */
    public String genToken(String tokenValue) {
        LOGGER.info("JwtUtil genToken...");
    	return gen(tokenValue, jwtExpirationMs);
    }
    
    /**
     * 產生RefreshToken
     */
    public String genRefreshToken() {
    	String tokenValue = RandomUtil.getUUID();
    	return gen(tokenValue, jwtRefreshExpirationMs);
    }
    
    /**
     * 產生token.
     *
     * @param tokenValue the token value
     * @param expirationTime the expiration time
     * @return the string
     */
    private String gen(String tokenValue, long expirationTime) {
    	Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_SUB, tokenValue);

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration( new Date( Instant.now().toEpochMilli() + expirationTime))
                .signWith( SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * 解析token.
     *
     * @param req the req
     * @return the string
     */
    public String resolveToken(HttpServletRequest req) {
    	// token rq 表頭 "Authorization".
        String bearerToken = req.getHeader(Constants.TOKEN_HERDER);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
        	// 取代掉Bearer 
            return StringUtils.replace(bearerToken, "Bearer ", "");
        }
        return null;
    }
    /**
     * 驗證JWT
     */
    public Boolean validateToken(String token, String val) {
        //看來只有用在 forntend ui 驗證
        LOGGER.info("JwtUtil validateToken...");

        String tokenSub = getSubFromToken(token); // 根據token獲取tokenSub
        if(StringUtils.isEmpty(val)) {
        	return false;
        }

        return (tokenSub.equals(val) && !isTokenExpired(token));
    } //validateToken

    /**
     * 獲取token是否過期
     */
    public Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 根據token獲取tokenSub
     */
    public String getSubFromToken(String token) {
    	String tokenSub = "";
    	Claims claims = getClaimsFromToken(token);
    	if(claims != null ) {
    		tokenSub =  claims.getSubject();
    	}
        return tokenSub;
    }

    /**
     * 獲取token的過期時間
     */
    public Date getExpirationDateFromToken(String token) {
    	Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration;
    }
    
    /**
     * 獲取token 剩下時間(秒).
     */
    public long getExpirationRemain(String token) {
        Date expiration = getClaimsFromToken(token).getExpiration();
        return DateUtil.milliSecondToSec(expiration.getTime() - new Date().getTime());
    }

    /**
     * 解析JWT
     */
    public Claims getClaimsFromToken(String token) {
    	try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
    	} catch (Exception e) {
    		LOGGER.error("Invalid JWT getClaimsFromToken: {}", e.getMessage());
    		// 未取得授權
			throw new KgoApiException(new ErrorResult(KgoCommonApiError.UNAUTHORIZED), e);
    		// return null;
		}
    }

//    catch (SignatureException e) {
//		LOGGER.error("Invalid JWT signature: {}", e.getMessage());
//	} catch (MalformedJwtException e) {
//		LOGGER.error("Invalid JWT token: {}", e.getMessage());
//	} catch (ExpiredJwtException e) {
//		LOGGER.error("JWT token is expired: {}", e.getMessage());
//	} catch (UnsupportedJwtException e) {
//		LOGGER.error("JWT token is unsupported: {}", e.getMessage());
//	} catch (IllegalArgumentException e) {
//		LOGGER.error("JWT claims string is empty: {}", e.getMessage());
//	}
        
        
//		try {
//			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
//			return true;
//		} catch (SignatureException e) {
//			logger.error("Invalid JWT signature: {}", e.getMessage());
//		} catch (MalformedJwtException e) {
//			logger.error("Invalid JWT token: {}", e.getMessage());
//		} catch (ExpiredJwtException e) {
//			logger.error("JWT token is expired: {}", e.getMessage());
//		} catch (UnsupportedJwtException e) {
//			logger.error("JWT token is unsupported: {}", e.getMessage());
//		} catch (IllegalArgumentException e) {
//			logger.error("JWT claims string is empty: {}", e.getMessage());
//		}
//    }
}