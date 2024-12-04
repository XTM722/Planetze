package com.example.planetze.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User implements Serializable {
    public String email;
    public String name;
    public String userID;
    public double score;

    public List<String> activityLog;

    public Map<String, String> answers;
    public User() {
        this.answers = new HashMap<>();
        this.activityLog = new ArrayList<>();
    }
    public User(String userID, String email, String name,double score) {
        this.userID = userID;
        this.email = email;
        this.name = name;
        this.answers = new HashMap<>();
        this.score = 0.0;
        this.activityLog = new ArrayList<>();
    }

}
