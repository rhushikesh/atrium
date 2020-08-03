//---------------------------------------------------
//  Generated content, modify:
//  logic/generateLogic.gradle
//  if necessary - enjoy the day 🙂
//---------------------------------------------------

package ch.tutteli.atrium.scala2.logic

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.creating.AssertionContainer
import ch.tutteli.atrium.logic._

class IteratorLogic[T](container: AssertionContainer[T]) {

    def hasNext(): Assertion = IteratorKt.hasNext(container)
    def hasNotNext(): Assertion = IteratorKt.hasNotNext(container)
}
