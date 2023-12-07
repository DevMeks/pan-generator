package com.devmeks.pangenerator.controller;


import com.devmeks.pangenerator.dto.request.CreatePanDto;
import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.service.PanGenerator;
import com.devmeks.pangenerator.util.PanUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


/**
 * The type Pan controller.
 */
@RestController
@RequestMapping("/api/v1/pan-generator")
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
   * This controller method generates a PAN using the mobile number
   * and card scheme provided
   * @param requestDto the request dto
   * @return the response entity
   */
  @RequestMapping(path = "/mobile/pan",
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Mono<ResponseDto>> generatePanUsingMobileNumberForSpecifiedCardScheme(
      @Valid @RequestBody CreatePanFromMobileNumDto requestDto) {

    var response = panGenerator.createPanFromMobileNumber(requestDto);
    return panUtils.processResponse(response);

  }


  @RequestMapping(path = "/random/pan",
      method = RequestMethod.POST,
      consumes = {MediaType.APPLICATION_JSON_VALUE},
      produces = {MediaType.APPLICATION_JSON_VALUE})
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Mono<ResponseDto>> generatePanForSpecifiedCardScheme(
      @Valid @RequestBody CreatePanDto requestDto) {
    var response = panGenerator.generateRandomPan(requestDto.getCardScheme(),
        requestDto.isGlobalVerveCard());
    return panUtils.processResponse(response);


  }
}
