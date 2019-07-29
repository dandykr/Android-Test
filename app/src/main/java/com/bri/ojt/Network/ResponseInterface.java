package com.bri.ojt.Network;

public interface ResponseInterface {
    void OnBadRequest(String message);
    void OnUnauthorized(String message);
}
