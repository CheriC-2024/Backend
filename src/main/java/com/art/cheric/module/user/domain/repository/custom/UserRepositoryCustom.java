package com.art.cheric.module.user.domain.repository.custom;

import com.art.cheric.global.enums.ArtType;
import com.art.cheric.global.enums.UserOrderType;
import com.art.cheric.module.user.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoryCustom {
    Page<User> getUsersBySortAndFilterAndPaging(Boolean isFollowing, List<Long> followingIds, Boolean isArtist, List<ArtType> artTypes,
                                                UserOrderType order, Pageable pageable);

}
