<#assign title="Adicionar fúncionário">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <form class="col" action="/notary/${notaryId}/add-official" method="post">
            <div class="m-3">
                <label for="name" class="form-label">Nome: </label>
                <input id="name" type="text" class="form-control" name="name">
                <div class="invalid-feedback">
                    Você deve inserir nome do funcionário
                </div>
            </div>
            <div class="m-3">
                <label for="email" class="form-label">Email: </label>
                <input id="email" type="email" class="form-control" name="email">
                <div class="invalid-feedback">
                    Você deve inserir Email do funcionário
                </div>
            </div>
            <div class="m-3">
                <label for="cpf" class="form-label">CPF: </label>
                <input id="cpf" type="text" class="form-control mask-cpf" name="cpf">
                <div class="invalid-feedback">
                    Você deve inserir CPF do funcionário
                </div>
            </div>
            <div class="m-3">
                <label for="sex" class="form-label">Sexo: </label>
                <select name="sex" class="form-select">
                    <#list sex as i,v>
                        <option value="${i}">${v}</option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Você deve inserir o sexo do funcionário
                </div>
            </div>
            <div class="m-3">
                <label for="password" class="form-label">Senha temporária: </label>
                <input id="password" type="password" class="form-control" name="password">
                <div id="password-invalid-msg" class="invalid-feedback">
                    Você deve informar a senha temporária do usuário.
                </div>
            </div>

            <div class="m-3">
                <button type="submit" class="btn btn-primary">Criar Funcionário</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>