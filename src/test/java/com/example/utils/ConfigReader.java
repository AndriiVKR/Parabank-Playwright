package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

        private static final Properties props = new Properties();

        static {
            try (InputStream input = ConfigReader.class.getClassLoader().getResourceAsStream("config.properties")) {
                if (input == null) {
                    throw new RuntimeException("Unable to find config.properties");
                }
                props.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load config.properties", e);
            }
        }

        public static String get(String key) {
            return props.getProperty(key);
        }
    }


