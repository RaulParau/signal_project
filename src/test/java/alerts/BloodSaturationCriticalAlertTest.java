package alerts;

import com.alerts.Alert;
import com.alerts.Week3Logic_StrategyPattern.BloodSaturationCriticalAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class BloodSaturationCriticalAlertTest {

    private BloodSaturationCriticalAlert alertCondition;

    @BeforeEach
    public void setUp(){
        alertCondition = new BloodSaturationCriticalAlert();
    }

   @Test
    public void testCriticalBloodSaturation(){
        PatientRecord saturationRecord = new PatientRecord(1, 80, "Saturation", 100);

        List<PatientRecord> records = new ArrayList<>();
        records.add(saturationRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals("Saturation too low", alert.getCondition());

    }

    @Test
    public void testHealthyBloodSaturation(){
        PatientRecord saturationRecord = new PatientRecord(1, 100, "Saturation", 100L);

        List<PatientRecord> records = List.of(saturationRecord);

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNull(alert);

    }
}
