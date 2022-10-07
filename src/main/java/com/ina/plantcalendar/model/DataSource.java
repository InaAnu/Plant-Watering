package com.ina.plantcalendar.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
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
    public static final String COLUMN_EVENT_LAST_WATERED_ON = "last_watered_on";
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

    public static final String TABLE_FULL_EVENT_INFO_VIEW = "full_event_info";
    public static final String CREATE_FULL_EVENT_INFO_VIEW = "CREATE VIEW IF NOT EXISTS " + TABLE_FULL_EVENT_INFO_VIEW + " AS " +
            "SELECT " + TABLE_EVENTS + "." + COLUMN_EVENT_ID + " AS event_id" + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + " AS plant_type" + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_RECURRENCE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + " AS event_type" + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_LAST_WATERED_ON + ", "
            + TABLE_EVENTS + "." + COLUMN_EVENT_DATE +
            " FROM " + TABLE_PLANT_INFO +
            " INNER JOIN " + TABLE_PLANT_ALIASES +
            " ON " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + " = " + TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_SCIENTIFIC_NAME +
            " INNER JOIN " + TABLE_EVENTS +
            " ON " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + " = " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT +
            " ORDER BY " + TABLE_EVENTS + "." + COLUMN_EVENT_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_RECURRENCE + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_LAST_WATERED_ON + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_DATE;

    public static final String QUERY_PLANT_BY_NAME = "SELECT *" +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_ALIASES_ALIAS + " LIKE ? OR " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " LIKE ?";

    public static final String QUERY_PLANT_BY_EXACT_SCIENTIFIC_NAME = "SELECT *" +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " = ?";

    public static final String GET_ALL_PLANTS = "SELECT * FROM " + TABLE_PLANT_INFO_LIST_VIEW;

    public static final String QUERY_IF_EVENT_EXISTS = "SELECT " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_DATE +
            " FROM " + TABLE_EVENTS +
            " WHERE " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + " LIKE ?" +
            " AND " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + " LIKE ?" +
            " AND " + TABLE_EVENTS + "." + COLUMN_EVENT_DATE + " BETWEEN ? AND ?" +
            " ORDER BY " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_DATE;

    public static final String QUERY_PLANT_ID_BY_SCIENTIFIC_NAME = "SELECT " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID +
            " FROM " + TABLE_PLANT_INFO +
            " WHERE " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " = ?";

    public static final String ADD_EVENT = "INSERT INTO " + TABLE_EVENTS +
            " (" + COLUMN_EVENT_PLANT + ", " + COLUMN_EVENT_TYPE + ", " + COLUMN_EVENT_LAST_WATERED_ON + ", " + COLUMN_EVENT_DATE + ")" +
            " VALUES (?, ?, ?, ?)";

    private PreparedStatement queryPlantByName;
    private PreparedStatement queryPlantByExactScientificName;
    private PreparedStatement queryIfEventExists;
    private PreparedStatement queryPlantIdByScientificName;
    private PreparedStatement addEvent;

    private final Connection conn;

    // TODO If I have a connection already open I don't want to open another one, but in this case Datasource is singleton so for now it should work
    // TODO Check what will happen if I cannot connect to the database and make a workaround for that
    @Autowired
    public DataSource() throws SQLException {
        try {
            this.conn = DriverManager.getConnection(CONNECTION_STRING);
        } catch (SQLException e) {
            System.out.println("Could not connect to database: " + e);
            throw e;
        }
    }

    // TODO investigate when I should be closing the connection!!!
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

    public boolean createViewForPlantInfo() throws SQLException {
        try (Statement statement = conn.createStatement()){
            statement.execute(CREATE_PLANT_INFO_LIST_VIEW);
            System.out.println("Created view: plant_list");
            return true;
        } catch (SQLException e) {
            System.out.println("Create plant_list view failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean createViewForFullEventInfo() throws SQLException {
        try (Statement statement = conn.createStatement()) {
            statement.execute(CREATE_FULL_EVENT_INFO_VIEW);
            System.out.println("Created view: full_event_info");
            return true;
        } catch (SQLException e) {
            System.out.println("Create full_event_info failed: " + e.getMessage());
            throw e;
        }
    }

    public List<Plant> queryPlantByName(String name) throws SQLException {

        try {
            queryPlantByName = conn.prepareStatement(QUERY_PLANT_BY_NAME);
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
            throw e;
        }
    }

    public Plant queryPlantByExactScientificName(String exactScientificName) throws SQLException {

        try {
            queryPlantByExactScientificName = conn.prepareStatement(QUERY_PLANT_BY_EXACT_SCIENTIFIC_NAME);
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
            throw e;
        }
    }

    public List<Plant> queryPlants() throws SQLException {

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
            System.out.println("Querying plants failed: " + e.getMessage());
            throw e;
        }
    }

    public int queryPlantIdByScientificName (String scientificName) throws SQLException{

        try {
            queryPlantIdByScientificName = conn.prepareStatement(QUERY_PLANT_ID_BY_SCIENTIFIC_NAME);
            queryPlantIdByScientificName.setString(1, scientificName);
            ResultSet resultSet = queryPlantIdByScientificName.executeQuery();

            return resultSet.getInt(1);
        } catch (SQLException e) {
            System.out.println("Querying plant id by scientific name failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean isEventInDB (String scientificName, Event.EventType eventType, LocalDate eventDate) throws SQLException {

        try {
            Plant plant = queryPlantByExactScientificName(scientificName);
            int wateringRecurrence = plant.getWateringRecurrence();

            queryIfEventExists = conn.prepareStatement(QUERY_IF_EVENT_EXISTS);
            queryPlantIdByScientificName = conn.prepareStatement(QUERY_PLANT_ID_BY_SCIENTIFIC_NAME);
            queryIfEventExists.setInt(1, queryPlantIdByScientificName(scientificName));
            queryIfEventExists.setString(2, eventType.toString());
            queryIfEventExists.setString(3, eventDate.minusDays(wateringRecurrence).toString());
            queryIfEventExists.setString(4, eventDate.plusDays(wateringRecurrence).toString());
            ResultSet resultSet = queryIfEventExists.executeQuery();

                if(resultSet.isBeforeFirst()) {
                    System.out.println("Event is already in the database.");
                    return true;
                } else {
                    return false;
                }

        } catch (SQLException e) {
            System.out.println("Querying exact event failed: " + e.getMessage());
            throw e;
        }
    }

    public boolean addEvent (int plantId, Event.EventType eventType, LocalDate lastWateredOn, LocalDate eventDate) throws SQLException {

        try {
            addEvent = conn.prepareStatement(ADD_EVENT);
            addEvent.setInt(1, plantId);
            addEvent.setString(2, eventType.toString());
            addEvent.setString(3, lastWateredOn.toString());
            addEvent.setString(4, eventDate.toString());

            addEvent.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(ADD_EVENT);
            System.out.println("Adding new event to the database failed: " + e.getMessage());
            throw new SQLException("Adding new event to the database failed: " + e.getMessage(), e);
        }
    }

}
