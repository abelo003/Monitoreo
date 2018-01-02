/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.business;

import com.cruz.mx.monitoreo.enums.BITSO_CURRENCY;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author acruzb
 */
@Repository
public class BitsoBusiness {
    
    @Value("${service.bitso.ticker}")
    private String serviceURL;
    private final RestTemplate restTemplate;
    private final HttpEntity<String> entity;

    public BitsoBusiness() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        InetSocketAddress address = new InetSocketAddress("10.50.8.20", 8080);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        factory.setProxy(proxy);
        restTemplate = new RestTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0");
        entity = new HttpEntity<>(headers);
    }
    
    public JsonNode consultarTodo(){
        return restTemplate.exchange(serviceURL, HttpMethod.GET, entity, JsonNode.class).getBody();
    }
    
    public JsonNode consultarCambio(BITSO_CURRENCY currency){
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(serviceURL)
            .queryParam("book", currency.toString().toLowerCase());
        return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, JsonNode.class).getBody();
    }
    
}
