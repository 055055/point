package com.travel.point.error.common

data class CommonException(val commonError: CommonError) : RuntimeException()
