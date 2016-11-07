package io.f2xy.app.utils;

import io.f2xy.app.utils.interfaces.Settings;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Properties dosyalarını Settings interface i ile kullanabilmek için implementation
 * Date: 18.09.2013
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class Properties implements Settings {

    private java.util.Properties prop;
    private File file;

    public Properties(String file) throws IOException {
        this(new File(file));
    }

    public Properties(File file) throws IOException {
        prop = new java.util.Properties();
        this.file = file;
        FileReader fr = null;
        try {
            fr = new FileReader(file);
            prop.load(fr);
        } finally {
            if(fr != null)
                try {
                    fr.close();
                } catch (IOException e) {
                    //
                }
        }
    }

    public File getFile() {
        return file;
    }

    @Override
    public void save(String key, String value) {
        prop.setProperty(key,value);
        updatePropertyFile(null);
    }

    @Override
    public void saveAll(Map<String, String> values) {
        prop.putAll(values);
        updatePropertyFile(null);
    }

    @Override
    public String load(String key, String defaultValue) {
        return prop.getProperty(key,defaultValue);
    }

    @Override
    public List<String> keys() {
        List<String> k = new ArrayList<String>();

        Enumeration<Object> e = prop.keys();
        while (e.hasMoreElements()){
            k.add(e.nextElement().toString());
        }

        return k;
    }

    @Override
    public void delete(String key) {
        prop.remove(key);
        updatePropertyFile(key);
    }

//    private void store() {
//        FileWriter fw = null;
//
//        try{
//            fw = new FileWriter(file);
//            prop.store(fw,null);
//        } catch (IOException e) {
//            logger.error("IO Exception",e);
//        }finally {
//            if(fw != null)
//                try {
//                    fw.close();
//                } catch (IOException e) {
//                    logger.error("File Writer Close Exception",e);
//                }
//        }
//    }

    //properties dosyasınındaki açıklama satırlarını bozmadan kaydetmek için
    public void updatePropertyFile(String deleteKey){
        BufferedReader br = null;
        List<String> outList = new ArrayList<String>();

        java.util.Properties temp = new java.util.Properties();
        temp.putAll(prop);

        try {
            br = new BufferedReader(new FileReader(file));

            String str;
            while ((str = br.readLine()) != null){

                String s = str.trim();

                if(s.contains("=")){
                    String[] p = s.split("=");

                    if(temp.containsKey(p[0])) {
                        outList.add(p[0] + "=" + temp.get(p[0]));
                        temp.remove(p[0]);
                    }else if(deleteKey != null && deleteKey.equals(p[0])){
                        continue;
                    }else {
                        outList.add(str);
                    }

                } else {
                    outList.add(str);
                }
            }

            //yeni eklenen değerleri doyaya ekle
            for(String i : temp.stringPropertyNames()){
                outList.add(i + "=" + temp.getProperty(i));
            }

        } catch (FileNotFoundException e) {
            //logger.error("File Not Found Exception", e);
            return;
        } catch (IOException e) {
            //logger.error("IO Exception", e);
            return;
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    //logger.error("File Reader Close Exception", e);
                }
            }
        }


        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));

            for(String j : outList){
                bw.write(j);
                bw.newLine();
            }


        } catch (IOException e) {
            //logger.error("IO Exception", e);
        } finally {
            if(bw != null){
                try {
                    bw.close();
                } catch (IOException e) {
                    //logger.error("File Writer Close Exception",e);
                }
            }
        }
    }
}
