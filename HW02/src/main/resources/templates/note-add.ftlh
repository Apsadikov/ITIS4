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
<div class="content add-note z-depth-3">
    <form action="/note/add" method="post">
        <input type="text" class="search-input" name="name" placeholder="Name">
        <textarea name="text" rows="8" class="note-textarea" placeholder="Note"></textarea>
        <input type="submit" class="add">
    </form>
</div>
</body>
</html>