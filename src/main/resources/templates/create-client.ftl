<#assign title="Login">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
    <#include "./partials/menu.ftl">
    <div class="container" style="margin-top:80px">
        <div class="row justify-content-center">
            <div class="card col col-lg-8 py-2">
                <form method="post" action="/create-account/client">
                    <div class="row">
                        <div class="col">
                            <label for="name" class="form-label">Nome: </label>
                            <input type="text" class="form-control" name="name" placeholder="Fulano da Silva">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="email" class="form-label">Email: </label>
                            <input type="email" class="form-control" name="email" placeholder="fulano@gmail.com">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="cpf" class="form-label">CPF: </label>
                            <input type="text" class="form-control mask-cpf" name="cpf" placeholder="111.111.111-11">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="password" class="form-label">Senha: </label>
                            <input type="password" class="form-control" name="password" placeholder="*********">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col p-3">
                            <button type="submit" class="btn btn-primary">Criar conta</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/static/main.js"></script>
</body>
</html>