package io.levelsoftware.androidplayground.extension

import java.util.Random

/**
 * Generate a random number within a range.
 *
 * Usage: (0..10).random()
 *
 * https://stackoverflow.com/a/45687695/5125812
 */
fun ClosedRange<Int>.random() = Random().nextInt(endInclusive - start) + start