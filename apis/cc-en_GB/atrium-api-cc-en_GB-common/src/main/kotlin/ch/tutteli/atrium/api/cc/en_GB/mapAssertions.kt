@file:Suppress("DEPRECATION" /* will be removed with 1.0.0 */)
package ch.tutteli.atrium.api.cc.en_GB

import ch.tutteli.atrium.assertions.Assertion
import ch.tutteli.atrium.creating.Assert
import ch.tutteli.atrium.creating.AssertionPlant
import ch.tutteli.atrium.creating.AssertionPlantNullable
import ch.tutteli.atrium.domain.builders.AssertImpl
import ch.tutteli.atrium.domain.builders.ExpectImpl
import ch.tutteli.kbox.glue
import kotlin.js.JsName

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] contains a key as defined by [keyValuePair]'s [Pair.first]
 * with a corresponding value as defined by [keyValuePair]'s [Pair.second] -- optionally the same assertions
 * are created for the [otherPairs].
 *
 * Notice, that it does not search for unique matches. Meaning, if the map is `mapOf('a' to 1)` and [keyValuePair] is
 * defined as `'a' to 1` and one of the [otherPairs] is defined as `'a' to 1` as well, then both match,
 * even though they match the same entry.
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <K, V, T: Map<out K, V>> Assert<T>.contains(keyValuePair: Pair<K, V>, vararg otherPairs: Pair<K, V>)
    = addAssertion(AssertImpl.map.contains(this, keyValuePair glue otherPairs))

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] contains a key as defined by [keyValue]'s [KeyValue.key]
 * with a corresponding value which either holds all assertions [keyValue]'s
 * [KeyValue.valueAssertionCreatorOrNull] might create or needs to be `null` in case
 * [KeyValue.valueAssertionCreatorOrNull] is defined as `null`
 * -- optionally the same assertions are created for the [otherKeyValues].
 *
 * Notice, that it does not search for unique matches. Meaning, if the map is `mapOf('a' to 1)` and [keyValue] is
 * defined as `Key('a') { isGreaterThan(0) }` and one of the [otherKeyValues] is defined as `Key('a') { isLessThan(2) }`
 * , then both match, even though they match the same entry.
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <K, V : Any, T: Map<out K, V?>> Assert<T>.contains(
    keyValue: KeyValue<K, V>,
    vararg otherKeyValues: KeyValue<K, V>
) = addAssertion(AssertImpl.map.containsKeyWithValueAssertions(this, (keyValue glue otherKeyValues).map { it.toPair() }))

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] contains the given [key].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <K> Assert<Map<out K, *>>.containsKey(key: K)
    = addAssertion(AssertImpl.map.containsKey(this, key))

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] does not contain the given [key].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <K> Assert<Map<out K, *>>.containsNotKey(key: K)
    = addAssertion(AssertImpl.map.containsNotKey(this, key))



/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] contains the given [key], creates a feature
 * assertion plant for the corresponding value and returns the newly created plant.
 *
 * @return The newly created plant for the feature
 * @throws AssertionError Might throw an [AssertionError] if the given [key] does not exist.
 */
@JsName("getExisting")
fun <K, V: Any, T: Map<out K, V>> Assert<T>.getExisting(key: K): Assert<V>
    = AssertImpl.map.getExisting(this, key)

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] contains the given [key] and that
 * the corresponding value holds all assertions the given [assertionCreator] might create for it.
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if a created [Assertion]s (by calling [assertionCreator])
 *   does not hold.
 * @throws IllegalArgumentException in case the given [assertionCreator] did not create a single assertion.
 */
fun <K, V: Any, T: Map<out K, V>> Assert<T>.getExisting(key: K, assertionCreator: Assert<V>.() -> Unit)
    = addAssertion(AssertImpl.map.getExisting(this, key, assertionCreator))


/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] contains the given [key], creates a feature
 * assertion plant for the corresponding nullable value and returns the newly created plant.
 *
 * @return The newly created plant for the feature
 * @throws AssertionError Might throw an [AssertionError] if the given [key] does not exist.
 */
