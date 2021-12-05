/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import Pojos.Patient;
import com.mysql.cj.Query;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author drijc
 */
public class JPA {
    /*public Patient checkPassword(String email, String password) {
        try {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] hash = md.digest();
        Query q = em.createNativeQuery("SELECT * FROM Patient WHERE username = ? AND pwd = ?", Patient.class);
        q.setParameter(1, email);
        q.setParameter(2, hash);
        return (Patient) q.getSingleResult();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoResultException e) {
            return null;
        }
        return null;
}*/
}
