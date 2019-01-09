/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zapateria.firebase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author JHON
 */
public class Firebase {
    
    public void enviarMensaje() throws IOException{
         final String apiKey = "AAAAAijR8YI:APA91bHvoVG1pSlOp-OkF40e0nXv3jLBew92qRvrWSiPNwHUolbDkAgFEq1846t0qEgK-0V6vWYS6_GkAmwv_SaGmpB5_hKpSGDKTBUdWDUvhr1pJhDaURdZ24KA4l-7UFmgzWRoE1oj";
        URL url = new URL("https://fcm.googleapis.com/fcm/send");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "key=" + apiKey);

        conn.setDoOutput(true);

        String input = "{\"notification\" : {\"title\" : \"EMEL CABRON\"}, "
                + "\"to\":\"fxvgBN28EHY:APA91bHTn31VOzVUWt6ypagU6gcfoq--yV0L13wTTmxN3on2MYlUNOHgE3iEBLYhczWXo9o1E-AJwb09IkDVdbwq8DbbGVRtcFxRDpfKVAKYhM-44GjzoMoVFM08nRnKOAoxiDHwCn2w\"}";

        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();
        os.close();

        int responseCode = conn.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + input);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

// print result 
        System.out.println(response.toString());

        
    }

    public Firebase() {
    }
    
    public static void main(String[] args) throws IOException {
        Firebase f= new Firebase();
        f.enviarMensaje();
    }
    
}
