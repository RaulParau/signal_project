package Week5_test;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.PatientRecord;
import com.data_management.PatientWebsocketClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTesting {

    private DataStorage storage;
    private PatientWebsocketClient client;
    private AlertGenerator alertGenerator;


    @BeforeEach
    public void setUp() throws URISyntaxException {
        storage = DataStorage.getInstance();
        storage.clear(); // Ensure clean state
        client = new PatientWebsocketClient(new URI("ws://localhost:1011"), storage);
        alertGenerator = new AlertGenerator(storage);
    }

    @Test
    public void testPipelineWithNormalData(){
        String message = "1, 1744, SystolicPressure, 120.0";
        client.onMessage(message);

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);
        assertEquals(1, records.size());

        PatientRecord record = records.get(0);
        assertEquals(1, record.getPatientId());
        assertEquals("SystolicPressure", record.getRecordType());
        assertEquals(120.0, record.getMeasurementValue());

        alertGenerator.evaluateData(storage.getPatient(1));
        assertEquals(0, alertGenerator.getTriggeredAlerts().size());
    }

    @Test
    public void testPipelineWithCriticalData(){
        String message = "1, 1744, SystolicPressure, 80.0";
        client.onMessage(message);

        List<PatientRecord> records = storage.getRecords(1, 0, Long.MAX_VALUE);
        assertEquals(1, records.size());

        PatientRecord record = records.get(0);
        assertEquals(1, record.getPatientId());
        assertEquals("SystolicPressure", record.getRecordType());
        assertEquals(80.0, record.getMeasurementValue());

        alertGenerator.evaluateData(storage.getPatient(1));
        assertEquals(1, alertGenerator.getTriggeredAlerts().size());
    }
}
