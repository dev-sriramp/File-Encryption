package com.anonymous.encryption;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
/**
 *
 * @author AnOnYmOuS
 */



public class Encryption {
public static void main(String[] args) throws Exception {

    FileOutputStream outFile;
    try (FileInputStream inFile = new FileInputStream("/home/anonymous/Desktop/Test.txt")) {
        outFile = new FileOutputStream("/home/anonymous/Desktop/plainfile.desktop");
        String password = "javapapers";
        PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory
                .getInstance("PBEWithMD5AndTripleDES");
        SecretKey secretKey = secretKeyFactory.generateSecret(pbeKeySpec);
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance("PBEWithMD5AndTripleDES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, pbeParameterSpec);
        outFile.write(salt);
        byte[] input = new byte[64];
        int bytesRead;
        while ((bytesRead = inFile.read(input)) != -1) {
            byte[] output = cipher.update(input, 0, bytesRead);
            if (output != null)
                outFile.write(output);
        }   byte[] output = cipher.doFinal();
        if (output != null)
            outFile.write(output);
    }
    outFile.flush();
    outFile.close();
  }
}