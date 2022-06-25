package com.travel.point.mock

import com.travel.point.domain.Photo
import com.travel.point.domain.Place
import com.travel.point.domain.Review
import com.travel.point.domain.User
import com.travel.point.store.entity.pointreview.PointReviewEntity
import com.travel.point.type.EventActionType
import java.util.*

fun getMockPointReviewEntity(
    reviewId: String = UUID.randomUUID().toString(),
    content: String = "testContent",
    userId: String = "testUserId",
    placeId: String = UUID.randomUUID().toString(),
    attachedPhotoIds: List<String> = listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString()),
    actionType: EventActionType = EventActionType.ADD
) =
    PointReviewEntity(
        Review(
            id = reviewId,
            content = content,
            user = User(userId),
            place = Place(placeId),
            photo = Photo(attachedPhotoIds),
            actionType = actionType
        )
    )

fun getMockPointReviewEntityForCountNotDeletedPlaceId() =
    listOf(
        PointReviewEntity(
            Review(
                id = UUID.randomUUID().toString(),
                content = "content1",
                user = User("testUserId1"),
                place = Place("testPlaceId1"),
                photo = Photo(emptyList()),
                actionType = EventActionType.ADD
            )
        ),
        PointReviewEntity(
            Review(
                id = UUID.randomUUID().toString(),
                content = "content2",
                user = User("testUserId2"),
                place = Place("testPlaceId1"),
                photo = Photo(listOf(UUID.randomUUID().toString(), UUID.randomUUID().toString())),
                actionType = EventActionType.ADD
            )
        ),
        PointReviewEntity(
            Review(
                id = UUID.randomUUID().toString(),
                content = "content3",
                user = User("testUserId3"),
                place = Place("testPlaceId2"),
                photo = Photo(emptyList()),
                actionType = EventActionType.MOD
            )
        )
    )

