package com.ina.plantcalendar.services;

import com.ina.plantcalendar.dto.UpcomingEventDTO;
import com.ina.plantcalendar.model.Plant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.sql.SQLException;
import java.util.stream.Collectors;


@Service
public class FooterService {

    private final EventsService eventsService;

    @Autowired
    public FooterService(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    public void fillFooterData(Model model) throws SQLException {
        var upcomingEvents = eventsService.getUpcomingAggregatedEventsForTheUpcomingWeek()
                .stream()
                .map(event -> new UpcomingEventDTO(event.getPlants().stream().map(Plant::getAlias).collect(Collectors.toList()),
                        event.getEventType(),
                        event.getDate()))
                .collect(Collectors.toList());

        model.addAttribute("upcoming_events", upcomingEvents);
    }
}
