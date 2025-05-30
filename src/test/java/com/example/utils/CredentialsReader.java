package com.example.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CredentialsReader {
    private static final String CREDENTIALS_FILE = "credentials.json";
    private static final String REGISTRATION_DATA_FILE = "registration-data.json";
    public record UserCredentials(String username, String password, String expectedResult) {
    }

    public static Map<String, UserCredentials> loadLoginCredentials() throws IOException {
        Map<String, UserCredentials> credentialsMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = CredentialsReader.class.getClassLoader().getResourceAsStream(CREDENTIALS_FILE);
        if (inputStream == null) {
            try {
                throw new IOException("Credentials file not found " + CREDENTIALS_FILE);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        JsonNode rootNode = mapper.readTree(inputStream);
        JsonNode users = rootNode.get("users");
        for (JsonNode user : users) {
            String id = user.get("id").asText();
            String username = user.get("username").asText();
            String password = user.get("password").asText();
            String expectedResult = user.get("expectedResult").asText();
            credentialsMap.put(id, new UserCredentials(username, password, expectedResult));
        }
        return credentialsMap;
    }

    public static Map<String, UserRegistrationData> loadRegistrationData() throws IOException {
        Map<String, UserRegistrationData> dataMap = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = CredentialsReader.class.getClassLoader().getResourceAsStream(REGISTRATION_DATA_FILE);
        if (inputStream == null) {
            throw new IOException("Registration data file not found: " + REGISTRATION_DATA_FILE);
        }
        JsonNode rootNode = mapper.readTree(inputStream);
        JsonNode users = rootNode.get("users");
        for (JsonNode user : users) {
            String id = user.get("id").asText();
            String firstName = user.get("firstName").asText();
            String lastName = user.get("lastName").asText();
            String address = user.get("address").asText();
            String city = user.get("city").asText();
            String state = user.get("state").asText();
            String zipCode = user.get("zipCode").asText();
            String phone = user.get("phone").asText();
            String ssn = user.get("ssn").asText();
            String password = user.get("password").asText();
            dataMap.put(id, new UserRegistrationData(firstName, lastName, address, city, state, zipCode, phone, ssn, password));
        }
        return dataMap;
    }

    public static class UserRegistrationData {
        private final String firstName;
        private final String lastName;
        private final String address;
        private final String city;
        private final String state;
        private final String zipCode;
        private final String phone;
        private final String ssn;
        private final String password;

        public UserRegistrationData(String firstName, String lastName, String address, String city, String state, String zipCode, String phone, String ssn, String password) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.address = address;
            this.city = city;
            this.state = state;
            this.zipCode = zipCode;
            this.phone = phone;
            this.ssn = ssn;
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getAddress() {
            return address;
        }

        public String getCity() {
            return city;
        }

        public String getState() {
            return state;
        }

        public String getZipCode() {
            return zipCode;
        }

        public String getPhone() {
            return phone;
        }

        public String getSsn() {
            return ssn;
        }

        public String getPassword() {
            return password;
        }
    }
}
