package data_management;

import com.alerts.Alert;
import com.alerts.SelfTriggeredAlert;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static com.alerts.AlertType.MANUAL_TRIGGER;

public class SelfTriggeredAlertTest {
    private SelfTriggeredAlert alertCondition;

    @BeforeEach
    public void setUp(){
        alertCondition = new SelfTriggeredAlert();
    }

    @Test
    public void testForSelfTriggeredAlert(){
        List<PatientRecord> records = new ArrayList<>();

        records.add(new PatientRecord(1, 1.0, "Button was pressed", 1));

        Alert alert = alertCondition.checkCondition(records);

        Assertions.assertNotNull(alert);
        Assertions.assertEquals(MANUAL_TRIGGER, alert.getAlertType());
        Assertions.assertEquals("Manual Triggered Alert: Nurse/Patient button pressed", alert.getCondition());
    }
}
