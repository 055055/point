package com.travel.point.mock

import com.travel.point.store.entity.pointhistory.PointHistoryEntity
import com.travel.point.type.PointType

fun getMockPointHistoryEntityList(
    pointType: PointType = PointType.REVIEW
) =
    listOf(
        PointHistoryEntity(
            pointEntity = getMockPoint(),
            pointReviewEntity = getMockPointReviewEntity(),
            pointType = pointType,
            point = 1,
            comment = "point"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(),
            pointReviewEntity = getMockPointReviewEntity(),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(),
            pointReviewEntity = getMockPointReviewEntity(),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(),
            pointReviewEntity = getMockPointReviewEntity("testReviewId"),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point 3"
        ),
        PointHistoryEntity(
            pointEntity = getMockPoint(),
            pointReviewEntity = getMockPointReviewEntity("testReviewId1"),
            pointType = PointType.BONUS,
            point = 1,
            comment = "point 3"
        )
    )

fun getMockPointHistoryEntity(
    reviewId: String = "testReviewId",
    pointType: PointType = PointType.REVIEW
) =
    PointHistoryEntity(
        pointEntity = getMockPoint(),
        pointReviewEntity = getMockPointReviewEntity(reviewId = reviewId),
        pointType = pointType,
        point = 1,
        comment = "point"
    )
