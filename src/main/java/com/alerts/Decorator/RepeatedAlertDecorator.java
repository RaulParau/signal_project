package com.alerts.Decorator;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.alerts.Alert_Types.AlertInterface;
import com.data_management.DataStorage;

public class RepeatedAlertDecorator extends AlertDecorator {

    private long interval;
    AlertGenerator generator;
    Alert decoratedAlert;

    public RepeatedAlertDecorator(Alert decoratedAlert, long interval, AlertGenerator generator) {
        super(decoratedAlert);
        this.interval = interval;
        this.generator = generator;
        this.decoratedAlert = decoratedAlert;
    }

    public void triggerRepeatedAlert() throws InterruptedException {
        for(int i = 0; i < 2; i++){
            Thread.sleep(interval);
            generator.triggerAlert(decoratedAlert);
        }
    }

}
