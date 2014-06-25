package ru.timebilling.model.service.conversion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import ru.timebilling.model.domain.Application;
import ru.timebilling.model.domain.Expense;
import ru.timebilling.model.domain.User;
import ru.timebilling.rest.domain.Record;
import ru.timebilling.rest.domain.UserDetails;
import ru.timebilling.web.component.AppContext;

@Service
public class UserDetailsConverter extends AbstractConverter<User, UserDetails>{
	
    @Autowired
    AppContext appContext;

	
	@Override
	public User toEntity(UserDetails v) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public UserDetails toDTO(User e) {
    	UserDetails ret = new UserDetails();
		Integer role = e.getRole().getRole();
		Application app = appContext.getApplication();
		
		ret.setAppScreenName(app.getScreenName());
		ret.setRole(role);
		ret.setUsername(e.getUsername());
		ret.setEmail(e.getEmail());
		return ret;
	}
	


}
