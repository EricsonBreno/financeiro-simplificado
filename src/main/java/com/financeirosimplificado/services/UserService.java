package com.financeirosimplificado.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.financeirosimplificado.domain.user.User;
import com.financeirosimplificado.domain.user.UserRole;
import com.financeirosimplificado.dto.UserDTO;
import com.financeirosimplificado.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) {

        User newUser = new User(userDTO);
        this.saveUser(newUser);
        
        return newUser;

    }

    public void validateTransaction(User payer, BigDecimal amount) throws Exception {
        if (payer.getRole().equals(UserRole.MERCHANT)) {
            throw new Exception("User type MERCHANT cannot send transactions, only receive");
        }

        if (payer.getBalance().compareTo(amount) < 0) {
            throw new Exception("Insufficient balance");
        }
    }


    public User findUserById(Long id) throws Exception{
        return this.userRepository.findUserById(id).orElseThrow(() -> new Exception("User does not exist"));
    }

    public void saveUser(User user) {
        this.userRepository.save(user);
    }

}
