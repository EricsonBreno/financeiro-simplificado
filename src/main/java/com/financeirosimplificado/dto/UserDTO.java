package com.financeirosimplificado.dto;

import com.financeirosimplificado.domain.user.UserRole;

public record UserDTO(String firstName, String lastName, String document, UserRole role, String balance, String email, String password) {

}
