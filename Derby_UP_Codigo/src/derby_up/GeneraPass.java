/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package derby_up;

/**
 *
 * @author lelguea
 */
public class GeneraPass {
    
    
     
    public static String md5(String dato) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(dato.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            System.err.println(e.toString());
        }
    return null;
    }
    
    public static void main(String[] asdadads) {
        System.out.println(md5("lelguea"));
    }
}
    
