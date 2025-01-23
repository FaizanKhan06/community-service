package com.capstone.community_service.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class CommunityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="community_id")
    private int communityId;
    @Column(name="community_name")
    private String communityName;
    @Column(name="community_head")
    private String communityHead;
    @Column(name="current_amount")
    private double currentAmount;
    @Column(name="rule_id")
    private int ruleId;
    @Column(name="is_public")
    private boolean isPublic;
    @Column(name="is_active")
    private boolean isActive;

}
