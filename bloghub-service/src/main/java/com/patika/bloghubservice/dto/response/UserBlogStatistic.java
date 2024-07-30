package com.patika.bloghubservice.dto.response;

import java.util.List;

public record UserBlogStatistic(Integer blogCount, List<BlogStatistic> blogStatistics) {

}