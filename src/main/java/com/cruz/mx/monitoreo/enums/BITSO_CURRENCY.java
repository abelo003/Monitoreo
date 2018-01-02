/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo.enums;

/**
 *
 * @author acruzb
 */
public enum BITSO_CURRENCY {

    BTC_MXN("Bitcoin", "Pesos mexicanos"),
    ETH_MXN("Ethereum", "Pesos mexicanos"),
    XRP_MXN("Ripple", "Pesos mexicanos"),
    XRP_BTC("Ripple", "Bitcoin"),
    ETH_BTC("Ethereum", "Bitcoin"),
    BCH_BTC("Bitcoin Cash", "Bitcoin"),
    LTC_BTC("Litecoin", "Bitcoin"),
    LTC_MXN("Litecoin", "Pesos mexicanos");

    private final String from;
    private final String to;

    BITSO_CURRENCY(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
    
}
