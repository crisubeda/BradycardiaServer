/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Pojos;

import BITalino.BITalino;

/**
 *
 * @author carmen
 */
public class Patient {

    private int ID;
    String fullName;
    String username;
    String address;
    String phonenumber;
    String email;
    String diagnosis;
    Integer docId;
    private byte[] password;
    private String macBitalino;
    private BITalino bitalino; //no guardar en base de datos

    public Patient(int id, String fullname, String username, String adress, String phonenumber, String email, String diagnosis, int docId, String mac) {
        super();
        this.ID = id;
        this.fullName = fullname;
        this.username = username;
        this.address = adress;
        this.phonenumber = phonenumber;
        this.email = email;
        this.diagnosis = diagnosis;
        this.docId = docId;
        this.macBitalino = mac;
        this.bitalino = new BITalino();
    }

    public Patient(String username, String fullname, byte[] password) {
        super();
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.macBitalino = null;
        this.bitalino = new BITalino();
    }

    public Patient() {
        super();
        this.fullName = "";
        this.username = "";
        this.macBitalino = null;
    }

    public Patient(String username, String fullname) {
        super();
        this.fullName = fullName;
        this.username = username;
        this.macBitalino = null;
        this.bitalino = new BITalino();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public Integer getDocId() {
        return docId;
    }

    public void setDocId(Integer docId) {
        this.docId = docId;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public int getID() {
        return ID;
    }

    public void setMacBitalino(String macBitalino) {
        this.macBitalino = macBitalino;
    }

    public String getMacBitalino() {
        return macBitalino;
    }

    public BITalino getBitalino() {
        return bitalino;
    }

    public void setNewBitalino() {
        this.bitalino = new BITalino();
    }

    @Override
    public String toString() {
        return "p#" + ID + ";" + fullName + ";" + username + ";" + address + ";" + phonenumber + ";" + email + ";" + diagnosis + ";" + docId + ";" + password + ";" + macBitalino + ";" + bitalino + ";#";
    }

}
