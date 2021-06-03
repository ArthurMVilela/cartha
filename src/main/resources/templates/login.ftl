<#assign title="Login">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
    <div class="container" style="margin-top:80px">
        <div class="row justify-content-center">
            <div class="col col-md-6 col-lg-4">
                <form class="needs-validation" method="post" action="/login">
                    <div class="m-3">
                        <label for="email" class="form-label">Email: </label>
                        <input id="email" type="email" class="form-control or-required" name="email" data-or-required="cpf-cnpj">
                    </div>
                    <div class="m-3">
                        <label for="cpf-cnpj" class="form-label">CPF/CNPJ: </label>
                        <input id="cpf-cnpj" type="text" class="form-control mask-cpf-cnpj or-required" name="cpf" data-or-required="email">
                    </div>
                    <div class="m-3">
                        <label for="password" class="form-label">Senha: </label>
                        <input id="password" type="password" class="form-control" name="password" required>
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