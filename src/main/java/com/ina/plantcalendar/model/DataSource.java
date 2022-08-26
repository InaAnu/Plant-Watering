package com.ina.plantcalendar.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    // TODO Make a way to add plants to the database

    public static final String DB_NAME = "plant_calendar.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:/Users/ina/Library/Mobile Documents/com~apple~CloudDocs/Ina/Programming/Personal Projects/Plant-Watering/src/main/resources/data/" + DB_NAME;

    public static final String TABLE_PLANT_ALIASES = "plant_aliases";
    public static final String COLUMN_PLANT_ALIASES_ID = "_id";
    public static final String COLUMN_PLANT_ALIASES_ALIAS = "alias";
    public static final String COLUMN_PLANT_ALIASES_SCIENTIFIC_NAME = "scientific_name";

    public static final String TABLE_PLANT_INFO = "plant_information";
    public static final String COLUMN_PLANT_INFO_ID = "_id";
    public static final String COLUMN_PLANT_INFO_SCIENTIFIC_NAME = "scientific_name";
    public static final String COLUMN_PLANT_INFO_TYPE = "type";
    public static final String COLUMN_PLANT_INFO_WATERING_PATTERN = "watering_pattern";

    public static final String TABLE_PLANT_INFO_LIST_VIEW = "plant_list";
    public static final String CREATE_PLANT_INFO_LIST_VIEW = "CREATE VIEW IF NOT EXISTS " + TABLE_PLANT_INFO_LIST_VIEW + " AS " +
            "SELECT " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_PATTERN +
            " FROM " + TABLE_PLANT_INFO + " INNER JOIN " + TABLE_PLANT_ALIASES +
            " ON " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + " = " + TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_SCIENTIFIC_NAME +
            " ORDER BY " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_PATTERN;

    public static final String QUERY_PLANT_BY_NAME = "SELECT *" +
//            + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " + COLUMN_PLANT_ALIASES_ALIAS + ", " +
//            COLUMN_PLANT_INFO_TYPE + ", " + COLUMN_PLANT_INFO_WATERING_PATTERN +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_ALIASES_ALIAS + " LIKE ? OR " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " LIKE ?";

    public static final String QUERY_PLANTS = "SELECT * FROM " + TABLE_PLANT_INFO_LIST_VIEW;
    private PreparedStatement queryPlantByName;

    private Connection conn;

    // TODO Check what will happen if I cannot connect to the database and make a workaround for that

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            System.out.println(CREATE_PLANT_INFO_LIST_VIEW);
            System.out.println(QUERY_PLANT_BY_NAME);
            queryPlantByName = conn.prepareStatement(QUERY_PLANT_BY_NAME);
            return true;
        } catch (SQLException e){
            System.out.println("Could not connect to database: " + e);
            return false;
        }
    }

    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e){
            System.out.println("Could not close connection to database: " + e);
        }
    }

    public boolean createViewForPlantInfo() {
        try (Statement statement = conn.createStatement()){
            statement.execute(CREATE_PLANT_INFO_LIST_VIEW);
            System.out.println("View Created");
            return true;
        } catch (SQLException e) {
            System.out.println("Create view failed: " + e.getMessage());
            return false;
        }
    }

    public List<Plant> queryPlantByName(String name) {

        try {
            queryPlantByName.setString(1, "%" + name + "%");
            queryPlantByName.setString(2, "%" + name + "%");
            ResultSet resultSet = queryPlantByName.executeQuery();

            List<Plant> plants = new ArrayList<>();
            while(resultSet.next()) {
                Plant plant = new Plant();
                plant.setScientificName(resultSet.getString("scientific_name"));
                plant.setAlias(resultSet.getString("alias"));
                plant.setType(Plant.PlantType.valueOf(resultSet.getString("type")));
                plant.setWateringPattern(resultSet.getString("watering_pattern"));
                plants.add(plant);
            }
            return plants;
        } catch(SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Plant> queryPlants() {

        try(Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY_PLANTS)) {
            List<Plant> plants = new ArrayList<>();
            while(resultSet.next()) {
                Plant plant = new Plant();
                plant.setScientificName(resultSet.getString("scientific_name"));
                plant.setAlias(resultSet.getString("alias"));
                plant.setType(Plant.PlantType.valueOf(resultSet.getString("type")));
                plant.setWateringPattern(resultSet.getString("watering_pattern"));
                plants.add(plant);
            }
            return plants;
        } catch (SQLException e) {
            System.out.println("Query failed:" + e);
            return null;
        }
    }
}
