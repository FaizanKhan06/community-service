package com.capstone.community_service.pojo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityWithRulesPojo {
    private int communityId;
    private String communityName;
    private String communityHead;
    private double currentAmount;
    private RulesPojo rule;
    private boolean isPublic;
    private int remainingTermPeriod;
    private LocalDateTime nextContributionDate;
    private boolean isActive;
    private boolean isDeleted;
}