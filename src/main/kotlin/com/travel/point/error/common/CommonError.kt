package com.travel.point.error.common

import org.springframework.http.HttpStatus

enum class CommonError(val description: String, val httpStatus: HttpStatus) {
    CHECK_USER_ID("회원 아이디를 확인해 주세요.", HttpStatus.NOT_FOUND),
    SERVICE_ERROR("내부 서버 오류", HttpStatus.INTERNAL_SERVER_ERROR),
}
