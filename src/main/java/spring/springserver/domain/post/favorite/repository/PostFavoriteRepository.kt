package spring.springserver.domain.post.favorite.repository

import org.springframework.data.jpa.repository.JpaRepository
import spring.springserver.domain.member.entity.Member
import spring.springserver.domain.post.entity.Post
import spring.springserver.domain.post.favorite.entity.PostFavorite

interface PostFavoriteRepository: JpaRepository<PostFavorite, Long> {

    fun existsByMemberAndPost(member: Member, post: Post): Boolean

    fun countByPostId(postId: Long): Long

    fun deleteByMemberAndPost(member: Member, post: Post): Long

    fun deleteAllByPostIn(posts: Collection<Post>)

    fun findAllByMemberOrderByPostUpdatedAtDesc(member: Member): List<PostFavorite>
}
