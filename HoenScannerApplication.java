package com.hoenscanner;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import com.hoenscanner.models.SearchResult;
import com.hoenscanner.resources.SearchResource;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {

    public static List<SearchResult> searchResults = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        new HoenScannerApplication().run(args);
    }

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        searchResults.addAll(objectMapper.readValue(
            new File("src/main/resources/rental_cars.json"), new TypeReference<List<SearchResult>>() {}));

        searchResults.addAll(objectMapper.readValue(
            new File("src/main/resources/hotels.json"), new TypeReference<List<SearchResult>>() {}));

        System.out.println("Data loaded successfully!");

        // Register API resource
        environment.jersey().register(new SearchResource());
    }
}