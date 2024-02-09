package com.francalino.frankley.endpointtester.util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHelper {
	
	public static HttpResponse<String> get(String url) {
		
		HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();

        HttpRequest request = HttpRequest.newBuilder()
	        .uri(URI.create(url))
	        .GET()
	        .build();

        try {
            HttpResponse<String> response =
            	httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            
            return response;
            
        } catch (Exception e) {
        	
        	throw new RuntimeException(e);
        	
        }
        
	}

}
