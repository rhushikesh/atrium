package ch.tutteli.atrium.api.cc.de_CH.assertions.charsequence.contains.builders

import ch.tutteli.atrium.api.cc.de_CH.enthaeltNicht
import ch.tutteli.atrium.api.cc.de_CH.nichtOderHoechstens
import ch.tutteli.atrium.assertions.charsequence.contains.ICharSequenceContains
import ch.tutteli.atrium.assertions.charsequence.contains.builders.CharSequenceContainsBuilder
import ch.tutteli.atrium.assertions.charsequence.contains.builders.CharSequenceContainsNotOrAtMostCheckerBuilderBase

/**
 * Represents the builder of a `contains not or at most` check within the fluent API of a
 * sophisticated `contains` assertion for [CharSequence].
 *
 * @param T The input type of the search.
 * @param D The decoration behaviour which should be applied for the input of the search.
 *
 * @constructor Represents the builder of a `contains not or at most` check within the fluent API of a
 *              sophisticated `contains` assertion for [CharSequence].
 * @param times The number which the check will compare against the actual number of times an expected object is
 *              found in the input of the search.
 * @param containsBuilder The previously used [CharSequenceContainsBuilder].
 */
open class CharSequenceContainsNotOrAtMostCheckerBuilder<T : CharSequence, D : ICharSequenceContains.IDecorator>(
    times: Int,
    containsBuilder: CharSequenceContainsBuilder<T, D>
) : CharSequenceContainsNotOrAtMostCheckerBuilderBase<T, D>(
    times,
    containsBuilder,
    containsBuilder.plant::enthaeltNicht.name,
    containsBuilder::nichtOderHoechstens.name
)