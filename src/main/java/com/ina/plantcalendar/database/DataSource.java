package com.ina.plantcalendar.database;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
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
public class DataSource implements IDataSource {

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
    public static final String COLUMN_EVENT_START_DATE = "start_date";
    public static final String COLUMN_EVENT_END_DATE = "end_date";

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
    public static final String COLUMN_FULL_EVENT_INFO_VIEW_EVENT_ID= "event_id";
    public static final String COLUMN_FULL_EVENT_INFO_VIEW_EVENT_TYPE= "event_type";
    public static final String COLUMN_FULL_EVENT_INFO_VIEW_PLANT_TYPE= "plant_type";
    public static final String CREATE_FULL_EVENT_INFO_VIEW = "CREATE VIEW IF NOT EXISTS " + TABLE_FULL_EVENT_INFO_VIEW + " AS " +
            "SELECT " + TABLE_EVENTS + "." + COLUMN_EVENT_ID + " AS " + COLUMN_FULL_EVENT_INFO_VIEW_EVENT_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + " AS " + COLUMN_FULL_EVENT_INFO_VIEW_PLANT_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_RECURRENCE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + " AS " + COLUMN_FULL_EVENT_INFO_VIEW_EVENT_TYPE + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_START_DATE + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_END_DATE +
            " FROM " + TABLE_PLANT_INFO +
            " INNER JOIN " + TABLE_PLANT_ALIASES +
            " ON " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + " = " + TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_SCIENTIFIC_NAME +
            " INNER JOIN " + TABLE_EVENTS +
            " ON " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID + " = " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT +
            " ORDER BY " + TABLE_EVENTS + "." + COLUMN_EVENT_ID + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + ", " +
            TABLE_PLANT_ALIASES + "." + COLUMN_PLANT_ALIASES_ALIAS + ", " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_TYPE + ", " +
            TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_WATERING_RECURRENCE + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_START_DATE + ", " +
            TABLE_EVENTS + "." + COLUMN_EVENT_END_DATE;

    public static final String QUERY_PLANT_BY_NAME = "SELECT *" +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_ALIASES_ALIAS + " LIKE ? OR " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " LIKE ?";

    public static final String QUERY_PLANT_BY_EXACT_SCIENTIFIC_NAME = "SELECT *" +
            " FROM " + TABLE_PLANT_INFO_LIST_VIEW +
            " WHERE " + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " = ?";

    public static final String GET_ALL_PLANTS = "SELECT * FROM " + TABLE_PLANT_INFO_LIST_VIEW;

    public static final String QUERY_IF_EVENT_EXISTS = "SELECT " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE +
            " FROM " + TABLE_EVENTS +
            " WHERE " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + " LIKE ?" +
            " AND " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE + " LIKE ?" +
            " ORDER BY " + TABLE_EVENTS + "." + COLUMN_EVENT_PLANT + ", " + TABLE_EVENTS + "." + COLUMN_EVENT_TYPE;

    public static final String QUERY_PLANT_ID_BY_SCIENTIFIC_NAME = "SELECT " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_ID +
            " FROM " + TABLE_PLANT_INFO +
            " WHERE " + TABLE_PLANT_INFO + "." + COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " = ?";

    public static final String ADD_RECURRING_EVENT_WITH_END_DATE = "INSERT INTO " + TABLE_EVENTS +
            " (" + COLUMN_EVENT_PLANT + ", " + COLUMN_EVENT_TYPE + ", " + COLUMN_EVENT_START_DATE + ", " + COLUMN_EVENT_END_DATE + ")" +
            " VALUES (?, ?, ?, ?, ?)";

    public static final String ADD_RECURRING_EVENT_WITHOUT_END_DATE = "INSERT INTO " + TABLE_EVENTS +
            " (" + COLUMN_EVENT_PLANT + ", " + COLUMN_EVENT_TYPE + ", " + COLUMN_EVENT_START_DATE + ", " + COLUMN_EVENT_END_DATE + ")" +
            " VALUES (?, ?, ?, ?, ?)";

