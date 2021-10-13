package ch.tutteli.atrium.api.fluent.en_GB.samples

import ch.tutteli.atrium.api.fluent.en_GB.*
import ch.tutteli.atrium.api.verbs.internal.expect
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.Month
import kotlin.test.Test

class LocalDateTimeExpectationSamples {

    @Test
    fun yearFeature() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .year // subject is now of type Int (actually 2021)
            .toEqual(2021)

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .year // subject is now of type Int (actually 2021)
                .notToEqual(1980)    // fails
                .toBeLessThan(2000)  // not reported because notToEqual already fails
            //                          use `.year { ... }` if you want that all expectations are evaluated
        }
    }

    @Test
    fun year() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .year { // subject inside this block is of type Int (actually 2021)
                toEqual(2021)
                toBeGreaterThan(2020)
            } // subject here is back to type LocalDateTime

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .year { // subject inside this block is of type Int (actually 2021)
                    notToEqual(1980)    // fails
                    toBeLessThan(2000)  // not reported because notToEqual already fails
                    //                     use `.year.` if you want a fail fast behaviour
                } // subject here is back to type LocalDateTime
        }
    }

    @Test
    fun monthFeature() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .month // subject is now of type Int (actually Month.OCTOBER.value i.e. 10)
            .toEqual(10)

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .month               // subject is now of type Int (actually Month.OCTOBER.value i.e. 10)
                .toBeLessThan(9)     // fails
                .toBeGreaterThan(11) // not reported because toBeLessThan already fails
            //                          use `.month { ... }` if you want that all expectations are evaluated
        }
    }

    @Test
    fun month() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .month { // subject inside this block is of type Int (actually Month.OCTOBER.value i.e. 10)
                toBeGreaterThan(5)
                notToEqual(8)
            } // subject here is back to type LocalDateTime

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .month { // subject inside this block is of type Int (actually Month.OCTOBER.value i.e. 10)
                    toBeLessThan(9)      // fails
                    toBeGreaterThan(11)  // still evaluated even though toBeLessThan already fails
                    //                      use `.month.` if you want a fail fast behaviour
                } // subject here is back to type LocalDateTime
        }
    }

    @Test
    fun dayOfWeekFeature() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .dayOfWeek // subject is now of type DayOfWeek (actually SATURDAY)
            .toEqual(DayOfWeek.SATURDAY)


        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .dayOfWeek // subject is now of type DayOfWeek (actually SATURDAY)
                .toEqual(DayOfWeek.MONDAY)       // fails
                .notToEqual(DayOfWeek.SATURDAY)  // not reported because toEqual already fails
            //                                      use `.dayOfWeek { ... }` if you want that all expectations are evaluated

        }
    }

    @Test
    fun dayOfWeek() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .dayOfWeek { // subject inside this block is of type DayOfWeek (actually SATURDAY)
                toEqual(DayOfWeek.SATURDAY)
                notToEqual(DayOfWeek.SUNDAY)
            } // subject here is back to type LocalDateTime

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .dayOfWeek { // subject inside this block is of type DayOfWeek (actually SATURDAY)
                    toEqual(DayOfWeek.MONDAY)       // fails
                    notToEqual(DayOfWeek.SATURDAY)  // still evaluated even though toEqual already fails
                    //                                 use `.dayOfWeek.` if you want a fail fast behaviour
                } // subject here is back to type LocalDateTime
        }
    }

    @Test
    fun dayFeature() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .day // subject is now of type Int (actually 9)
            .toEqual(9)

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .day // subject is now of type Int (actually 9)
                .toEqual(5)          // fails
                .toBeGreaterThan(10) // not reported because toEqual already fails
            //                          use `.day { ... }` if you want that all expectations are evaluated
        }
    }

    @Test
    fun day() {
        expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
            .day { // subject inside this block is of type Int (actually 9)
                toEqual(9)
                toBeGreaterThan(5)
            } // subject here is back to type LocalDateTime

        fails {
            expect(LocalDateTime.of(2021, Month.OCTOBER, 9, 11, 56))
                .day { // subject inside this block is of type Int (actually 9)
                    toEqual(5)       // fails
                    toBeLessThan(7)  // still evaluated even though toEqual already fails
                    //                  use `.day.` if you want a fail fast behaviour
                } // subject here is back to type LocalDateTime
        }
    }
}
