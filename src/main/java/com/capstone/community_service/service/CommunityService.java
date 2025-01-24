package com.capstone.community_service.service;

import com.capstone.community_service.entity.CommunityEntity;
import com.capstone.community_service.pojo.CommunityAddInputPojo;
import com.capstone.community_service.pojo.CommunityPojo;
import com.capstone.community_service.pojo.CommunityUpdateInputPojo;
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

    // Get all active communities
    public List<CommunityPojo> getActiveCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsActive(true);
        List<CommunityPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            communityPojos.add(convertEntityToPojo(communityEntity));
        }
        return communityPojos;
    }

    // Get all active communities
    public List<CommunityPojo> getDeactivatedCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsActive(false);
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

    // Get all public communities
    public List<CommunityPojo> getPrivateCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsPublic(false);
        List<CommunityPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            communityPojos.add(convertEntityToPojo(communityEntity));
        }
        return communityPojos;
    }

    // Add a new community
    public CommunityPojo addACommunity(CommunityAddInputPojo newCommunityPojo) {
        CommunityEntity existingCommunityEntity = communityRepository
                .findByCommunityHead(newCommunityPojo.getCommunityHead()).orElse(null);
        if (existingCommunityEntity == null) {
            CommunityEntity newCommunityEntity = new CommunityEntity();
            newCommunityEntity.setCommunityName(newCommunityPojo.getCommunityName());
            newCommunityEntity.setCommunityHead(newCommunityPojo.getCommunityHead());
            newCommunityEntity.setPublic(newCommunityPojo.isPublic());
            newCommunityEntity.setRuleId(0);
            newCommunityEntity.setActive(false);
            newCommunityEntity.setCurrentAmount(0);
            newCommunityEntity.setDeleted(false);
            CommunityEntity savedCommunity = communityRepository.saveAndFlush(newCommunityEntity);
            return convertEntityToPojo(savedCommunity);
        }
        return null;
    }

    // Update an existing community
    public CommunityPojo updateCommunity(CommunityUpdateInputPojo editCommunityPojo) {
        CommunityEntity existingCommunityEntity = communityRepository.findById(editCommunityPojo.getCommunityId())
                .orElse(null);
        if (existingCommunityEntity != null) {
            existingCommunityEntity.setCommunityName(editCommunityPojo.getCommunityName());
            existingCommunityEntity.setPublic(editCommunityPojo.isPublic());
            CommunityPojo communityPojo = convertEntityToPojo(existingCommunityEntity);
            communityRepository.save(existingCommunityEntity);
            return communityPojo;
        }
        return null;
    }

    public void setRuleId(int communityId, int ruleId) {
        CommunityEntity communityEntity = communityRepository.findById(communityId).orElse(null);
        if (communityEntity != null) {
            communityEntity.setRuleId(ruleId);
            communityRepository.save(communityEntity);
        }
    }

    public void activateCommunity(int communityId) {
        Optional<CommunityEntity> communityEntityOptional = communityRepository.findById(communityId);
        if (communityEntityOptional.isPresent()) {
            CommunityEntity communityEntity = communityEntityOptional.get();
            communityEntity.setActive(true);
            communityRepository.save(communityEntity);
        } else {
            throw new RuntimeException("Community not found with ID: " + communityId);
        }
    }

    public void deactivateCommunity(int communityId) {
        Optional<CommunityEntity> communityEntityOptional = communityRepository.findById(communityId);
        if (communityEntityOptional.isPresent()) {
            CommunityEntity communityEntity = communityEntityOptional.get();
            communityEntity.setActive(false);
            communityEntity.setDeleted(true);
            communityRepository.save(communityEntity);
        } else {
            throw new RuntimeException("Community not found with ID: " + communityId);
        }
    }

    // Delete a community (soft delete)
    public void deleteCommunity(int communityId) {
        Optional<CommunityEntity> communityEntityOptional = communityRepository.findById(communityId);
        if (communityEntityOptional.isPresent()) {
            CommunityEntity communityEntity = communityEntityOptional.get();
            communityEntity.setActive(false);
            communityEntity.setDeleted(true);
            communityRepository.save(communityEntity);
        } else {
            throw new RuntimeException("Community not found with ID: " + communityId);
        }
    }
}
