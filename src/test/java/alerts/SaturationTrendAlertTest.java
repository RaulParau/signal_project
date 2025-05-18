package alerts;

import com.alerts.Alert;
import com.alerts.SaturationTrendAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static com.alerts.AlertType.OXYGEN_SATURATION_RAPID_DROP;

public class SaturationTrendAlertTest {
    private SaturationTrendAlert alertCondition;

    @BeforeEach
    public void setup() {
        alertCondition = new SaturationTrendAlert();
    }

    @Test
    public void testForCriticalTrend(){
        List<PatientRecord> records = new ArrayList<>();

        // Base time: 0ms
        records.add(new PatientRecord(1, 98.0, "Saturation", 0)); // Initial high value
        records.add(new PatientRecord(1, 98.0, "Saturation", 100_000)); // Initial high value
        records.add(new PatientRecord(1, 98.0, "Saturation", 200_000)); // Initial high value
        // Drop to 91.0 after 5 minutes (300,000 ms)
        records.add(new PatientRecord(1, 91.0, "Saturation", 300_000)); // Drop of 7 within time window

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals(OXYGEN_SATURATION_RAPID_DROP, alert.getAlertType());
        Assertions.assertEquals("Saturation drops too quick", alert.getCondition());
    }

    @Test
    public void testForNormalTrend(){
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 99, "Saturation", 1));
        records.add(new PatientRecord(1, 98, "Saturation", 2));

        Alert alert = alertCondition.checkCondition(records);
        Assertions.assertNull(alert);
    }
}
