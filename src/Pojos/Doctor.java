/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

/**
 *
 * @author carmen
 */
public class Doctor {

    private int ID;
    String fullName;
    String username;
    String email;

    private byte[] password;

    public Doctor(String username, String fullname, byte[] password) {
        super();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
    }

    public Doctor(String username, String fullname) {
        super();
        this.fullName = fullName;
        this.username = username;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
