package com.travel.point.domain

import com.travel.point.type.PointType
import com.travel.point.type.EventActionType

data class Review(
    val id: String,
    val content: String,
    val user: User,
    val place: Place,
    val photo: Photo,
    val actionType: EventActionType,
) {

    fun convertToPoint() =
        Point(
            user = this.user,
            score = this.calculatePointScore(),
            type = PointType.REVIEW
        )

    private fun calculatePointScore(): Int {
        var score = 0
        if (content.isNotBlank()) score++
        if (photo.ids.isNotEmpty()) score++
        return score
    }
}
