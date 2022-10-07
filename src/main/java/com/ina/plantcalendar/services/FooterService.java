package com.ina.plantcalendar.services;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Events;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;


@Service
public class FooterService {

    private final Events events;

    @Autowired
    public FooterService(Events events) {
        this.events = events;
    }

    //        TODO Change this section to use the code from the database

    public ArrayList<Event> upcomingEvents;

    Plant calathea = new Plant("Calathea Lancifolia", "Rattlesnake Plant", Plant.PlantType.CALATHEA, 7);
    Plant maranta = new Plant("Maranta Leuconeura", "Fascinator Tricolor", Plant.PlantType.MARANTA, 7);
    Plant goldenPhotos = new Plant("Epipremnum Aureum", "Golden Pothos", Plant.PlantType.ARUM, 7);
    Plant basil = new Plant("Ocium Basilicum", "Basil", Plant.PlantType.HERB, 1);
    Plant veronaVein = new Plant("Parthenocissum Striata", "Verona Vein", Plant.PlantType.CLIMBER, 7);
    Plant tundraTornado = new Plant("Sedum Makinoi", "Tundra Tornado", Plant.PlantType.SUCCULENT, 14);

    public void fillFooterData(Model model) {
        events.addRecurringEventTest(calathea, Event.EventType.WATERING, LocalDate.now().minusDays(6));
        events.addRecurringEventTest(maranta, Event.EventType.WATERING, LocalDate.now().minusDays(5));
        events.addRecurringEventTest(tundraTornado, Event.EventType.WATERING, LocalDate.now().minusDays(7));
        events.addRecurringEventTest(basil, Event.EventType.WATERING, LocalDate.now().minusDays(1));
        events.addRecurringEventTest(veronaVein, Event.EventType.WATERING, LocalDate.now().minusDays(3));
        events.addRecurringEventTest(goldenPhotos, Event.EventType.WATERING,LocalDate.now().minusDays(4));

        upcomingEvents = events.getUpcomingEvents(6);

        model.addAttribute("upcoming_events", upcomingEvents);
    }
}
