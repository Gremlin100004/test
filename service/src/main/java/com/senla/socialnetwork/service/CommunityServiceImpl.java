package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.CommunityDto;
import com.senla.socialnetwork.service.exception.BusinessException;
import com.senla.socialnetwork.service.util.CommunityMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {
    private static final int FIRST_RESULT = 0;
    private static final int MAX_RESULTS = 0;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    //Todo maybe do it with pagination
    @Override
    @Transactional
    public List<CommunityDto> getAllCommunities() {
        log.debug("[getOwnCommunities]");
        return CommunityMapper.getCommunityDto(communityDao.getAllRecords());
    }

    @Override
    @Transactional
    public List<CommunityDto> getCommunities(int firstResult, int maxResults) {
        log.debug("[getOwnCommunities]");
        log.trace("[firstResult: {}, maxResults: {}]", firstResult, maxResults);
        return CommunityMapper.getCommunityDto(communityDao.getCommunities(firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getOwnCommunities(String email, int firstResult, int maxResults) {
        log.debug("[getOwnCommunities]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return CommunityMapper.getCommunityDto(communityDao.getOwnCommunitiesByEmail(email, firstResult, maxResults));
    }

    @Override
    @Transactional
    public List<CommunityDto> getSubscribedCommunities(String email, int firstResult, int maxResults) {
        log.debug("[getSubscribedCommunities]");
        log.trace("[email: {}, firstResult: {}, maxResults: {}]", email, firstResult, maxResults);
        return CommunityMapper.getCommunityDto(
            communityDao.getSubscribedCommunitiesByEmail(email, firstResult, maxResults));
    }
    //Todo filtered communities by type, by create date

    @Override
    @Transactional
    public void subscribeToCommunity(String email, Long communityId) {
        log.debug("[subscribeToCommunity]");
        log.debug("[email: {}, communityId: {}]", email, communityId);
        Community community = communityDao.findByIdAndEmail(email, communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.isDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        List<UserProfile> userProfiles = userProfileDao.getCommunityUsers(communityId);
        userProfiles.add(userProfileDao.findByEmail(email));
        community.setSubscribedUsers(userProfiles);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public void unsubscribeFromCommunity(String email, Long communityId) {
        log.debug("[subscribeToCommunity]");
        log.debug("[email: {}, communityId: {}]", email, communityId);
        Community community = communityDao.findByIdAndEmail(email, communityId);
        if (community == null) {
            throw new BusinessException("Error, there is no such community");
        } else if (community.isDeleted()) {
            throw new BusinessException("Error, the community has already been deleted");
        }
        List<UserProfile> userProfiles = userProfileDao.getCommunityUsers(communityId);
        if (userProfiles.isEmpty()) {
            throw new BusinessException("Error, this user is not subscribed to the community");
        }
        userProfiles.remove(userProfileDao.findByEmail(email));
        community.setSubscribedUsers(userProfiles);
        communityDao.updateRecord(community);
    }

    @Override
    @Transactional
    public CommunityDto addCommunity(CommunityDto communityDto) {
        log.debug("[addCommunity]");
        log.debug("[CommunityDto: {}]", communityDto);
        if (communityDto == null) {
            throw new BusinessException("Error, null community");
        }
        return CommunityMapper.getCommunityDto(communityDao.saveRecord(
            CommunityMapper.getCommunity(
                communityDto, communityDao, userProfileDao, locationDao, schoolDao, universityDao)));
    }

    @Override
    @Transactional
    public void updateCommunity(CommunityDto communityDto) {
        log.debug("[updateCommunity]");
        log.debug("[CommunityDto: {}]", communityDto);
        if (communityDto == null) {
            throw new BusinessException("Error, null community");
        }
        communityDao.updateRecord(CommunityMapper.getCommunity(
            communityDto, communityDao, userProfileDao, locationDao, schoolDao, universityDao));
    }

    @Override
    @Transactional
    public void deleteCommunityByUser(String email, Long messageId) {
        log.debug("[deleteMessageByUser]");
        log.debug("[email: {}, messageId: {}]", email, messageId);
        Community community = communityDao.findByIdAndEmail(email, messageId);
        if (community == null) {
            throw new BusinessException("Error, there is no such message");
        } else if (community.isDeleted()) {
            throw new BusinessException("Error, the message has already been deleted");
        }
        List<Post> posts = postDao.getByCommunityId(community.getId(), FIRST_RESULT, MAX_RESULTS);
        posts.forEach(post -> post.setDeleted(true));
        community.setDeleted(true);
        community.setSubscribedUsers(new ArrayList<>());
        community.setPosts(posts);
        communityDao.updateRecord(community);
    }

}
