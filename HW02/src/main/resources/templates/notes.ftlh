<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
    <link rel="stylesheet" href="/style.css"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div class="search-bar z-depth-3">
    <form method="get" action="/notes">
        <input type="text" value="${name}" name="name" class="search-input" placeholder="Search.." id="query">
        <button class="search-icon"><i class="material-icons" id="search">search</i></button>
    </form>
</div>
<script>
    var query = document.getElementById("query");
    var search = document.getElementById("search");
    search.addEventListener("click", function (ev) {
        if (query.value !== undefined || query.value.length !== 0) {
            window.location.href = "http://localhost:8080/notes?name=" + query.value;
        }
    });
</script>
<div class="content z-depth-3">
    <#list notes as note>
        <div class="note">
            <div>Name: ${note.name}</div>
            <div>Note: ${note.text}</div>
        </div>
    </#list>
    <div class="footer">
        <#if (totalPage != 0)>
            <div class="pages">
            <#assign x=lastPage>
            <#if (totalPage > 4 && startPage > 1)>
            <a href="/notes?name=${name}" class="start">Начало</a>
            </#if>
            <#list startPage..x as i>
                <a href="/notes/page/${i}?name=${name}" class="page <#if page == i>visit</#if>">${i}</a>
            </#list>
            <#if (totalPage > 4 && lastPage < totalPage)>
            <a href="/notes/page/${totalPage}?name=${name}" class="end">Конец</a>
            </#if>
            </div>
        <#else>
            <div class="not-found">Not Found</div>
        </#if>
        <a class="btn-floating btn-large pulse create" href="/note/add"><i class="material-icons create-btn">add</i></a>
    </div>
</div>
</body>
</html>