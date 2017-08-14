package org.uhafactory.paging;

import org.junit.Test;

import static org.assertj.core.api.Java6Assertions.assertThat;

/**
 * Created by lineplus on 2017. 5. 1..
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