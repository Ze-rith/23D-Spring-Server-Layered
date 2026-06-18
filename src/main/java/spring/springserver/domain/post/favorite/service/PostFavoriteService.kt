package spring.springserver.domain.post.favorite.service

import spring.springserver.domain.post.data.response.PostResponse
import spring.springserver.domain.post.favorite.data.response.PostFavoriteResponse

interface PostFavoriteService {

    fun favoritePost(
        postId: Long
    ): PostFavoriteResponse

    fun unfavoritePost(
        postId: Long
    ): PostFavoriteResponse

    fun viewFavoritePosts(): List<PostResponse>
}
