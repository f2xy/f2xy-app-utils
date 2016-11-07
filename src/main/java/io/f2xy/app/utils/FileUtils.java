package io.f2xy.app.utils;

import java.io.*;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Date: 15/07/2015
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class FileUtils {

    public static byte[] createChecksum(File filename) throws IOException, NoSuchAlgorithmException {
        InputStream fis = new FileInputStream(filename);

        byte[] buffer = new byte[1024];
        MessageDigest complete = MessageDigest.getInstance("MD5");
        int numRead;

        do {
            numRead = fis.read(buffer);
            if (numRead > 0) {
                complete.update(buffer, 0, numRead);
            }
        } while (numRead != -1);

        fis.close();
        return complete.digest();
    }

    // see this How-to for a faster way to convert
    // a byte array to a HEX string
    public static String getMD5Checksum(File filename) throws IOException, NoSuchAlgorithmException {
        byte[] b = createChecksum(filename);
        String result = "";

        for (int i=0; i < b.length; i++) {
            result += Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    public static byte[] readFile(File file) throws IOException {
        InputStream fis = new FileInputStream(file);

        byte[] buffer = new byte[(int) file.length()];

        fis.read(buffer);
        fis.close();

        return buffer;
    }

    public static String readTextFile(File file) throws IOException {

        BufferedReader in = null;

        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String str;
            StringBuilder s = new StringBuilder();

            while ((str = in.readLine()) != null) {
                s.append(str).append("\n");
            }

            return s.toString();

        } finally {
            if(in != null)
                in.close();
        }
    }

    public static void saveTextToFile(File file,String text) throws IOException {
        FileOutputStream out = null;

        try{
            out = new FileOutputStream(file);
            out.write(text.getBytes());
        }finally {
            if(out != null){
                out.close();
            }
        }
    }

    public static void addToZipFile(File file, ZipOutputStream zos) throws IOException {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(file.getName());
            zos.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
        }finally {
            zos.closeEntry();
            if(fis != null)
                fis.close();
        }
    }

    public static String getFileExtension(String fileName){
        int last = fileName.lastIndexOf(".");

        if(last > -1)
            return fileName.substring(last+1);
        return "";
    }

    public static String getFileNameWithoutExtension(String fileName){
        int last = fileName.lastIndexOf(".");

        if(last > -1)
            return fileName.substring(0,last);

        return fileName;
    }

    /**
     * Tek bir dosyayı kopyalar
     * @param source Kaynak Dosya Adı
     * @param target Hedef Dosya Adı Hedef Klasör Yoksa Otomatik Oluşturulur
     * @throws IOException
     */
    public static void copy(File source, File target) throws IOException
    {
        if(!target.getParentFile().exists())
            target.getParentFile().mkdirs();

        if(!target.exists())
            target.createNewFile();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fcSource = null;
        FileChannel fcTarget = null;

        try{
            fis = new FileInputStream(source);
            fcSource = fis.getChannel();

            fos = new FileOutputStream(target);
            fcTarget = fos.getChannel();

            fcTarget.transferFrom(fcSource, 0, fcSource.size());
        }
        finally
        {
            if(fcSource != null)
                fcSource.close();

            if(fcTarget != null)
                fcTarget.close();

            if(fos != null) {
                fos.flush();
                fos.close();
            }

            if(fis != null)
                fis.close();

        }
    }

    public static void move(File source, File targetFolder) throws IOException {
        copy(source,new File(targetFolder,source.getName()));

        if(!source.delete())
            throw new IOException("File Not Delete. File Name is " + source.toString());
    }
}
