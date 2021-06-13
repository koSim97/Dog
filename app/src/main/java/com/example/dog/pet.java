package com.example.dog;

public class pet {
    String pName;
    String pAge;
    String pSpecies;
    String pBloodType;
    String pSex;
    String pKind;

    public pet(){}

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpAge() {
        return pAge;
    }

    public void setpAge(String pAge) {
        this.pAge = pAge;
    }

    public String getpSpecies() {
        return pSpecies;
    }

    public void setpSpecies(String pSpecies) {
        this.pSpecies = pSpecies;
    }

    public String getpBloodType() {
        return pBloodType;
    }

    public void setpBloodType(String pBloodType) {
        this.pBloodType = pBloodType;
    }

    public String getpSex() {
        return pSex;
    }

    public void setpSex(String pSex) {
        this.pSex = pSex;
    }

    public String getpKind() {
        return pKind;
    }

    public void setpKind(String pKind) {
        this.pKind = pKind;
    }

    public pet(String pName, String pAge, String pSex, String pSpecies, String pBloodType, String pKind){
        this.pName = pName;
        this.pAge = pAge;
        this.pSex = pSex;
        this.pSpecies = pSpecies;
        this.pBloodType = pBloodType;
        this.pKind = pKind;
    }
}
