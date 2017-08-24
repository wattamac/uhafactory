package org.uhafactory.paging;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 */
public class OneBasedPagedRequestTest {

    @Test(expected = IllegalArgumentException.class)
    public void test_pageNumberInvalid(){
        new OneBasedPagedRequest(0, 1);
    }
    @Test(expected = IllegalArgumentException.class)
    public void test_pageSizeInvalid(){
        new OneBasedPagedRequest(1, -1);
    }

    @Test
    public void testGetOffSet(){
        OneBasedPagedRequest pagedRequest = new OneBasedPagedRequest(1, 10);
        assertThat(pagedRequest.getOffset()).isEqualTo(0);

        pagedRequest = new OneBasedPagedRequest(2, 20);
        assertThat(pagedRequest.getOffset()).isEqualTo(20);
    }
}