package com.capstone.community_service.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "community")
public class CommunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "community_id")
    private int communityId;
    @Column(name = "community_name")
    private String communityName;
    @Column(name = "community_head")
    private String communityHead;
    @Column(name = "current_amount")
    private double currentAmount;
    @Column(name = "rule_id")
    private int ruleId;
    @Column(name = "is_public")
    private boolean isPublic;
    @Column(name = "remaining_term_period")
    private int remainingTermPeriod;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "next_contribution_date")
    private LocalDateTime nextContributionDate;
    @Column(name = "is_deleted")
    private boolean isDeleted;
}
