package com.microservice.house.service;

public interface MailService {
    //---------------------------------------------------------------------------------------------
    public void sendMail(String title,String url,String email);
    //---------------------------------------------------------------------------------------------
    public void registerNotify(String email);
    //---------------------------------------------------------------------------------------------
    public boolean enable(String key);
}
