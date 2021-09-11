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

                <form class="needs-validation" method="post" action="/login" id="login-frm">
                    <div class="m-3">
                        <label for="email" class="form-label">Email: </label>
                        <input id="email" type="email" class="form-control" name="email">
                        <div id="email-invalid-msg" class="invalid-feedback">
                            Você deve informar email OU CPF/CNPJ
                        </div>
                    </div>
                    <div class="m-3">
                        <label for="cpf-cnpj" class="form-label">CPF/CNPJ: </label>
                        <input id="cpf-cnpj" type="text" class="form-control mask-cpf-cnpj" name="cpf" disabled>
                        <div id="cpf-cnpj-invalid-msg" class="invalid-feedback">
                            Você deve informar email OU CPF/CNPJ
                        </div>
                    </div>
                    <div class="m-3">
                        <label for="password" class="form-label">Senha: </label>
                        <input id="password" type="password" class="form-control" name="password">
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
    <script type="text/javascript">
        $("#login-frm").submit(function (evt) {
            console.log("AAAAAAAAAAA")
            let email = $("#email")
            let cpfCnpj = $("#cpf-cnpj")
            let password = $("#password")

            // console.log(email.is(":empty"))

            if(!email.val() && !cpfCnpj.val()) {
                email.addClass("is-invalid")
                cpfCnpj.addClass("is-invalid")
                evt.preventDefault()
                evt.stopPropagation()
            }
            if(!password.val()) {
                email.addClass("is-invalid")
                evt.preventDefault()
                evt.stopPropagation()
            }
        })
        function validate() {

        }
    </script>
</body>
</html>