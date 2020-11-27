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
@ApiModel(value = "Private Message")
public class PrivateMessageDto extends GeneralDto {
    @ApiModelProperty(value = "Departure date of message",
        example = "2020-09-21 10:00")
    @Past
    @NotNull
    private Date departureDate;
    @ApiModelProperty(value = "User who is sending the message")
    @NotNull
    private UserProfileDto sender;
    @ApiModelProperty(value = "User to whom the message is addressed")
    @NotNull
    private UserProfileDto recipient;
    @ApiModelProperty(value = "Message content",
        example = "Hello, dear friend!")
    @NotNull
    private String content;
    private boolean isRead;
    private boolean isDeleted;

}
