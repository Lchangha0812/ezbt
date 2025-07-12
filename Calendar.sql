# CREATE DATABASE `ezbt`;

-- 사용자 정보
CREATE TABLE `users`
(
    `id`         BIGINT       NOT NULL AUTO_INCREMENT COMMENT '사용자 고유 ID',
    `user_name`  VARCHAR(255) NOT NULL COMMENT '사용자 이름',
    `email`      VARCHAR(255) NOT NULL COMMENT '사용자 이메일',
    `created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    `updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_email` (`email`)
) COMMENT '사용자 정보';

-- 출장 정보
CREATE TABLE `business_trips`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '출장 고유 ID',
    `user_id`     BIGINT       NOT NULL COMMENT '출장 생성자 ID',
    `title`       VARCHAR(255) NOT NULL COMMENT '출장 제목',
    `start_date`  DATE         NOT NULL COMMENT '출장 시작일',
    `end_date`    DATE         NOT NULL COMMENT '출장 종료일',
    `destination` VARCHAR(255) NULL COMMENT '출장지',
    `status`      VARCHAR(20)  NOT NULL DEFAULT 'DRAFT' COMMENT '출장 상태 (DRAFT, PENDING_APPROVAL, APPROVED, REJECTED, COMPLETED)',
    `created_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    `updated_at`  TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (`id`)
) COMMENT '출장 정보';

-- 출장 승인 이력
CREATE TABLE `trip_approvals`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT COMMENT '승인 이력 고유 ID',
    `trip_id`     BIGINT      NOT NULL COMMENT '관련 출장 ID',
    `approver_id` BIGINT      NOT NULL COMMENT '승인자 ID',
    `status`      VARCHAR(20) NOT NULL COMMENT '승인 상태 (APPROVED, REJECTED)',
    `comment`     TEXT        NULL COMMENT '승인/반려 의견',
    `created_at`  TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '처리 일시',
    PRIMARY KEY (`id`)
) COMMENT '출장 승인 이력';

-- 출장 경비
CREATE TABLE `expenses`
(
    `id`          BIGINT         NOT NULL AUTO_INCREMENT COMMENT '경비 고유 ID',
    `trip_id`     BIGINT         NOT NULL COMMENT '관련 출장 ID',
    `description` VARCHAR(255)   NOT NULL COMMENT '경비 내역 설명',
    `amount`      DECIMAL(10, 2) NOT NULL COMMENT '금액',
    `currency`    VARCHAR(3)     NOT NULL COMMENT '통화 (예: KRW, USD)',
    `receipt_url` VARCHAR(2048)  NULL COMMENT '영수증 파일 URL',
    `created_at`  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성 일시',
    `updated_at`  TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '수정 일시',
    PRIMARY KEY (`id`)
) COMMENT '출장 경비';
