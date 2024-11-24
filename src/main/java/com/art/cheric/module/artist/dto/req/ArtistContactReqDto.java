package com.art.cheric.module.artist.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.URL;

@Schema(description = "작가 연락망 DTO")
public record ArtistContactReqDto(
        @Schema(description = "인스타그램 링크", example = "https://www.instagram.com/swu.official/")
        @URL(message = "유효한 URL이 아닙니다.")
        String instagram,

        @Schema(description = "트위터 링크", example = "https://www.twitter.com/swu.official/")
        @URL(message = "유효한 URL이 아닙니다.")
        String twitter,

        @Schema(description = "네이버 블로그 링크", example = "https://www.naver.blog.com/swu.official/")
        @URL(message = "유효한 URL이 아닙니다.")
        String naverBlog,

        @Schema(description = "이메일 링크", example = "swu.official@swu.ac.kr")
        @Email(message = "유효한 이메일이 아닙니다.")
        String email
) {
}
