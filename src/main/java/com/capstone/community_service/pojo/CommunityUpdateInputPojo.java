package com.capstone.community_service.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityUpdateInputPojo {

    private int communityId;
    private String communityName;
    private boolean isPublic;

}
