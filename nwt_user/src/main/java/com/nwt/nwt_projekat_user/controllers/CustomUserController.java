package com.nwt.nwt_projekat_user.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.nwt.nwt_projekat_user.error.ErrorConstants;
import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.NOT_FOUND;

@RestController
@RequestMapping("/user/custom-user")
public class CustomUserController {

    @Autowired
    CustomUserDataService customUserDataService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @PatchMapping(path = "/{id}", consumes = "application/json-patch+json")
    @ResponseBody
    public CustomUser editUser(@PathVariable Long id, @RequestBody JsonPatch patch){
        try {
            CustomUser customUser = customUserDataService.getUserById(id);
            CustomUser customUserPatched = applyPatchToCustomer(patch, customUser);
            customUserDataService.createOrUpdateUser(customUserPatched);
            return customUserPatched;
        } catch (JsonPatchException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new WrappedException(NOT_FOUND);
        }

    }

    private CustomUser applyPatchToCustomer(
            JsonPatch patch, CustomUser targetCustomer) throws JsonPatchException, JsonProcessingException {
        JsonNode jsonNode = objectMapper.convertValue(targetCustomer, JsonNode.class);
        JsonNode patched = patch.apply(objectMapper.convertValue(targetCustomer, JsonNode.class));
        return objectMapper.treeToValue(patched, CustomUser.class);
    }

}
