package com.auspost.postcode.PostCode;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostCodeController {

    @GetMapping
    public String hello() {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "h1 {text-align: center;}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "\n"
                + "<h1>Yaay!! you did it XD</h1>\n"
                + "\n"
                + "</body>\n"
                + "</html>\n";
    }

}
