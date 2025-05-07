package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

import java.util.Random;

public class SelfTriggeredAlertGenerator implements PatientDataGenerator{

    private final Random random = new Random();

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        if(random.nextDouble() < 0.1){
            outputStrategy.output(
                                patientId,
                                System.currentTimeMillis(),
                                "Button was pressed",
                                Double.toString(1.0)// For alert is on
            );
        }
    }
}
