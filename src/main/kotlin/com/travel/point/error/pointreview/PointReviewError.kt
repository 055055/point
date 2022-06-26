package com.travel.point.error.pointreview

import org.springframework.http.HttpStatus

enum class PointReviewError(val description: String, val httpStatus: HttpStatus) {
    ALREADY_SAVED_REVIEW("이미 저장되어 있는 리뷰 입니다.", HttpStatus.CONFLICT),
    ALREADY_DELETED_REVIEW("이미 삭제되어 있는 리뷰 입니다.", HttpStatus.CONFLICT),
    CHECK_REVIEW_ID("리뷰 아이디를 다시 확인해 주세요.", HttpStatus.NOT_FOUND),
    EMPTY_POINT_HISTORY("포인트 이력이 없습니다.", HttpStatus.NOT_FOUND),
    MISMATCH_DELETE_POINT("삭제할 포인트 이력 불일치", HttpStatus.CONFLICT),
}
