package io.f2xy.app.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Date: 16.05.2013
 *
 * @author Hakan GÜR <hakangur82@gmail.com>
 * @version 1.0
 */
public class ExceptionUtils {

    native int getStackTraceDepth();

    native StackTraceElement getStackTraceElement(int index);

    public static String getDetailedMessage(Throwable t) {
        StringWriter m = new StringWriter();
        PrintWriter o = new PrintWriter(m, true);

        if(t.getMessage() != null)
            o.println(t.getMessage());

        t.printStackTrace(o);

        if (t.getCause() != null)
            o.println(getDetailedMessage(t.getCause()));

        return m.toString();
    }

}
