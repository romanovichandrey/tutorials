package by.bytechs.controller;

import by.bytechs.security.model.AppUserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author Romanovich Andrei 12.01.2021 - 11:57
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping
    public String hello(Principal principal) {
        return "Hello " + ((AppUserDetails) principal).getFullName();
    }
}
