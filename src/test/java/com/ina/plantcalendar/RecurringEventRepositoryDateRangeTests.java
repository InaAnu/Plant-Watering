package com.ina.plantcalendar;

import com.ina.plantcalendar.database.IJpaPlantRepository;
import com.ina.plantcalendar.database.IJpaRecurringEventRepository;
import com.ina.plantcalendar.database.IMyDataSource;
import com.ina.plantcalendar.database.RecurringEvent;
import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Plant;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class RecurringEventRepositoryDateRangeTests {

    @Autowired
    private IJpaRecurringEventRepository recurringEventRepository;
    @Autowired
    private IJpaPlantRepository plantRepository;

    Plant plant1 = new Plant("plant1", Plant.PlantType.HERB, 1);
    Plant plant2 = new Plant("plant2", Plant.PlantType.MARANTA, 1);
    Plant plant3 = new Plant("plant3", Plant.PlantType.ARUM, 1);

    @Test
    void injectedComponentsAreNotNull() {
        assertNotNull(recurringEventRepository);
        assertNotNull(plantRepository);
    }

    // List<RecurringEvent> findInTheDateRange(LocalDate from, LocalDate to);
    @Test
    void whenNoRecurringEventsInTheDateRangeThereAreNoResults() {
        var result = recurringEventRepository.findInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(7));

        assertEquals(0, result.size());
    }

    @Test
    void whenOneRecurringEventInTheDateRangeThereIsOneResult() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusDays(8),null));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(7));

        assertEquals(1, result.size());
    }

    @Test
    void whenRecurringEventStartDateIsOverOneMonthInThePastAndThereIsOneEventInTheDateRangeThereIsOneResult() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusYears(1),null));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(7));

        assertEquals(1, result.size());
    }

    @Test
    void whenThereIsMoreThanOneRecurringEventButOnlyOneEventIsInTheDateRangeThereIsOneResult() {
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now(),LocalDate.now().plusDays(3)));
        recurringEventRepository.save(new RecurringEvent(plant3, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now(), LocalDate.now().plusDays(7));

        assertEquals(1, result.size());
        assertEquals("plant2", result.get(0).getPlant().getScientificName());
    }

    @Test
    void whenThereAreThreeRecurringEventsInTheDateRangeThereAreThreeEvents() {
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant3, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now().minusYears(1), LocalDate.now().plusYears(1));

        assertEquals(3, result.size());
    }

    @Test
    void whenThereAreThreeRecurringEventsForOnePlantThereAreThreeResults() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),LocalDate.now().plusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusMonths(1),null));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now().minusYears(1), LocalDate.now().plusYears(1));

        assertEquals(3, result.size());
        assertEquals(plant1, result.get(0).getPlant());
        assertEquals(plant1, result.get(1).getPlant());
        assertEquals(plant1, result.get(2).getPlant());
    }

    @Test
    void whenYouSearchInTheDateRangeAndTheEndOfRangeIsNotSpecifiedEventsWithoutSpecifiedEndDateAreCollected() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusYears(1),null));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now(), null);

        assertEquals(3, result.size());
    }

    @Test
    void whenYouSearchInTheDateRangeAndTheEndOfRangeIsNotSpecifiedEventsEndingBeforeTheStartDateAreNotCollected() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusDays(1)));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusYears(1),null));

        var result = recurringEventRepository.findInTheDateRange(LocalDate.now(), null);

        assertEquals(2, result.size());
    }

    // List<RecurringEvent> findByScientificNameInTheDateRange(String scientificName, LocalDate from, LocalDate to);
    @Test
    void whenYouSearchInTheDateRangeAndByScientificNameAndTheEndOfRangeIsNotSpecifiedEventsWithoutSpecifiedEndDateAreCollected() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusYears(1),null));

        var result = recurringEventRepository.findByScientificNameInTheDateRange("plant1", LocalDate.now(), null);

        assertEquals(3, result.size());
    }

    @Test
    void whenThereAreOtherRecurringEventsWithDifferentPlantsOnlyTheEventsForASpecifiedPlantAreCollected() {
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant3, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));

        var result = recurringEventRepository.findByScientificNameInTheDateRange("plant1", LocalDate.now().minusYears(1), LocalDate.now().plusYears(1));

        assertEquals(3, result.size());
        assertEquals("plant1", result.get(0).getPlant().getScientificName());
        assertEquals("plant1", result.get(1).getPlant().getScientificName());
        assertEquals("plant1", result.get(2).getPlant().getScientificName());
    }

    // List<RecurringEvent> findByScientificNameAndEventTypeInTheDateRange(String scientificName, Event.EventType eventType, LocalDate from, LocalDate to);

    @Test
    void whenYouSearchInTheDateRangeAndByScientificNameAndTypeAndTheEndOfRangeIsNotSpecifiedEventsWithoutSpecifiedEndDateAreCollected() {
        plantRepository.save(plant1);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusYears(1),null));

        var result = recurringEventRepository.findByScientificNameAndEventTypeInTheDateRange("plant1", Event.EventType.WATERING, LocalDate.now(), null);

        assertEquals(3, result.size());
    }

    @Test
    void whenThereAreOtherRecurringEventsWithDifferentPlantsAndTheEventTypeForThePlantIsTheSameForEveryEventOnlyTheEventsForASpecifiedPlantAreCollected() {
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant3, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));

        var result = recurringEventRepository.findByScientificNameAndEventTypeInTheDateRange("plant1", Event.EventType.WATERING, LocalDate.now().minusYears(1), LocalDate.now().plusYears(1));

        assertEquals(3, result.size());
        assertEquals("plant1", result.get(0).getPlant().getScientificName());
        assertEquals("plant1", result.get(1).getPlant().getScientificName());
        assertEquals("plant1", result.get(2).getPlant().getScientificName());
    }

    @Test
    void whenThereAreRecurringEventsWithDifferentEventTypeForTheSamePlantOnlyTheEventsOfTheSpecifiedTypeAreCollected() {
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.REPLANTING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.FERTILIZING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant3, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));

        var result = recurringEventRepository.findByScientificNameAndEventTypeInTheDateRange("plant1", Event.EventType.WATERING, LocalDate.now().minusYears(1), LocalDate.now().plusYears(1));

        assertEquals(1, result.size());
        assertEquals("plant1", result.get(0).getPlant().getScientificName());
        assertEquals(Event.EventType.WATERING, result.get(0).getType());
    }

    @Test
    void whenThereAreNoRecurringEventsWithAGivenPlantThereAreNoEventsCollected() {
        plantRepository.save(plant1);
        plantRepository.save(plant2);
        plantRepository.save(plant3);
        recurringEventRepository.save(new RecurringEvent(plant1, Event.EventType.WATERING,LocalDate.now().minusMonths(1),LocalDate.now().minusWeeks(2)));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now(),null));
        recurringEventRepository.save(new RecurringEvent(plant2, Event.EventType.WATERING,LocalDate.now().plusMonths(1),LocalDate.now().plusYears(1)));

        var result = recurringEventRepository.findByScientificNameAndEventTypeInTheDateRange("plant3", Event.EventType.WATERING,LocalDate.now(), null);

        assertEquals(0, result.size());
    }
}
