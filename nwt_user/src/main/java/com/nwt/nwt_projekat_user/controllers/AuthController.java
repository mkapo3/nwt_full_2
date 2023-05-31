package com.nwt.nwt_projekat_user.controllers;

import com.nwt.nwt_projekat_user.error.exception.WrappedException;
import com.nwt.nwt_projekat_user.jwt.JwtTokenUtil;
import com.nwt.nwt_projekat_user.models.Cart;
import com.nwt.nwt_projekat_user.models.CustomUser;
import com.nwt.nwt_projekat_user.models.Role;
import com.nwt.nwt_projekat_user.models.Wishlist;
import com.nwt.nwt_projekat_user.repository.role.RoleDataService;
import com.nwt.nwt_projekat_user.repository.user.CustomUserDataService;
import com.nwt.nwt_projekat_user.request_response.GeneralSuccessResponse;
import com.nwt.nwt_projekat_user.request_response.auth.LoginDto;
import com.nwt.nwt_projekat_user.request_response.auth.LoginResponse;
import com.nwt.nwt_projekat_user.request_response.auth.TokenDto;
import com.nwt.nwt_projekat_user.request_response.auth.ValidateTokenResponse;
import com.nwt.nwt_projekat_user.services.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;

import static com.nwt.nwt_projekat_user.error.ErrorConstants.GENERAL_ERROR;
import static com.nwt.nwt_projekat_user.error.ErrorConstants.USER_ALREADY_EXISTS;

@RestController
@RequestMapping("/user/auth")
public class AuthController {

    @Autowired
    CustomUserDataService customUserDataService;

    @Autowired
    AuthenticationProvider authenticationProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleDataService roleDataService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/register")
    @ResponseBody
    public GeneralSuccessResponse registerCustomUser(@RequestBody @Valid CustomUser customUser){
        if (customUserDataService.getUserByEmail(customUser.getEmail()) != null){
            throw new WrappedException(USER_ALREADY_EXISTS);
        }


        Cart cart = new Cart();
        Wishlist wishlist = new Wishlist();
        cart.setCartProducts(new HashSet<>());

        customUser.setCart(cart);
        customUser.setWishlist(wishlist);
        customUser.setReservations(new HashSet<>());

        customUser.setPassword(passwordEncoder.encode(customUser.getPassword()));

        Role roles = roleDataService.getRoleByName("ROLE_ADMIN");
        customUser.setRoles(Collections.singleton(roles));

        customUserDataService.createOrUpdateUser(customUser);
        return new GeneralSuccessResponse();
    }

    @PostMapping("/login")
    @ResponseBody
    public LoginResponse authenticateUser(@RequestBody LoginDto loginDto){
        authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginDto.getEmail());
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtTokenUtil.generateToken(userDetails));
        return loginResponse;
    }

    @PostMapping("/validate")
    public ValidateTokenResponse validateToken(@RequestBody TokenDto tokenDto){
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(jwtTokenUtil.getUsernameFromToken(tokenDto.getToken()));
        if(jwtTokenUtil.validateToken(tokenDto.getToken(), userDetails)){
            ValidateTokenResponse validateTokenResponse = new ValidateTokenResponse();
            validateTokenResponse.setAuthorities(userDetails.getAuthorities());
            validateTokenResponse.setEmail(userDetails.getUsername());
            return validateTokenResponse;
        }
        else{
            throw new WrappedException(GENERAL_ERROR);
        }
    }



}
