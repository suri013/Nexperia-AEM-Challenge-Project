# Nexperia AEM Challenge (GitHub-ready)

Implements **AdvancedBlogList** with:
- **Component**: `/apps/nexperia/challenge/components/advancedbloglist`
- **ClientLib**: `nexperia.advancedbloglist`
- **Servlet**: `GET /bin/nexperia/blogposts` (params: `page`, `size`, `sort`, `q`)
- **Sling Model**: `AdvancedBlogListModel`
- **OSGi Service**: `BlogServiceImpl` (external API + in-memory TTL cache, configurable)
- **Config**: `/apps/nexperia/osgiconfig/*.cfg.json`

## Build
Requires AEM SDK at `http://localhost:4502` for full install. For compile only:
```bash
mvn clean install
```
For full install (if AEM SDK available):
```bash
mvn -PautoInstallSinglePackage clean install
```

## Mapping to requirements
- Pagination, sorting, search via servlet + JS.
- Related posts method available in service.
- Accessible markup + responsive CSS scaffold.
- Sling Models, OSGi configs, server-side error handling, JUnit-ready.

## Submit
Push this project to GitHub as `nexperia-code-challenge` and send the link.