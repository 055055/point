package com.travel.point.mock

import com.travel.point.domain.User
import com.travel.point.store.entity.point.PointEntity

fun getMockPoint(
    userId: String = "test1",
    point: Int = 1
): PointEntity {
    val pointEntity = PointEntity(
        user = User(id = userId),
    )
    pointEntity.addPoint(point)
    return pointEntity
}
