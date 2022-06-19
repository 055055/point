package com.travel.point.domain

import com.travel.point.type.ReviewActionType

data class Review(
    val id: String,
    val content: String,
    val user: User,
    val place: Place,
    val photo: Photo,
    val actionType: ReviewActionType,
) {
    fun calculateScore(): Int {
        var score = 0
        if (content.isNotBlank()) score++
        if (photo.ids.isNotEmpty()) score++
        return score
    }
}
