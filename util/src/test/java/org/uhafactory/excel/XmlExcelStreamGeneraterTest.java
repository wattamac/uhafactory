package org.uhafactory.excel;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class XmlExcelStreamGeneraterTest {

    @Test
    public void testEscape() {
        XmlExcelStreamGenerator generater = new XmlExcelStreamGenerator();
        assertThat(generater.escape("abcd\"")).isEqualTo("abcd&#34;");
        assertThat(generater.escape("abcd'")).isEqualTo("abcd&#39;");
        assertThat(generater.escape("abcd<")).isEqualTo("abcd&#60;");
        assertThat(generater.escape("abcd>")).isEqualTo("abcd&#62;");
        assertThat(generater.escape("abcd&")).isEqualTo("abcd&#38;");

        assertThat(generater.escape("&ac><'\"")).isEqualTo("&#38;ac&#62;&#60;&#39;&#34;");
        assertThat(generater.escape("&ac>&<'\"")).isEqualTo("&#38;ac&#62;&#38;&#60;&#39;&#34;");
    }
}