package com.hritik.portfolio.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Entity
@Table(name = "site_visitors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class SiteVisitor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ip_address", nullable = false, unique = true, updatable = false)
    private String ipAddress;

    @Column(name = "last_user_agent")
    private String lastUserAgent;

    @Column(name = "visit_count", nullable = false)
    private Integer visitCount;

    @Column(name = "last_visit_date")
    private LocalDateTime lastVisitDate;
}