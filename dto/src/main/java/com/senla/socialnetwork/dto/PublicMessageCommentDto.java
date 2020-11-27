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
@ApiModel(value = "Public Message Comment")
public class PublicMessageCommentDto extends GeneralDto {
    @ApiModelProperty(value = "Create comment date",
        example = "2020-09-21 10:00")
    @Past
    @NotNull
    private Date creationDate;
    @ApiModelProperty(value = "Comment author")
    @NotNull
    private UserProfileDto author;
    @ApiModelProperty(value = "Public message to which the comment belongs")
    @NotNull
    private PublicMessageDto publicMessage;
    @ApiModelProperty(value = "Great event")
    @NotNull
    private String content;
    private boolean isDeleted;

}
