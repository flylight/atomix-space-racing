package org.atomix.space.racing.galaxy.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

  @GetMapping
  public String getPage() {

    return "index.html";
  }

}
