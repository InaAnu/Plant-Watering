package com.ina.plantcalendar.model;

import java.sql.*;
import java.time.LocalDate;
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
    public static final String COLUMN_PLANT_INFO_WATERING_RECURRENCE = "watering_recurrence";

    public static final String TABLE_EVENTS = "events";
    public static final String COLUMN_EVENT_ID = "_id";
    public static final String COLUMN_EVENT_PLANT = "plant";
    public static final String COLUMN_EVENT_TYPE = "type";
    public static final String COLUMN_EVENT_DATE = "date";

    public static final String TABLE_PLANT_INFO_LIST_VIEW = "plant_list";
    public static final String CREATE_PLANT_INFO_LIST_VIEW = "CREATE VIEW IF NOT EXISTS " + TABLE_PLANT_INFO_LIST_VIEW + " AS " +
            "SELECT " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_RECURRENCE +
            " FROM " + TABLE_PLANT_INFO + " INNER JOIN " + TABLE_PLANT_ALIASES +
            " ON " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + " = " + TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_SCIENTIFIC_NAME +
            " ORDER BY " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_RECURRENCE;

    public static final String QUERY_PLANT_BY_NAME = "SELECT *" +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_ALIASES_ALIAS + " LIKE ? OR " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " LIKE ?";

    public static final String QUERY_PLANT_BY_EXACT_SCIENTIFIC_NAME = "SELECT *" +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " = ?";

    public static final String GET_ALL_PLANTS = "SELECT * FROM " + TABLE_PLANT_INFO_LIST_VIEW;

    public static final String QUERY_EXACT_EVENT = "SELECT " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_DATE +
            " FROM " + TABLE_EVENTS +
            " WHERE " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + " LIKE ?" +
            " AND " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + " LIKE ?" +
            " AND " + TABLE_EVENTS + "." + COLUMN_EVENT_DATE + " LIKE ?" +
            " ORDER BY " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_DATE;

    private PreparedStatement queryPlantByName;
    private PreparedStatement queryPlantByExactScientificName;
    private PreparedStatement queryExactEvent;

    private Connection conn;

    // TODO Check what will happen if I cannot connect to the database and make a workaround for that

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
//            System.out.println(CREATE_PLANT_INFO_LIST_VIEW);
//            System.out.println(QUERY_PLANT_BY_NAME);
            queryPlantByName = conn.prepareStatement(QUERY_PLANT_BY_NAME);
            queryPlantByExactScientificName = conn.prepareStatement(QUERY_PLANT_BY_EXACT_SCIENTIFIC_NAME);
            queryExactEvent = conn.prepareStatement(QUERY_EXACT_EVENT);
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

//    public void test(){
//        try (Statement statement = conn.createStatement()){
//            ResultSet resultSet = statement.executeQuery("pragma table_info(plant_list)");
//
//            List<String> columns = new ArrayList<>();
//            while(resultSet.next()) {
//                columns.add(resultSet.getString("name") + " " + resultSet.getString("type"));
//                System.out.println(resultSet.getString("name") + " " + resultSet.getString("type"));
//            }
//            System.out.println("Gequeried");
//        } catch (SQLException e) {
//            System.out.println("Create view failed: " + e.getMessage());
//        }
//    }

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
                plant.setWateringRecurrence(resultSet.getInt("watering_recurrence"));
                plants.add(plant);
            }
            return plants;
        } catch(SQLException e) {
            System.out.println("Querying plant by name failed: " + e.getMessage());
            return null;
        }
    }

    public Plant queryPlantByExactScientificName(String exactScientificName) {

        try {
            queryPlantByExactScientificName.setString(1, exactScientificName);
            ResultSet resultSet = queryPlantByExactScientificName.executeQuery();

            Plant plant = new Plant();
                plant.setScientificName(resultSet.getString("scientific_name"));
                plant.setAlias(resultSet.getString("alias"));
                plant.setType(Plant.PlantType.valueOf(resultSet.getString("type")));
                plant.setWateringRecurrence(resultSet.getInt("watering_recurrence"));

                return plant;
        } catch(SQLException e) {
            System.out.println("Querying plant by exact scientific name failed: " + e.getMessage());
            return null;
        }
    }

    public List<Plant> queryPlants() {
        System.out.println(GET_ALL_PLANTS);
        try(Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_PLANTS)) {
            List<Plant> plants = new ArrayList<>();
            while(resultSet.next()) {
                Plant plant = new Plant();
                plant.setScientificName(resultSet.getString("scientific_name"));
                plant.setAlias(resultSet.getString("alias"));
                plant.setType(Plant.PlantType.valueOf(resultSet.getString("type")));
                int wateringRecurrence = resultSet.getInt("watering_recurrence");
                plant.setWateringRecurrence(wateringRecurrence);
                plant.setWateringPatternText(wateringRecurrence);
                plants.add(plant);
            }
            return plants;
        } catch (SQLException e) {
            System.out.println("Querying plants failed:" + e);
            return null;
        }
    }

    public Event queryExactEvent (String scientificName, Event.EventType eventType, LocalDate date) {
        System.out.println(QUERY_EXACT_EVENT);

        // TODO I need to query a VIEW where the plant details are also in the table, rather than event, which will show only the number of the plant, from which we need to get further details.
        try {
            queryExactEvent.setString(1, scientificName); // TODO This is null, need to find out why
            queryExactEvent.setString(2, eventType.toString());
            queryExactEvent.setString(3, date.toString());
            ResultSet resultSet = queryExactEvent.executeQuery();

            Event event;

                if(resultSet != null) {
                    Plant plant = queryPlantByExactScientificName(scientificName);
                    event = new Event(plant, eventType, date);
                } else {
                    event = new Event(null, null, null);
                }
                return event;

        } catch (SQLException e) {
            System.out.println("Querying exact event failed: " + e.getMessage());
            return null;
        }
    }

    public Event queryExactEventTest (String scientificName, Event.EventType eventType, LocalDate date) {
        System.out.println(QUERY_EXACT_EVENT);

        // TODO I need to query a VIEW where the plant details are also in the table, rather than event, which will show only the number of the plant, from which we need to get further details.
        String forNow = scientificName;
        try {
            scientificName = "1";
            queryExactEvent.setString(1, scientificName);
            queryExactEvent.setString(2, eventType.toString());
            queryExactEvent.setString(3, date.toString());
            ResultSet resultSet = queryExactEvent.executeQuery();

            Event event;

            // TODO Resultset is not null at this point - I want to check if it's empty and I don't know how yet
            if(resultSet != null) {
                scientificName = forNow;
                Plant plant = queryPlantByExactScientificName(scientificName);
                event = new Event(plant, eventType, date);
            } else {
                event = new Event(null, null, null);
            }
            return event;

        } catch (SQLException e) {
            System.out.println("Querying exact event failed: " + e.getMessage());
            return null;
        }
    }

//    public boolean isEventInDB (String scientificName, Event.EventType eventType, LocalDate date) {
//
//        if (queryExactEvent(scientificName, eventType, date) == null) {
//            // TODO I want to assure that when an event is passed it will be either true or false and I don't know yet how to tackle the issue if SQL exception has been thrown.
//        }
//    }
}
