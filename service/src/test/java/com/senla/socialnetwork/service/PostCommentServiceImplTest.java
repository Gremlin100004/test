package com.senla.socialnetwork.service;

import com.senla.socialnetwork.dao.CommunityDao;
import com.senla.socialnetwork.dao.LocationDao;
import com.senla.socialnetwork.dao.PostCommentDao;
import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.dao.SchoolDao;
import com.senla.socialnetwork.dao.UniversityDao;
import com.senla.socialnetwork.dao.UserProfileDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Location;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.domain.PostComment;
import com.senla.socialnetwork.domain.School;
import com.senla.socialnetwork.domain.University;
import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.PostCommentDto;
import com.senla.socialnetwork.service.config.CommunityTestData;
import com.senla.socialnetwork.service.config.LocationTestData;
import com.senla.socialnetwork.service.config.PostCommentTestData;
import com.senla.socialnetwork.service.config.PostTestData;
import com.senla.socialnetwork.service.config.SchoolTestData;
import com.senla.socialnetwork.service.config.TestConfig;
import com.senla.socialnetwork.service.config.UniversityTestData;
import com.senla.socialnetwork.service.config.UserProfileTestData;
import com.senla.socialnetwork.service.config.UserTestData;
import com.senla.socialnetwork.service.exception.BusinessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class PostCommentServiceImplTest {
    private static final int FIRST_RESULT = 0;
    private static final int NORMAL_MAX_RESULTS = 10;
    @Autowired
    PostCommentService postCommentService;
    @Autowired
    CommunityDao communityDao;
    @Autowired
    PostDao postDao;
    @Autowired
    PostCommentDao postCommentDao;
    @Autowired
    UserProfileDao userProfileDao;
    @Autowired
    LocationDao locationDao;
    @Autowired
    SchoolDao schoolDao;
    @Autowired
    UniversityDao universityDao;

    @Test
    void PostCommentServiceImpl_getComments() {
        List<PostComment> postComments = PostCommentTestData.getTestPostComments();
        List<PostCommentDto> postCommentsDto = PostCommentTestData.getTestPostCommentsDto();
        Mockito.doReturn(postComments).when(postCommentDao).getAllRecords(FIRST_RESULT, NORMAL_MAX_RESULTS);

        List<PostCommentDto> resultPostCommentsDto = postCommentService.getComments(FIRST_RESULT, NORMAL_MAX_RESULTS);
        Assertions.assertNotNull(resultPostCommentsDto);
        Assertions.assertEquals(PostCommentTestData.getRightNumberPostComments(), resultPostCommentsDto.size());
        Assertions.assertFalse(resultPostCommentsDto.isEmpty());
        Assertions.assertEquals(resultPostCommentsDto, postCommentsDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).getAllRecords(
            FIRST_RESULT, NORMAL_MAX_RESULTS);
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_addComment() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        PostCommentDto postCommentDto = PostCommentTestData.getTestPostCommentDto();
        postCommentDto.setId(null);
        Post post = PostTestData.getTestPost();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(postComment).when(postCommentDao).saveRecord(ArgumentMatchers.any(PostComment.class));
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        PostCommentDto resultPostCommentDto = postCommentService.addComment(postCommentDto);
        Assertions.assertNotNull(resultPostCommentDto);
        Mockito.verify(postCommentDao, Mockito.times(1)).saveRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(postCommentDao, Mockito.never()).findById(PostCommentTestData.getPostCommentId());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(6)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(2)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(2)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(postCommentDao);
        Mockito.reset(postDao);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PostCommentServiceImpl_updateComment() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        PostCommentDto postCommentDto = PostCommentTestData.getTestPostCommentDto();
        Post post = PostTestData.getTestPost();
        Community community = CommunityTestData.getTestCommunity();
        UserProfile userProfile = UserProfileTestData.getTestUserProfile();
        Location location = LocationTestData.getTestLocation();
        School school = SchoolTestData.getTestSchool();
        University university = UniversityTestData.getTestUniversity();
        Mockito.doReturn(postComment).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());
        Mockito.doReturn(post).when(postDao).findById(PostTestData.getPostId());
        Mockito.doReturn(community).when(communityDao).findById(CommunityTestData.getCommunityId());
        Mockito.doReturn(userProfile).when(userProfileDao).findById(UserProfileTestData.getUserProfileId());
        Mockito.doReturn(location).when(locationDao).findById(LocationTestData.getLocationId());
        Mockito.doReturn(school).when(schoolDao).findById(SchoolTestData.getSchoolId());
        Mockito.doReturn(university).when(universityDao).findById(UniversityTestData.getUniversityId());

        Assertions.assertDoesNotThrow(() -> postCommentService.updateComment(postCommentDto));
        Mockito.verify(postCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(PostCommentTestData.getPostCommentId());
        Mockito.verify(postDao, Mockito.times(1)).findById(PostTestData.getPostId());
        Mockito.verify(communityDao, Mockito.times(1)).findById(CommunityTestData.getCommunityId());
        Mockito.verify(userProfileDao, Mockito.times(2)).findById(UserProfileTestData.getUserProfileId());
        Mockito.verify(locationDao, Mockito.times(6)).findById(LocationTestData.getLocationId());
        Mockito.verify(schoolDao, Mockito.times(2)).findById(SchoolTestData.getSchoolId());
        Mockito.verify(universityDao, Mockito.times(2)).findById(UniversityTestData.getUniversityId());
        Mockito.reset(postCommentDao);
        Mockito.reset(postDao);
        Mockito.reset(communityDao);
        Mockito.reset(userProfileDao);
        Mockito.reset(locationDao);
        Mockito.reset(schoolDao);
        Mockito.reset(universityDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.deleteCommentByUser(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.times(1)).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser_postCommentDao_findByIdAndEmail_nullObject() {
        Mockito.doReturn(null).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteCommentByUser(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteCommentByUser_postCommentDao_findByIdAndEmail_deletedObject() {
        PostComment postComment = PostCommentTestData.getTestPostComment();
        postComment.setDeleted(true);
        Mockito.doReturn(postComment).when(postCommentDao).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteCommentByUser(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findByIdAndEmail(
            UserTestData.getEmail(), PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).updateRecord(
            ArgumentMatchers.any(PostComment.class));
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteComment() {
        PostComment comment = PostCommentTestData.getTestPostComment();
        Mockito.doReturn(comment).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());

        Assertions.assertDoesNotThrow(() -> postCommentService.deleteComment(PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).deleteRecord(PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(PostCommentTestData.getPostCommentId());
        Mockito.reset(postCommentDao);
    }

    @Test
    void PostCommentServiceImpl_deleteComment_postCommentDao_findById_nullObject() {
        Mockito.doReturn(null).when(postCommentDao).findById(PostCommentTestData.getPostCommentId());

        Assertions.assertThrows(BusinessException.class, () -> postCommentService.deleteComment(
            PostCommentTestData.getPostCommentId()));
        Mockito.verify(postCommentDao, Mockito.times(1)).findById(
            PostCommentTestData.getPostCommentId());
        Mockito.verify(postCommentDao, Mockito.never()).deleteRecord(PostCommentTestData.getPostCommentId());
        Mockito.reset(postCommentDao);
    }

}