package com.cydeo.dto;


import com.cydeo.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@Data
public class TaskDTO {

    private Long id;
    private ProjectDTO project;
    private UserDTO assignedEmployee;
    private String tskSubject;
    private String taskDetail;
    private Status status;
    private LocalDate assignedDate;

    public TaskDTO(ProjectDTO project, UserDTO assignedEmployee, String tskSubject, String taskDetail, Status status, LocalDate assignedDate) {
        this.project = project;
        this.assignedEmployee = assignedEmployee;
        this.tskSubject = tskSubject;
        this.taskDetail = taskDetail;
        this.status = status;
        this.assignedDate = assignedDate;
        this.id= UUID.randomUUID().getMostSignificantBits();
    }
}
