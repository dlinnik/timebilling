package ru.timebilling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import ru.timebilling.web.component.AppContext;

/**
 * Содержит базовые методы для контроллеров проекта
 * @author vshmelev
 *
 */
public class AbstractController {
	
	@Autowired
	AppContext appId;
	
	/**
	 * собирает строку для редиректа //учитывая appId
	 * @param url
	 * @return
	 */
	public String buildRedirectUrl(String url){		
//		return "redirect:" + "/app/" + appId.getId() + url;
		return "redirect:" + url;
	}

}
