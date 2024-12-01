package com.dustopia.karaoke.repository.projection;

public interface GetAllAttendantStats {

    int getId();

    String getUsername();

    String getPassword();

    String getName();

    float getSalary();

    boolean getAvailable();

    int getTotalServingTime();

    String getNote();

}
