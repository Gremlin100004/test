package com.senla.socialnetwork.dto;

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
@ApiModel(value = "Post Comment")
public class PostCommentDto extends GeneralDto {
    @ApiModelProperty(value = "Create community date",
        example = "2020-09-21 10:00")
    @Past
    @NotNull
    private Date creationDate;
    @ApiModelProperty(value = "Comment author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Community post")
    @NotNull
    private PostDto post;
    @ApiModelProperty(value = "Comment content",
        example = "good news!")
    @NotNull
    private String content;
    private boolean isDeleted;

}
