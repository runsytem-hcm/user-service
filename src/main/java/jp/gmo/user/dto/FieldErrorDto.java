package jp.gmo.user.dto;

import lombok.Data;

@Data
public class FieldErrorDto {
    private final String itemName;
    private final String message;
}
