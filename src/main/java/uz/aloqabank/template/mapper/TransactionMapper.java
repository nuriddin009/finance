package uz.aloqabank.template.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import uz.aloqabank.template.controller.request.TransactionRequest;
import uz.aloqabank.template.dto.SelectItem;
import uz.aloqabank.template.dto.Transaction;
import uz.aloqabank.template.model.MTransaction;

@Mapper(componentModel = "spring", uses = {})
public interface TransactionMapper extends EntityMapper<Transaction, MTransaction> {

    @Named("toDto")
    Transaction toDto(MTransaction m);

    @Named("toEntity")
    void toEntity(TransactionRequest request, @MappingTarget MTransaction m);

    @Named("toSelectItem")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SelectItem toSelectItem(MTransaction m);

}
