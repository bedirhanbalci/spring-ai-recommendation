package com.patika.bloghubservice.util;

public record Pageable(int size, int page) {

    public int getOffset() {
        return size * page;
    }
}
