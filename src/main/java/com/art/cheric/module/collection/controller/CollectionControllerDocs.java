package com.art.cheric.module.collection.controller;

import com.art.cheric.global.common.ResponseDto;
import com.art.cheric.global.enums.BasicSortType;
import com.art.cheric.module.art.dto.req.ArtIdListReqDto;
import com.art.cheric.module.collection.dto.req.CollectionIdListReqDto;
import com.art.cheric.module.collection.dto.req.CollectionReqDto;
import com.art.cheric.module.user.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Collection", description = "컬렉션 관련 API")
public interface CollectionControllerDocs {

    @Operation(summary = "컬렉션 생성 API", description = "컬렉션을 생성합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"1 컬렉션이 생성되었습니다.\" }")
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 403, \"message\": \"해당 작가의 작품은 이제 사용할 수 없습니다.\" }," +
                                    "{ \"code\": 403, \"message\": \"해당 소장 작품은 현재 인증이 되지 않았습니다.\" }," +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"해당 이름을 가진 컬렉션이 이미 있습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postCollection(User user, CollectionReqDto collectionReq);

    @Operation(summary = "컬렉션 작품 추가 API", description = "컬렉션에 작품을 추가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Created",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 201, \"message\": \"Created\" }")
                    )
            ),
            @ApiResponse(responseCode = "403", description = "접근이 허용되지 않습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 403, \"message\": \"해당 작가의 작품은 이제 사용할 수 없습니다.\" }," +
                                    "{ \"code\": 403, \"message\": \"해당 소장 작품은 현재 인증이 되지 않았습니다.\" }," +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "[" +
                                    "{ \"code\": 404, \"message\": \"해당 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"작가 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"소장 작품을 찾을 수 없습니다.\" }," +
                                    "{ \"code\": 404, \"message\": \"해당하는 컬렉션을 찾을 수 없습니다.\" }" +
                                    "]"
                            )
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복된 값입니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 409, \"message\": \"컬렉션 내에 똑같은 작품이 들어갈 수 없습니다.\" }")
                    )
            )
    })
    ResponseEntity<ResponseDto> postCollectionArt(User user, Long collectionId, ArtIdListReqDto collectionArtReq);

    @Operation(summary = "본인 컬렉션 리스트 API", description = "컬렉션 리스트를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n"
                                    + "  \"code\": 200,\n"
                                    + "  \"message\": \"OK\",\n"
                                    + "  \"data\": [\n"
                                    + "    {\n"
                                    + "      \"collectionId\": 3,\n"
                                    + "      \"latestArtImgUrl\": \"https://1cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "      \"name\": \"별빛 전시 후보 3\",\n"
                                    + "      \"description\": \"별빛 전시를 위한 후보군입니다. 다양한 색채에 조금 더 중점을 두어 준비했습니다.\"\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"collectionId\": 2,\n"
                                    + "      \"latestArtImgUrl\": \"https://3cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "      \"name\": \"별빛 전시 후보 2\",\n"
                                    + "      \"description\": \"별빛 전시를 위한 후보군입니다. 다양한 색채에 조금 더 중점을 두어 준비했습니다.\"\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"collectionId\": 1,\n"
                                    + "      \"latestArtImgUrl\": \"https://3cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "      \"name\": \"별빛 전시 후보 1\",\n"
                                    + "      \"description\": \"별빛 전시를 위한 후보군입니다. 다양한 색채에 조금 더 중점을 두어 준비했습니다.\"\n"
                                    + "    }\n"
                                    + "  ]\n"
                                    + "}")
                    )
            )
    })
    ResponseEntity<ResponseDto> getSelfCollectionList(User user);

    @Operation(summary = "컬렉션 id 별 조회 API", description = "컬렉션 id 리스트로 컬렉션들을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{\n"
                                    + "  \"code\": 200,\n"
                                    + "  \"message\": \"OK\",\n"
                                    + "  \"data\": [\n"
                                    + "    {\n"
                                    + "      \"collectionId\": 1,\n"
                                    + "      \"name\": \"별빛 전시 후보 1\",\n"
                                    + "      \"artBriefRess\": [\n"
                                    + "        {\n"
                                    + "          \"artId\": 1,\n"
                                    + "          \"imgUrl\": \"https://1cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "          \"collectorsArt\": true\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "          \"artId\": 2,\n"
                                    + "          \"imgUrl\": \"https://2cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "          \"collectorsArt\": true\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "          \"artId\": 3,\n"
                                    + "          \"imgUrl\": \"https://3cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "          \"collectorsArt\": true\n"
                                    + "        }\n"
                                    + "      ]\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"collectionId\": 2,\n"
                                    + "      \"name\": \"별빛 전시 후보 2\",\n"
                                    + "      \"artBriefRess\": [\n"
                                    + "        {\n"
                                    + "          \"artId\": 2,\n"
                                    + "          \"imgUrl\": \"https://2cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "          \"collectorsArt\": true\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "          \"artId\": 3,\n"
                                    + "          \"imgUrl\": \"https://3cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "          \"collectorsArt\": true\n"
                                    + "        }\n"
                                    + "      ]\n"
                                    + "    },\n"
                                    + "    {\n"
                                    + "      \"collectionId\": 3,\n"
                                    + "      \"name\": \"별빛 전시 후보 3\",\n"
                                    + "      \"artBriefRess\": [\n"
                                    + "        {\n"
                                    + "          \"artId\": 1,\n"
                                    + "          \"imgUrl\": \"https://1cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "          \"collectorsArt\": true\n"
                                    + "        },\n"
                                    + "        {\n"
                                    + "          \"artId\": 3,\n"
                                    + "          \"imgUrl\": \"https://3cheric-bucket.s3.ap-northeast-2.amazonaws.com/ARTIST_ART_IMG/1/716dc032-40da-4e9a-97a1-e27ea8abbbd2-ArtImage1.png\",\n"
                                    + "          \"name\": \"별이 빛나는 밤에\",\n"
                                    + "          \"cherryPrice\": null,\n"
                                    + "     ")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "해당 자원을 찾을 수 없습니다.",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ResponseDto.class),
                            examples =
                            @ExampleObject(value = "{ \"code\": 404, \"message\": \"해당하는 컬렉션을 찾을 수 없습니다.\" }"
                            )
                    )
            )
    })
    ResponseEntity<ResponseDto> getSelfCollectionList(User user, CollectionIdListReqDto collectionIdListReq,
                                                      BasicSortType sortType);
}

