package com.hritik.portfolio.entity;

import com.hritik.portfolio.enums.SkillCategory;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "skills")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Skill extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SkillCategory category;

    @Column(nullable = false)
    private Integer proficiency; // 0 to 100

    @Column(name = "years_of_experience")
    private Double yearsOfExperience;

    @Column(name = "icon_url")
    private String iconUrl;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;
}
