package com.okta.examples.springbootoidcsso.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    ObjectMapper mapper = new ObjectMapper();

    @GetMapping("/")
    public ModelAndView home(@AuthenticationPrincipal OidcUser user) throws JsonProcessingException {
        ModelAndView mav = new ModelAndView();
        mav.addObject("user", user.getUserInfo());
        mav.addObject("idToken", user.getIdToken().getTokenValue());
        mav.addObject(
            "claims",
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user.getClaims())
        );
        mav.setViewName("home");
        return mav;
    }
}
