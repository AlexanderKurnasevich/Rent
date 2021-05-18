package me.ride.service;

import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public interface UtilService {

    Date parseStringToDate(String date1);
}
