package com.alerts;

import com.alerts.Alert_Types.AlertInterface;
import com.alerts.Week3Logic_StrategyPattern.AlertCondition;
import com.alerts.Week3Logic_StrategyPattern.AlertType;
import com.alerts.Week3Logic_StrategyPattern.HypotensiveHypoxemiaAlert;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;
    private List<AlertCondition> alertConditions;

    private List<Alert> triggeredAlerts = new ArrayList<>(); // This is just for testing purposes
    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
        this.alertConditions = new ArrayList<>();
    }

    public void registerAlertCondition(AlertCondition condition){
        alertConditions.add(condition);
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        // Implementation goes here
        List<PatientRecord> records = dataStorage.getRecords(
                patient.getPatientId(),
                1L,
                System.currentTimeMillis()
        );
        boolean bloodPressureAlertTriggered = false;

        // Loop through all alert conditions
        for (AlertCondition condition : alertConditions) {
            Alert alert = condition.checkCondition(records);
            if (alert != null) {
                // Check if the blood pressure alert was triggered
                if (alert.getAlertType() == AlertType.BLOOD_PRESSURE_CRITICAL) {
                    bloodPressureAlertTriggered = true;
                }
                triggerAlert(alert);
            }
        }

        // If blood pressure alert was triggered, check for Hypotensive Hypoxemia
        if (bloodPressureAlertTriggered) {
            Alert hypotensiveAlert = new HypotensiveHypoxemiaAlert().checkCondition(records);
            if (hypotensiveAlert != null) {
                triggerAlert(hypotensiveAlert);
            }
        }
    }

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    public void triggerAlert(Alert alert) {
        triggeredAlerts.add(alert);

        System.out.println("Alert " + alert.getAlertType() +
                " Cause : " + alert.getCondition() +
                " for patient " + alert.getPatientId() +
                " at time " + alert.getTimestamp());
    }

    public List getTriggeredAlerts(){
        return triggeredAlerts;
    }

    //This method is just used for testing
    public void clearTriggeredAlerts() {
        triggeredAlerts.clear();
    }

}
