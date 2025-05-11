package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements AlertCondition and checks if the condition for an alert regarding a hypotensive hypoxemia are
 * met
 */

    public class HypotensiveHypoxemiaAlert implements AlertCondition{

        private final long TIME_INTERVAL = 1000;

    /**
     *
      * @param patientRecord
     * @return
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
