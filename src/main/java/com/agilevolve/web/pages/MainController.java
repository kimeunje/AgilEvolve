package com.agilevolve.web.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
  @GetMapping(value = { "/", "/login", "/register", "/board/*" })
  public String entry() {
    return "index";
  }
}
