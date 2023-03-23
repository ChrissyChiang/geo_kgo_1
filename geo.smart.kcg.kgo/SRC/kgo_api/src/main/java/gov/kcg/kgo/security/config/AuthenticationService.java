package gov.kcg.kgo.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service("AuthenticationService")
public class AuthenticationService implements AuthenticationProvider {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

//	@Autowired
//	private SysUserViewRepository sysUserViewRepository;
//	
//	@Autowired
//	private SysRoleUserRepository sysRoleUserRepository;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		// userEmail
		String userEmail = authentication.getName();
		// 無須密碼驗證 userPwd 為空
		String userPwd = authentication.getCredentials().toString();

		LOGGER.info("AuthenticationService authenticate userEmail/userPwd: "+userEmail+"/"+userPwd);
		
		// 只有一位後台管理員 登錄成功 role = ROLE_ADMIN
//		String[] roleArr = {"ROLE_ADMIN"};
//		List<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList(roleArr);

//		if(StringUtils.isNotEmpty(userId)) {
//			// 無須密碼驗證 userPwd 為空
//			UserDetails userDetails = new CustomUserDetails(userId, userPwd, grantedAuthority);
//			return new UsernamePasswordAuthenticationToken(userDetails, userPwd, grantedAuthority);
//		}
//		SysUserView loginedUser = sysUserViewRepository.findByEmail(userEmail);

//		if (loginedUser != null) {
//				List<SysRoleUser> userRoles = sysRoleUserRepository.findByIdCathayNo(loginedUser.getId().getCathayNo());
//				if(CollectionUtils.isEmpty(userRoles)) {
//					return null;
//				}
//				List<String> roleList = userRoles.stream().map(r->r.getId().getRoleId()).collect(Collectors.toList());
//
//				String[] array = roleList.stream().toArray(String[]::new);
//
//				List<GrantedAuthority> grantedAuthority = AuthorityUtils.createAuthorityList(array);
//
//				CustomUserDetails userDetails = new CustomUserDetails(loginedUser, grantedAuthority);
//				return new UsernamePasswordAuthenticationToken(userDetails, userPwd, grantedAuthority);
//		}
		return null;
	}

	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
