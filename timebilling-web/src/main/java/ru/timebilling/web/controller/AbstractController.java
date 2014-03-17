package ru.timebilling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import ru.timebilling.web.component.AppId;

/**
 * Содержит базовые методы для контроллеров проекта
 * @author vshmelev
 *
 */
public class AbstractController {
	
	@Autowired
	AppId appId;
	
	/**
	 * собирает строку для редиректа учитывая appId
	 * @param url
	 * @return
	 */
	public String buildRedirectUrl(String url){		
		return "redirect:" + "/app/" + appId.getId() + url;
		
	}

}
