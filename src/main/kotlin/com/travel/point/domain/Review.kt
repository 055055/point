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
}
