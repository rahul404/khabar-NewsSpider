package org.kjsce.khabar.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@GetMapping("/")
public class DemoController {
    public ResponseEntity<?> index(){
        return ResponseEntity.ok("Hello World!!!");
    }
}
