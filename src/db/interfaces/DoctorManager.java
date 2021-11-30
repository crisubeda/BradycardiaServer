/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import Pojos.Doctor;

/**
 *
 * @author Cristina
 */
public interface DoctorManager {
    public void createDoctor(Doctor doc);
    public void deleteDoctor(Integer id);
    public void modifyDoctor(Doctor doc);
    public Integer searchByFullName(String fullname);
}
