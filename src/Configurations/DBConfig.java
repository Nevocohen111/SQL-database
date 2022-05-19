package Configurations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConfig {
    private static final Properties properties;
    static {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src\\resources\\config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
