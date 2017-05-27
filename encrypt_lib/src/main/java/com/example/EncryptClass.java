package com.example;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptClass {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        EncryptClass main = new EncryptClass();

        //加密
		main.encryptImageFiles("/home/xiaoniu/桌面/Emoji8/");

        //解密
//        main.deencryptImageFiles("/home/xiaoniu/桌面/Emoji8/encrypted/");

//        StringBuilder sb = new StringBuilder();
//        for(char ch: HINT) {
//            sb.append(ch);
//        }
//        char c = '_';
//        int b = c;//字符的ascii码值
//        System.out.println("zzz: "+sb +" _ : "+ b);
    }

    private static final char[] HINT = {914, 255,
            924 , 257, 7805, 1077, 1075, 7725, 1010, 1181, 1109,
            924 , 1365, 389, 7725, 7739, 1077
    };

    public void findBigFIle(String dir) {

        File folder = new File(dir);

        File[] files = folder.listFiles();

        for (File file: files) {
            String iconFileName = file.getName();
            if (isAcronym(iconFileName)) {
                System.out.println("zzz fileName: "+iconFileName);
            } else {
                //System.out.println("nomal fileName: "+iconFileName);
            }
        }
    }

    public static boolean isAcronym(String word)
    {
        for(int i = 0; i < word.length(); i++)
        {
            String sN = "0123456789";
            String sL = "abcdefghijklmnopqrstuvwxyz";
            String sU = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            char c = word.charAt(i);
            //System.out.println("zzz aaa c: "+c + " word "+word);
            if(sU.indexOf(c) != -1){
                System.out.println("zzz 1111111111 c: "+c + " word "+word);
                return true;
            }
        }
        return false;
    }

    //encrypt加密
    public void encryptImageFiles(String dir){

        String destPath = dir + "/encrypted/";
        File destFolder = new File(destPath);

        if(!destFolder.exists()){
            destFolder.mkdirs();
        }


        File folder = new File(dir);

        File[] files = folder.listFiles();

        for(int i=0; i<files.length; i++){
            File iconFile = files[i];
            String iconFileName = iconFile.getName();

            if(!iconFileName.endsWith(".png")){
                continue;
            }

            byte[] iconBytes = readFile(iconFile);

            try {
                FileInputStream fis = new FileInputStream(dir + iconFileName);
                FileOutputStream fos = new FileOutputStream(destPath + iconFileName);

                SecretKeySpec sks = new SecretKeySpec("Emoji_JingNCK567".getBytes(), "AES");
                // Create cipher
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.ENCRYPT_MODE, sks);
                // Wrap the output stream
                CipherOutputStream cos = new CipherOutputStream(fos, cipher);
                // Write bytes
                int b;
                byte[] d = new byte[8];
                while((b = fis.read(d)) != -1) {
                    cos.write(d, 0, b);
                }
                // Flush and close streams.
                cos.flush();
                cos.close();
                fis.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }

    private byte[] readFile(File file){

        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ( (read = ios.read(buffer)) != -1 ) {
                ous.write(buffer, 0, read);
            }
        } catch (Exception e){

        } finally {
            try {
                if ( ous != null )
                    ous.close();
            } catch ( IOException e) {
            }

            try {
                if ( ios != null )
                    ios.close();
            } catch ( IOException e) {
            }
        }

        return ous.toByteArray();
    }


    //Decrypted解密
    public void deencryptImageFiles(String dir){

        String destPath = dir + "/outencrypted/";
        File destFolder = new File(destPath);

        if(!destFolder.exists()){
            destFolder.mkdirs();
        }


        File folder = new File(dir);

        File[] files = folder.listFiles();

        for(int i=0; i<files.length; i++){
            File iconFile = files[i];
            String iconFileName = iconFile.getName();

            if(!iconFileName.endsWith(".png")){
                continue;
            }

            byte[] iconBytes = readFile(iconFile);

            try {
                FileInputStream fis = new FileInputStream(dir + iconFileName);
                FileOutputStream fos = new FileOutputStream(destPath + iconFileName);

                SecretKeySpec sks = new SecretKeySpec("Emoji_JingNCK567".getBytes(), "AES");
                // Create cipher
                Cipher cipher = Cipher.getInstance("AES");
                cipher.init(Cipher.DECRYPT_MODE, sks);
                // Wrap the output stream
                CipherOutputStream cos = new CipherOutputStream(fos, cipher);
                // Write bytes
                int b;
                byte[] d = new byte[8];
                while((b = fis.read(d)) != -1) {
                    cos.write(d, 0, b);
                }
                // Flush and close streams.
                cos.flush();
                cos.close();
                fis.close();

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }


        }

    }
}
