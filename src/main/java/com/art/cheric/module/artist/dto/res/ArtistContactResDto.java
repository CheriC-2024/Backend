package com.art.cheric.module.artist.dto.res;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "작가 연락망 DTO")
@Getter
@Builder(access = AccessLevel.PRIVATE)
public class ArtistContactResDto {
    @Schema(description = "인스타그램 링크", example = "https://www.instagram.com/swu.official/")
    String instagram;

    @Schema(description = "트위터 링크", example = "https://www.twitter.com/swu.official/")
    String twitter;

    @Schema(description = "네이버 블로그 링크", example = "https://www.naver.blog.com/swu.official/")
    String naverBlog;

    @Schema(description = "이메일 링크", example = "swu.official@swu.ac.kr")
    String email;

    public static ArtistContactResDto of(String instagram, String twitter, String naverBlog, String email) {
        return ArtistContactResDto.builder()
                .instagram(instagram)
                .twitter(twitter)
                .naverBlog(naverBlog)
                .email(email)
                .build();
    }
}
