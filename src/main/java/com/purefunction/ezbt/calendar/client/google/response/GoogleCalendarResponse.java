package com.purefunction.ezbt.calendar.client.google.response;

import java.util.List;

public record GoogleCalendarResponse<T> (List<T> items, String nextSyncToken) {}
