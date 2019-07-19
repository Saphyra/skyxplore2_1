package com.github.saphyra.skyxplore.community.blockedcharacter;

import com.github.saphyra.skyxplore.character.CharacterQueryService;
import com.github.saphyra.skyxplore.common.OneStringParamRequest;
import com.github.saphyra.skyxplore.common.domain.character.CharacterView;
import com.github.saphyra.skyxplore.common.domain.character.CharacterViewConverter;
import com.github.saphyra.skyxplore.common.RequestConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
class BlockedCharacterController {
    private static final String ALLOW_BLOCKED_CHARACTER_MAPPING = "blockedcharacter";
    private static final String BLOCK_CHARACTER_MAPPING = "blockcharacter";
    private static final String GET_BLOCKED_CHARACTERS_MAPPING = "blockedcharacter";
    private static final String GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING = "blockcharacter/name";

    private final BlockedCharacterQueryService blockedCharacterQueryService;
    private final BlockCharacterService blockCharacterService;
    private final CharacterQueryService characterQueryService;
    private final CharacterViewConverter characterViewConverter;

    @DeleteMapping(ALLOW_BLOCKED_CHARACTER_MAPPING)
    void allowBlockedCharacter(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to allow blockedCharacter {}", characterId, request.getValue());
        blockCharacterService.allowBlockedCharacter(request.getValue(), characterId);
    }

    @PostMapping(BLOCK_CHARACTER_MAPPING)
    void blockCharacter(
        @Valid @RequestBody OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to block {}", characterId, request.getValue());
        blockCharacterService.blockCharacter(request.getValue(), characterId);
    }

    @GetMapping(GET_BLOCKED_CHARACTERS_MAPPING)
    List<CharacterView> getBlockedCharacters(
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} wants to know his blocked characters list.", characterId);
        return characterViewConverter.convertDomain(blockedCharacterQueryService.getBlockedCharacters(characterId));
    }

    @PostMapping(GET_CHARACTERS_CAN_BE_BLOCKED_MAPPING)
    List<CharacterView> getCharactersCanBeBlocked(
        @RequestBody @Valid OneStringParamRequest request,
        @CookieValue(RequestConstants.COOKIE_CHARACTER_ID) String characterId
    ) {
        log.info("{} querying blockable characters by name like {}", characterId, request.getValue());
        return characterViewConverter.convertDomain(characterQueryService.getCharactersCanBeBlocked(request.getValue(), characterId));
    }
}
