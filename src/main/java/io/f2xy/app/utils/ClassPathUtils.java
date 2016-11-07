package io.f2xy.app.utils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * Date: 23.01.2014
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class ClassPathUtils {

    public static List<String> getApplicationModules() throws IOException {
        List<String> list = new ArrayList<String>();

        List<JarFile> jars = ClassPathUtils.getApplicationLoadedJar();

        for(JarFile jar : jars){
            Manifest manifest = jar.getManifest();
            Attributes attr = manifest.getMainAttributes();
            Attributes.Name moduleName = new Attributes.Name("Module-Name");
            Attributes.Name moduleVersion = new Attributes.Name("Module-Version");

            if(attr.containsKey(moduleName)){
                //System.out.println("Module : " + attr.get(moduleName) + "("+attr.get(new Attributes.Name("Module-Version"))+")");
                list.add(attr.get(moduleName).toString() + "("+ attr.getValue(moduleVersion).toString() +")");
            }
        }

        return list;
    }

    public static List<JarFile> getApplicationLoadedJar() throws IOException {

        List<JarFile> list = new ArrayList<JarFile>();
        List<String> pathList = getApplicationLoadedClassPath();

        for(String i : pathList){
            File f = new File(i);
            if(f.isFile())
                list.add(new JarFile(f));
        }

        return list;
    }

    public static List<String> getApplicationLoadedClassPath() {
        return Arrays.asList(ManagementFactory.getRuntimeMXBean().getClassPath().split(";"));
    }

}
