package io.f2xy.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Date: 30.09.2013
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class DateUtils {

    public static Date findWorkingDay(Date date,int dayRoll){
        Calendar calendar = Calendar.getInstance();
        int day;

        calendar.setTime(date);

        if(dayRoll < 0){
            day = (dayRoll / 5) * 7 + (dayRoll % 5);
            calendar.add(Calendar.DAY_OF_YEAR,day);

            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                calendar.add(Calendar.DAY_OF_YEAR,-1);
            else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                calendar.add(Calendar.DAY_OF_YEAR,-2);

        }else{
            day = (dayRoll / 5) * 7 - (dayRoll % 5);
            calendar.add(Calendar.DAY_OF_YEAR,day);

            if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                calendar.add(Calendar.DAY_OF_YEAR,2);
            else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
                calendar.add(Calendar.DAY_OF_YEAR,1);

        }

        return calendar.getTime();
    }

    /**
     * 1W , 1D , 1M , 3M , 1Y gibi girilen değerlerin gün olarak karşılığını verir
     * @param s
     * @return
     */
    public static double timeStringToDay(String s) throws IllegalArgumentException{

        char c = s.charAt(s.length()-1);
        double carp;

        switch (c){
            case 'D':
                carp = 1.0;
                break;
            case 'W':
                carp = 7.0;
                break;
            case 'M':
                carp = 30.0;
                break;
            case 'Y':
                carp = 365.0;
                break;
            default:
                throw new IllegalArgumentException(s + " is wrong date string");
        }

        return Double.parseDouble(s.substring(0,s.length()-1)) * carp;
    }

    public static Date parse(String d){

        try {
            SimpleDateFormat sdf = new SimpleDateFormat((d.length() == 8)?"yyyyMMdd":"dd.MM.yyyy");

            return sdf.parse(d);

        }catch (ParseException e){
            e.printStackTrace();
            return new Date();
        }
    }

    public static String format(String date,String source,String target){
        SimpleDateFormat sdf = new SimpleDateFormat(source);
        SimpleDateFormat sdfOut = new SimpleDateFormat(target);

        try {
            return sdfOut.format((sdf.parse(date)));
        } catch (ParseException e) {
            return date;
        }
    }

    public static String format(Date date,String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    //Engin Hasçelik - 26.08.16
    public static long dateDiff(String st1, String st2){

        SimpleDateFormat myFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date date1 = myFormat.parse(st1);
            Date date2 = myFormat.parse(st2);
            long diff = date2.getTime() - date1.getTime();
            if (diff < 0 )
                diff = -1 * diff;

            return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);//vadeye kalan gün sayısı

        } catch (ParseException e) {
            return -1;
        }
    }

}
