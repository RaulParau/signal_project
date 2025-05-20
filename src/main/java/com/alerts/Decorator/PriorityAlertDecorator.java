package com.alerts.Decorator;

import com.alerts.Alert;
import com.alerts.Alert_Types.AlertInterface;

public class PriorityAlertDecorator extends AlertDecorator{

    private int priority;

    public PriorityAlertDecorator(AlertInterface decoratedAlert, int priority) {
        super(decoratedAlert);
        this.priority = priority;
    }

    public int getPriority(){
        return this.priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public String getCondition(){
        return "[Priority: " + priority + "]" + super.getCondition();
    }
}

