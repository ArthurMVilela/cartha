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
            <div class="card col col-lg-8">
                <form method="post" action="/login">
                    <div class="m-3">
                        <label for="email" class="form-label">Email: </label>
                        <input type="email" class="form-control" name="email">
                    </div>
                    <div class="m-3">
                        <label for="cpf" class="form-label">CPF: </label>
                        <input type="text" class="form-control mask-cpf" name="cpf">
                    </div>
                    <div class="m-3">
                        <label for="password" class="form-label">Senha: </label>
                        <input type="password" class="form-control" name="password">
                    </div>
                    <div class="m-3">
                        <button type="submit" class="btn btn-primary">Entrar</button>
                        <a class="btn btn-primary" href="/create-account/client" role="button">Cadastrar-se</a>
                    </div>

                </form>
            </div>
        </div>
    </div>

    <script type="text/javascript" src="/static/main.js"></script>
</body>
</html>