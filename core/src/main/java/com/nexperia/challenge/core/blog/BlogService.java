package com.nexperia.challenge.core.blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
  List<Map<String, Object>> fetchPosts(int page, int size, String sortBy, String q) throws Exception;
  List<Map<String, Object>> related(String postId, int max) throws Exception;
}