package com.nexperia.challenge.core.servlets;

import com.nexperia.challenge.core.blog.BlogService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component(service = Servlet.class,
  property = {
    "sling.servlet.paths=/bin/nexperia/blogposts",
    "sling.servlet.methods=GET"
  })
public class BlogPostsServlet extends SlingSafeMethodsServlet {

  @Reference
  private BlogService blogService;

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
      throws ServletException, IOException {
    int page = parseInt(request.getParameter("page"), 1);
    int size = parseInt(request.getParameter("size"), 10);
    String sort = request.getParameter("sort");
    String q = request.getParameter("q");

    response.setContentType("application/json");
    try {
      List<Map<String, Object>> posts = blogService.fetchPosts(page, size, sort, q);
      mapper.writeValue(response.getWriter(), posts);
    } catch (Exception e) {
      response.setStatus(500);
      mapper.writeValue(response.getWriter(), Map.of("error", e.getMessage()));
    }
  }
  private int parseInt(String s, int def){
    try { return s==null?def:Integer.parseInt(s); } catch(Exception e){ return def; }
  }
}