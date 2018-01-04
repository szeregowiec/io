package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {



    public static String getHashedPassword(String Password){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Password.getBytes());
            byte[] bytes = md.digest();

            StringBuilder sb = new StringBuilder();
            for(byte myByte : bytes)
            {
                sb.append(Integer.toString((myByte & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
