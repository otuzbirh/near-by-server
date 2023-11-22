package com.sb.nearby.util;

public class Constants {
    public static final String API_VERSION = "/v1";
    public static final String BASE_URL = "/api" + API_VERSION;

    public static final class Product {
        public static final double EARTH_RADIUS_KM = 6371.0;
        public static final String UPLOAD_FOLDER_NAME = "uploads/";
        public static final String UPLOAD_PATH = "src/main/resources/static/" + UPLOAD_FOLDER_NAME;
    }

}
