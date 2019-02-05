package com.dam.ehedeihega.masacorporal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Man extends Person implements Parcelable {
    public static final Creator<Man> CREATOR = new Creator<Man>() {
        @Override
        public Man createFromParcel(Parcel in) {
            return new Man(in);
        }

        @Override
        public Man[] newArray(int size) {
            return new Man[size];
        }
    };

    public Man(String fistName, String lastName, int age, double height, double weight, String sex) {
        super(fistName, lastName, age, height, weight, sex);
    }

    public Man(int age, double height, double weight, String sex) {
        super(age, height, weight, sex);
    }

    //Constructor para el Parcel
    protected Man(Parcel in) {
        this(in.readString(), in.readString(), Integer.parseInt(in.readString()),
                Double.parseDouble(in.readString()), Double.parseDouble(in.readString()), in.readString());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.getFistName());
        dest.writeString(this.getLastName());
        dest.writeString(String.valueOf(this.getAge()));
        dest.writeString(String.valueOf(this.getHeight()));
        dest.writeString(String.valueOf(this.getWeight()));
        dest.writeString(this.getSex());
    }

    //Método implementado de Person
    @Override
    public double getIdealWeight() {
        double height = getHeight() * 100;
        return height - 100 - ((height - 150)/4);
    }

}
