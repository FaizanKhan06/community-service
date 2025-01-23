package com.capstone.community_service.controller;

import com.capstone.community_service.pojo.CommunityPojo;
import com.capstone.community_service.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommunityController {
    @Autowired
    CommunityService communityService;

    //Get all communities
    @GetMapping("/communities")
    public ResponseEntity<List<CommunityPojo>> getAllCommunities() {
        List<CommunityPojo> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    // Get a specific community by ID
    @GetMapping("/communities/{communityId}")
    public ResponseEntity<CommunityPojo> getACommunity(@PathVariable int communityId) {
        CommunityPojo community = communityService.getACommunity(communityId);
        if (community != null) {
            return ResponseEntity.ok(community);
        }
        return ResponseEntity.notFound().build();
    }

    // Add a new community
    @PostMapping("/communities")
    public ResponseEntity<CommunityPojo> addACommunity(@RequestBody CommunityPojo newCommunity) {
        CommunityPojo addedCommunity = communityService.addACommunity(newCommunity);
        return ResponseEntity.ok(addedCommunity);
    }

    // Update an existing community
    @PutMapping("/communities/{communityId}")
    public ResponseEntity<CommunityPojo> updateCommunity(@PathVariable int communityId,@RequestBody CommunityPojo editCommunity) {
        // Ensure the ID matches
        editCommunity.setCommunityId(communityId);
        CommunityPojo updatedCommunity = communityService.updateCommunity(editCommunity);
        if (updatedCommunity != null) {
            return ResponseEntity.ok(updatedCommunity);
        }
        return ResponseEntity.notFound().build();
    }

    // Get all active communities
    @GetMapping("/communities/active")
    public ResponseEntity<List<CommunityPojo>> getActiveCommunities() {
        List<CommunityPojo> activeCommunities = communityService.getActiveCommunities();
        return ResponseEntity.ok(activeCommunities);
    }

    // Get all public communities
    @GetMapping("/communities/public")
    public ResponseEntity<List<CommunityPojo>> getPublicCommunities() {
        List<CommunityPojo> publicCommunities = communityService.getPublicCommunities();
        return ResponseEntity.ok(publicCommunities);
    }

    // Soft delete a community
    @DeleteMapping("communities/{communityId}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable int communityId) {
        try {
            communityService.deleteCommunity(communityId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}



