package com.dazavv.supply.supplymanagementservice.auth.mapper;

import com.dazavv.supply.supplymanagementservice.auth.dto.response.UserResponse;
import com.dazavv.supply.supplymanagementservice.auth.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)

public interface UserMapper {
    UserResponse toUserDto(User user);
    List<UserResponse> toUserDtoList(List<User> users);
}
