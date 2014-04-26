package ru.timebilling.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ru.timebilling.persistance.AppNameSupplier;
import ru.timebilling.persistance.domain.User;
import ru.timebilling.persistance.domain.UserRoleEnum;
import ru.timebilling.persistance.repository.UserRepository;
import ru.timebilling.web.component.UserSessionComponent;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private UserSessionComponent userInSession;
    
    @Autowired
    private AppService appService;
    
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User domainUser = null;
		try{
			//Workaround to set application to context
			appService.getCurrentApplicationContext();
			
			domainUser = userRepository.findByEmail(userName);
		}catch(Throwable e){
			e.printStackTrace();
		}
		if(domainUser == null){
			throw new UsernameNotFoundException("User not found");
		}
		
		userInSession.setCurrentUser(domainUser);
		
		boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;

        return new org.springframework.security.core.userdetails.User(
                domainUser.getEmail(),
                domainUser.getPassword(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                getAuthorities(domainUser.getRole().getRole()));	}
	
    /**
     * Retrieves a collection of {@link GrantedAuthority} based on a numerical role
     *
     * @param role the numerical role
     * @return a collection of {@link GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }

    /**
     * Converts a numerical role to an equivalent list of roles
     *
     * @param role the numerical role
     * @return list of roles as as a list of {@link String}
     */
    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();
        if (role.intValue() == 1) {
            roles.add(UserRoleEnum.ROLE_USER.name());
            roles.add(UserRoleEnum.ROLE_ADMIN.name());
        } else if (role.intValue() == 2) {
            roles.add(UserRoleEnum.ROLE_USER.name());
        }
        return roles;
    }

    /**
     * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects
     *
     * @param roles {@link String} of roles
     * @return list of granted authorities
     */
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

	
	
	

}
