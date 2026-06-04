package spring.springserver.domain.community.post.controller

import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import spring.springserver.domain.community.common.data.response.DeleteResponse
import spring.springserver.domain.community.post.data.request.CreatePostRequest
import spring.springserver.domain.community.post.data.request.UpdatePostRequest
import spring.springserver.domain.community.post.data.response.CommunityPostResponse
import spring.springserver.domain.community.post.data.response.CreatePostResponse
import spring.springserver.domain.community.post.data.response.UpdatePostResponse
import spring.springserver.domain.community.post.service.CommunityPostService
import spring.springserver.global.data.BaseResponse

@RestController
@RequestMapping("/api/community/post")
class CommunityPostController(
    private val communityPostService: CommunityPostService
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun createPost(@Valid @RequestBody createPostRequest: CreatePostRequest): BaseResponse<CreatePostResponse> {

        return BaseResponse.ok(communityPostService.createPost(createPostRequest, null))
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun createPostWithFile(
        @Valid @RequestPart("request") createPostRequest: CreatePostRequest,
        @RequestPart(value = "multipartFile", required = false) multipartFile: MultipartFile?
    ): BaseResponse<CreatePostResponse> {

        return BaseResponse.ok(communityPostService.createPost(createPostRequest, multipartFile))
    }

    @GetMapping
    fun getPost(@RequestParam postId: Long): BaseResponse<CommunityPostResponse> {

        return BaseResponse.ok(communityPostService.getPost(postId))
    }

    @GetMapping("/search")
    fun searchPosts(@RequestParam keyword: String): BaseResponse<List<CommunityPostResponse>> {

        return BaseResponse.ok(communityPostService.searchPosts(keyword))
    }

    @PatchMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updatePost(@Valid @RequestBody updatePostRequest: UpdatePostRequest): BaseResponse<UpdatePostResponse> {

        return BaseResponse.ok(communityPostService.updatePost(updatePostRequest, null))
    }

    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updatePostWithFile(
        @Valid @RequestPart("request") updatePostRequest: UpdatePostRequest,
        @RequestPart(value = "multipartFile", required = false) multipartFile: MultipartFile?
    ): BaseResponse<UpdatePostResponse> {

        return BaseResponse.ok(communityPostService.updatePost(updatePostRequest, multipartFile))
    }

    @DeleteMapping
    fun deletePost(@RequestParam postId: Long): BaseResponse<DeleteResponse> {

        return BaseResponse.ok(communityPostService.deletePost(postId))
    }
}
