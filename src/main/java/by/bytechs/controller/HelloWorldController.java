package by.bytechs.controller;

import by.bytechs.security.model.AppUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Romanovich Andrei 12.01.2021 - 11:57
 */
@RestController
@RequestMapping("/")
public class HelloWorldController {

    @GetMapping
    public String hello(Authentication authentication) {
        return "Hello " + ((AppUserDetails) authentication.getPrincipal()).getFullName();
    }
}
