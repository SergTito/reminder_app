package com.example.reminder_app.mapper;

import com.example.reminder_app.dto.ReminderDTO;
import com.example.reminder_app.entity.ReminderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReminderMapper {

    ReminderMapper INSTANCE = Mappers.getMapper(ReminderMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),

    })
    ReminderEntity fromDto(ReminderDTO dto);



    ReminderDTO toDto(ReminderEntity entity);
}
