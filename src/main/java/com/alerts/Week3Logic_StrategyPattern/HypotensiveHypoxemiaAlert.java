package com.alerts.Week3Logic_StrategyPattern;

import com.alerts.Alert;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * This class implements AlertCondition and checks if the condition for an alert regarding a hypotensive hypoxemia are
 * met
 */

    public class HypotensiveHypoxemiaAlert implements AlertCondition {

        private final long TIME_INTERVAL = 1000;

    /**
     * This method first checks if the record we are looking at is of type "SystolicPressure" and then evaluates if the
     * pressure is in a critical state (below 90). Then it calls the helper method matchingRecords, to check if there are
     * other records, showing that there is a reading of low blood saturation within a timeframe of 1 second. If at least
     * one matching record is found, a Hypotensive Hypoxemia alert is triggered.
     *
      * @param patientRecord is a list of patient records.
     * @return Hypotensive Hypoxemia alert if the conditions are met, null otherwise
     */
    @Override
        public Alert checkCondition(List<PatientRecord> patientRecord) {
            for(PatientRecord record: patientRecord){
                if(record.getRecordType().equals("SystolicPressure") && record.getMeasurementValue() < 90){
                    //find records that are within 1 second of the critical systolic pressure record where the blood saturation
                    //is below 92%
                    List<PatientRecord> matchingRecord = matchingRecords(record.getTimestamp(), patientRecord);

                    if(!matchingRecord.isEmpty()){
                        return new Alert(
                                Integer.toString(record.getPatientId()),
                                "Alarm: Hypotensive Hypoxemia",
                                record.getTimestamp(),
                                AlertType.HYPOTENSIVE_HYPOXEMIA
                        );
                    }
                }
            }
            return null;
        }

    /**
     * This method is a helper method to check if there are records showing that the blood saturation was in a critical
     * state around the same timestamp that the blood pressure was critical. It looks for such records by filtering for
     * records within 1 second of the timestamp of the record showing critical blood pressure.
     *
     * @param timestamp is the timestamp of the record showing critical blood pressure
     * @param patientRecord is a list of all the patient records.
     *
     * @return a list of patient records, with low blood saturation at the time of the record showing critical blood
     * pressure
     */
    public List<PatientRecord> matchingRecords(long timestamp, List<PatientRecord> patientRecord){

            List<PatientRecord> matchingRecords = new ArrayList<>();

            for(PatientRecord record: patientRecord){
                if(record.getRecordType().equals("Saturation") && record.getMeasurementValue() < 92){
                    long timeDifference = Math.abs(record.getTimestamp() - timestamp);
                    if(timeDifference <= TIME_INTERVAL){
                        matchingRecords.add(record);
                    }
                }
            }

            return matchingRecords;
        }
    }
