package com.app.moa.domain.project_post;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProjectPostDTO {
    private Long id;
    private String studentMajor;
    private String projectField;
    private String projectProfit;
    private String projectPeriod;
    private String projectDeadline;
    private String projectStartDate;
    private String projectSchedule;
    private String projectRequirement;

    public ProjectPostVO toVO() {
        return new ProjectPostVO(id, studentMajor, projectField, projectProfit, projectPeriod, projectDeadline, projectStartDate, projectSchedule, projectRequirement);
    }
}
