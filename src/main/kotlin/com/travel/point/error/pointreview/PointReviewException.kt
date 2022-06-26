package com.travel.point.error.pointreview

data class PointReviewException(val pointReviewError: PointReviewError) : RuntimeException()

