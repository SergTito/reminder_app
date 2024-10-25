package com.example.reminder_app.mapper;

import com.example.reminder_app.dto.UserDTO;
import com.example.reminder_app.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserEntity fromDto(UserDTO dto);

    @Mapping(target = "password", ignore = true)
    UserDTO toDto(UserEntity entity);

}
