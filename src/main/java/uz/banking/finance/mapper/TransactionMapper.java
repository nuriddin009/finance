package uz.banking.finance.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import uz.banking.finance.controller.request.TransactionRequest;
import uz.banking.finance.dto.SelectItem;
import uz.banking.finance.dto.Transaction;
import uz.banking.finance.model.MTransaction;

@Mapper(componentModel = "spring", uses = {})
public interface TransactionMapper extends EntityMapper<Transaction, MTransaction> {

    @Named("toDto")
    Transaction toDto(MTransaction m);

    @Named("toEntity")
    void toEntity(TransactionRequest request, @MappingTarget MTransaction m);

    @Named("toSelectItem")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "description")
    SelectItem toSelectItem(MTransaction m);

}
