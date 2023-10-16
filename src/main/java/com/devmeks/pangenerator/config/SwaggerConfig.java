package com.devmeks.pangenerator.config;

import com.devmeks.pangenerator.dto.request.CreatePanFromMobileNumDto;
import com.devmeks.pangenerator.dto.response.ResponseDto;
import com.devmeks.pangenerator.exception.model.ApiError;
import com.devmeks.pangenerator.util.enums.ResponseStatus;
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
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
@OpenAPI30
public class SwaggerConfig {

  @Bean
  public OpenAPI springOpenApi() {

    var error = ApiError.ceateApiError();
    error.setErrorMessage("MobileNumber must be 11 digits long");

    var successfulResponse = new ResponseDto();
    successfulResponse.setPan("5555555555555555");
    successfulResponse.setResponseStatus(ResponseStatus.SUCCESSFUL);


    var invalidMobileNumberResponse = new ResponseDto();
    invalidMobileNumberResponse.setResponseStatus(ResponseStatus.INVALID_REQUEST);
    invalidMobileNumberResponse.setError(error);

    var request = CreatePanFromMobileNumDto
        .builder()
        .mobileNumber("080XXXXXXXX")
        .cardScheme("Verve")
        .build();


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
            .addPathItem("/generate-pan", new PathItem()
                .post(new Operation()
                    .description("GENERATES PAN USING MOBILE NUMBER AND CARD SCHEME")
                    .tags(new ArrayList<>() {{
                      add("Generate Pan");
                    }})
                    .requestBody(new RequestBody()
                        .description("generate pan using mobile number and card scheme")
                        .required(true)
                        .content(new Content()
                            .addMediaType("application/json",
                                new MediaType()
                                    .example(request)
                                    .schema(new Schema<CreatePanFromMobileNumDto>())))
                    )
                    .responses(
                        new ApiResponses()
                            .addApiResponse("201", new ApiResponse()
                                .description("Successful Response")
                                .content(new Content()
                                    .addMediaType("application/json",
                                        new MediaType()
                                            .example(successfulResponse)
                                    )))
                            .addApiResponse("400", new ApiResponse()
                                .description("Invalid mobileNumber")
                                .content(new Content()
                                    .addMediaType("application/json",
                                        new MediaType()
                                            .example(invalidMobileNumberResponse)
                                    )))
                    )

                )))
        ;
  }


}
