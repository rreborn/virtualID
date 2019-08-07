package com.album.kaktus.virtualid.Model;

import java.util.ArrayList;

public class Contact {
    private int id;
    private String name;
    private String picURI;
    private ArrayList<String> noHP;

    public Contact(){
        name = "";
        picURI = "";
        noHP = new ArrayList<>();
    }

    public void setID(int id2){
        id = id2;
    }

    public void setName(String nama){
        name = nama;
    }

    public void setPicURI(String uri){ picURI = uri; }

    public void setNumber(String number){
        noHP.add(number);
    }

    public int getID() { return id;}

    public String getName() {
        return name;
    }

    public String getPicURI() {
        return picURI;
    }

    public ArrayList<String> getNumber() {
        return noHP;
    }
}
