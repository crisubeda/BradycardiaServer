/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db.interfaces;

import Pojos.Patient;
import java.io.File;

/**
 *
 * @author drijc
 */
public interface FilesManager {
        public void insertFile(File file, Patient pat);
}