    public static final String QUERY_ALL_RECURRING_EVENTS_FOR_A_CHOSEN_PLANT_IN_THE_DATE_RANGE = "SELECT * FROM " + TABLE_FULL_EVENT_INFO_VIEW + " WHERE " +
            COLUMN_PLANT_INFO_SCIENTIFIC_NAME + " = ? AND " +
            COLUMN_EVENT_START_DATE + " BETWEEN ? AND ? AND " + COLUMN_EVENT_END_DATE + " IS NULL OR " + COLUMN_EVENT_END_DATE + " >= ?";

    public static final String QUERY_ALL_RECURRING_EVENTS_IN_THE_DATE_RANGE = "SELECT * FROM " + TABLE_FULL_EVENT_INFO_VIEW + " WHERE " + COLUMN_EVENT_START_DATE + " BETWEEN ? AND ? AND " +
            COLUMN_EVENT_END_DATE + " IS NULL OR " + COLUMN_EVENT_END_DATE + " >= ?";


    private PreparedStatement queryPlantByName;
    private PreparedStatement queryPlantByExactScientificName;
    private PreparedStatement queryIfEventExists;
    private PreparedStatement queryPlantIdByScientificName;
    private PreparedStatement addRecurringEvent;
    private PreparedStatement queryAllRecurringEventsForAChosenPlantInTheDateRange;
    private PreparedStatement queryAllRecurringEventsInTheDateRange;

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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
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

    @Override
    public int queryPlantIdByScientificName(String scientificName) throws SQLException{
        try {
            queryPlantIdByScientificName = conn.prepareStatement(QUERY_PLANT_ID_BY_SCIENTIFIC_NAME);
            queryPlantIdByScientificName.setString(1, scientificName);
            ResultSet resultSet = queryPlantIdByScientificName.executeQuery();

            int result;
            if(resultSet.next()) {
                result = resultSet.getInt("_id");
            } else {
                result = -1;
            }
            return result;
        } catch (SQLException e) {
            System.out.println("Querying plant id by scientific name failed: " + e.getMessage());
            throw e;
        }
    }

