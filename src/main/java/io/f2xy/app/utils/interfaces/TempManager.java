package io.f2xy.app.utils.interfaces;

import java.io.File;
import java.io.IOException;

/**
 * Date: 08.11.2013
 *
 * @author Hakan GÜR
 * @version 1.0
 */
public interface TempManager {

    public File getTempDir() throws IOException;

    public void setTempDir(File dir) throws IOException;

    public File createTempFile() throws IOException;

    public File createTempFileWithExtension(String extension) throws IOException;

    public File createTempFile(String fileName) throws IOException;

    public File createTempFile(String fileName, String extension) throws IOException;

    public File createTempDirectory() throws IOException;

}
