package com.example.ToDoList;

public class ToDoModel {
    String time;
    String task;
    String date;
    String location;

    public ToDoModel()
    {

    }

    public ToDoModel(String time, String task, String date, String location) {
        this.time = time;
        this.task = task;
        this.date = date;
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String gettask() {
        return task;
    }

    public void settask(String task) {
        this.task = task;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getlocation() {
        String returnValue = String.valueOf(location);
        return returnValue;
    }

    public void setlocation(String location) {
        this.location = location;
    }
}