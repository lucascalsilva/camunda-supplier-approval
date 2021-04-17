package br.com.poc.suppliers.model;

import lombok.Builder;
import lombok.Data;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class TimeRecord {

    private Long hours;
    private Long minutes;
    private Long seconds;

    public TimeRecord(Long durationInMillis){
        hours = MILLISECONDS.toHours(durationInMillis);
        minutes = MILLISECONDS.toMinutes(durationInMillis);
        seconds = MILLISECONDS.toSeconds(durationInMillis);

        minutes = minutes - (hours * 60);
        seconds = seconds - (minutes * 60);
    }

    @Override
    public String toString(){
        return addZero(hours) + ":" + addZero(minutes) + ":" + addZero(seconds);
    }

    public String addZero(Long time){
        return time < 10 ? "0" + time : time.toString();
    }

}
