package com.gts.create_document.dto;


import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vocation {

    private String employeeName;
    private String employeePosition;
    private Date startDate;
    private Date endDate;
    private String bossName;
    private String bossPosition;
}
