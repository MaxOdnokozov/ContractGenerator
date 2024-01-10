package com.maksym.odnokozov.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

  @GetMapping("/login")
  public String login(
          @RequestParam(value = "error", required = false) String error,
          @RequestParam(value = "logout", required = false) String logout,
          Model model) {
    String errorMessage = null;
    if (error != null) {
      errorMessage = "Username or Password is incorrect !!";
    }
    if (logout != null) {
      errorMessage = "You have been successfully logged out !!";
    }
    model.addAttribute("errorMessage", errorMessage);
    return "login";
  }
}
