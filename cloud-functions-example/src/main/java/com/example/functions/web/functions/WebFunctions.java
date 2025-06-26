package com.example.functions.web.functions;

import com.example.functions.payload.RequestData;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class WebFunctions {


    // return value
    @Bean
    public Supplier<String> sayHello() {
        return () -> {
            return "Hello, how are you?";
        };

    }

    @Bean
    public Consumer<RequestData> inputData() {
        return (inputData) -> {

            //processing...
            System.out.println(inputData.getName());
            System.out.println(inputData.getContact());
        };


    }

    @Bean
    public Function<RequestData, String> submitData() {
        return (inputData) -> {

            return "Data submitted " + inputData.getName();
        };
    }

}
