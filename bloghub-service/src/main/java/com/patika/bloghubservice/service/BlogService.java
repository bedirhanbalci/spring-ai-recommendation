package com.patika.bloghubservice.service;

import com.patika.bloghubservice.client.RecommendationService;
import com.patika.bloghubservice.client.dto.RecommendationRequestList;
import com.patika.bloghubservice.client.dto.RecommendationResponseList;
import com.patika.bloghubservice.converter.BlogConverter;
import com.patika.bloghubservice.dto.request.BlogSaveRequest;
import com.patika.bloghubservice.dto.request.LikeBlogRequest;
import com.patika.bloghubservice.dto.response.BlogResponse;
import com.patika.bloghubservice.dto.response.BlogStatistic;
import com.patika.bloghubservice.dto.response.UserBlogStatistic;
import com.patika.bloghubservice.exception.BlogHubException;
import com.patika.bloghubservice.exception.ExceptionMessages;
import com.patika.bloghubservice.model.Blog;
import com.patika.bloghubservice.model.BlogComment;
import com.patika.bloghubservice.model.User;
import com.patika.bloghubservice.model.enums.BlogStatus;
import com.patika.bloghubservice.model.enums.StatusType;
import com.patika.bloghubservice.repository.BlogRepository;
import com.patika.bloghubservice.util.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final RecommendationService recommendationService;


    public BlogResponse createBlog(BlogSaveRequest request) {

        User foundUser = userService.getByEmail(request.getEmail());
        // 8. soru f şıkkı :• Sadece onaylanmış bir kullanıcı blog yayınlayabilir.
        if (!foundUser.getStatusType().equals(StatusType.APPROVED))
            throw new BlogHubException(ExceptionMessages.USER_STATUS_EXCEPTION);


        Blog blog = new Blog(request.getTitle(), request.getText(), foundUser);

        blogRepository.save(blog);

        return BlogConverter.toResponse(blog);
    }

    protected Blog getBlogByTitleForOtherService(String title) {
        return blogRepository.findByTitle(title).orElseThrow(() -> new BlogHubException(ExceptionMessages.BLOG_NOT_FOUND_EXCEPTION));
    }

    public BlogResponse getBlogByTitle(String title) {
        Blog foundBlog = blogRepository.findByTitle(title)
                .orElseThrow(() -> new BlogHubException(ExceptionMessages.BLOG_NOT_FOUND_EXCEPTION));

        return BlogConverter.toResponse(foundBlog);
    }


    public void addComment(String title, String email, String comment) {

        Blog foundBlog = getBlogByTitleForOtherService(title);

        User user = userService.getByEmail(email);

        BlogComment blogComment = new BlogComment(user, comment);

        foundBlog.getBlogCommentList().add(blogComment);

        blogRepository.addComment(title, foundBlog);

    }

    public void changeBlogStatus(BlogStatus blogStatus, String title) {
        Blog foundBlog = getBlogByTitleForOtherService(title);

        if (foundBlog.getBlogStatus().equals(BlogStatus.PUBLISHED) && blogStatus.equals(BlogStatus.DELETED)) {
            throw new BlogHubException(ExceptionMessages.CHANGE_BLOG_STATUS_EXCEPTION);
        }

        foundBlog.setBlogStatus(blogStatus);
    }

    protected List<Blog> getAll() {
        return blogRepository.findAll();
    }

    public List<BlogResponse> getAllBLogs(int size, int page) {
        List<BlogResponse> list = new ArrayList<>(blogRepository.findAll(new Pageable(size, page)).stream()
                .map(BlogConverter::toResponse)
                .toList());

        list.sort(Comparator.comparing(BlogResponse::getTitle));

        return list;
    }

    public Long getLikeCountByTitle(String title) {

        Blog blog = getBlogByTitleForOtherService(title);

        return blog.getUserLikeCounts().values().stream()
                .mapToLong(Integer::longValue)
                .sum();
    }


    //7. soru b şıkkı : bir kullanıcı sadece maksimum 50 kere beğenebilir
    public void likeBlog(LikeBlogRequest likeBlogRequest) {
        Blog blog = getBlogByTitleForOtherService(likeBlogRequest.title());
        User user = userService.getByEmail(likeBlogRequest.email());

        Integer likeCount = blog.getUserLikeCounts().get(user);
        // user daha önce like atmış ve  sayısı 50 den büyükse hata fırtlatır
        if (likeCount != null && likeCount >= 50) {
            throw new BlogHubException(ExceptionMessages.LIKE_COUNT_EXCEPTION);
        }

        // daha önce like atmamış(map te mevcut değil) ise 1 olarak kaydedilir. eğer daha önce like atmışsa eski değere bir eklenir.
        blog.getUserLikeCounts().merge(user, 1, Integer::sum);

        blogRepository.likeBlog(likeBlogRequest.title(), blog);
    }

    // 7. soru c şıkkı : • Kullanıcı kendi PUBLISHED ve DRAFT olan blog’larını getiren endpoint
    public List<BlogResponse> getAllDraftOrPublishedBlogsByEmail(String email) {
        List<Blog> userBlogs = blogRepository.findByUserEmail(email);

        return userBlogs.stream()
                .filter(blog -> blog.getBlogStatus().equals(BlogStatus.PUBLISHED) || blog.getBlogStatus().equals(BlogStatus.DRAFT))
                .map(BlogConverter::toResponse)
                .toList();
    }

    // 7. soru d şıkkı : • Kullanıcı kendi blog’larından DRAFT statüsünde olanları hard delete ile silebilir.
    public String hardDeleteBlogIsDraft(String title, String email) {
        // userın blogları çekildi
        List<Blog> userBlogs = blogRepository.findByUserEmail(email);

        //userın blogları arasından title göre ilgili blog bulundu
        Blog foundBlog = userBlogs.stream()
                .filter(blog -> blog.getTitle().equals(title))
                .findFirst().orElseThrow(() -> new BlogHubException(ExceptionMessages.BLOG_NOT_FOUND_EXCEPTION));

        // bulunan blog PUBLISHED status te ise silemeyiz
        if (foundBlog.getBlogStatus().equals(BlogStatus.PUBLISHED)) {
            throw new BlogHubException(ExceptionMessages.CHANGE_BLOG_STATUS_EXCEPTION);
        }

        // if e girmediyse DRAFT status tedir. silebiliriz.
        blogRepository.delete(foundBlog.getTitle());

        return foundBlog.getTitle();
    }


    // ODEV : blog istatistikeri

    // getTotalBlogCount : parametresiz -> toplam blog sayısını dönecek
    public Integer getTotalBlogCount() {
        return blogRepository.count();
    }

    // getBlogCountByStatus : status alacak -> toplam blog sayısını dönecek
    public Integer getBlogCountByStatus(BlogStatus blogStatus) {
        return blogRepository.findAllByBlogStatus(blogStatus).size();
    }

    // getBlogStatisticsByUser : email alacak :  user'a ait blog sayısı, her blog için toplam like sayısı ve toplam comment sayısı dönecek
    public UserBlogStatistic getBlogStatisticsByUser(String email) {
        User foundUser = userService.getByEmail(email);

        List<BlogStatistic> blogStatisticList = foundUser.getBlogList().stream()
                .map(blog -> new BlogStatistic(blog.getReadingCount(),
                        blog.getUserLikeCounts().values().stream()
                                .mapToLong(Integer::longValue)
                                .sum()
                        , blog.getBlogCommentList().size()))
                .toList();

        return new UserBlogStatistic(foundUser.getBlogList().size(), blogStatisticList);
    }

    // getBlogStatisticsByTitle : title alacak -> like count ve comment count dönecek
    public BlogStatistic getBlogStatisticsByTitle(String title) {
        Blog foundBlog = getBlogByTitleForOtherService(title);
        return new BlogStatistic(foundBlog.getReadingCount(),
                foundBlog.getUserLikeCounts().values().stream()
                        .mapToLong(Integer::longValue)
                        .sum(),
                foundBlog.getBlogCommentList().size());
    }

    public void readBlog(String title) {
        Blog foundBlog = getBlogByTitleForOtherService(title);
        foundBlog.setReadingCount(foundBlog.getReadingCount() + 1);
    }

    public Integer getBlogCountByKeyword(String keyword) {
        return blogRepository.countByTitleContains(keyword);
    }


    public List<BlogResponse> getBlogRecommendations(String emmail) {

        User user = userService.getByEmail(emmail);

        List<String> history = getAll().stream().filter(blog ->
                        blog.getUserLikeCounts().containsKey(user))
                .map(Blog::getTitle)
                .toList();

        if (history.isEmpty()) {
            throw new BlogHubException(ExceptionMessages.RECOMMENDATION_EXCEPTION);
        }

        List<String> repository = getAll().stream().map(Blog::getTitle).toList();

        RecommendationResponseList data = recommendationService.recommendations(new RecommendationRequestList(repository, history)).getData();

        return data.getBlogTitles().stream().map(this::getBlogByTitleForOtherService).map(BlogConverter::toResponse).toList();
    }
}
