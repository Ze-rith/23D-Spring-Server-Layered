package spring.springserver.domain.community.post.service

import org.springframework.web.multipart.MultipartFile
import spring.springserver.domain.community.common.data.response.DeleteResponse
import spring.springserver.domain.community.post.data.request.CreatePostRequest
import spring.springserver.domain.community.post.data.request.UpdatePostRequest
import spring.springserver.domain.community.post.data.response.CommunityPostResponse
import spring.springserver.domain.community.post.data.response.CreatePostResponse
import spring.springserver.domain.community.post.data.response.UpdatePostResponse

interface CommunityPostService {

    fun createPost(createPostRequest: CreatePostRequest, multipartFile: MultipartFile?): CreatePostResponse

    fun updatePost(updatePostRequest: UpdatePostRequest, multipartFile: MultipartFile?): UpdatePostResponse

    fun deletePost(postId: Long): DeleteResponse

    fun getPost(postId: Long): CommunityPostResponse

    fun searchPosts(keyword: String): List<CommunityPostResponse>
}
