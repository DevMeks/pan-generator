package com.devmeks.pangenerator.utility;


import com.devmeks.pangenerator.configuration.BINProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class PanUtils {

    private final BINProperties binProperties;

    private static final Random RANDOM = new Random();


    @Autowired
    public PanUtils(BINProperties binProperties) {
        this.binProperties = binProperties;
    }


    /** Retrieves the BIN from the property file based on the card
     * scheme provided*/
    public String retrieveIin(String cardScheme){

        switch (cardScheme.toLowerCase()){
            case "verve":
                return binProperties.getVerve();
            case "mastercard":
                return binProperties.getMastercard();
            default:
                return "000000";

        }


    }

    /**This method generates random digits of length 10. It generates it in two parts of 5
     * to further increase the chance of a random PAN being generated */
    public String generateRandomDigits() {

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < 2; i++){
            long m = (long) Math.pow(10, 4);
            long partialDigits = m + RANDOM.nextInt((int) (9 * m));
            builder.append(partialDigits);
        }


        return builder.toString();


    }


    /** This method generates the checksum digit of PAN*/
    public String generateChecksumDigit(String partialCardNumber) {
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
