package com.dazavv.supply.supplymanagementservice.delivery.mapper;

import com.dazavv.supply.supplymanagementservice.delivery.dto.responses.DeliveryResponse;
import com.dazavv.supply.supplymanagementservice.delivery.entity.DeliveryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = DeliveryItemMapper.class
)
public interface DeliveryMapper {
    @Mapping(source = "supplier.id", target = "supplierId")
    DeliveryResponse toDeliveryDto(DeliveryEntity deliveryEntity);
    List<DeliveryResponse> toDeliveryDtoList(List<DeliveryEntity> deliveryEntities);
}
