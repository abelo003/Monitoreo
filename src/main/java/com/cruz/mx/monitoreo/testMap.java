/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cruz.mx.monitoreo;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author acruzb
 */
public class testMap {
    static ObjectMapper oMapper = new ObjectMapper();
    public static void main(String[] args) throws IOException {
        Map<String, Object> data = new HashMap<String, Object>();
    data.put( "name", "Mars" );
    data.put( "age", 32 );
    data.put( "city", "NY" );
    JsonNode jsonNode = oMapper.convertValue(data, JsonNode.class);
//    String res = oMapper.writeValueAsString(data);
    System.out.printf( "JSON: %s", jsonNode );
//    String maps= "{\n" +
//" 	TablaAmortizacion = {\n" +
//" 		titles = No.de Pago,\n" +
//" 		Fecha de Pago,\n" +
//" 		Saldo Inicial,\n" +
//" 		Pago a Saldo Insoluto,\n" +
//" 		Intereses Ordinarios,\n" +
//" 		IVA de los Intereses,\n" +
//" 		Seguro de Vida,\n" +
//" 		Seguro de Danos,\n" +
//" 		Saldo Final,\n" +
//" 		Pago Total,\n" +
//" 		rows = [{\n" +
//" 			No.de Pago = 1,\n" +
//" 			Fecha de Pago = 14 / 09 / 2017,\n" +
//" 			Saldo Inicial = $0 .00,\n" +
//" 			Pago a Saldo Insoluto = $0 .00,\n" +
//" 			Intereses Ordinarios = $0 .00,\n" +
//" 			IVA de los Intereses = $0 .00,\n" +
//" 			Seguro de Vida = $0 .00,\n" +
//" 			Seguro de Danos = $0 .00,\n" +
//" 			Saldo Final = $0 .00,\n" +
//" 			Pago Total = $0 .00\n" +
//" 		}, {\n" +
//" 			No.de Pago = 2,\n" +
//" 			Fecha de Pago = 15 / 09 / 2017,\n" +
//" 			Saldo Inicial = $0 .00,\n" +
//" 			Pago a Saldo Insoluto = $0 .00,\n" +
//" 			Intereses Ordinarios = $0 .00,\n" +
//" 			IVA de los Intereses = $0 .00,\n" +
//" 			Seguro de Vida = $0 .00,\n" +
//" 			Seguro de Danos = $0 .00,\n" +
//" 			Saldo Final = $0 .00,\n" +
//" 			Pago Total = $0 .00\n" +
//" 		}, {\n" +
//" 			No.de Pago = 3,\n" +
//" 			Fecha de Pago = 16 / 09 / 2017,\n" +
//" 			Saldo Inicial = $0 .00,\n" +
//" 			Pago a Saldo Insoluto = $0 .00,\n" +
//" 			Intereses Ordinarios = $0 .00,\n" +
//" 			IVA de los Intereses = $0 .00,\n" +
//" 			Seguro de Vida = $0 .00,\n" +
//" 			Seguro de Danos = $0 .00,\n" +
//" 			Saldo Final = $0 .00,\n" +
//" 			Pago Total = $0 .00\n" +
//" 		}, {\n" +
//" 			No.de Pago = 4,\n" +
//" 			Fecha de Pago = 17 / 09 / 2017,\n" +
//" 			Saldo Inicial = $0 .00,\n" +
//" 			Pago a Saldo Insoluto = $0 .00,\n" +
//" 			Intereses Ordinarios = $0 .00,\n" +
//" 			IVA de los Intereses = $0 .00,\n" +
//" 			Seguro de Vida = $0 .00,\n" +
//" 			Seguro de Danos = $0 .00,\n" +
//" 			Saldo Final = $0 .00,\n" +
//" 			Pago Total = $0 .00\n" +
//" 		}, {\n" +
//" 			No.de Pago = 5,\n" +
//" 			Fecha de Pago = 18 / 09 / 2017,\n" +
//" 			Saldo Inicial = $0 .00,\n" +
//" 			Pago a Saldo Insoluto = $0 .00,\n" +
//" 			Intereses Ordinarios = $0 .00,\n" +
//" 			IVA de los Intereses = $0 .00,\n" +
//" 			Seguro de Vida = $0 .00,\n" +
//" 			Seguro de Danos = $0 .00,\n" +
//" 			Saldo Final = $0 .00,\n" +
//" 			Pago Total = $0 .00\n" +
//" 		}],\n" +
//" 		total = $0 .00\n" +
//" 	}\n" +
//" }";
//     Map<String, Object> map = oMapper.convertValue(maps, Map.class);
//        System.out.println("OKOKOKO");
//        System.out.println(map);
    }
    
}
