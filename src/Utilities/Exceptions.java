/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Patient;
import db.interfaces.PatientManager;

/**
 *
 * @author carmen
 */
public class Exceptions {

    Patient patient = new Patient();
    // PatientManager patientManager;

    public static boolean checkUsername(boolean patient, String username, PatientManager patientManager) {
        boolean free = false;
        if (patient) {
            Patient a = patientManager.getPatientByUsername(username);
            if (a == null) {
                free = true;
            }
        } else { // si no es patient es doctor
            // Doctor
        }
        return free;
    }

    public static boolean checkEmail(String email) {
        boolean isEmail = true;
        int index = email.indexOf("@");
        if (index != -1 && index != 0 && index != email.length()) {
            isEmail = false;
        }
        return true;
    }

    public static boolean checkInt(String integer) {
        boolean isInt = true;
        try {
            int a = Integer.parseInt(integer);
        } catch (NumberFormatException ex) {
            isInt = false;
        }
        return isInt;
    }

    public static int convertInt(String integer) {
        int isInt = 0;
        try {
            isInt = Integer.parseInt(integer);
        } catch (NumberFormatException ex) {
        }
        return isInt;
    }

    public static boolean checkString(String integer) {
        boolean isString = false;
        try {
            float a = Float.parseFloat(integer);
        } catch (NumberFormatException ex) {
            isString = true;
        }
        return isString;
    }

    public static boolean checkFloat(String integer) {
        boolean isFloat = true;
        try {
            float a = Float.parseFloat(integer);
        } catch (NumberFormatException ex) {
            isFloat = false;
        }
        return isFloat;
    }

    public static boolean checkPhone(String phone) {
        boolean isPhone = true;
        if (phone.length() != 9) {
            isPhone = false;
        } else if (!checkInt(phone)) {
            isPhone = false;
        } else {
            isPhone = true;
        }
        return isPhone;
    }
}
