package org.uhafactory.excel;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 */
public class XmlExcelStreamGeneraterTest {

    @Test
    public void testEscape() {
        XmlExcelStreamGenerator generater = new XmlExcelStreamGenerator();
        assertThat(generater.escape("abcd\""), is("abcd&#34;"));
        assertThat(generater.escape("abcd'"), is("abcd&#39;"));
        assertThat(generater.escape("abcd<"), is("abcd&#60;"));
        assertThat(generater.escape("abcd>"), is("abcd&#62;"));
        assertThat(generater.escape("abcd&"), is("abcd&#38;"));

        assertThat(generater.escape("&ac><'\""), is("&#38;ac&#62;&#60;&#39;&#34;"));
        assertThat(generater.escape("&ac>&<'\""), is("&#38;ac&#62;&#38;&#60;&#39;&#34;"));
    }
}