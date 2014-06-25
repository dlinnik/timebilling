package ru.timebilling.web.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import ru.timebilling.model.domain.User;
import ru.timebilling.model.domain.UserRoleEnum;
import ru.timebilling.model.service.UserDetailsServiceImpl;

@Component("userSessionComponent") 
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserSessionComponent {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    
    public boolean isAdmin(){
    	return userDetailsService.getRoles(
    			this.currentUser.getRole().getRole()).
    			contains(UserRoleEnum.ROLE_ADMIN.name());
    }
}