    @Override
    public boolean isEventInDB(String scientificName, Event.EventType eventType) throws SQLException {
        try {
            queryIfEventExists = conn.prepareStatement(QUERY_IF_EVENT_EXISTS);
            queryPlantIdByScientificName = conn.prepareStatement(QUERY_PLANT_ID_BY_SCIENTIFIC_NAME);
            queryIfEventExists.setInt(1, queryPlantIdByScientificName(scientificName));
            queryIfEventExists.setString(2, eventType.toString());
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

    @Override
    public boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate startDate, LocalDate endDate) throws SQLException {
        try {
            addRecurringEvent = conn.prepareStatement(ADD_RECURRING_EVENT_WITH_END_DATE);
            addRecurringEvent.setInt(1, plantId);
            addRecurringEvent.setString(2, eventType.toString());
            addRecurringEvent.setString(3, startDate.toString());
            addRecurringEvent.setString(4, endDate.toString());


            addRecurringEvent.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Adding new event to the database failed: " + e.getMessage());
            throw new SQLException("Adding new event to the database failed: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean addRecurringEvent(int plantId, Event.EventType eventType, LocalDate startDate) throws SQLException {
        try {
            addRecurringEvent = conn.prepareStatement(ADD_RECURRING_EVENT_WITHOUT_END_DATE);
            addRecurringEvent.setInt(1, plantId);
            addRecurringEvent.setString(2, eventType.toString());
            addRecurringEvent.setString(3, startDate.toString());

            addRecurringEvent.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Adding new event to the database failed: " + e.getMessage());
            throw new SQLException("Adding new event to the database failed: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Event> findAllEventsForAPlantByDate(LocalDate from, LocalDate to, String scientificName) throws SQLException {
        try {
            queryAllRecurringEventsForAChosenPlantInTheDateRange = conn.prepareStatement(QUERY_ALL_RECURRING_EVENTS_FOR_A_CHOSEN_PLANT_IN_THE_DATE_RANGE);
            queryAllRecurringEventsForAChosenPlantInTheDateRange.setString(1, scientificName);
            queryAllRecurringEventsForAChosenPlantInTheDateRange.setString(2, from.toString());
            queryAllRecurringEventsForAChosenPlantInTheDateRange.setString(3, to.toString());
            queryAllRecurringEventsForAChosenPlantInTheDateRange.setString(4, to.toString());
            ResultSet resultSet = queryAllRecurringEventsForAChosenPlantInTheDateRange.executeQuery();

            List<Event> events = new ArrayList<>();
            while(resultSet.next()) {
                scientificName = resultSet.getString(COLUMN_PLANT_INFO_SCIENTIFIC_NAME);
                String alias = resultSet.getString(COLUMN_PLANT_ALIASES_ALIAS);
                Plant.PlantType plantType = Plant.PlantType.valueOf(resultSet.getString(COLUMN_FULL_EVENT_INFO_VIEW_PLANT_TYPE));
                int wateringRecurrence = resultSet.getInt(COLUMN_PLANT_INFO_WATERING_RECURRENCE);
                Plant plant = new Plant(scientificName, alias, plantType,wateringRecurrence);
                Event.EventType eventType = Event.EventType.valueOf(resultSet.getString(COLUMN_FULL_EVENT_INFO_VIEW_EVENT_TYPE));

                // TODO How to get the date from SQLite (and not null)
                LocalDate startDate = resultSet.getDate(COLUMN_EVENT_START_DATE).toLocalDate();
                LocalDate endDate = resultSet.getDate(COLUMN_EVENT_END_DATE).toLocalDate();
                RecurringEvent recurringEvent = new RecurringEvent(plant, eventType, startDate, endDate);

                List<Event> eventsFromTheRecurringEvent = recurringEvent.getAllEventsInTheDateRange(from,to);
                events.addAll(eventsFromTheRecurringEvent);
            }
            return events;
        } catch (SQLException e) {
            System.out.println("Querying events in the given range failed: " + e);
            throw e;
        }
    }

    @Override
    public List<Event> findAllEventsByDate(LocalDate from, LocalDate to) throws SQLException {
        try {
            queryAllRecurringEventsInTheDateRange = conn.prepareStatement(QUERY_ALL_RECURRING_EVENTS_IN_THE_DATE_RANGE);
            queryAllRecurringEventsInTheDateRange.setString(1, from.toString());
            queryAllRecurringEventsInTheDateRange.setString(2, to.toString());
            queryAllRecurringEventsInTheDateRange.setString(3, to.toString());
            ResultSet resultSet = queryAllRecurringEventsInTheDateRange.executeQuery();

            List<Event> events = new ArrayList<>();
            while(resultSet.next()) {
                String scientificName = resultSet.getString(COLUMN_PLANT_INFO_SCIENTIFIC_NAME);
                String alias = resultSet.getString(COLUMN_PLANT_ALIASES_ALIAS);
                Plant.PlantType plantType = Plant.PlantType.valueOf(resultSet.getString(COLUMN_FULL_EVENT_INFO_VIEW_PLANT_TYPE));
                int wateringRecurrence = resultSet.getInt(COLUMN_PLANT_INFO_WATERING_RECURRENCE);
                Plant plant = new Plant(scientificName, alias, plantType,wateringRecurrence);
                Event.EventType eventType = Event.EventType.valueOf(resultSet.getString(COLUMN_FULL_EVENT_INFO_VIEW_EVENT_TYPE));

                // TODO How to get the date from SQLite (and not null)
                LocalDate startDate = resultSet.getDate(COLUMN_EVENT_START_DATE).toLocalDate();
                LocalDate endDate = resultSet.getDate(COLUMN_EVENT_END_DATE).toLocalDate();
                RecurringEvent recurringEvent = new RecurringEvent(plant, eventType, startDate, endDate);

                List<Event> eventsFromTheRecurringEvent = recurringEvent.getAllEventsInTheDateRange(from,to);
                events.addAll(eventsFromTheRecurringEvent);
            }
            return events;
        } catch (SQLException e) {
            System.out.println("Querying events in the given range failed: " + e);
            throw e;
        }
    }
}
