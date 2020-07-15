package com.kizilkaya.kocak.logcatcher.utils.models;

public class JWTJsonModel {

    public String Username;
    public String Token;
    public String IdOfDevice;

    public JWTJsonModel(String username, String token, String id){
        this.Username=username;
        this.Token=token;
        this.IdOfDevice=id;
    }

}
