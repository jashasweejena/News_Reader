package com.example.newsreader

import com.example.newsreader.data.models.Article
import com.example.newsreader.data.models.Source
import com.example.newsreader.data.source.NewsRepository
import com.example.newsreader.data.source.remote.ArticlesRemoteDataSource
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.Exception

class NewsRepositoryTest {

    @Test
    fun testGetNewsArticles() {
        val newsRemoteDataSource: ArticlesRemoteDataSource = mockk()
        val repository = NewsRepository(newsRemoteDataSource)

        val currentTime = System.currentTimeMillis()
        val article: Article = Article(
            author = "Author",
            content = "Content",
            description = "Description",
            publishedAt = currentTime.toString(),
            source = Source(name = "Source", id = 1),
            title = "Title",
            url = "https://www.ndr.de/nachrichten/niedersachsen/braunschweig_harz_goettingen/VW-Krise-Konzernchef-Diess-kuendigt-Jobabbau-in-Wolfsburg-an,vw5706.html",
            urlToImage = "https://www.ndr.de/nachrichten/niedersachsen/braunschweig_harz_goettingen/volkswagen2082_v-contentxl.jpg",
        )
        var articlesObservables = Single.just(listOf(article))

        // Test data validation
        every { newsRemoteDataSource.getNewsArticles() } returns articlesObservables

        var output = repository.getNewsArticles().test()
        output.assertComplete()
        output.assertNoErrors()
        assertEquals(listOf(article), output.values().first())

        // Test error cases
        every { newsRemoteDataSource.getNewsArticles() } returns Single.error(Exception())
        output = repository.getNewsArticles().test()
        output.assertError(Exception::class.java)

    }
}