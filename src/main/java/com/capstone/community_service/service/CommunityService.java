package com.capstone.community_service.service;

import com.capstone.community_service.entity.CommunityEntity;
import com.capstone.community_service.pojo.CommunityPojo;
import com.capstone.community_service.repository.CommunityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CommunityService {
    @Autowired
    CommunityRepository communityRepository;

    private CommunityPojo convertEntityToPojo(CommunityEntity communityEntity) {
        CommunityPojo communityPojo = new CommunityPojo();
        BeanUtils.copyProperties(communityEntity, communityPojo);
        return communityPojo;
    }

    // Get all communities
    public List<CommunityPojo> getAllCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findAll();
        List<CommunityPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            communityPojos.add(convertEntityToPojo(communityEntity));
        }
        return communityPojos;
    }

    // Get a specific community by ID
    public CommunityPojo getACommunity(int communityId) {
        Optional<CommunityEntity> communityEntityOptional = communityRepository.findById(communityId);
        // or throw an exception
        return communityEntityOptional.map(this::convertEntityToPojo).orElse(null);
    }

    // Add a new community
    public CommunityPojo addACommunity(CommunityPojo newCommunityPojo) {
        CommunityEntity newCommunityEntity = new CommunityEntity();
        BeanUtils.copyProperties(newCommunityPojo, newCommunityEntity);
        CommunityEntity savedCommunity = communityRepository.saveAndFlush(newCommunityEntity);
        return convertEntityToPojo(savedCommunity);
    }

    // Update an existing community
    public CommunityPojo updateCommunity(CommunityPojo editCommunityPojo) {
        Optional<CommunityEntity> existingEntityOptional = communityRepository.findById(editCommunityPojo.getCommunityId());
        if (existingEntityOptional.isPresent()) {
            CommunityEntity existingEntity = existingEntityOptional.get();
            BeanUtils.copyProperties(editCommunityPojo, existingEntity);
            CommunityEntity updatedCommunity = communityRepository.save(existingEntity);
            return convertEntityToPojo(updatedCommunity);
        }
        return null;
    }

    // Get all active communities
    public List<CommunityPojo> getActiveCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsActive(true);
        List<CommunityPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            communityPojos.add(convertEntityToPojo(communityEntity));
        }
        return communityPojos;
    }

    // Get all public communities
    public List<CommunityPojo> getPublicCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsPublic(true);
        List<CommunityPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            communityPojos.add(convertEntityToPojo(communityEntity));
        }
        return communityPojos;
    }

    // Delete a community (soft delete)
    public void deleteCommunity(int communityId) {
        Optional<CommunityEntity> communityEntityOptional = communityRepository.findById(communityId);
        if (communityEntityOptional.isPresent()) {
            CommunityEntity communityEntity = communityEntityOptional.get();
            communityEntity.setActive(false);
            communityRepository.save(communityEntity);
        } else {
            throw new RuntimeException("Community not found with ID: " + communityId);
        }
    }
}

