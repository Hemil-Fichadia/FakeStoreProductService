package dev.hemil.fakestoreproductservice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/razorpayWebHook")
public class RazorpayWebHookController {

    @PostMapping("/")
    public ResponseEntity acceptRazorpayWebHookRequest(){
        return null;
    }
}
