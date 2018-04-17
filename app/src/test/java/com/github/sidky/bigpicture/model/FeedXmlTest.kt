package com.github.sidky.bigpicture.model

import android.util.Xml
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.jupiter.api.Assertions.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.util.*

object FeedXmlSpek : Spek({
    given("XML for an item") {
        val xml: String = """
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>Globe staff photos of the month, March 2018</title>http:
            <description>&lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/30/BostonGlobe.com/Metro/Images/kreiter_wetspringweather4_met.jpg>]]>&lt;br /&gt;Three-year-old Ellie Daiute of Braintree took big steps to make it up up the stairs at City Hall Plaza. Higher temperatures near the end of the month made the day feel like spring.(Suzanne Kreiter/Globe Staff)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html</guid>
            <categories/>
            <pubDate>Mon, 02 Apr 2018 22:08:38 GMT</pubDate>
        </item>"""

        on("parse") {
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            val reader = StringReader(xml)
            parser.setInput(reader)
            parser.nextTag()
            val item = Item.parse(parser)

            it ("should parse") {
                assertNotNull(item)
            }

            it("should parse title correctly") {
                assertEquals("Globe staff photos of the month, March 2018", item?.title)
            }

            it("should parse description correctly") {
                assertEquals("<br /><img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/30/BostonGlobe.com/Metro/Images/kreiter_wetspringweather4_met.jpg><br />Three-year-old Ellie Daiute of Braintree took big steps to make it up up the stairs at City Hall Plaza. Higher temperatures near the end of the month made the day feel like spring.(Suzanne Kreiter/Globe Staff)<br /><br />",
                        item?.description)
            }

            it("should read the link correctly") {
                assertEquals("https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html",
                        item?.link)
            }

            it("should parse the date correctly") {
                val PUB_DATE = Calendar.getInstance()
                PUB_DATE.set(2018, 3, 2, 22, 8, 38)
                PUB_DATE.set(Calendar.MILLISECOND, 0)
                PUB_DATE.timeZone = TimeZone.getTimeZone("UTC")

                assertEquals(PUB_DATE.time, item?.pubDate)
            }
        }
    }

    given("XML for channel") {
        val xml = """
    <channel>
        <title>Big Picture</title>
        <description>News Stories in Photographs from the Boston Globe.</description>
        <language>en-us</language>
        <copyright>Copyright 2014</copyright>
        <generator>Methode</generator>
        <image>
            <url>http://www.bostonglobe.com/rw/SysConfig/WebPortal/BostonGlobe/Framework/images/globe-logo-250px.png</url>
            <title>The Boston Globe</title>
            <link>http://www.bostonglobe.com</link>
        </image>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>Remembering Martin Luther King Jr.</title>http:<description>A look at some of the pivotal moments in the life of civil rights leader Martin Luther King Jr. as we mark the 50th anniversary of the tragic end of his life on April 4, 1968.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/28/BostonGlobe.com/Metro/Images/AP_560322095.jpg>]]>&lt;br /&gt;The Rev. Martin Luther King Jr. was welcomed with a kiss from his wife Coretta after leaving court in Montgomery, Ala., March 22, 1956. King was found guilty of conspiracy to boycott city buses in a campaign to desegregate the bus system, but a judge suspended his ${'$'}500 fine pending appeal.
            (Gene Herrick/Associated Press)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/04/03/remembering-martin-luther-king-april/9GcOZCbFbNlR5fStF13wDK/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/04/03/remembering-martin-luther-king-april/9GcOZCbFbNlR5fStF13wDK/story.html</guid>
            <categories/>
            <pubDate>Tue, 03 Apr 2018 19:50:57 GMT</pubDate>
        </item>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>Globe staff photos of the month, March 2018</title>http:<description> Here’s a look at some of the best images taken by Globe photographers last month: multiple nor’easters, St. Patrick’s Day, protesting gun violence, hockey and basketball state high school championships, Good Friday, and the start of Red Sox season.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/30/BostonGlobe.com/Metro/Images/kreiter_wetspringweather4_met.jpg>]]>&lt;br /&gt;Three-year-old Ellie Daiute of Braintree took big steps to make it up up the stairs at City Hall Plaza. Higher temperatures near the end of the month made the day feel like spring.
            (Suzanne Kreiter/Globe Staff)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html</guid>
            <categories/>
            <pubDate>Mon, 02 Apr 2018 22:08:38 GMT</pubDate>
        </item>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>2018 Paralympic Winter Games</title>http:<description>Scenes from the Paralympics in PyeongChang, South Korea taking place March 9-18. 670 athletes with disabilities from around the world compete in 80 events in six different sports.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/16/BostonGlobe.com/BigPicture/Images/2f5c7201abc54e6baf090e04dd1883e6-2f5c7201abc54e6baf090e04dd1883e6-0.jpg>]]>&lt;br /&gt;Australia’s Victoria Pendergast competes during the Alpine Skiing Sitting Men’s Giant Slalom run 2 at the Jeongseon Alpine Centre in Jeongseon, South Korea on March 14.
            (Joel Marklund/OIS/IOC via AP)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/03/16/paralympic-winter-games/hHVXJtIdly8xVLTDQLC4EO/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/03/16/paralympic-winter-games/hHVXJtIdly8xVLTDQLC4EO/story.html</guid>
            <categories/>
            <pubDate>Mon, 19 Mar 2018 16:44:42 GMT</pubDate>
        </item>
    </channel>
            """

        on("parse") {
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            val reader = StringReader(xml)
            parser.setInput(reader)
            parser.nextTag()
            val channel = Channel.parse(parser)

            it("should parse a channel") {
                assertNotNull(channel)
            }

            it("should parse the title correctly") {
                assertEquals("Big Picture", channel?.title)
            }

            it("should parse description correctly") {
                assertEquals("News Stories in Photographs from the Boston Globe.",
                        channel?.description)
            }
            it("should parse 3 items") {
                assertEquals(3, channel?.items?.size)
            }
        }
    }

    given("XML for rss") {
        val xml = """
<rss xmlns:media="http://search.yahoo.com/mrss/" version="2.0">
    <channel>
        <title>Big Picture</title>
        <description>News Stories in Photographs from the Boston Globe.</description>
        <language>en-us</language>
        <copyright>Copyright 2014</copyright>
        <generator>Methode</generator>
        <image>
            <url>http://www.bostonglobe.com/rw/SysConfig/WebPortal/BostonGlobe/Framework/images/globe-logo-250px.png</url>
            <title>The Boston Globe</title>
            <link>http://www.bostonglobe.com</link>
        </image>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>Remembering Martin Luther King Jr.</title>http:<description>A look at some of the pivotal moments in the life of civil rights leader Martin Luther King Jr. as we mark the 50th anniversary of the tragic end of his life on April 4, 1968.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/28/BostonGlobe.com/Metro/Images/AP_560322095.jpg>]]>&lt;br /&gt;The Rev. Martin Luther King Jr. was welcomed with a kiss from his wife Coretta after leaving court in Montgomery, Ala., March 22, 1956. King was found guilty of conspiracy to boycott city buses in a campaign to desegregate the bus system, but a judge suspended his ${'$'}500 fine pending appeal.
            (Gene Herrick/Associated Press)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/04/03/remembering-martin-luther-king-april/9GcOZCbFbNlR5fStF13wDK/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/04/03/remembering-martin-luther-king-april/9GcOZCbFbNlR5fStF13wDK/story.html</guid>
            <categories/>
            <pubDate>Tue, 03 Apr 2018 19:50:57 GMT</pubDate>
        </item>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>Globe staff photos of the month, March 2018</title>http:<description> Here’s a look at some of the best images taken by Globe photographers last month: multiple nor’easters, St. Patrick’s Day, protesting gun violence, hockey and basketball state high school championships, Good Friday, and the start of Red Sox season.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/30/BostonGlobe.com/Metro/Images/kreiter_wetspringweather4_met.jpg>]]>&lt;br /&gt;Three-year-old Ellie Daiute of Braintree took big steps to make it up up the stairs at City Hall Plaza. Higher temperatures near the end of the month made the day feel like spring.
            (Suzanne Kreiter/Globe Staff)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/04/02/globe-staff-photos-month-march/fx1OqcVbKEyCnPCqqMePfK/story.html</guid>
            <categories/>
            <pubDate>Mon, 02 Apr 2018 22:08:38 GMT</pubDate>
        </item>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>2018 Paralympic Winter Games</title>http:<description>Scenes from the Paralympics in PyeongChang, South Korea taking place March 9-18. 670 athletes with disabilities from around the world compete in 80 events in six different sports.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/16/BostonGlobe.com/BigPicture/Images/2f5c7201abc54e6baf090e04dd1883e6-2f5c7201abc54e6baf090e04dd1883e6-0.jpg>]]>&lt;br /&gt;Australia’s Victoria Pendergast competes during the Alpine Skiing Sitting Men’s Giant Slalom run 2 at the Jeongseon Alpine Centre in Jeongseon, South Korea on March 14.
            (Joel Marklund/OIS/IOC via AP)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/03/16/paralympic-winter-games/hHVXJtIdly8xVLTDQLC4EO/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/03/16/paralympic-winter-games/hHVXJtIdly8xVLTDQLC4EO/story.html</guid>
            <categories/>
            <pubDate>Mon, 19 Mar 2018 16:44:42 GMT</pubDate>
        </item>
        <item xmlns:atom="http://www.w3.org/2005/Atom">
            <title>Back to back nor’easter storms slam Massachusetts</title>http:<description>Two nor’easter storms in the past week have brought snow, power outages and flooding to towns across Massachusetts.
            &lt;br /&gt;<![CDATA[<img src=http://c.o0bg.com/rf/image_960w/Boston/2011-2020/2018/03/02/BostonGlobe.com/Metro/Images/tlumacki_stormflooding_metro708.jpg>]]>&lt;br /&gt;A woman with a child in her arms was rescued from the rising waters in the Houghs Neck section of Quincy on Friday, March 2.
            (John Tlumacki/Globe Staff)&lt;br /&gt;&lt;br /&gt;</description>
            <link>https://www.bostonglobe.com/news/bigpicture/2018/03/08/back-back-nor-easter-storms-slam-mass/OP4gvvXiFZ4n92WgPrCkeK/story.html</link>
            <guid>https://www.bostonglobe.com/news/bigpicture/2018/03/08/back-back-nor-easter-storms-slam-mass/OP4gvvXiFZ4n92WgPrCkeK/story.html</guid>
            <categories/>
            <pubDate>Thu, 08 Mar 2018 22:45:47 GMT</pubDate>
        </item>
    </channel>
</rss>
            """

        on("parse") {
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            val reader = StringReader(xml)
            parser.setInput(reader)
            parser.nextTag()
            val rss = Rss.parse(parser)

            it("should parse a rss feed") {
                assertNotNull(rss)
            }

            it("should parse a correct channel feed") {
                assertNotNull(rss.channel)
            }

            it("should parse 4 items") {
                assertEquals(4, rss.channel?.items?.size)
            }
        }

        on("parseAsRoot") {
            val parser = XmlPullParserFactory.newInstance().newPullParser()
            val reader = StringReader(xml)
            parser.setInput(reader)
            val rss = Rss.parseAsRoot(parser)

            it("should parse RSS feed correctly given the parser directly") {
                assertNotNull(rss)
            }
        }
    }
})