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
    private val article: Article = Article(
        author = "Author",
        content = "Content",
        description = "Description",
        publishedAt = System.currentTimeMillis().toString(),
        source = Source(name = "Source", id = "123"),
        title = "Title",
        url = "https://www.ndr.de/nachrichten/niedersachsen/braunschweig_harz_goettingen/VW-Krise-Konzernchef-Diess-kuendigt-Jobabbau-in-Wolfsburg-an,vw5706.html",
        urlToImage = "https://www.ndr.de/nachrichten/niedersachsen/braunschweig_harz_goettingen/volkswagen2082_v-contentxl.jpg",
        id = 1L
    )

    private val articleTwo: Article = article.copy(id = 2L)
    private val articlesList = listOf(article, articleTwo)

    private val articlesObservables = Single.just(articlesList)

    private val repository =
        NewsRepository(newsLocalDataSource, newsRemoteDataSource, onlineChecker)

    @Test
    fun `test remote data source when local is empty and device is offline`() {

        every { newsLocalDataSource.getArticles() } returns Single.just(listOf())
        every { onlineChecker.isOnline() } returns false
        every { newsLocalDataSource.saveArticles(articlesList) } returns Unit

        // Test data validation
        every { newsRemoteDataSource.getArticles() } returns articlesObservables

        var output = repository.getArticles().test()
        output.assertComplete()
        output.assertNoErrors()
        assertEquals(articlesList, output.values().first())

        // Test error cases
        every { newsRemoteDataSource.getArticles() } returns Single.error(Exception())
        output = repository.getArticles().test()
        output.assertError(Exception::class.java)
    }

    @Test
    fun `test remote data source device is online and local is empty`() {

        every { newsLocalDataSource.getArticles() } returns Single.just(listOf())
        every { onlineChecker.isOnline() } returns true
        every { newsLocalDataSource.saveArticles(articlesList) } returns Unit

        // Test data validation
        every { newsRemoteDataSource.getArticles() } returns articlesObservables

        var output = repository.getArticles().test()
        output.assertComplete()
        output.assertNoErrors()
        assertEquals(articlesList, output.values().first())

        // Test error cases
        every { newsRemoteDataSource.getArticles() } returns Single.error(Exception())
        output = repository.getArticles().test()
        output.assertError(Exception::class.java)
    }


    @Test
    fun `test fetch from local database when device is offline and local is not empty`() {

        every { newsLocalDataSource.getArticles() } returns articlesObservables
        every { onlineChecker.isOnline() } returns false
        every { newsLocalDataSource.saveArticles(articlesList) } returns Unit

        every { newsRemoteDataSource.getArticles() } returns Single.just(listOf(article))

        var output = repository.getArticles().test()
        output.assertComplete()
        output.assertNoErrors()
        assertEquals(articlesList, output.values().first())

        // Test error cases
        every { newsLocalDataSource.getArticles() } returns Single.error(Exception())
        output = repository.getArticles().test()
        output.assertError(Exception::class.java)
    }
}