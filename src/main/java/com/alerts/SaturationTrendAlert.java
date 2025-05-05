package com.alerts;

import com.data_management.PatientRecord;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SaturationTrendAlert implements AlertCondition{
    private final static double THRESHOLD = 5;
    private final static long TIME_DIFFERENCE = 600_000;
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
                   return new Alert(Integer.toString(saturation.get(i).getPatientId()),
                           "Saturation drops too quick",
                           saturation.get(i+j).getTimestamp(),
                           AlertType.OXYGEN_SATURATION_RAPID_DROP
                   );
               }
           }
        }
        return null;
    }
}
