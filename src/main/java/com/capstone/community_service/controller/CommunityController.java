package com.capstone.community_service.controller;

import com.capstone.community_service.pojo.CommunityAddInputPojo;
import com.capstone.community_service.pojo.CommunityPojo;
import com.capstone.community_service.pojo.CommunityUpdateInputPojo;
import com.capstone.community_service.service.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/communities")
public class CommunityController {
    @Autowired
    CommunityService communityService;

    // Get all communities
    @GetMapping("")
    public ResponseEntity<List<CommunityPojo>> getAllCommunities() {
        List<CommunityPojo> communities = communityService.getAllCommunities();
        return ResponseEntity.ok(communities);
    }

    // Get a specific community by ID
    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityPojo> getACommunity(@PathVariable int communityId) {
        CommunityPojo community = communityService.getACommunity(communityId);
        if (community != null) {
            return ResponseEntity.ok(community);
        }
        return ResponseEntity.notFound().build();
    }

    // Add a new community
    @PostMapping("")
    public ResponseEntity<CommunityPojo> addACommunity(@RequestBody CommunityAddInputPojo newCommunity) {
        CommunityPojo addedCommunity = communityService.addACommunity(newCommunity);
        return ResponseEntity.ok(addedCommunity);
    }

    // Update an existing community
    @PutMapping("")
    public ResponseEntity<CommunityPojo> updateCommunity(@RequestBody CommunityUpdateInputPojo editCommunity) {
        CommunityPojo updatedCommunity = communityService.updateCommunity(editCommunity);
        if (updatedCommunity != null) {
            return ResponseEntity.ok(updatedCommunity);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{communityId}")
    public ResponseEntity<Void> setRuleId(@PathVariable int communityId, @RequestParam int ruleId) {
        communityService.setRuleId(communityId, ruleId);
        return ResponseEntity.ok().body(null);
    }

    // Get all active communities
    @GetMapping("/active")
    public ResponseEntity<List<CommunityPojo>> getActiveCommunities() {
        List<CommunityPojo> activeCommunities = communityService.getActiveCommunities();
        return ResponseEntity.ok(activeCommunities);
    }

    @GetMapping("/deactive")
    public ResponseEntity<List<CommunityPojo>> getDeactivatedCommunities() {
        List<CommunityPojo> activeCommunities = communityService.getDeactivatedCommunities();
        return ResponseEntity.ok(activeCommunities);
    }

    // Get all public communities
    @GetMapping("/public")
    public ResponseEntity<List<CommunityPojo>> getPublicCommunities() {
        List<CommunityPojo> publicCommunities = communityService.getPublicCommunities();
        return ResponseEntity.ok(publicCommunities);
    }

    @GetMapping("/private")
    public ResponseEntity<List<CommunityPojo>> getPrivateCommunities() {
        List<CommunityPojo> publicCommunities = communityService.getPrivateCommunities();
        return ResponseEntity.ok(publicCommunities);
    }

    // Soft delete a community
    @PostMapping("/activate/{communityId}")
    public ResponseEntity<Void> activateCommunity(@PathVariable int communityId) {
        try {
            communityService.activateCommunity(communityId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/deactivate/{communityId}")
    public ResponseEntity<Void> deactivateCommunity(@PathVariable int communityId) {
        try {
            communityService.deactivateCommunity(communityId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{communityId}")
    public ResponseEntity<Void> deleteCommunity(@PathVariable int communityId) {
        try {
            communityService.deleteCommunity(communityId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
