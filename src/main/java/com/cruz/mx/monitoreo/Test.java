/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import com.fasterxml.jackson.databind.JsonNode;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author acruzb
 */
public class Test {

    public static void main(String[] args) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        InetSocketAddress address = new InetSocketAddress("10.50.8.20", 8080);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        factory.setProxy(proxy);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate(factory);
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString("https://api.bitso.com/v3/ticker/")
            .queryParam("book", "xrp_mxn");

        JsonNode response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, JsonNode.class).getBody();

        System.out.println(response);
    }
}
