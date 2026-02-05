package com.dazavv.supply.supplymanagementservice.delivery.mapper;

import com.dazavv.supply.supplymanagementservice.delivery.dto.responses.DeliveryResponse;
import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeliveryMapper {
    DeliveryResponse toDeliveryDto(DeliveryEntity deliveryEntity);
    List<DeliveryResponse> toDeliveryDtoList(List<DeliveryEntity> deliveryEntities);
}
