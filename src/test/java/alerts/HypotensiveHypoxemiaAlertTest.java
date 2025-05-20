package alerts;

import com.alerts.Alert;
import com.alerts.Week3Logic.HypotensiveHypoxemiaAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.alerts.Week3Logic.AlertType.HYPOTENSIVE_HYPOXEMIA;

public class HypotensiveHypoxemiaAlertTest {

    private HypotensiveHypoxemiaAlert alertCondition;

    @BeforeEach
    public void setUp() {
        alertCondition = new HypotensiveHypoxemiaAlert();
    }

    @Test
    public void testAlertIsTriggeredWhenConditionsMet() {
        List<PatientRecord> records = new ArrayList<>();
        long baseTime = 1_684_000_000_000L;

        // Low systolic pressure
        records.add(new PatientRecord(1, 85, "SystolicPressure", baseTime));

        // Low saturation within 1 second
        records.add(new PatientRecord(1, 89, "Saturation", baseTime + 500));

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals(HYPOTENSIVE_HYPOXEMIA, alert.getAlertType());
        Assertions.assertEquals("Alarm: Hypotensive Hypoxemia", alert.getCondition());
    }

    @Test
    public void testNoAlertWhenConditionsNotMet() {
        List<PatientRecord> records = new ArrayList<>();
        long baseTime = 1_684_000_000_000L;

        records.add(new PatientRecord(1, baseTime, "SystolicPressure", 100));

        records.add(new PatientRecord(1, 85, "SystolicPressure", baseTime + 1000));
        records.add(new PatientRecord(1, 89, "Saturation", baseTime + 5000)); // too far in time

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNull(alert);
    }
}

