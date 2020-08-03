//---------------------------------------------------
//  Generated content, modify:
//  logic/generateLogic.gradle
//  if necessary - enjoy the day 🙂
//---------------------------------------------------

package ch.tutteli.atrium.scala2.logic

import ch.tutteli.atrium.creating.AssertionContainer
import ch.tutteli.atrium.domain.creating.changers.ChangedSubjectPostStep
import kotlin.reflect.KClass
import ch.tutteli.atrium.logic._

class ThrowableLogic[T](container: AssertionContainer[T]) {


    def cause(expectedType: KClass[TExpected]): ChangedSubjectPostStep[Throwable?, TExpected] = ThrowableKt.cause(container, expectedType)
}
