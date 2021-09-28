<#assign title="Criar conta de cliente">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <#if errorMessage??>
        <div class="row">
            <div class="alert alert-danger" role="alert">
                ${errorMessage}
            </div>
        </div>
    </#if>
    <div class="row justify-content-center">
        <form class="col" action="/create-account" method="post" id="create-client-account-form">
            <div class="m-3">
                <label for="name" class="form-label">Nome: </label>
                <input id="name" type="text" class="form-control" name="name" required>
                <div class="invalid-feedback">
                    Você deve inserir o seu nome
                </div>
            </div>
            <div class="m-3">
                <label for="email" class="form-label">Email: </label>
                <input id="email" type="email" class="form-control" name="email" required>
                <div class="invalid-feedback">
                    Você deve inserir o seu email
                </div>
            </div>
            <div class="m-3">
                <label for="cpf" class="form-label">CPF: </label>
                <input id="cpf" type="text" class="form-control mask-cpf" name="cpf" required>
                <div class="invalid-feedback">
                    Você deve inserir seu CPF
                </div>
            </div>
            <div class="m-3">
                <label for="sex" class="form-label">Sexo: </label>
                <select name="sex" class="form-select" required>
                    <#list sex as i,v>
                        <option value="${i}">${v}</option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Você deve inserir o seu sexo
                </div>
            </div>
            <div class="m-3">
                <label for="color" class="form-label">Cor: </label>
                <select name="color" class="form-select" required>
                    <#list color as i,v>
                        <option value="${i}">${v}</option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Você deve inserir a sua cor
                </div>
            </div>
            <div class="m-3">
                <label for="civil-status" class="form-label">Estado cívil: </label>
                <select name="civil-status" class="form-select" required>
                    <#list civilStatus as i,v>
                        <option value="${i}">${v}</option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Você deve inserir o seu estado civil
                </div>
            </div>
            <div class="m-3">
                <label for="birthday" class="form-label">Data de nascimento: </label>
                <div id="birthday" class="input-group mb-3">
                    <input id="birthday-day" name="birthday-day" type="number" class="form-control" min="1" max="31" required>
                    <span class="input-group-text"> de </span>
                    <select id="birthday-month" name="birthday-month" class="form-select" required>
                        <#list months as i,v>
                            <option value="${i}">${v}</option>
                        </#list>
                    </select>
                    <span class="input-group-text"> de </span>
                    <input id="birthday-year" name="birthday-year" type="number" class="form-control" min="1900" max="2021" required>
                </div>
            </div>

            <div class="m-3">
                <label for="nationality" class="form-label">Nacionalidade: </label>
                <input id="nationality" type="text" class="form-control" name="nationality" required>
                <div class="invalid-feedback">
                    Você deve inserir sua nascionalidade
                </div>
            </div>

            <div class="m-3">
                <label for="password" class="form-label">Senha: </label>
                <input id="password" type="password" class="form-control" name="password" required>
                <div id="password-invalid-msg" class="invalid-feedback">
                    Você deve informar a sua senha.
                </div>
            </div>
            <div class="m-3">
                <label for="password-repeat" class="form-label">Repetir senha: </label>
                <input id="password-repeat" type="password" class="form-control" name="password-repeat" required>
                <div id="password-invalid-msg" class="invalid-feedback">
                    Você deve digitar novamente sua senha.
                </div>
            </div>

            <div class="m-3">
                <button type="submit" class="btn btn-primary">Criar Conta</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
<script type="text/javascript" src="/static/createClientAccount.js"></script>
</body>
</html>