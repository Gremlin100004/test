package com.senla.socialnetwork.service.config;

import com.senla.socialnetwork.domain.UserProfile;
import com.senla.socialnetwork.dto.UserProfileDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserProfileTestData {
    private static final Long USER_PROFILE_ID = 1L;
    private static final Long FRIEND_ID = 3L;
    private static final Long USER_PROFILE_OTHER_ID = 2L;
    private static final Long FRIEND_OTHER_ID = 4L;
    private static final Date USER_PROFILE_CREATION_DATE = new Date();

    public static Long getUserProfileId() {
        return USER_PROFILE_ID;
    }

    public static Long getUserProfileOtherId() {
        return USER_PROFILE_OTHER_ID;
    }

    public static Long getFriendId() {
        return FRIEND_ID;
    }

    public static Long getFriendOtherId() {
        return FRIEND_OTHER_ID;
    }

    public static UserProfile getTestUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(USER_PROFILE_ID);
        userProfile.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        userProfile.setLocation(LocationTestData.getTestLocation());
        userProfile.setSchool(SchoolTestData.getTestSchool());
        userProfile.setUniversity(UniversityTestData.getTestUniversity());
        return userProfile;
    }

    public static UserProfileDto getTestUserProfileDto() {
        UserProfileDto userProfileDto = new UserProfileDto();
        userProfileDto.setId(USER_PROFILE_ID);
        userProfileDto.setRegistrationDate(USER_PROFILE_CREATION_DATE);
        userProfileDto.setLocation(LocationTestData.getTestLocationDto());
        userProfileDto.setSchool(SchoolTestData.getTestSchoolDto());
        userProfileDto.setUniversity(UniversityTestData.getTestUniversityDto());
        return userProfileDto;
    }

    public static List<UserProfile> getTestUserProfiles() {
        UserProfile userProfileOne = getTestUserProfile();
        UserProfile userProfileTwo = getTestUserProfile();
        userProfileTwo.setId(USER_PROFILE_OTHER_ID);
        List<UserProfile> userProfiles = new ArrayList<>();
        userProfiles.add(userProfileOne);
        userProfiles.add(userProfileTwo);
        return userProfiles;
    }

    public static List<UserProfileDto> getTestUserProfilesDto() {
        UserProfileDto userProfileDtoOne = getTestUserProfileDto();
        UserProfileDto UserProfileDtoTwo = getTestUserProfileDto();
        UserProfileDtoTwo.setId(USER_PROFILE_OTHER_ID);
        List<UserProfileDto> userProfilesDto = new ArrayList<>();
        userProfilesDto.add(userProfileDtoOne);
        userProfilesDto.add(UserProfileDtoTwo);
        return userProfilesDto;
    }

}