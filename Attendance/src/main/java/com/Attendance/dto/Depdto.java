
package com.Attendance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Depdto {

    private Long id;
    private String name;
    private String location;
    private String abbreviatedName;

}