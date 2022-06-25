package com.travel.point.web.param

import com.travel.point.domain.*
import com.travel.point.service.param.PointChannelDto
import com.travel.point.type.EventActionType
import com.travel.point.type.PointEventType

class PointDto {
    data class Request(
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

    data class Response(
        val point: Point,
    )
}
