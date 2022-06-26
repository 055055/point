package com.travel.point.web.param

import com.travel.point.domain.Photo
import com.travel.point.domain.Place
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.service.param.PointRequest
import com.travel.point.type.EventActionType
import com.travel.point.type.PointEventType

data class PointRequest(
    val type: String,
    val action: String,
    val reviewId: String,
    val content: String,
    val attachedPhotoIds: List<String>? = null,
    val userId: String,
    val placeId: String,
) {
    fun convertToChannelDto() =
        PointRequest(
            pointEventType = PointEventType.valueOf(type),
            review = Review(
                id = reviewId,
                content = content,
                user = User(userId),
                place = Place(placeId),
                photo = Photo(attachedPhotoIds ?: emptyList()),
                actionType = EventActionType.valueOf(action)
            )
        )
}
