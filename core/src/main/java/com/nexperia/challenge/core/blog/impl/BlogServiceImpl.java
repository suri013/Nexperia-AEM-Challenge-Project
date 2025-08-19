package com.nexperia.challenge.core.blog.impl;

import com.nexperia.challenge.core.blog.BlogService;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@ObjectClassDefinition(name = "Nexperia Blog Service Config",
        description = "External API + cache controls")
@interface Config {
  @AttributeDefinition(name = "External API URL",
          description = "e.g. https://jsonplaceholder.typicode.com/posts")
  String api_url() default "https://jsonplaceholder.typicode.com/posts";

  @AttributeDefinition(name = "Cache TTL seconds")
  int cache_ttl_seconds() default 120;
}

@Component(service = BlogService.class)
@Designate(ocd = Config.class)
public class BlogServiceImpl implements BlogService {

  private volatile String apiUrl;
  private volatile int ttlSeconds;

  private final HttpClient http = HttpClient.newBuilder()
      .connectTimeout(Duration.ofSeconds(10)).build();
  private final ObjectMapper mapper = new ObjectMapper();

  static class CacheEntry {
    List<Map<String, Object>> data;
    long ts;
    CacheEntry(List<Map<String, Object>> d, long t){ this.data=d; this.ts=t; }
  }
  private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

  @Activate @Modified
  protected void activate(Config cfg){
    this.apiUrl = cfg.api_url();
    this.ttlSeconds = cfg.cache_ttl_seconds();
  }

  @Override
  public List<Map<String, Object>> fetchPosts(int page, int size, String sortBy, String q) throws Exception {
    List<Map<String, Object>> all = getAll();

    if(q != null && !q.isBlank()){
      String qq = q.toLowerCase(Locale.ROOT);
      all = all.stream().filter(m ->
        String.valueOf(m.getOrDefault("title","")).toLowerCase(Locale.ROOT).contains(qq) ||
        String.valueOf(m.getOrDefault("body","")).toLowerCase(Locale.ROOT).contains(qq)
      ).collect(Collectors.toList());
    }

    if("title".equalsIgnoreCase(sortBy)){
      all.sort(Comparator.comparing(m -> String.valueOf(m.getOrDefault("title",""))));
    } else if("author".equalsIgnoreCase(sortBy)){
      all.sort(Comparator.comparing(m -> String.valueOf(m.getOrDefault("userId",""))));
    } else if("category".equalsIgnoreCase(sortBy)){
      all.sort(Comparator.comparing(m -> String.valueOf(m.getOrDefault("category",""))));
    }

    int from = Math.max(0, (page-1)*size);
    int to = Math.min(all.size(), from + size);
    if(from >= to) return Collections.emptyList();
    return all.subList(from, to);
  }

  @Override
  public List<Map<String, Object>> related(String postId, int max) throws Exception {
    List<Map<String, Object>> all = getAll();
    String userId = null;
    for(Map<String,Object> m : all){
      if(String.valueOf(m.get("id")).equals(postId)){
        userId = String.valueOf(m.get("userId"));
        break;
      }
    }
    if(userId == null) return Collections.emptyList();
    String uid = userId;
    return all.stream()
      .filter(m -> !String.valueOf(m.get("id")).equals(postId))
      .filter(m -> String.valueOf(m.get("userId")).equals(uid))
      .limit(max)
      .collect(Collectors.toList());
  }

  private List<Map<String, Object>> getAll() throws Exception {
    String key = "ALL";
    long now = System.currentTimeMillis();
    CacheEntry e = cache.get(key);
    if(e != null && (now - e.ts) < (ttlSeconds * 1000L)){
      return e.data;
    }
    HttpRequest req = HttpRequest.newBuilder(URI.create(apiUrl)).GET().build();
    HttpResponse<String> resp = http.send(req, HttpResponse.BodyHandlers.ofString());
    if(resp.statusCode() >= 200 && resp.statusCode() < 300){
      List<Map<String,Object>> list = mapper.readValue(resp.body(), new TypeReference<List<Map<String,Object>>>(){});
      cache.put(key, new CacheEntry(list, now));
      return list;
    }
    return Collections.emptyList();
  }
}