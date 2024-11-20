package com.art.cheric.global.enums;

import com.google.cloud.vision.v1.Feature;
import lombok.Getter;

@Getter
public enum CloudVisionType {
    LABEL_DETECTION(4, 5),
    IMAGE_PROPERTIES(7, 10);


    private final int value;
    private final int maxResult;

    CloudVisionType(int value, int maxResult){
        this.value = value;
        this.maxResult = maxResult;
    }

    public Feature.Type getFeature(){
        return Feature.Type.forNumber(this.value);
    }
}
