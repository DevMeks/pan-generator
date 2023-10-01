package com.devmeks.pangenerator.controller;


import com.devmeks.pangenerator.service.PanGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/pan")
public class PanController {

    private final PanGenerator panGenerator;

    @Autowired
    public PanController(PanGenerator panGenerator){
        this.panGenerator = panGenerator;
    }




    @RequestMapping("/generate")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces={MediaType.APPLICATION_JSON_VALUE})
    public Mono<String> createPan(@RequestBody RequestDto requestDto){

        return panGenerator.createPanFromMobileNumber(requestDto);



    }
}
