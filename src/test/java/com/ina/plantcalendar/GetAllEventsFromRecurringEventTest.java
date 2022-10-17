package com.ina.plantcalendar;

import com.ina.plantcalendar.database.IDataSource;
import com.ina.plantcalendar.database.RecurringEvent;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import com.ina.plantcalendar.services.EventsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetAllEventsFromRecurringEventTest {

    Plant plantWateredDaily = new Plant("Calathea", Plant.PlantType.CALATHEA, 1);
    Plant plantWateredWeekly = new Plant("Calathea", Plant.PlantType.CALATHEA, 7);


// Testing the range

    @Test
    public void whenTheRangeStartsAfterTheRecurringEventEndDateThereShouldBeNoResults() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(6));

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now().plusDays(7),LocalDate.now().plusDays(13));

        assertEquals(List.of(), result);
    }

    @Test
    public void whenTheRangeEndsBeforeTheRecurringEventStartDateThereShouldBeNoResults() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(6));

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now().minusDays(7),LocalDate.now().minusDays(1));

        assertEquals(List.of(), result);
    }

    // Testing behaviour for plants watered weekly

    @Test
    public void whenThereIsOneEventInTheRangeAndTheRecurringEventStartDateIsTheSameAsTheBeginningOfTheRangeTheListContainsOneEvent() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(7)); // The only event will be on start date

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(6));

        assertEquals(1, result.size());
        assertEquals(recurringEvent.getStartDate(),result.get(0).getEventDate());
    }

    @Test
    public void whenThereIsOneEventInTheRangeAndTheRecurringEventStartDateDoesNotMatchTheStartDateOfTheRangeTheListContainsOneEvent() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now().plusDays(1),LocalDate.now().plusDays(6)); // The only event will be on start date

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(7));

        assertEquals(1, result.size());
        assertEquals(recurringEvent.getStartDate(),result.get(0).getEventDate());
    }

    @Test
    public void whenThereAreTwoEventsInTheRangeAndTheRangeStartsBeforeTheRecurringEventStartDateTheListContainsTwoEvents() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(7)); // Event will fall on the recurring event's start date and 7 days later

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now().minusDays(7), LocalDate.now().plusDays(15));

        assertEquals(2, result.size());
        assertEquals(recurringEvent.getStartDate(), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(7), result.get(1).getEventDate());
    }

    @Test
    public void whenThereAreTwoEventsInTheRangeAndTheRangeStartsAfterTheRecurringEventsStartDateButTheRangeStartsOnTheSameDateAsTheRecurringEventLandsTheListContainsTwoEvents() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(25)); // Event will fall on the recurring event's start date and every 7 days after that until end date

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now().plusDays(7), LocalDate.now().plusDays(14));

        assertEquals(2, result.size());
        assertEquals(recurringEvent.getStartDate().plusDays(7), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(14), result.get(1).getEventDate());
    }

    @Test
    public void whenThereAreTwoEventsInTheRangeAndTheRangeStartsAfterTheRecurringEventsStartDateButTheRangeStartsOnADifferentDayThanTheRecurringEventLandsTheListContainsTwoEvents() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(25)); // Event will fall on the recurring event's start date and every 7 days after that until end date

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now().plusDays(13), LocalDate.now().plusDays(22));

        assertEquals(2, result.size());
        assertEquals(recurringEvent.getStartDate().plusDays(14), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(21), result.get(1).getEventDate());
    }

    @Test
    public void whenTheEndDateOfTheRecurringEventIsSetToNullTheEventsWithinTheRangeProvidedByUserAreReturned() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredWeekly, Event.EventType.WATERING,LocalDate.now(),null);

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(13));

        assertEquals(2, result.size());
        assertEquals(recurringEvent.getStartDate(), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(7), result.get(1).getEventDate());
    }

    // Testing for plants watered daily

    @Test
    public void whenTheEventRecursEveryDayAndThereAreTwoEventsInTheRangeAndTheRangeStartsBeforeTheRecurringEventStartDateTheListContainsTwoEvents() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredDaily, Event.EventType.WATERING, LocalDate.now().plusDays(1),LocalDate.now().plusDays(2)); // Event will fall on the recurring event's start date and 7 days later

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(6));

        assertEquals(2, result.size());
        assertEquals(recurringEvent.getStartDate(), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(1), result.get(1).getEventDate());
    }

    @Test
    public void whenTheEventRecursEveryDayAndThereAreTwoEventsInTheRangeAndTheRangeStartsAfterTheRecurringEventsStartDateTheListContainsTwoEvents() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredDaily, Event.EventType.WATERING, LocalDate.now(),LocalDate.now().plusDays(2)); // Event will fall on the recurring event's start date and every 7 days after that until end date

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now().plusDays(1), LocalDate.now().plusDays(14));

        assertEquals(2, result.size());
        assertEquals(recurringEvent.getStartDate().plusDays(1), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(2), result.get(1).getEventDate());
    }

    @Test
    public void whenTheEventRecursEveryDayAndTheEndDateOfTheRecurringEventIsSetToNullTheEventsWithinTheRangeProvidedByUserAreReturned() {
        RecurringEvent recurringEvent = new RecurringEvent(plantWateredDaily, Event.EventType.WATERING,LocalDate.now(),null);

        var result = recurringEvent.getAllEventsInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(2));

        assertEquals(3, result.size());
        assertEquals(recurringEvent.getStartDate(), result.get(0).getEventDate());
        assertEquals(recurringEvent.getStartDate().plusDays(1), result.get(1).getEventDate());
    }
}
