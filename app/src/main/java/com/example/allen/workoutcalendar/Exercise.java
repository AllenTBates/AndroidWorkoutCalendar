package com.example.allen.workoutcalendar;

import java.util.Date;

/**
 * Created by Allen on 2/15/2018.
 */

public class Exercise {

    private Date date;
    private String content;
    private String type;

    public Exercise(){

    };

    public Exercise(Date date){
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
