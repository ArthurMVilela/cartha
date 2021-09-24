<#assign title="Criar conta de cliente">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <form class="col" action="/create-account" method="post">
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
                <label for="color" class="form-label">Cor: </label>
                <select name="color" class="form-select">
                    <#list color as i,v>
                        <option value="${i}">${v}</option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Você deve inserir a cor do funcionário
                </div>
            </div>
            <div class="m-3">
                <label for="civil-status" class="form-label">Estado cívil: </label>
                <select name="civil-status" class="form-select">
                    <#list civilStatus as i,v>
                        <option value="${i}">${v}</option>
                    </#list>
                </select>
                <div class="invalid-feedback">
                    Você deve inserir o estado cívil
                </div>
            </div>
            <div class="m-3">
                <label for="birthday" class="form-label">Data de nascimento: </label>
                <div id="birthday" class="input-group mb-3">
                    <input id="birthday-day" name="birthday-day" type="number" class="form-control" min="1" max="31">
                    <span class="input-group-text"> de </span>
                    <select id="birthday-month" name="birthday-month" class="form-select">
                        <#list months as i,v>
                            <option value="${i}">${v}</option>
                        </#list>
                    </select>
                    <span class="input-group-text"> de </span>
                    <input id="birthday-year" name="birthday-year" type="number" class="form-control" min="1900" max="2021">
                </div>
            </div>

            <div class="m-3">
                <label for="nationality" class="form-label">Nacionalidade: </label>
                <input id="nationality" type="text" class="form-control" name="nationality">
                <div class="invalid-feedback">
                    Você deve inserir Nacíonalidade
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
                <button type="submit" class="btn btn-primary">Criar Conta</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>