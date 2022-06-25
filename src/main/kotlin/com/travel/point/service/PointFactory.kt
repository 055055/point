package com.travel.point.service

import com.travel.point.type.PointEventType
import org.springframework.stereotype.Service

@Service
class PointFactory(
    pointServiceList: List<PointService>
) {
    private val pointServiceMap: HashMap<PointEventType, PointService> = HashMap()

    init {
        if (pointServiceList.isEmpty()) {
            throw IllegalArgumentException("point service is not found")
        }

        pointServiceList.forEach {
            pointServiceMap[it.eventType()] = it
        }
    }

    fun getPointService(pointEventType: PointEventType): PointService? = pointServiceMap[pointEventType]
}
