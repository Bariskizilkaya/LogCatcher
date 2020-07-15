package com.kizilkaya.kocak.logcatcher.controller;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kizilkaya.kocak.logcatcher.utils.JWTUtil;
import com.kizilkaya.kocak.logcatcher.utils.models.JWTJsonModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    JWTUtil jwtUtil;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String service() {
        return "{\"text\":\"HelloWorld\"}";
    }

    @RequestMapping(value = "/asd", method = RequestMethod.POST)
    public String nodeMcu(@RequestBody String data) {
        System.out.println("Data is : " + data);
        //sendData("GeldiGittiCnm", data,"json");
        return "Cevap";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerMcu(@RequestBody String id) {
        System.out.println("Registered hardware id : " + id);
        return "Cevap";
    }

    @RequestMapping(value="/generateJWT", method= RequestMethod.GET)
    public String generate(){
        String response ="Error:123";
         try {
             response= createToken();
        } catch (Exception e){}

        return response;
    }

    private String createToken() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JWTJsonModel jwtJsonModel = new JWTJsonModel(jwtUtil.generateJWT(),"HelloWorld","Cabbar");

        return objectMapper.writeValueAsString(jwtJsonModel);
    }

    public void sendData(String data, String ip, String endpoint) {

        String urlParameters = "param1=a&param2=b&param3=c";
        byte[] postData = urlParameters.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        String request = "http://" + ip + ":80/json";
        try {
            URL url = new URL(request);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(postData);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
