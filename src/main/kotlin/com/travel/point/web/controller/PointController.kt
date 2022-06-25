package com.travel.point.web.controller

import com.travel.point.domain.User
import com.travel.point.service.PointAdapter
import com.travel.point.web.param.PointDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/events")
@RestController
class PointController(
    private val pointAdapter: PointAdapter
) {

    @PostMapping
    fun calculatePoint(@RequestBody request: PointDto.Request) {
        pointAdapter.calculatePoint(request.convertToChannelDto())
    }

    @GetMapping("/point/{userId}")
    fun getPoint(@PathVariable userId: String) =
        pointAdapter.getPoint(
            User(id = userId)
        )?.convertToPointResponse()
}
