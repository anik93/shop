package com.alc.shop.mapper;

import com.alc.shop.model.dao.User;
import com.alc.shop.model.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    User toDao(UserDto userDto);

    UserDto toDto(User user);

}
