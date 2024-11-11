package com.art.cheric.module.artist.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ArtistContact {

    private String instagram;

    private String twitter;

    private String naverBlog;

    private String email;

    public void updateInstagram(String instagram) {
        this.instagram = instagram;
    }

    public void updateTwitter(String twitter) {
        this.twitter = twitter;
    }

    public void updateNaverBlog(String naverBlog) {
        this.naverBlog = naverBlog;
    }

    public void updateEmail(String email) {
        this.email = email;
    }
}
