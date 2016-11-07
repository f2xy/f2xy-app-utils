package io.f2xy.app.utils;

import io.f2xy.app.utils.annotations.Description;

import java.util.Locale;

/**
 * Date: 22/10/2015
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class Localization {

    public static String getClassDescription(Class c,Locale locale){

        if(c.isAnnotationPresent(Description.class)){
            Description description = (Description) c.getAnnotation(Description.class);

            if(locale.getLanguage().equalsIgnoreCase("en"))
                return description.en();
            else if(locale.getLanguage().equalsIgnoreCase("tr"))
                return description.tr();
        }

        return "!Require Translate " + c.getSimpleName();
    }

}
