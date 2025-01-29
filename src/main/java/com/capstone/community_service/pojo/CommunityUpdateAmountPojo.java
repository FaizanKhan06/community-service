package com.capstone.community_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommunityUpdateAmountPojo {

    private int communityId;
    private String email;
    private double amount;

}
