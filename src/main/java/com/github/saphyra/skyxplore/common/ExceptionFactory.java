package com.github.saphyra.skyxplore.common;

import com.github.saphyra.exceptionhandling.domain.ErrorMessage;
import com.github.saphyra.exceptionhandling.exception.LockedException;
import com.github.saphyra.exceptionhandling.exception.PaymentRequiredException;
import com.github.saphyra.exceptionhandling.exception.RestException;
import com.github.saphyra.exceptionhandling.exception.UnauthorizedException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionFactory {
    private static final String EMAIL_ALREADY_EXISTS_PREFIX = "Email %s is already exists.";
    private static final String EQUIPMENT_NOT_FOUND_PREFIX = "Equipment not found with id %s";
    private static final String EQUIPPED_SLOT_NOT_FOUND_PREFIX = "EquippedSlot not found with slotId %s";
    private static final String FACTORY_NOT_FOUND_PREFIX = "Factory not found with userId %s";
    private static final String NOT_ENOUGH_MATERIALS_PREFIX = "Not enough %s. Required: %s, present: %s";
    private static final String NOT_ENOUGH_MONEY_PREFIX = "%s tried to spend %s money, but (s)he only has %s";
    private static final String USER_NAME_ALREADY_EXISTS_PREFIX = "User with name %s already exists.";

    public static RestException emailAlreadyExists(String newEmail) {
        return new LockedException(createErrorMessage(ErrorCode.EMAIL_ALREADY_EXISTS), String.format(EMAIL_ALREADY_EXISTS_PREFIX, newEmail));
    }

    public static RuntimeException equipmentNotFound(String id) {
        return new RuntimeException(String.format(EQUIPMENT_NOT_FOUND_PREFIX, id));
    }

    public static RuntimeException equippedSlotNotFound(String slotId) {
        return new RuntimeException(String.format(EQUIPPED_SLOT_NOT_FOUND_PREFIX, slotId));
    }

    public static RuntimeException factoryNotFound(String characterId) {
        return new RuntimeException(String.format(FACTORY_NOT_FOUND_PREFIX, characterId));
    }

    public static RestException notEnoughMaterials(String key, Integer amount, Integer actual) {
        return new PaymentRequiredException(createErrorMessage(ErrorCode.NOT_ENOUGH_MATERIALS), String.format(NOT_ENOUGH_MATERIALS_PREFIX, key, amount, actual));
    }

    public static RestException notEnoughMoney(String characterId, Integer moneyToSpend, Integer money) {
        return new PaymentRequiredException(createErrorMessage(ErrorCode.NOT_ENOUGH_MONEY), String.format(NOT_ENOUGH_MONEY_PREFIX, characterId, moneyToSpend, money));
    }

    public static RestException userNameAlreadyExists(String userName) {
        return new LockedException(createErrorMessage(ErrorCode.USER_NAME_ALREADY_EXISTS), String.format(USER_NAME_ALREADY_EXISTS_PREFIX, userName));
    }

    public static RestException wrongPassword() {
        return new UnauthorizedException(createErrorMessage(ErrorCode.WRONG_PASSWORD), "Wrong password");
    }

    private static ErrorMessage createErrorMessage(ErrorCode errorCode) {
        return new ErrorMessage(errorCode.name());
    }
}
