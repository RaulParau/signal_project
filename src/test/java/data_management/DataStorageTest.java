package data_management;

import static org.junit.jupiter.api.Assertions.*;

import com.data_management.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.util.List;

class DataStorageTest {
    @Test
    void testAddAndGetRecords() {
        // TODO Perhaps you can implement a mock data reader to mock the test data?
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    void testGetAllPatient(){
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(1, 99, "Saturation", 1000L);
        storage.addPatientData(2, 120, "SystolicPressure", 2000L);

        List <Patient> patients = storage.getAllPatients();
        assertEquals(2, patients.size());
    }

    @Test
    void testGetAllRecordsInTimeframe(){
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(1, 99, "Saturation", 1000L);
        storage.addPatientData(1, 120, "SystolicPressure", 4000L);

        List<PatientRecord> records = storage.getRecords(1, 2000L, 5000L);

        assertEquals(1, records.size());
        assertEquals(120, records.get(0).getMeasurementValue());

    }

    @Test
    void testReturnsNoRecordForUnknownPatient(){
        DataStorage storage = DataStorage.getInstance();
        List<PatientRecord> records =  storage.getRecords(90, 0, 100L);
        assertTrue(records.isEmpty());
    }

    @Test
    void testGetAllPatientsUniqueness() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(6, 99.0, "Saturation", 1000L);
        storage.addPatientData(6, 101.0, "Saturation", 2000L);

        List<Patient> patients = storage.getAllPatients();
        long count = patients.stream().filter(p -> p.getPatientId() == 6).count();
        assertEquals(1, count); // Should not duplicate patient entries
    }

    @Test
    void testDifferentRecordTypes() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(5, 120.0, "SystolicPressure", 1000L);
        storage.addPatientData(5, 80.0, "DiastolicPressure", 2000L);

        List<PatientRecord> records = storage.getRecords(5, 0, 3000L);
        assertEquals(2, records.size());
        assertEquals("SystolicPressure", records.get(0).getRecordType());
        assertEquals("DiastolicPressure", records.get(1).getRecordType());
    }

    @Test
    void testRecordsOutsideTimeRange() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(4, 98.0, "Saturation", 5000L);

        List<PatientRecord> records = storage.getRecords(4, 1000L, 4000L);
        assertTrue(records.isEmpty());
    }

    @Test
    void testAddMultipleRecordsToSamePatient() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(3, 80.0, "HeartRate", 1000L);
        storage.addPatientData(3, 85.0, "HeartRate", 2000L);

        List<PatientRecord> records = storage.getRecords(3, 0, 3000L);
        assertEquals(2, records.size());
    }


}
