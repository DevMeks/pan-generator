package com.devmeks.pangenerator.service;


import com.devmeks.pangenerator.configuration.BINProperties;
import com.devmeks.pangenerator.model.PAN;
import com.devmeks.pangenerator.repository.PANRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public final class PanGenerator {

    private final BINProperties binProperties;


    private final PANRepo panRepo;


    @Autowired
    public PanGenerator(PANRepo panRepo, BINProperties binProperties) {
        this.binProperties = binProperties;
        this.panRepo = panRepo;
    }




    public Mono<String> createPan(String phoneNumber, String cardScheme){

        String iin;
        StringBuilder panBuilder = new StringBuilder();

        switch (cardScheme.toLowerCase()){
            case "verve":
                iin = binProperties.getVerve();
                break;
            case "mastercard":
                iin = binProperties.getMastercard();
                break;
            default:
                iin = "000000";

        }

        String partialPan = iin + phoneNumber.substring(2);

        String pan = panBuilder
                .append(partialPan)
                .append(generateChecksumDigit(partialPan)).toString();

        PAN panObject = PAN.builder()
                        .cardNumber(pan)
                        .build();


        try{
            panRepo.save(panObject);

        }catch(Exception e){
            log.error(e.getMessage());

        }

        return Mono.just(pan);









    }

    private String generateChecksumDigit(String partialCardNumber) {
        int sum = 0;
        boolean alternate = false;

        for (int i = partialCardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(partialCardNumber.substring(i, i + 1));

            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }

            sum += n;
            alternate = !alternate;
        }

        return  String.valueOf((sum * 9) % 10);
    }
}
