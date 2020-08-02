package lt.lukas.newsapp

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test


class DateFormattersTest {

    @Test
    fun formatNewsapiDate_valid() {
        // Assemble
        val newsApidate = "2020-08-02T17:37:00Z"

        // Act
        val resultDate = DateFormatters.formatNewsapiDate(newsApidate)

        // Assert
        assertThat(resultDate).isEqualTo("2020-August-02 17:37")
    }

    @Test
    fun formatNewsapiDate_empty() {
        // Assemble
        val newsApidate = ""

        // Act
        val resultDate = DateFormatters.formatNewsapiDate(newsApidate)

        // Assert
        assertThat(resultDate).isEqualTo("")
    }

    @Test
    fun formatNewsapiDate_malformed() {
        // Assemble
        val newsApidate = "malformedDate"

        // Act
        val resultDate = DateFormatters.formatNewsapiDate(newsApidate)

        // Assert
        assertThat(resultDate).isEqualTo("")
    }
}
