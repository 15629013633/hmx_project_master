package com.hmx.system.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class SystemController {


	@RequestMapping("/info/init")
	public ModelAndView infoInit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/unfinished");
		return modelAndView;
	}

	@RequestMapping("/banner/init")
	public ModelAndView bannerInit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/unfinished");
		return modelAndView;
	}
	@RequestMapping("/feedback/init")
	public ModelAndView feedbackInit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/unfinished");
		return modelAndView;
	}
	@RequestMapping("/hot/init")
	public ModelAndView hotInit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/unfinished");
		return modelAndView;
	}
	@RequestMapping("/aboutUs/init")
	public ModelAndView aboutUsInit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/unfinished");
		return modelAndView;
	}
	@RequestMapping("/version/init")
	public ModelAndView versionInit() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/unfinished");
		return modelAndView;
	}

}
