package com.Attendance.repository;

import com.Attendance.modle.AttendanceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceModel, Long> {
    List<AttendanceModel> findByEmployeeIdAndDateBetween(Long employeeId, LocalDate startDate, LocalDate endDate);
    Optional<AttendanceModel> findByEmployeeIdAndDate(Long employeeId, LocalDate date);
    List<AttendanceModel> findByEmployeeId(Long employeeId);
}