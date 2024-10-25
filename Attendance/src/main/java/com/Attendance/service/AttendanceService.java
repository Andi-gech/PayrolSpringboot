package com.Attendance.service;
import org.apache.poi.ss.usermodel.*;
import com.Attendance.dto.AttendanceDto;
import com.Attendance.dto.Depdto;
import com.Attendance.dto.EmployeeDto;
import com.Attendance.modle.AttendanceModel;
import com.Attendance.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private AttendanceRepository attendanceRepository;



    public List<AttendanceModel> getAllAttendanceRecords() {
        return attendanceRepository.findAll();
    }

    public Optional<AttendanceModel> getAttendanceById(Long id) {
        return attendanceRepository.findById(id);
    }

    public AttendanceModel createAttendance(AttendanceDto attendanceDto) {
        try {
            // Create an empty AttendanceModel object
            AttendanceModel attendance = new AttendanceModel();

            // URLs to fetch employee and department details
            String employeeUrl = "http://192.168.0.103:5555/api/v1/employee/" + attendanceDto.getEmployeeId();
            String departmentUrl = "http://192.168.0.103:4040/api/departments/" + attendanceDto.getDepartmentId();

            // Fetch employee and department details
            EmployeeDto employee = restTemplate.getForObject(employeeUrl, EmployeeDto. class);
            Depdto department = restTemplate.getForObject(departmentUrl, Depdto.class);

            // Check if both employee and department exist
            if (employee == null) {
                throw new RuntimeException("Employee not found with ID: " + attendanceDto.getEmployeeId());
            }

            if (department == null) {
                throw new RuntimeException("Department not found with ID: " + attendanceDto.getDepartmentId());
            }

            // Set the necessary fields for attendance
            attendance.setEmployeeId(attendanceDto.getEmployeeId());
            attendance.setStatus(attendanceDto.getStatus().name());
            attendance.setDate(attendanceDto.getDate());
            attendance.setDepartmentId(attendanceDto.getDepartmentId());

            // Save and return the attendance record
            return attendanceRepository.save(attendance);

        } catch (RestClientException e) {
            // Handle HTTP-related exceptions (e.g., server unreachable, invalid URL, etc.)
            throw new RuntimeException("Error fetching employee or department details: " + e.getMessage(), e);

        } catch (Exception e) {
            // Handle general exceptions
            throw new RuntimeException("An error occurred while creating attendance: " + e.getMessage(), e);
        }
    }
    public List<AttendanceModel> findAttendanceByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return attendanceRepository.findAll().stream()
                .filter(a -> !a.getDate().isBefore(startDate) && !a.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }
    public List<AttendanceModel> findEachAttendanceByMonth(int empid,int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        return attendanceRepository.findByEmployeeId(Long.valueOf(empid)).stream()
                .filter(a -> !a.getDate().isBefore(startDate) && !a.getDate().isAfter(endDate))
                .collect(Collectors.toList());
    }

    public AttendanceModel createOrUpdateAttendance(AttendanceDto attendanceDto) {
        // Try to find the attendance record with the same employeeId and date
        AttendanceModel attendance = attendanceRepository.findByEmployeeIdAndDate(
                        attendanceDto.getEmployeeId(), attendanceDto.getDate())
                .orElse(new AttendanceModel());  // If not found, create a new instance

        // Set the fields based on the AttendanceDto
        attendance.setEmployeeId(attendanceDto.getEmployeeId());
        attendance.setDepartmentId(attendanceDto.getDepartmentId());  // Corrected department field
        attendance.setStatus(attendanceDto.getStatus().name());
        attendance.setDate(attendanceDto.getDate());

        // Save and return the updated or newly created attendance record
        return attendanceRepository.save(attendance);
    }


    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}