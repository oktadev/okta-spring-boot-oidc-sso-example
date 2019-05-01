package com.okta.examples.springbootoidcsso.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user.getUserInfo());
        mav.addObject("idToken", user.getIdToken().getTokenValue());
        mav.setViewName("home");
        return mav;
    }
}
