package com.startup.oda.utils;

import com.startup.oda.dto.request.RegisterRequest;
import com.startup.oda.exception.exceptionsList.InvalidInputException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static boolean isValidPhone(String phone) {
        String regex = "^\\d{1,13}$";
        if (phone == null || phone.isEmpty()){
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phone);
        if (!matcher.matches()) {
            throw new InvalidInputException();
        }

        return true;
    }
    public static boolean isValidEmail(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (email == null || email.isEmpty()) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new InvalidInputException();
        }

        return true;
    }

    public static boolean isValidFirstName(String firstName) {
        String regex = "^[A-Za-z][a-zA-Z]*$";

        Pattern pattern = Pattern.compile(regex);

        if (firstName == null || firstName.isEmpty()) {
            return false;
        }

        Matcher firstNameMatcher = pattern.matcher(firstName);

        if (!firstNameMatcher.matches()) {
            throw new InvalidInputException();
        }

        return true;
    }

    public static boolean isValidLastName(String lastName) {
        String regex = "^[A-Za-z][a-zA-Z]*$";

        Pattern pattern = Pattern.compile(regex);

        if (lastName == null || lastName.isEmpty()) {
            return false;
        }

        Matcher lastNameMatcher = pattern.matcher(lastName);

        if (!lastNameMatcher.matches()) {
            throw new InvalidInputException();
        }

        return true;
    }
    public static boolean isValidPrice(Integer price){
        if (price == null){
            return true;
        }
        String regex = "^50$|^[5-9]\\d$|^[1-9]\\d{2,6}$";

        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(price.toString());

        if(!matcher.matches()){
            throw new InvalidInputException();
        }
        return false;
    }

    public static boolean isValidRoomsBeds(Integer amount){
        if (amount == null){
            return true;
        }
        return amount > 50;
    }
    public static boolean isValidPassword(String password){
        return password.length() >= 8 && password.length() <= 24;
    }
    public static boolean isValidInfo(String info){
        if(info == null){
            return false;
        }
        return info.length() <= 400;
    }

    public static boolean validateRegisterRequest(RegisterRequest request){
        if (!Validation.isValidEmail(request.getEmail())){
            return false;
        }
        if (!Validation.isValidPassword(request.getPassword())){
            return false;
        }
        if (!Validation.isValidFirstName(request.getFirstName())){
            return false;
        }
        return Validation.isValidLastName(request.getLastName());
    }

//    public static boolean validateAddRequest(AddRequest request){
//        if (isValidPrice(request.getMinPrice())){
//            return false;
//        }
//        if (isValidPrice(request.getMaxPrice())){
//            return false;
//        }
//        if (isValidRoomsBeds(request.getRooms())){
//            return false;
//        }
//        if (isValidRoomsBeds(request.getBeds())){
//            return false;
//        }
//        if (!isValidNationality(request.getDistrict())){
//            return false;
//        }
//        if (request.getMinPrice() > request.getMaxPrice()){
//            return false;
//        }
//        return isValidNationality(request.getCity());
//    }
//    public static boolean validateAddProperty(AddPropertyRequest request){
//        if (isValidPrice(request.getPrice())){
//            return false;
//        }
//        if (isValidRoomsBeds(request.getRooms())){
//            return false;
//        }
//        if (isValidRoomsBeds(request.getBeds())){
//            return false;
//        }
//        if (!isValidNationality(request.getDistrict())){
//            return false;
//        }
//        if (!isValidInfo(request.getInfo())){
//            return false;
//        }
//        if (request.getArea() < 0){
//            return false;
//        }
//        return isValidNationality(request.getCity());
//    }
}
