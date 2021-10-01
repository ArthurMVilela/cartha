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
                <#if errorMessage??>
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </#if>

                <form method="post" action="/login" id="login-form">
                    <div class="m-3">
                        <label for="email" class="form-label">Email: </label>
                        <input id="email" type="email" class="form-control" name="email">
                        <div id="email-invalid-msg" class="invalid-feedback">
                            Você deve informar email OU CPF
                        </div>
                    </div>
                    <div class="m-3">
                        <label for="cpf" class="form-label">CPF: </label>
                        <input id="cpf" type="text" class="form-control mask-cpf" name="cpf">
                        <div id="cpf-invalid-msg" class="invalid-feedback">
                            Você deve informar email OU CPF
                        </div>
                    </div>
                    <div class="m-3">
                        <label for="password" class="form-label">Senha: </label>
                        <input id="password" type="password" class="form-control" name="password" required>
                        <div id="password-invalid-msg" class="invalid-feedback">
                            Você deve informar sua senha
                        </div>
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
    <script type="text/javascript" src="/static/loginForm.js"></script>
</body>
</html>