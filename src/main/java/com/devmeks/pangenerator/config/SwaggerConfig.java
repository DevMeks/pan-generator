package com.devmeks.pangenerator.config;

import com.devmeks.pangenerator.dto.request.CreatePanDto;
import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.exception.ApiError;
import com.devmeks.pangenerator.model.Pan;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.Paths;
import io.swagger.v3.oas.models.annotations.OpenAPI30;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
@OpenAPI30
public class SwaggerConfig {

  @Bean
  public OpenAPI springOpenApi() {

    var cardNumber = "5555555555555555";
    var successResponse = "Successful Response";

    var invalidMobileNumberError = ApiError.ceateApiError();
    invalidMobileNumberError.setErrorMessage("MobileNumber must be 11 digits long");

    var missingCardSchemeError = ApiError.ceateApiError();
    missingCardSchemeError.setErrorMessage("cardScheme should not be empty");

    var successfulResponse = new ResponseDto();
    successfulResponse.setPan(cardNumber);
    successfulResponse.setResponseStatus(ResponseStatus.SUCCESSFUL);

    var panList = new ArrayList<Pan>();
    panList.add(Pan.builder().id("5gf434fe").cardNumber(cardNumber).build());

    var getPansResponse =
        ResponseDto.builder()
            .pans(panList)
            .responseStatus(ResponseStatus.SUCCESSFUL)
            .build();

    var getPanResponse =
        ResponseDto.builder()
            .pan(cardNumber)
            .responseStatus(ResponseStatus.SUCCESSFUL)
            .build();

    var getNoPanResponse =
        ResponseDto.builder()
            .responseStatus(ResponseStatus.NO_RECORD_FOUND)
            .build();


    var invalidMobileNumberResponse = new ResponseDto();
    invalidMobileNumberResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
    invalidMobileNumberResponse.setError(invalidMobileNumberError);

    var missingCardSchemeResponse = new ResponseDto();
    missingCardSchemeResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
    missingCardSchemeResponse.setError(missingCardSchemeError);

    var request = CreatePanFromMobileNumDto
        .builder()
        .mobileNumber("080XXXXXXXX")
        .cardScheme("Verve")
        .build();

    var randomPanRequest = CreatePanDto
        .builder()
        .cardScheme("Verve")
        .isGlobalVerveCard(true)
        .build();

    final String JSON_MEDIA_TYPE = "application/json";

    var generatePanTagList = new ArrayList<String>();
    generatePanTagList.add("Generate Pan");

    var getPanTagList = new ArrayList<String>();
    getPanTagList.add("Get Pan(s)");


    return new OpenAPI()
        .info(new Info()
            .title("Pan Generator API")
            .description("Generates PAN for different card schemes")
            .version("v1.0")
            .contact(new Contact()
                .name("Chukwuemeka Vin-Anuonye")
                .email("chib.vinan@gmail.com")
            )
            .license(new License()
                .name("Terms of Use")
                .url("https://github.com/git/git-scm.com/blob/main/MIT-LICENSE.txt")
            )
        )

        .addServersItem(new Server().url("http://localhost:9993"))
        .paths(new Paths()
            .addPathItem("/api/v1/pan-generator/mobile/pans", new PathItem()
                .post(new Operation()
                    .description("GENERATES PAN USING MOBILE NUMBER AND CARD SCHEME")
                    .tags(generatePanTagList)
                    .operationId("01")
                    .summary("This operation generates a PAN for the specified card scheme" +
                        " using the mobile number provided")
                    .requestBody(new RequestBody()
                        .description("generate pan using mobile number and card scheme")
                        .required(true)
                        .content(new Content()
                            .addMediaType(JSON_MEDIA_TYPE,
                                new MediaType()
                                    .example(request)
                                    .schema(new Schema<CreatePanFromMobileNumDto>())))
                    ).responses(
                        new ApiResponses()
                            .addApiResponse("201", new ApiResponse()
                                .description(successResponse)
                                .content(new Content()
                                    .addMediaType(JSON_MEDIA_TYPE,
                                        new MediaType()
                                            .example(successfulResponse)
                                    )))
                            .addApiResponse("400", new ApiResponse()
                                .description("Invalid mobileNumber")
                                .content(new Content()
                                    .addMediaType(JSON_MEDIA_TYPE,
                                        new MediaType()
                                            .example(invalidMobileNumberResponse)
                                    )))
                            .addApiResponse("401", new ApiResponse()
                                .description("Missing card scheme")
                                .content(new Content()
                                    .addMediaType(JSON_MEDIA_TYPE,
                                        new MediaType()
                                            .example(missingCardSchemeResponse)
                                    )))

                    )

                )).addPathItem("/api/v1/pan-generator/random/pans", new PathItem()
                .post(new Operation()
                    .description("GENERATES RANDOM PAN FOR PROVIDED CARD SCHEME")
                    .tags(generatePanTagList)
                    .operationId("02")
                    .summary("This operation generates a random PAN for the supplied card scheme")
                    .requestBody(new RequestBody()
                        .description("generate random pan for provided card scheme")
                        .required(true)
                        .content(new Content()
                            .addMediaType(JSON_MEDIA_TYPE,
                                new MediaType()
                                    .example(randomPanRequest)
                                    .schema(new Schema<CreatePanDto>())))
                    )
                    .responses(
                        new ApiResponses()
                            .addApiResponse("201", new ApiResponse()
                                .description(successResponse)
                                .content(new Content()
                                    .addMediaType(JSON_MEDIA_TYPE,
                                        new MediaType()
                                            .example(successfulResponse)
                                    )))
                            .addApiResponse("400", new ApiResponse()
                                .description("Invalid mobileNumber")
                                .content(new Content()
                                    .addMediaType(JSON_MEDIA_TYPE,
                                        new MediaType()
                                            .example(invalidMobileNumberResponse)
                                    ))).addApiResponse("400", new ApiResponse()
                                .description("Missing card scheme")
                                .content(new Content()
                                    .addMediaType(JSON_MEDIA_TYPE,
                                        new MediaType()
                                            .example(missingCardSchemeResponse)
                                    )))

                    )

                ))
            .addPathItem("/api/v1/pan-generator/pans/{pageNumber}/{pageSize}",
                new PathItem()
                    .get(new Operation()
                        .description("RETRIEVES A LIST OF PANs USING PAGINATION")
                        .summary("This operation returns a list of Pans")
                        .tags(getPanTagList)
                        .parameters(Arrays.asList(
                            new Parameter()
                                .name("pageNumber")
                                .in(String.valueOf(ParameterIn.PATH))
                                .required(true)
                                .description("The page number to be returned")
                                .example(1)
                                .allowEmptyValue(false),
                            new Parameter()
                                .name("pageSize")
                                .in(String.valueOf(ParameterIn.PATH))
                                .required(true)
                                .description("The number of pages to be returned")
                                .example(1)
                                .allowEmptyValue(false)
                        ))
                        .responses(
                            new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                    .description(successResponse)
                                    .content(new Content()
                                        .addMediaType(JSON_MEDIA_TYPE,
                                            new MediaType()
                                                .example(getPansResponse))))
                        )
                    )


            ).addPathItem("/api/v1/pan-generator/pans",
                new PathItem()
                    .get(new Operation()
                        .description("Retrieves a Pan")
                        .summary("This operation retrieves a Pan using the unique id  tied to the Pan")
                        .tags(getPanTagList)
                        .parameters(Collections.singletonList(
                            new Parameter()
                                .name("panUid")
                                .in(String.valueOf(ParameterIn.QUERY))
                                .required(true)
                                .description("The UID mapped to the Pan")
                                .example("543r-32egt-534g")
                                .allowEmptyValue(false)
                        ))
                        .responses(
                            new ApiResponses()
                                .addApiResponse("200", new ApiResponse()
                                    .description("Successful Response")
                                    .content(new Content()
                                        .addMediaType(JSON_MEDIA_TYPE,
                                            new MediaType()
                                                .example(getPanResponse))))
                                .addApiResponse("404", new ApiResponse()
                                    .description("No Record Found")
                                    .content(new Content()
                                        .addMediaType(JSON_MEDIA_TYPE,
                                            new MediaType()
                                                .example(getNoPanResponse)
                                        )
                                    )
                                )


                        )
                    )));

  }


}
