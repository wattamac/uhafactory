package org.uhafactory.paging;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;


/**
 */
public class OneBasedPagedResultTest {

    @Test
    public void test(){
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6");
        OneBasedPagedRequest request = new OneBasedPagedRequest(1, 2);
        OneBasedPagedResult result = new OneBasedPagedResult(list, request);

        assertThat(result.getTotalElements()).isEqualTo(list.size());
        assertThat(result.getContent()).isEqualTo(Lists.newArrayList("1", "2"));
    }

    @Test
    public void test_page2(){
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6");
        OneBasedPagedRequest request = new OneBasedPagedRequest(2, 2);
        OneBasedPagedResult result = new OneBasedPagedResult(list, request);

        assertThat(result.getTotalElements()).isEqualTo(list.size());
        assertThat(result.getContent()).isEqualTo(Lists.newArrayList("3", "4"));
    }

    @Test
    public void test_page_over(){
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        OneBasedPagedRequest request = new OneBasedPagedRequest(3, 3);
        OneBasedPagedResult result = new OneBasedPagedResult(list, request);

        assertThat(result.getTotalElements()).isEqualTo(list.size());
        assertThat(result.getContent()).isEqualTo(Lists.newArrayList("7"));
    }

    @Test
    public void test_page_over_2(){
        List<String> list = Lists.newArrayList("1", "2", "3", "4", "5", "6", "7");
        OneBasedPagedRequest request = new OneBasedPagedRequest(4, 3);
        OneBasedPagedResult result = new OneBasedPagedResult(list, request);

        assertThat(result.getTotalElements()).isEqualTo(list.size());
        assertThat(result.getContent()).isEmpty();
    }
}