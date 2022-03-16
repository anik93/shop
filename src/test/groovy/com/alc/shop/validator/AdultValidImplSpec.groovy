package com.alc.shop.validator

import com.alc.shop.validation.impl.AdultValidImpl
import spock.lang.Specification

import java.time.LocalDate

class AdultValidImplSpec extends Specification {

    def adultValidImpl = new AdultValidImpl()

    def 'should test adult validator'() {
        when:
        def result = adultValidImpl.isValid(date, null)

        then:
        result == expected

        where:
        date                           || expected
        LocalDate.now().minusYears(18) || true
        LocalDate.now().minusYears(17) || false
    }

}
