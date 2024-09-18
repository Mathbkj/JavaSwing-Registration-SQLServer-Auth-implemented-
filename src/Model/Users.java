package Model;

import java.text.DecimalFormat;

public class Users{
    private String Email,Name,SurName;
    private float PhoneNumber;
    public Users(String Email, String Name, String SurName, float PhoneNumber){
        this.Email = Email;
        this.Name=Name;
        this.SurName=SurName;
        this.PhoneNumber=PhoneNumber;
    }
    public Users(){

    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public float getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(float phoneNumber) {
        PhoneNumber = phoneNumber;
    }
}
