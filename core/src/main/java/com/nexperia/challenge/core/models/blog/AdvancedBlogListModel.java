package com.nexperia.challenge.core.models.blog;

import com.nexperia.challenge.core.blog.BlogService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Model(adaptables = {SlingHttpServletRequest.class},
       defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class AdvancedBlogListModel {

  @SlingObject
  private SlingHttpServletRequest request;

  @OSGiService
  private BlogService blogService;

  @ValueMapValue
  private int pageSize;

  @ValueMapValue
  private String sortBy;

  @ValueMapValue
  private String searchPlaceholder;

  private List<Map<String, Object>> initialPosts;

  @PostConstruct
  protected void init(){
    try{
      initialPosts = blogService.fetchPosts(1, pageSize>0?pageSize:10, sortBy, null);
    }catch(Exception ignored){}
  }

  public List<Map<String, Object>> getInitialPosts(){ return initialPosts; }
  public String getApi(){ return "/bin/nexperia/blogposts"; }
  public int getPageSize(){ return pageSize>0?pageSize:10; }
  public String getSortBy(){ return sortBy==null?"":sortBy; }
  public String getSearchPlaceholder(){ return searchPlaceholder==null?"Search posts...":searchPlaceholder; }
}