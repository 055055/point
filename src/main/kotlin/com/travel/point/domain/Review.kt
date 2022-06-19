package com.travel.point.domain

import com.travel.point.type.ActionType

data class Review(
    val id: String,
    val content: String,
    val user: User,
    val place: Place,
    val photo: Photo,
    val actionType: ActionType,
) {
    fun calculateScore(): Int {
        var score = 0
        if (content.length > 1) score++
        if (photo.ids.size > 1) score++
        return score
    }
}
