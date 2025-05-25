package week4.Factory_Test;

import static org.junit.jupiter.api.Assertions.*;

import com.alerts.Alert;
import com.alerts.Factory.ECGAlertFactory;
import com.alerts.Week3Logic_StrategyPattern.AlertType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ECGAlertFactoryTest {

    private ECGAlertFactory factory;

    @BeforeEach
    public void setup() {
        factory = new ECGAlertFactory();
    }

    @Test
    public void testCreateAlert() {
        String patientId = "123";
        String condition = "Abnormal ECG detected";
        long timestamp = 987654321L;

        Alert alert = factory.createAlert(patientId, condition, timestamp);

        assertNotNull(alert);
        assertEquals(patientId, alert.getPatientId());
        assertEquals(condition, alert.getCondition());
        assertEquals(timestamp, alert.getTimestamp());
        assertEquals(AlertType.ECG_ABNORMALITY, alert.getAlertType());
    }
}

