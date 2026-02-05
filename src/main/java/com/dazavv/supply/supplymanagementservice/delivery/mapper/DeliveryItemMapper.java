package com.dazavv.supply.supplymanagementservice.delivery.mapper;

import com.dazavv.supply.supplymanagementservice.delivery.dto.responses.DeliveryItemResponse;
import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryItemMapper {
    DeliveryItemResponse toDeliveryItemDto(DeliveryItemEntity item);
    List<DeliveryItemResponse> toDeliveryItemDtoList(List<DeliveryItemEntity> items);
}
