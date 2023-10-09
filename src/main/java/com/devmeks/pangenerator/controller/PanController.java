package com.devmeks.pangenerator.controller;


import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.service.PanGenerator;
import com.devmeks.pangenerator.util.PanUtils;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * The type Pan controller.
 */
@RestController
@RequestMapping("/api/v1/pan")
public class PanController {

  private final PanGenerator panGenerator;
  private final PanUtils panUtils;

  /**
   * Instantiates a new Pan controller.
   *
   * @param panGenerator the pan generator
   * @param panUtils     the pan utils
   */
  @Autowired
  public PanController(PanGenerator panGenerator, PanUtils panUtils) {

    this.panGenerator = panGenerator;
    this.panUtils = panUtils;
  }


  /**
   * Create pan response entity .
   *
   * @param requestDto the request dto
   * @return the response entity
   */
  @RequestMapping("/generate-pan")
  @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})


  public ResponseEntity<Mono<ResponseDto>> createPan(
      @Valid @RequestBody CreatePanFromMobileNumDto requestDto) {

    var response = panGenerator.createPanFromMobileNumber(requestDto);
    return panUtils.processResponse(response);


  }
}
