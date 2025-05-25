package Week5_test;

import com.data_management.DataStorage;
import com.data_management.FileDataReader;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.concurrent.ConcurrentHashMap;
import static org.junit.jupiter.api.Assertions.*;

public class FileDataReaderTest {

    private Path tempFile;
    private DataStorage dataStorage;

    @BeforeEach
    public void setup() throws IOException {

        tempFile = Files.createTempFile("patient_data", ".csv");
        String content = "patientId: 1, timestamp: 1000, label: SystolicPressure, value: 75.5\n";
        Files.write(tempFile, content.getBytes());

        dataStorage = DataStorage.getInstance();
        dataStorage.clear();
    }

    @AfterEach
    public void cleanup() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testReadDataStoresCorrectly() throws IOException {
        ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();
        fileMap.put("SystolicPressure", tempFile.toString());

        FileDataReader reader = new FileDataReader(fileMap);
        reader.readData(dataStorage);

        // Assert data was stored
        Patient patient = dataStorage.getPatient(1);
        assertNotNull(patient, "Patient should exist after reading file");

        var records = patient.getRecords(0, Long.MAX_VALUE);
        assertEquals(1, records.size(), "There should be one record");

        PatientRecord record = records.get(0);
        assertEquals("SystolicPressure", record.getRecordType());
        assertEquals(75.5, record.getMeasurementValue());
        assertEquals(1000L, record.getTimestamp());
    }
}

