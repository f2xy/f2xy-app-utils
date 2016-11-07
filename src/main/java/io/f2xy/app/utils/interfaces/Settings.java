package io.f2xy.app.utils.interfaces;

import java.util.List;
import java.util.Map;

/**
 * Date: 16.05.2013
 *
 * @author Hakan GÜR
 * @version 1.0
 */
public interface Settings {

    public void save(String key, String value);

    public void saveAll(Map<String, String> values);

    public String load(String key, String defaultValue);

    //public Setting load(String key, Setting defaultValue);

    public List<String> keys();

    public void delete(String key);

}
