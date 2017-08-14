package org.uhafactory.paging;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lineplus on 2017. 5. 1..
 */
@Getter
@Setter
public class OneBasedPagedResult<T> implements Serializable {
	private static final long serialVersionUID = 5493938339397841203L;
	private OneBasedPagedRequest request;
	private long totalElements;

	private List<T> content;

	public OneBasedPagedResult(List<T> result, OneBasedPagedRequest pagedRequest) {
		this.request = pagedRequest;
		this.totalElements = CollectionUtils.isEmpty(result) ? 0 : result.size();
		this.content = subList(result, pagedRequest);
	}

    private List<T> subList(List<T> result, OneBasedPagedRequest pagedRequest) {
        if(pagedRequest.getOffset() > result.size()){
            return ImmutableList.of();
        }

	    int startIndex = pagedRequest.getOffset();
	    int endIndex = Math.min(result.size(), startIndex + pagedRequest.getPageSize());

        return Lists.newArrayList(result.subList(startIndex, endIndex));
    }

    public int getPageNumber() {
		return request.getPageNumber();
	}

	public int getPageSize() {
		return request.getPageSize();
	}
}
