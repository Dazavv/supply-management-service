package com.dazavv.supply.supplymanagementservice.supplier.mapper;

import com.dazavv.supply.supplymanagementservice.supplier.dto.responses.SupplierResponse;
import com.dazavv.supply.supplymanagementservice.supplier.entity.SupplierEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    SupplierResponse toSupplierDto(SupplierEntity supplierEntity);
    List<SupplierResponse> toSupplierDtoList(List<SupplierEntity> supplierEntities);
}
