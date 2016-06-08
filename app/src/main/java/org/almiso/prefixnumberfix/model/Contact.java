package org.almiso.prefixnumberfix.model;

import java.util.ArrayList;

/**
 * Created by Alexandr on 08.06.16.
 */
public class Contact {
    public int id;
    public String first_name;
    public String last_name;
    public ArrayList<String> phones = new ArrayList<>();
    public ArrayList<String> phoneTypes = new ArrayList<>();
    public ArrayList<String> shortPhones = new ArrayList<>();
    public ArrayList<Integer> phoneDeleted = new ArrayList<>();


    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", phones=" + getStringValueOfStringArray(phones) +
                ", phoneTypes=" + getStringValueOfStringArray(phoneTypes) +
                ", shortPhones=" + getStringValueOfStringArray(shortPhones) +
                ", phoneDeleted=" + getStringValueOfIntegerArray(phoneDeleted) +
                '}';
    }

    private String getStringValueOfStringArray(ArrayList<String> array){
        String result = "";
        for(String item : array){
            result += item;
            result += ", ";
        }
        return result;
    }
    private String getStringValueOfIntegerArray(ArrayList<Integer> array){
        String result = "";
        for(int item : array){
            result += String.valueOf(item);
            result += ", ";
        }
        return result;
    }
}
