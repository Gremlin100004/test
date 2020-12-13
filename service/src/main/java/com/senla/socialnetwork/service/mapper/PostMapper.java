package com.senla.socialnetwork.service.mapper;

import com.senla.socialnetwork.dao.PostDao;
import com.senla.socialnetwork.domain.Community;
import com.senla.socialnetwork.domain.Post;
import com.senla.socialnetwork.dto.PostDto;
import com.senla.socialnetwork.dto.PostForCreationDto;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class PostMapper {
    public static PostDto getPostDto(final Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setCreationDate(post.getCreationDate());
        if (post.getTitle() != null) {
            postDto.setTitle(post.getTitle());
        }
        if (post.getContent() != null) {
            postDto.setContent(post.getContent());
        }
        postDto.setCommunity(CommunityMapper.getCommunityDto(post.getCommunity()));
        postDto.setDeleted(post.getIsDeleted());
        return postDto;
    }

    public static List<PostDto> getPostDto(final List<Post> posts) {
        return posts.stream()
                .map(PostMapper::getPostDto)
                .collect(Collectors.toList());
    }

    public static Post getPost(final PostDto postDto,
                               final PostDao postDao,
                               final String email) {
        Post post = postDao.findByIdAndEmail(email, postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        return post;
    }

    public static Post getNewPost(final PostForCreationDto postDto, final Community community) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCommunity(community);
        post.setCreationDate(new Date());
        post.setIsDeleted(false);
        return post;
    }

}
