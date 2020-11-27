package com.senla.socialnetwork.dto;

import com.senla.socialnetwork.domain.enumaration.CommunityType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel(value = "Community")
public class CommunityDto extends GeneralDto {
    @ApiModelProperty(value = "Create community date",
        example = "2020-09-21 10:00")
    @Past
    @NotNull
    private Date creationDate;
    @ApiModelProperty(value = "Community author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Type of community")
    @NotNull
    private CommunityType type;
    @ApiModelProperty(value = "Tittle of community",
        example = "Sport events")
    @NotNull
    private String tittle;
    @ApiModelProperty(value = "Community information",
        example = "This community is dedicated to amateur sports activities")
    private String information;
    private boolean isDeleted;

}
