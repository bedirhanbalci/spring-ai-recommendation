package com.patika.bloghubservice.dto.request;

import com.patika.bloghubservice.model.enums.StatusType;

import java.util.List;

public record ChangeStatusBulkRequest(
        List<String> emailList,
        StatusType userType) {
}
