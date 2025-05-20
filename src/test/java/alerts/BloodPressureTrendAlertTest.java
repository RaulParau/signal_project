package alerts;

import com.alerts.Alert;
import com.alerts.Week3Logic.BloodPressureTrendAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.alerts.Week3Logic.AlertType.BLOOD_PRESSURE_TREND;

public class BloodPressureTrendAlertTest {
    private BloodPressureTrendAlert alertCondition;

    @BeforeEach
    public void setup() {
        alertCondition = new BloodPressureTrendAlert();
    }

    @Test
    public void testIncreasingSystolicTrendTriggersAlert() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 100, "SystolicPressure", 100));
        records.add(new PatientRecord(1, 120, "SystolicPressure", 110));
        records.add(new PatientRecord(1, 140, "SystolicPressure", 120));

        Alert alert = alertCondition.checkCondition(records);
        Assertions.assertNotNull(alert);
        Assertions.assertEquals(BLOOD_PRESSURE_TREND, alert.getAlertType());
        Assertions.assertEquals("Systolic blood pressure trend increases too quick", alert.getCondition());
    }

    @Test
    public void testDecreasingSystolicTrendTriggersAlert() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 140, "SystolicPressure", 100));
        records.add(new PatientRecord(1, 120, "SystolicPressure", 110));
        records.add(new PatientRecord(1, 100, "SystolicPressure", 120));

        Alert alert = alertCondition.checkCondition(records);
        Assertions.assertNotNull(alert);
        Assertions.assertEquals(BLOOD_PRESSURE_TREND, alert.getAlertType());
        Assertions.assertEquals("Systolic blood pressure trend decreases too quick", alert.getCondition());
    }

    @Test
    public void testIncreasingDiastolicTrendTriggersAlert() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 80, "DiastolicPressure", 100));
        records.add(new PatientRecord(1, 100, "DiastolicPressure", 110));
        records.add(new PatientRecord(1, 115, "DiastolicPressure", 120));

        Alert alert = alertCondition.checkCondition(records);
        Assertions.assertNotNull(alert);
        Assertions.assertEquals(BLOOD_PRESSURE_TREND, alert.getAlertType());
        Assertions.assertEquals("Diastolic blood pressure trend increases too quick", alert.getCondition());
    }

    @Test
    public void testDecreasingDiastolicTrendTriggersAlert() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 115, "DiastolicPressure", 100));
        records.add(new PatientRecord(1, 100, "DiastolicPressure", 110));
        records.add(new PatientRecord(1, 80, "DiastolicPressure", 120));

        Alert alert = alertCondition.checkCondition(records);
        Assertions.assertNotNull(alert);
        Assertions.assertEquals(BLOOD_PRESSURE_TREND, alert.getAlertType());
        Assertions.assertEquals("Diastolic blood pressure trend decreases too quick", alert.getCondition());
    }

    @Test
    public void testNoTrendReturnsNull() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 100, "SystolicPressure", 100));
        records.add(new PatientRecord(1, 111, "SystolicPressure", 110));
        records.add(new PatientRecord(1, 115, "SystolicPressure", 120));

        Alert alert = alertCondition.checkCondition(records);
        Assertions.assertNull(alert);
    }
}