fun <K, V: Any, T: Map<out K, V?>> Assert<T>.getExisting(key: K): AssertionPlantNullable<V?>
    = AssertImpl.map.getExistingNullable(this, key)

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject]'s [Map.size] is [size].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Map<*, *>> Assert<T>.hasSize(size: Int)
    = addAssertion(AssertImpl.map.hasSize(this, size))

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] is an empty [Map].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Map<*, *>> Assert<T>.isEmpty()
    = addAssertion(AssertImpl.map.isEmpty(this))

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject] is not an empty [Map].
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if the assertion made is not correct.
 */
fun <T : Map<*, *>> Assert<T>.isNotEmpty()
    = addAssertion(AssertImpl.map.isNotEmpty(this))


/**
 * Creates an [AssertionPlant] for the [Assert.subject][AssertionPlant.subject]'s property [keys][Map.keys] so that further
 * fluent calls are assertions about it.
 *
 * Wrap it into Kotlin's [apply] if you want to make subsequent assertions on the current subject or use the overload
 * which expects an assertionCreator lambda where sub assertions are evaluated together (form an assertion group block).
 *
 * @return The newly created [AssertionPlant].
 */
val <K> Assert<Map<K, *>>.keys get() : Assert<Set<K>> = property(Map<K, *>::keys)

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject]'s property [keys][Map.keys] holds all assertions the given
 * [assertionCreator] might create for it.
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if a created [Assertion]s (by calling [assertionCreator])
 *   does not hold.
 * @throws IllegalArgumentException in case the given [assertionCreator] did not create a single assertion.
 */
fun <K, V, T: Map<K, V>> Assert<T>.keys(assertionCreator: Assert<Set<K>>.() -> Unit)
    = addAssertion(AssertImpl.map.keys(this, assertionCreator))

/**
 * Creates an [AssertionPlant] for the [Assert.subject][AssertionPlant.subject]'s property [values][Map.values] so that further
 * fluent calls are assertions about it.
 *
 * Wrap it into Kotlin's [apply] if you want to make subsequent assertions on the current subject or use the overload
 * which expects an assertionCreator lambda where sub assertions are evaluated together (form an assertion group block).
 *
 * @return The newly created [AssertionPlant].
 */
val <V> Assert<Map<*, V>>.values get() : Assert<Collection<V>> = property(Map<*, V>::values)

/**
 * Makes the assertion that the [Assert.subject][AssertionPlant.subject]'s property [values][Map.values] holds all assertions the given
 * [assertionCreator] might create for it.
 *
 * @return This plant to support a fluent API.
 * @throws AssertionError Might throw an [AssertionError] if a created [Assertion]s (by calling [assertionCreator])
 *   does not hold.
 * @throws IllegalArgumentException in case the given [assertionCreator] did not create a single assertion.
 */
fun <K, V, T: Map<K, V>> Assert<T>.values(assertionCreator: Assert<Collection<V>>.() -> Unit)
    = addAssertion(AssertImpl.map.values(this, assertionCreator))

/**
 * Turns `Assert<Map<K, V>>` into `Assert<Set<Map.Entry<K, V>>>`.
 *
 * The transformation as such is not reflected in reporting.
 * Use `property(subject::entries)` if you want to show the transformation in reporting.
 *
 * @return The newly created [AssertionPlant] for the transformed subject.
 */
fun <K, V> Assert<Map<out K, V>>.asEntries(): Assert<Set<Map.Entry<K, V>>>
    = ExpectImpl.changeSubject.unreported(this) { it.entries }

/**
 * Turns `Assert<Map<K, V>>` into `Assert<Set<Map.Entry<K, V>>>` and makes the assertion that the assertions the given
 * [assertionCreator] might create hold.
 *
 * The transformation as such is not reflected in reporting.
 * Use `property(subject::entries)` if you want to show the transformation in reporting.
 *
 * @return The newly created [AssertionPlant] for the transformed subject.
 */
fun <K, V> Assert<Map<out K, V>>.asEntries(assertionCreator: Assert<Set<Map.Entry<K, V>>>.() -> Unit): Assert<Set<Map.Entry<K, V>>>
    = asEntries().addAssertionsCreatedBy(assertionCreator)
