package org.uhafactory.paging;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 */
public class OneBasedPagedRequestTest {

    @Test
    public void test_pageNumberInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new OneBasedPagedRequest(0, 1));
    }

    @Test
    public void test_pageSizeInvalid() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new OneBasedPagedRequest(1, -1));
    }

    @Test
    public void testGetOffSet() {
        OneBasedPagedRequest pagedRequest = new OneBasedPagedRequest(1, 10);
        assertThat(pagedRequest.getOffset()).isEqualTo(0);

        pagedRequest = new OneBasedPagedRequest(2, 20);
        assertThat(pagedRequest.getOffset()).isEqualTo(20);
    }
}