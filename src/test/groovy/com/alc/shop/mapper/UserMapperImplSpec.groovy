package com.alc.shop.mapper

import com.alc.shop.model.dao.User
import com.alc.shop.model.dto.UserDto
import spock.lang.Specification

import java.time.LocalDate

class UserMapperImplSpec extends Specification {

    def userMapper = new UserMapperImpl()

    def 'should map user to user dto'() {
        given:
        def user = new User(id: 1, firstName: "first", lastName: 'last', email: 'email', birthDate: LocalDate.now())

        when:
        def result = userMapper.toDto(user)

        then:
        result.id == user.id
        result.firstName == user.firstName
        result.lastName == user.lastName
        result.email == user.email
        result.birthDate == user.birthDate
    }

    def 'should map user dto to user'() {
        given:
        def userDto = new UserDto(id: 1, firstName: "first", lastName: 'last', email: 'email', birthDate: LocalDate.now())

        when:
        def result = userMapper.toDao(userDto)

        then:
        result.id == null
        result.firstName == userDto.firstName
        result.lastName == userDto.lastName
        result.email == userDto.email
        result.birthDate == userDto.birthDate
    }
}
