package com.codeit.dockerpractice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class FilesPageController {

    @GetMapping("/files/page")
    public String filesPage() {
        // /resources/static/files.html 로 forward
        return "forward:/files.html";
    }
}
