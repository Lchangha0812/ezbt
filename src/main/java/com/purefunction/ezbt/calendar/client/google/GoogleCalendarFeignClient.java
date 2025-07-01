package com.purefunction.ezbt.calendar.client.google;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googleCalendar",
        url  = "https://www.googleapis.com",
        configuration = GoogleCalendarFeignConfig.class,
        fallback = GoogleCalendarFallback.class
)
@RequestMapping("/calendar/v3")
public interface GoogleCalendarFeignClient {

    @GetMapping(value = "/calendars/{calendarId}/events",
            params = "singleEvents=true")
    Object getEventsInit(
            @PathVariable String calendarId,
            @RequestParam("fields") String fields  // ex) "items(id,summary,start,end,status,location),nextSyncToken"
    );

    @GetMapping(value = "/calendars/{calendarId}/events", params = "syncToken")
    Object getEventsSync(
            @PathVariable String calendarId,
            @RequestParam String syncToken,
            @RequestParam("fields") String fields
    );

    @GetMapping(value = "/users/me/calendarList")
    Object listCalendarsInit(
            @RequestParam("fields") String fields
    );

    @GetMapping(value = "/users/me/calendarList", params = "syncToken")
    Object listCalendarsSync(
            @RequestParam String syncToken,
            @RequestParam("fields") String fields
    );
}
