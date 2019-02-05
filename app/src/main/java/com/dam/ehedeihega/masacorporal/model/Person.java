package com.dam.ehedeihega.masacorporal.model;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Person {
    private String fistName;
    private String lastName;
    private int age;
    private double height;
    private double weight;
    private String sex;

    //Constructor que llama el Parcel
    public Person(String fistName, String lastName, int age, double height, double weight, String sex) {
        this.fistName = fistName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
    }

    //Constructor para la MainActivity
    public Person(int age, double height, double weight, String sex) {
        this.fistName = "";
        this.lastName = "";
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sex = sex;
    }


    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getBMI() {
        double imc = getWeight() / (Math.pow(getHeight(), 2));
        return imc;
    }

    public abstract double getIdealWeight();

}
