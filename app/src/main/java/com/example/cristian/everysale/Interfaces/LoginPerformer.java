package com.example.cristian.everysale.Interfaces;

/**
 * Created by Giorgiboy on 07/08/2016.
 */
public interface LoginPerformer {

    public void OnLoginSuccess(String username, String password, long id);

    public void OnConnectionAbsent();

    public void OnLoginFailed();
}
