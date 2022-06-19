package com.travel.point.web.controller

import com.travel.point.service.PointService
import com.travel.point.web.param.PointDto
import org.springframework.web.bind.annotation.*

@RequestMapping("/events")
@RestController
class PointController(
    private val pointService: PointService
) {

    @PostMapping
    fun calculatePoint(request: PointDto.request) {
        pointService.calculatePoint(request.convertToChannelDto())
    }

    @GetMapping("/{userId}")
    fun getPoint(@PathVariable userId: String) {

    }
}
