package com.alc.shop.service

import com.alc.shop.model.dao.User
import com.alc.shop.repository.UserRepository
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

import java.time.LocalDate

class UserServiceSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def userService = new UserService(userRepository)

    def 'should get user by id'() {
        given:
        def id = 1

        when:
        userService.getById(id)

        then:
        1 * userRepository.getById(id)
        0 * _
    }

    def 'should delete user by id'() {
        given:
        def id = 1

        when:
        userService.deleteById(id)

        then:
        1 * userRepository.deleteById(id)
        0 * _
    }

    def 'should get user page'() {
        given:
        def pageable = PageRequest.of(0, 10)

        when:
        userService.getPage(pageable)

        then:
        1 * userRepository.findAll(pageable)
        0 * _
    }

    def 'should save user'() {
        given:
        def user = new User()

        when:
        userService.save(user)

        then:
        1 * userRepository.save(user)
        0 * _
    }

    def 'should update user'() {
        given:
        def id = 1
        def user = new User(firstName: 'first', lastName: 'last', email: 'email', birthDate: LocalDate.now())
        def userDb = new User(id: id, firstName: 'first1', lastName: 'last1', email: 'email1',
                birthDate: LocalDate.now().plusDays(1))

        when:
        def result = userService.update(user, id)

        then:
        1 * userRepository.getById(id) >> userDb
        0 * _
        result.id == 1
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        result.birthDate == user.birthDate
    }

}
