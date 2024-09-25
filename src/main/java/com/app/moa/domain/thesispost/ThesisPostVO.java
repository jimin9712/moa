package com.app.moa.domain.thesispost;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class ThesisPostVO {
    private Long id;
    private String professorMajor; //학교 및 전공
    private String researchField; // 프로젝트 분야
    private String researchProfit; //예상 금액
    private String researchPeriod; //예상 기간
    private String researchDeadline; //모집 마감일
    private String researchStartDate; //예상 시작일
    private String researchSchedule; // 논문 예정 사항
    private String researchRequirement; //모집 요건

    public ThesisPostDTO toDTO() {
        ThesisPostDTO thesisPostDTO = new ThesisPostDTO();
        thesisPostDTO.setId(id);
        thesisPostDTO.setProfessorMajor(professorMajor);
        thesisPostDTO.setResearchField(researchField);
        thesisPostDTO.setResearchProfit(researchProfit);
        thesisPostDTO.setResearchPeriod(researchPeriod);
        thesisPostDTO.setResearchDeadline(researchDeadline);
        thesisPostDTO.setResearchStartDate(researchStartDate);
        thesisPostDTO.setResearchSchedule(researchSchedule);
        thesisPostDTO.setResearchRequirement(researchRequirement);
        return thesisPostDTO;
    }
}