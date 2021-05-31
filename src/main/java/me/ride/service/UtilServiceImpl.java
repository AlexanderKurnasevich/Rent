package me.ride.service;

import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UtilServiceImpl implements UtilService {

    public Date parseStringToDate(String date1) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate1 = null;
        try {
            parsedDate1 = format.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return parsedDate1;
    }
}
