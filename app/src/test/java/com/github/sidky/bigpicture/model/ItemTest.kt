package com.github.sidky.bigpicture.model

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it

import org.junit.jupiter.api.Assertions.*
import java.util.*

internal class ItemTest : Spek({
    val MOCK_TITLE = "How to mock a killing bird"
    val MOCK_LINK = "http://zombo.com"
    val MOCK_PUB_DATE = Date()
    val DESCRIPTION_WITH_IMAGE = "  <img src=http://fake.com/image></img> foo bar baz bad"
    val DESCRIPTION_WITH_IMAGE_IN_QUOTE = "  <img src=\"http://fake.com/image\"/> foo bar baz bad"
    val DESCRIPTION_WITH_MULTIPLE_IMAGE_IN_QUOTE = "  <img src=\"http://fake.com/image1\"/> <img src=\"http://fake.com/image2\"/>foo bar baz bad"
    val DESCRIPTION_WITHOUT_IMAGE = "<p>boo hoo, no image</p>"
    val DESCRIPTION_WITH_MALFORMED_TEXT = "<img src="

    given("Valid image in the item description") {
        val item = Item(title = MOCK_TITLE,
                description = DESCRIPTION_WITH_IMAGE,
                link = MOCK_LINK,
                pubDate = MOCK_PUB_DATE)

        it("should parse the first image") {
            assertEquals("http://fake.com/image", item.image())
        }
    }

    given("Valid image in quote in item description") {
        val item = Item(title = MOCK_TITLE,
                description = DESCRIPTION_WITH_IMAGE_IN_QUOTE,
                link = MOCK_LINK,
                pubDate = MOCK_PUB_DATE)

        it("should parse the first image") {
            assertEquals("http://fake.com/image", item.image())
        }
    }

    given("No image item description") {
        val item = Item(title = MOCK_TITLE,
                description = DESCRIPTION_WITHOUT_IMAGE,
                link = MOCK_LINK,
                pubDate = MOCK_PUB_DATE)

        it("should parse the first image") {
            assertNull(item.image())
        }
    }

    given("Multiple image in item description") {
        val item = Item(title = MOCK_TITLE,
                description = DESCRIPTION_WITH_MULTIPLE_IMAGE_IN_QUOTE,
                link = MOCK_LINK,
                pubDate = MOCK_PUB_DATE)

        it("should parse the first image") {
            assertEquals("http://fake.com/image1", item.image())
        }
    }

    given("Malformed item description") {
        val item = Item(title = MOCK_TITLE,
                description = DESCRIPTION_WITH_MALFORMED_TEXT,
                link = MOCK_LINK,
                pubDate = MOCK_PUB_DATE)

        it("should parse the first image") {
            assertEquals("", item.image())
        }
    }

})