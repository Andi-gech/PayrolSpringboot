package com.Attendance.control;
import com.Attendance.dto.AttendanceDto;
import com.Attendance.modle.AttendanceModel;
import com.Attendance.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController

@RequestMapping("/api/attendance")
public class AttendanceController {
    @Autowired
    private AttendanceService attendanceService;




    @GetMapping("/{year}/{month}")
    public List<AttendanceModel> getAttendance(@PathVariable int year, @PathVariable int month) {
        return attendanceService.findAttendanceByMonth(year, month);
    }
    @GetMapping("{empid}/{year}/{month}")
    public List<AttendanceModel> getAttendance(@PathVariable int empid,@PathVariable int year, @PathVariable int month) {
        return attendanceService.findEachAttendanceByMonth(empid,year,month);
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

    @PostMapping("/createOrUpdate") 
    public ResponseEntity<AttendanceModel> createOrUpdateAttendance(@RequestBody AttendanceDto attendanceDto) {
        AttendanceModel attendance = attendanceService.createOrUpdateAttendance( attendanceDto);
        return ResponseEntity.ok(attendance);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }
}