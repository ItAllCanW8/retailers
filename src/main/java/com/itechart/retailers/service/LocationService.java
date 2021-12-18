package com.itechart.retailers.service;

public interface LocationService {
    boolean canAcceptApplication(Long applicationId);
    Integer getCurrentAvailableCapacity();
    void acceptApplication(Long applicationId);
}
