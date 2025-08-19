(function(){
  function $(sel, ctx){ return (ctx||document).querySelector(sel); }
  function escapeHtml(s){ return String(s).replace(/[&<>"']/g, m=>({ '&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;' }[m])); }
  function fetchPage(root, page){
    var api = root.getAttribute('data-api') || '/bin/nexperia/blogposts';
    var q = $('.abl-search', root).value || '';
    var sort = $('.abl-sort', root).value || '';
    var size = root.getAttribute('data-size') || '10';
    fetch(api + '?page=' + page + '&size=' + size + '&sort=' + encodeURIComponent(sort) + '&q=' + encodeURIComponent(q))
      .then(r => r.json())
      .then(list => {
        var ul = $('.abl-items', root);
        ul.innerHTML = list.map(p => '<li><h4>'+escapeHtml(p.title||'')+'</h4><p>'+escapeHtml(p.body||'')+'</p></li>').join('');
        root.setAttribute('data-page', page);
      }).catch(console.error);
  }
  document.addEventListener('DOMContentLoaded', function(){
    document.querySelectorAll('.advanced-blog-list').forEach(function(root){
      root.setAttribute('data-page','1');
      var next = $('.next', root), prev = $('.prev', root);
      next && next.addEventListener('click', function(){
        var p = parseInt(root.getAttribute('data-page')||'1',10)+1; fetchPage(root,p);
      });
      prev && prev.addEventListener('click', function(){
        var p = Math.max(1, parseInt(root.getAttribute('data-page')||'1',10)-1); fetchPage(root,p);
      });
      var s = $('.abl-search', root); if(s) s.addEventListener('input', function(){ fetchPage(root,1); });
      var sort = $('.abl-sort', root); if(sort) sort.addEventListener('change', function(){ fetchPage(root,1); });
    });
  });
})();