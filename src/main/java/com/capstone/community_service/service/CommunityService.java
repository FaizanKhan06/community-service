package com.capstone.community_service.service;

import com.capstone.community_service.entity.CommunityEntity;
import com.capstone.community_service.pojo.CommunityAddInputPojo;
import com.capstone.community_service.pojo.CommunityPojo;
import com.capstone.community_service.pojo.CommunityUpdateAmountPojo;
import com.capstone.community_service.pojo.CommunityUpdateInputPojo;
import com.capstone.community_service.pojo.CommunityWithRulesPojo;
import com.capstone.community_service.pojo.RulesPojo;
import com.capstone.community_service.repository.CommunityRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
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
    public List<CommunityWithRulesPojo> getPublicCommunitiesToShowUser() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsPublicAndRuleIdNotAndIsActiveAndIsDeleted(
                true, 0,
                false, false);
        List<CommunityWithRulesPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            CommunityWithRulesPojo communityWithRulesPojo = new CommunityWithRulesPojo();
            BeanUtils.copyProperties(communityEntity, communityWithRulesPojo);
            RestClient restClient = RestClient.create();
            RulesPojo response = restClient.get()
                    .uri("http://localhost:5004/api/rules/" + communityEntity.getRuleId())
                    .retrieve().body(RulesPojo.class);
            communityWithRulesPojo.setRule(response);
            communityPojos.add(communityWithRulesPojo);
        }
        return communityPojos;
    }

    // Get a specific community by ID
    public CommunityWithRulesPojo getACommunity(int communityId) {
        CommunityEntity communityEntity = communityRepository.findById(communityId).orElse(null);
        if (communityEntity != null) {
            CommunityWithRulesPojo communityWithRulesPojo = new CommunityWithRulesPojo();
            BeanUtils.copyProperties(communityEntity, communityWithRulesPojo);
            if (communityEntity.getRuleId() != 0) {
                RestClient restClient = RestClient.create();
                RulesPojo response = restClient.get()
                        .uri("http://localhost:5004/api/rules/" + communityEntity.getRuleId())
                        .retrieve().body(RulesPojo.class);
                communityWithRulesPojo.setRule(response);
                return communityWithRulesPojo;
            }
            return communityWithRulesPojo;
        }
        return null;
    }

    // Get a specific community by ID
    public CommunityWithRulesPojo getACommunityByCommunityHead(String communityHead) {
        List<CommunityEntity> communityEntities = communityRepository.findByCommunityHeadAndIsDeleted(communityHead,
                false);
        if (!communityEntities.isEmpty()) {
            CommunityEntity communityEntity = communityEntities.get(0);
            CommunityWithRulesPojo communityWithRulesPojo = new CommunityWithRulesPojo();
            BeanUtils.copyProperties(communityEntity, communityWithRulesPojo);
            if (communityEntity.getRuleId() != 0) {
                RestClient restClient = RestClient.create();
                RulesPojo response = restClient.get()
                        .uri("http://localhost:5004/api/rules/" + communityEntity.getRuleId())
                        .retrieve().body(RulesPojo.class);
                communityWithRulesPojo.setRule(response);
                return communityWithRulesPojo;
            }
            return communityWithRulesPojo;
        }
        return null;
    }

    public List<CommunityWithRulesPojo> getDeactiveCommunitiesByCommunityHead(String communityHead) {
        List<CommunityEntity> communityEntities = communityRepository.findByCommunityHeadAndIsDeleted(communityHead,
                true);
        List<CommunityWithRulesPojo> communityWithRulesPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            CommunityWithRulesPojo communityWithRulesPojo = new CommunityWithRulesPojo();
            BeanUtils.copyProperties(communityEntity, communityWithRulesPojo);
            RestClient restClient = RestClient.create();
            RulesPojo response = restClient.get()
                    .uri("http://localhost:5004/api/rules/" + communityEntity.getRuleId())
                    .retrieve().body(RulesPojo.class);
            communityWithRulesPojo.setRule(response);
            communityWithRulesPojos.add(communityWithRulesPojo);
        }
        return communityWithRulesPojos;
    }

    // Get all active communities
    public List<CommunityWithRulesPojo> getActiveCommunities() {
        List<CommunityEntity> communityEntities = communityRepository.findByIsActive(true);
        List<CommunityWithRulesPojo> communityPojos = new ArrayList<>();
        for (CommunityEntity communityEntity : communityEntities) {
            CommunityWithRulesPojo communityWithRulesPojo = new CommunityWithRulesPojo();
            BeanUtils.copyProperties(communityEntity, communityWithRulesPojo);
            if (communityEntity.getRuleId() != 0) {
                RestClient restClient = RestClient.create();
                RulesPojo response = restClient.get()
                        .uri("http://localhost:5004/api/rules/" + communityEntity.getRuleId())
                        .retrieve().body(RulesPojo.class);
                communityWithRulesPojo.setRule(response);
            }
            communityPojos.add(communityWithRulesPojo);
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
        List<CommunityEntity> existingCommunityEntities = communityRepository
                .findByCommunityHeadAndIsDeleted(newCommunityPojo.getCommunityHead(), false);
        if (existingCommunityEntities.isEmpty()) {
            CommunityEntity newCommunityEntity = new CommunityEntity();
            newCommunityEntity.setCommunityName(newCommunityPojo.getCommunityName());
            newCommunityEntity.setCommunityHead(newCommunityPojo.getCommunityHead());
            newCommunityEntity.setPublic(newCommunityPojo.isPublic());
            newCommunityEntity.setRuleId(0);
            newCommunityEntity.setActive(false);
            newCommunityEntity.setCurrentAmount(0);
            newCommunityEntity.setNextContributionDate(LocalDateTime.now());
            newCommunityEntity.setRemainingTermPeriod(0);
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

    public CommunityPojo updateAmountCommunity(CommunityUpdateAmountPojo editCommunityPojo) {
        CommunityEntity existingCommunityEntity = communityRepository.findById(editCommunityPojo.getCommunityId())
                .orElse(null);
        if (existingCommunityEntity != null) {
            existingCommunityEntity.setCurrentAmount(
                    Math.round((existingCommunityEntity.getCurrentAmount() + editCommunityPojo.getAmount()) * 100.0)
                            / 100.0);
            CommunityPojo communityPojo = convertEntityToPojo(existingCommunityEntity);
            communityRepository.save(existingCommunityEntity);
            return communityPojo;
        }
        return null;
    }

    public void updateNextContributionDate(int communityId, LocalDateTime nextContributionDate) {
        CommunityEntity communityEntity = communityRepository.findById(communityId).orElse(null);
        if (communityEntity != null) {
            int remainingTermPeriod = communityEntity.getRemainingTermPeriod() - 1;
            if (remainingTermPeriod <= 0) {
                communityEntity.setActive(false);
                communityEntity.setDeleted(true);
                communityEntity.setRemainingTermPeriod(0);
            } else {
                communityEntity.setRemainingTermPeriod(communityEntity.getRemainingTermPeriod() - 1);
                communityEntity.setNextContributionDate(nextContributionDate);
            }
            communityRepository.save(communityEntity);
        }
    }

    public void setRuleId(int communityId, int ruleId, int remainingTermPeriod, LocalDateTime nextContributionDate) {
        CommunityEntity communityEntity = communityRepository.findById(communityId).orElse(null);
        if (communityEntity != null) {
            communityEntity.setRuleId(ruleId);
            communityEntity.setRemainingTermPeriod(remainingTermPeriod);
            communityEntity.setNextContributionDate(nextContributionDate);
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
