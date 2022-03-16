package com.alc.shop.service;

import com.alc.shop.model.dao.User;
import com.alc.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    public Page<User> getPage(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public User update(User user, Long id) {
        User userDb = getById(id);
        userDb.setEmail(user.getEmail());
        userDb.setBirthDate(user.getBirthDate());
        userDb.setFirstName(user.getFirstName());
        userDb.setLastName(user.getLastName());
        return userDb;
    }
}
