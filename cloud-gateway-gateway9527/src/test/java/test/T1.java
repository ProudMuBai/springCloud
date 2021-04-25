package test;

import java.time.ZonedDateTime;

public class T1 {

    public static void main(String[] args) {
        ZonedDateTime zbi = ZonedDateTime.now();//默认时区
        System.out.println(zbi);
        //2021-04-06T11:19:43.547+08:00[GMT+08:00]
    }

}
