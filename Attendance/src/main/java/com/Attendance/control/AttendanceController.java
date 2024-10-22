package com.Attendance.control;
import com.Attendance.dto.AttendanceDto;
import com.Attendance.modle.AttendanceModel;
import com.Attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;




    @GetMapping
    public List<AttendanceModel> getAttendance(@RequestParam int year, @RequestParam int month) {
        return attendanceService.findAttendanceByMonth(year, month);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AttendanceModel> getAttendanceById(@PathVariable Long id) {
        return attendanceService.getAttendanceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AttendanceModel createAttendance(@RequestBody AttendanceDto attendanceDto) {
        return attendanceService.createAttendance(attendanceDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttendanceModel> updateAttendance(@PathVariable Long id, @RequestBody AttendanceDto attendanceDto) {
        return ResponseEntity.ok(attendanceService.updateAttendance(id, attendanceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}