package com.capstone.community_service.repository;

import com.capstone.community_service.entity.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {
    Optional<CommunityEntity> findByCommunityHead(String communityHead);

    List<CommunityEntity> findByIsActive(boolean isActive);

    List<CommunityEntity> findByIsPublic(boolean isPublic);
}
