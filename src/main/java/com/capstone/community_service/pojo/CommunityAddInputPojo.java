package com.capstone.community_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CommunityAddInputPojo {

    private String communityName;
    private String communityHead;
    private boolean isPublic;
}
