package com.travel.point.web.param

import com.travel.point.domain.Photo
import com.travel.point.domain.Place
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.service.param.PointChannelDto
import com.travel.point.type.ActionType

class PointDto {
    data class request(
        val type: String,
        val action: String,
        val reviewId: String,
        val content: String,
        val attachedPhotoIds: List<String>? = null,
        val userId: String,
        val placeId: String,
    ) {
        fun convertToChannelDto() =
            PointChannelDto(
                review = Review(
                    id = reviewId,
                    content = content,
                    user = User(userId),
                    place = Place(placeId),
                    photo = Photo(attachedPhotoIds?: emptyList()),
                    actionType = ActionType.valueOf(action)
                )
            )
    }

    data class response(
        val userId: String,
        val point: Long,
    )
}
