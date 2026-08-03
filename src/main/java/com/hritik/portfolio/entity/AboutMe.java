package com.hritik.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "about_me")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AboutMe extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String summary;

    @Column(name = "career_journey", columnDefinition = "TEXT")
    private String careerJourney;

    @Column(name = "current_focus", columnDefinition = "TEXT")
    private String currentFocus;

    @Column(columnDefinition = "TEXT")
    private String goals;
}
