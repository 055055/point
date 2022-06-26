package com.travel.point.mock

import com.travel.point.store.entity.pointhistory.PointHistoryEntity
import com.travel.point.type.PointType

fun getMockPointHistoryEntityList(
    pointType: PointType = PointType.REVIEW
) =
    listOf(
        PointHistoryEntity(
            pointEntity = getMockPoint(userId = "testUser1"),
            pointReviewEntity = getMockPointReviewEntity(userId = "testUser1"),
            pointType = pointType,
            point = 1,
            comment = "point"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(userId = "testUser2"),
            pointReviewEntity = getMockPointReviewEntity(userId = "testUser2"),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(userId = "testUser3"),
            pointReviewEntity = getMockPointReviewEntity(userId = "testUser3"),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(userId = "testUser4"),
            pointReviewEntity = getMockPointReviewEntity(userId = "testUser4", reviewId = "testReviewId"),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point 3"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(userId = "testUser5"),
            pointReviewEntity = getMockPointReviewEntity(userId = "testUser5", reviewId = "testReviewId1"),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point 3"
        )
    )

fun getMockPointHistoryEntity(
    userId: String = "testUserId1",
    reviewId: String = "testReviewId",
    pointType: PointType = PointType.REVIEW
) =
    PointHistoryEntity(
        pointEntity = getMockPoint(userId = userId),
        pointReviewEntity = getMockPointReviewEntity(userId = userId, reviewId = reviewId),
        pointType = pointType,
        point = 1,
        comment = "point"
    )
