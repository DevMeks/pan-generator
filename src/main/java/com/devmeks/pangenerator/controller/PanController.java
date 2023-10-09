package com.devmeks.pangenerator.controller;


import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.service.PanGenerator;
import com.devmeks.pangenerator.util.PanUtils;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@OpenAPIDefinition(
    info = @Info(
        title = "Pan Generator API",
        description = "Generates PAN for different card schemes",
        version = "1.0",
        contact = @Contact(name = "Chukwuemeka Vin-Anuonye", email = "chib.vinan@gmail.com")
    ),
    servers = @Server(url = "http://localhost:9993"))
@RequestMapping("/api/v1/pan")
@Tag(name = "PAN")
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
  @Operation(
      summary = "GENERATES PAN USING MOBILE NUMBER AND CARD SCHEME",
      responses = {
          @ApiResponse(responseCode = "201", description = "Created",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseDto.class))}),

          @ApiResponse(responseCode = "404", description = "Bad Request",
              content = {@Content(mediaType = "application/json",
                  schema = @Schema(implementation = ResponseDto.class))})}
  )

  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Mono<ResponseDto>> createPan(
      @Valid @RequestBody CreatePanFromMobileNumDto requestDto) {

    var response = panGenerator.createPanFromMobileNumber(requestDto);
    return panUtils.processResponse(response);


  }
}
