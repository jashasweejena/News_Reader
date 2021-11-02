package com.example.newsreader

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.models.Source
import com.example.newsreader.data.source.DataSource
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import com.example.newsreader.util.IOnlineChecker
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

class NewsRepositoryTest {
    private val newsRemoteDataSource: DataSource = mockk()
    private val newsLocalDataSource: DataSource = mockk()
    private val onlineChecker: IOnlineChecker = mockk()
    private val repository = NewsRepository(newsLocalDataSource, newsRemoteDataSource, onlineChecker)

    @Test
    fun testGetRemoteNewsArticles() {
        val currentTime = System.currentTimeMillis()
        val article: Article = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = currentTime.toString(),
            source = Source(name = "Source", id = "123"),
            title = "Title",
            url = "https://www.ndr.de/nachrichten/niedersachsen/braunschweig_harz_goettingen/VW-Krise-Konzernchef-Diess-kuendigt-Jobabbau-in-Wolfsburg-an,vw5706.html",
            urlToImage = "https://www.ndr.de/nachrichten/niedersachsen/braunschweig_harz_goettingen/volkswagen2082_v-contentxl.jpg",
            id = 1L
            )
        var articlesObservables = Single.just(listOf(article))

        every { newsLocalDataSource.getArticles() } returns Single.just(listOf())
        every { onlineChecker.isOnline() } returns true
        every { newsLocalDataSource.saveArticles(listOf(article)) } returns Unit

        // Test data validation
        every { newsRemoteDataSource.getArticles() } returns articlesObservables

        var output = repository.getArticles().test()
        output.assertComplete()
        output.assertNoErrors()
        assertEquals(listOf(article), output.values().first())

        // Test error cases
        every { newsRemoteDataSource.getArticles() } returns Single.error(Exception())
        output = repository.getArticles().test()
        output.assertError(Exception::class.java)

    }
}