/*
 * Copyright (c) 2019 - 2020 KazamaWataru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nekox.tl.parser

import nekox.tl.*

internal fun ByteArray.readTlScheme(): List<String> = steps.fold(inputStream().reader().readLines()) { list, step -> step(list) }

private typealias ParseStep = List<String>.() -> List<String>

private val dropStart: ParseStep = {
    filter(String::isNotBlank).dropWhile { !it.startsWith(doubleSlashToken) }
}

private val splitClasses: ParseStep = {
    flatMap { line ->
        if (line.startsWith(doubleSlashToken + classAddressToken)) {
            val name = line.substringAfter(doubleSlashToken + classAddressToken).substringBefore(
                    descriptionAddressToken
            ).trim()
            val description = line.substringAfter(name).trim()
            listOf(doubleSlashToken + description, name)
        } else listOf(line)
    }
}

private val removeDashes: ParseStep = {
    fold(mutableListOf()) { acc, string ->
        if (string.startsWith(slashDashToken)) {
            val last = acc.last().trim()
            val text = string.substringAfter(slashDashToken).trim()
            val new = last + spaceToken + text
            acc[acc.lastIndex] = new
        } else {
            acc += string
        }
        acc
    }
}

private val splitParameters: ParseStep = {
    flatMap { line ->
        val splitted = line.split(spaceToken + addressToken)
        val first = splitted.first().trim()
        val other = splitted.drop(1)
        listOf(first) + other.map { doubleSlashToken + addressToken + it.trim() }
    }
}

private val splitAdditions: ParseStep = {
    flatMap { line ->
        val splitted = line.split(";").filter(String::isNotBlank)
        val first = splitted.first().trim()
        val prefix = first.substringBefore(spaceToken)
        val other = splitted.drop(1)
        val otherPrefixed = other.map { "$prefix$questionToken$spaceToken${it.capitalize().trim()}" }
        listOf(first) + otherPrefixed
    }
}

private val splitSentences: ParseStep = {
    flatMap { line ->
        val splitted = line.split(". ")
        val first = splitted.first()
        val prefix = first.substringBefore(spaceToken)
        val other = splitted.drop(1)
        val otherPrefixed = other.map { "$prefix$spaceToken${it.trim()}" }
        listOf(first) + otherPrefixed
    }
}

private val removeSlashes: ParseStep = {
    map { it.substringAfter(doubleSlashToken) }
}

private val steps: List<ParseStep> = listOf(
        dropStart,
        splitClasses,
        removeDashes,
        splitParameters,
        splitAdditions,
        splitSentences,
        removeSlashes
)
