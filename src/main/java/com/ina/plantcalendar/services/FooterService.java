package com.ina.plantcalendar.services;

import com.ina.plantcalendar.model.Event;
import com.ina.plantcalendar.model.Events;
import com.ina.plantcalendar.model.Plant;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class FooterService {

    //        TODO Change this section to use the code from the database

    public ArrayList<Event> upcomingEvents;

    Plant calathea = new Plant("Calathea Lancifolia", "Rattlesnake Plant", Plant.PlantType.CALATHEA, 7);
    Plant maranta = new Plant("Maranta Leuconeura", "Fascinator Tricolor", Plant.PlantType.MARANTA, 7);
    Plant goldenPhotos = new Plant("Epipremnum Aureum", "Golden Pothos", Plant.PlantType.ARUM, 7);
    Plant basil = new Plant("Ocium Basilicum", "Basil", Plant.PlantType.HERB, 1);
    Plant veronaVein = new Plant("Parthenocissum Striata", "Verona Vein", Plant.PlantType.CLIMBER, 7);
    Plant tundraTornado = new Plant("Sedum Makinoi", "Tundra Tornado", Plant.PlantType.SUCCULENT, 14);

    Events events = new Events();

    public FooterService() throws SQLException {
    }

    public void fillFooterData(Model model) {
        events.addEvent(calathea, Event.EventType.WATERING, LocalDate.now().minusDays(6));
        events.addEvent(maranta, Event.EventType.WATERING, LocalDate.now().minusDays(5));
        events.addEvent(tundraTornado, Event.EventType.WATERING, LocalDate.now().minusDays(7));
        events.addEvent(basil, Event.EventType.WATERING, LocalDate.now().minusDays(1));
        events.addEvent(veronaVein, Event.EventType.WATERING, LocalDate.now().minusDays(3));
        events.addEvent(goldenPhotos, Event.EventType.WATERING,LocalDate.now().minusDays(4));

        upcomingEvents = events.getUpcomingEvents(6);

        model.addAttribute("upcoming_events", upcomingEvents);
    }
}
