package gov.kcg.kgo.security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	/** Logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 無密碼
		LOGGER.info("CustomUserDetailsService loadUserByUsername username: "+username);
		return new CustomUserDetails(username, "" , buildUserAuthority("ADMIN"));

    }
 
	private List<GrantedAuthority> buildUserAuthority(String userRole){
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(userRole));
		 
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
		 
		return Result;
	}
}
