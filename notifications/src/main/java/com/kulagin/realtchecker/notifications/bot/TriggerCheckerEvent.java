package com.kulagin.realtchecker.notifications.bot;

import org.springframework.context.ApplicationEvent;

public class TriggerCheckerEvent extends ApplicationEvent {
    public TriggerCheckerEvent(Object source) {
        super(source);
    }
}
