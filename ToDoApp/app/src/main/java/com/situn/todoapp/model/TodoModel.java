package com.situn.todoapp.model;

public class TodoModel {
    private String title;
    private String desc;
    private String checktodo;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getChecktodo() {
        return checktodo;
    }

    public void setChecktodo(String checktodo) {
        this.checktodo = checktodo;
    }

    public TodoModel(String title, String desc, String checktodo) {
        this.title = title;
        this.desc = desc;
        this.checktodo = checktodo;
    }
}
