package com.Attendance.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceDto {
    private Long id;
    private Long employeeId;
    private Long departmentId;
    private LocalDate date;
    public enum Status {
        ABSENT,PRESENT,LATE
    }
    @Enumerated(EnumType.STRING)
    private Status status;



}