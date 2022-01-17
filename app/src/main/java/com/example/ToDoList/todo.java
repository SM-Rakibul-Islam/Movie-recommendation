package com.example.ToDoList;

public class todo {

    String task;
    String date;
    String time;
    String location;

    public todo(String task, String date, String time, String location) {
        this.task = task;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public String gettask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    public String getlocation(){
        return location;
    }
}
