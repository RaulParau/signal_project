package alerts;
import com.alerts.Alert;
import com.alerts.EGCAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.alerts.AlertType.ECG_ABNORMALITY;

public class EGCAlertTest {

    private EGCAlert alertCondition;

    @BeforeEach
    public void setUp() {
        alertCondition = new EGCAlert();
    }

    @Test
    public void testAbnormalECGTriggersAlert() {
        List<PatientRecord> records = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            records.add(new PatientRecord(1, 1.0, "ECG", 100_000 + i * 1_000));
        }

        records.add(new PatientRecord(1, 2.0, "ECG", 111_000));

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals(ECG_ABNORMALITY, alert.getAlertType());
        Assertions.assertEquals("Abnormal ECG Data", alert.getCondition());
    }

    @Test
    public void testNoAlertWhenECGStable() {
        List<PatientRecord> records = new ArrayList<>();

        // All values remain stable and below the threshold
        for (int i = 0; i < 15; i++) {
            records.add(new PatientRecord(1, 1.0, "ECG", 100_000 + i * 1_000));
        }

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNull(alert);
    }
}

