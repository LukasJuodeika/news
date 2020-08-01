package lt.lukas.newsapp.exceptions

import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.net.UnknownHostException

class NetworkExceptionResolverTest {

    @Mock
    lateinit var errorDisplay: ErrorDisplay

    lateinit var networkExceptionResolver: NetworkExceptionResolver

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        networkExceptionResolver = NetworkExceptionResolver(errorDisplay)
    }

    @Test
    fun exception_unknownHost() {
        // Assemble
        val exception = UnknownHostException()

        // Act
        networkExceptionResolver.exception(exception)

        // Assert
        verify(errorDisplay).showNoInternetError()
        verify(errorDisplay, never()).showUnableToFetchData()
    }

    @Test
    fun exception_default() {
        // Assemble
        val exception = Exception()

        // Act
        networkExceptionResolver.exception(exception)

        // Assert
        verify(errorDisplay, never()).showNoInternetError()
        verify(errorDisplay).showUnableToFetchData()
    }
}
