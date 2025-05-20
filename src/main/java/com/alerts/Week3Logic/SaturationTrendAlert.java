package com.alerts.Week3Logic;

import com.alerts.Alert;
import com.alerts.Factory.AlertFactory;
import com.alerts.Factory.SaturationTrendAlertFactory;
import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implements an alert condition that triggers when the patients blood oxygen saturation drops by a threshold of 5 within
 * a fixed time period
 */
public class SaturationTrendAlert implements AlertCondition {
    /** The threshold value for detecting the rapid drop */
    private final static double THRESHOLD = 5;

    /** The time interval within that the drop has to occur (in milliseconds) */
    private final static long TIME_DIFFERENCE = 600_000;

    /**
     * This is the method checking if the drop occurred. The method filters the record so that only those are included
     * that correspond to the record type "Saturation". The records are then sorted in chronological order and compared to
     * see if a drop greater than the threshold appeared.
     *
     *  @param patientRecord is a list of all the patient records
     * @return a Blood Saturation trend critical alert if the conditions are met, null otherwise.
     */
    @Override
    public Alert checkCondition(List<PatientRecord> patientRecord) {

        List<PatientRecord> saturation = patientRecord.stream()
                .filter(r -> r.getRecordType().equals("Saturation"))
                .sorted(Comparator.comparing(PatientRecord::getTimestamp))
                .collect(Collectors.toList());

        for(int i = 0; i < saturation.size(); i++){
           for(int j = i + 1; j < saturation.size(); j++){
               long timeDifference = Math.abs(saturation.get(j).getTimestamp() - saturation.get(i).getTimestamp());

               if(timeDifference > TIME_DIFFERENCE){
                   break;
               }

               double drop = saturation.get(i).getMeasurementValue() - saturation.get(j).getMeasurementValue();

               if(drop > THRESHOLD){
                   AlertFactory factory = new SaturationTrendAlertFactory();
                   return factory.createAlert(Integer.toString(saturation.get(i).getPatientId()),
                           "Saturation drops too quick",
                           saturation.get(i+j).getTimestamp());
               }
           }
        }
        return null;
    }
}
