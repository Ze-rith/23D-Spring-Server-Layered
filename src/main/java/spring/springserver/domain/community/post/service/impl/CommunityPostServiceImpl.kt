package spring.springserver.domain.community.post.service.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager
import org.springframework.web.multipart.MultipartFile
import spring.springserver.domain.community.comment.repository.CommunityCommentRepository
import spring.springserver.domain.community.common.data.response.DeleteResponse
import spring.springserver.domain.community.common.service.CommunityAuthorizationService
import spring.springserver.domain.community.post.data.request.CreatePostRequest
import spring.springserver.domain.community.post.data.request.UpdatePostRequest
import spring.springserver.domain.community.post.data.response.CommunityPostResponse
import spring.springserver.domain.community.post.data.response.CreatePostResponse
import spring.springserver.domain.community.post.data.response.UpdatePostResponse
import spring.springserver.domain.community.post.repository.CommunityPostRepository
import spring.springserver.domain.community.post.service.CommunityPostService
import spring.springserver.domain.file.data.request.FileUploadRequest
import spring.springserver.domain.file.service.FileService
import java.time.LocalDateTime

@Service
@Transactional(rollbackFor = [Exception::class])
class CommunityPostServiceImpl(
    private val communityPostRepository: CommunityPostRepository,
    private val communityCommentRepository: CommunityCommentRepository,
    private val communityAuthorizationService: CommunityAuthorizationService,
    private val fileService: FileService
) : CommunityPostService {

    override fun createPost(createPostRequest: CreatePostRequest, multipartFile: MultipartFile?): CreatePostResponse {

        val member = communityAuthorizationService.getCurrentMember()

        val uploadedFileUrl = uploadPostFile(multipartFile)
        val communityPost = communityPostRepository.save(createPostRequest.toEntity(member, uploadedFileUrl))

        return CreatePostResponse.of(communityPost)
    }

    override fun updatePost(updatePostRequest: UpdatePostRequest, multipartFile: MultipartFile?): UpdatePostResponse {

        val member = communityAuthorizationService.getCurrentMember()

        val communityPost = communityAuthorizationService
            .getActivePost(updatePostRequest.postId)

        communityAuthorizationService.validateOwner(
            member,
            communityPost.member.getId()
        )

        val previousFileUrl = communityPost.fileUrl
        val uploadedFileUrl = uploadPostFile(multipartFile)

        communityPost.update(
            title = updatePostRequest.title.trim(),
            content = updatePostRequest.content?.trim()?.takeIf { it.isNotBlank() },
            fileUrl = uploadedFileUrl ?: updatePostRequest.fileUrl?.trim()?.takeIf { it.isNotBlank() },
        )

        if (uploadedFileUrl != null && previousFileUrl != null && previousFileUrl != uploadedFileUrl) {

            registerFileDeleteAfterCommit(previousFileUrl)
        }

        return UpdatePostResponse.of(communityPost)
    }

    override fun deletePost(postId: Long): DeleteResponse {

        val member = communityAuthorizationService.getCurrentMember()

        val communityPost = communityAuthorizationService.getActivePost(postId)

        communityAuthorizationService.validateOwner(
            member,
            communityPost.member.getId()
        )

        communityPost.softDelete(LocalDateTime.now())

        return DeleteResponse.of("삭제되었습니다.")
    }

    @Transactional(readOnly = true)
    override fun getPosts(): List<CommunityPostResponse> {

        return communityPostRepository.findAllActivePosts()
            .map { communityPost ->
                CommunityPostResponse.toPostResponse(
                    communityPost,
                    communityCommentRepository
                )
            }
    }

    @Transactional(readOnly = true)
    override fun getPost(postId: Long): CommunityPostResponse {

        val communityPost = communityAuthorizationService.getActivePost(postId)

        communityPost.increaseViewCount()

        return CommunityPostResponse.toPostResponse(communityPost, communityCommentRepository)
    }

    @Transactional(readOnly = true)
    override fun searchPosts(keyword: String): List<CommunityPostResponse> {

        val normalizedKeyword = keyword.trim()

        return communityPostRepository.searchPosts(normalizedKeyword)
            .map {

                communityPost ->
                CommunityPostResponse.toPostResponse(
                    communityPost,
                    communityCommentRepository
                )
            }
    }

    private fun uploadPostFile(multipartFile: MultipartFile?): String? {

        if (multipartFile == null || multipartFile.isEmpty) {

            return null
        }

        val uploadResponse = fileService.uploadFile(FileUploadRequest(multipartFile))
        val fileUrl = uploadResponse.fileUrl()

        registerUploadedFileRollbackCleanup(fileUrl)

        return fileUrl
    }

    private fun registerUploadedFileRollbackCleanup(fileUrl: String) {

        if (!TransactionSynchronizationManager.isSynchronizationActive()) {

            return
        }

        TransactionSynchronizationManager.registerSynchronization(
            object : TransactionSynchronization {

                override fun afterCompletion(status: Int) {

                    if (status == TransactionSynchronization.STATUS_ROLLED_BACK) {

                        fileService.deleteFile(fileUrl)
                    }
                }
            }
        )
    }

    private fun registerFileDeleteAfterCommit(fileUrl: String?) {

        val normalizedFileUrl = fileUrl?.trim()?.takeIf { it.isNotBlank() }

        if (normalizedFileUrl == null) {

            return
        }

        if (!TransactionSynchronizationManager.isSynchronizationActive()) {

            fileService.deleteFile(normalizedFileUrl)
            return
        }

        TransactionSynchronizationManager.registerSynchronization(
            object : TransactionSynchronization {

                override fun afterCommit() {

                    fileService.deleteFile(normalizedFileUrl)
                }
            }
        )
    }
}
