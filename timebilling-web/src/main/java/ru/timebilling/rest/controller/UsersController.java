package ru.timebilling.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ru.timebilling.model.repository.UserRepository;
import ru.timebilling.model.service.conversion.UserDetailsConverter;
import ru.timebilling.rest.domain.UserDetails;
import static ru.timebilling.model.service.conversion.ConversionUtils.wrapForLikeClause;

@Controller
public class UsersController extends BaseAPIController{
	
	@Autowired
	UserDetailsConverter converter;
	
	@Autowired
	UserRepository repository;
	
	@RequestMapping(value="/users/search", method=RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Iterable<UserDetails> findUserByNameOrEmail(
			Model model,
			@RequestParam(value="name", required=false) String name, 
			@RequestParam(value="email", required=false) String email)
	{
		if(email!=null){
			return converter.toDTO(repository.filterByEmail(wrapForLikeClause(email)));
		}else{
			return converter.toDTO(repository.filterByName(wrapForLikeClause(name)));
		}
	}
	
}
