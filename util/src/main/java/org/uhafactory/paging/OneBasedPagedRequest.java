package org.uhafactory.paging;

import com.google.common.base.Objects;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.io.Serializable;

/**
 */
@Getter
@Setter
public class OneBasedPagedRequest implements Pageable, Serializable {
    private static final long serialVersionUID = -3139478503688243180L;

    private int pageNumber;
    private int pageSize;

    public OneBasedPagedRequest(int page, int size) {
        Assert.isTrue(page > 0, "pageNumber must be higher than 0");
        Assert.isTrue(size > 0, "pageSize must be higher than 0");
        this.pageNumber = page;
        this.pageSize = size;
    }

    @Override
    public int getOffset() {
        return (pageNumber - 1) * pageSize;
    }


    @Override
    public Sort getSort() {
        return null;
    }

    @Override
    public Pageable next() {
        return new OneBasedPagedRequest(this.getPageNumber() + 1, this.getPageSize());
    }

    @Override
    public Pageable previousOrFirst() {
        return hasPrevious()? new OneBasedPagedRequest(this.getPageNumber() - 1, this.getPageSize()) : this;
    }

    @Override
    public Pageable first() {
        return new OneBasedPagedRequest(1, this.getPageSize());
    }

    @Override
    public boolean hasPrevious() {
        return this.getPageNumber() > 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneBasedPagedRequest request = (OneBasedPagedRequest) o;
        return pageNumber == request.pageNumber &&
                pageSize == request.pageSize;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pageNumber, pageSize);
    }
}
