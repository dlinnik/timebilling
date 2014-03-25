package ru.timebilling.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ru.timebilling.web.component.AppContext;

/**
 * Содержит базовые методы для контроллеров проекта
 * @author vshmelev
 *
 */
@Controller
public class AbstractController {
	
	@Autowired
	AppContext appId;
	
	/**
	 * редирект на основную страницу с проектами
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
    public String projects(Model model) {
		return buildRedirectUrl("/projects");
	}

	
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
