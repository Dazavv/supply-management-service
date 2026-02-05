package com.dazavv.supply.supplymanagementservice.product.mapper;

import com.dazavv.supply.supplymanagementservice.product.dto.responses.ProductResponse;
import com.dazavv.supply.supplymanagementservice.product.entity.ProductEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(source = "supplier.id", target = "supplierId")
    ProductResponse toProductDto(ProductEntity ProductEntity);
    List<ProductResponse> toProductDtoList(List<ProductEntity> productEntities);
}