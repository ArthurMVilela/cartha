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
                        <div class="col">
                            <label for="birthday" class="form-label">Data de nascimento:</label>
                            <div class="input-group" id="birthday">
                                <input type="number" name="birthday_day" class="form-control" min="1" max="31" placeholder="1">
                                <span class="input-group-text">de</span>
                                <select class="form-select" name="birthday_month">
                                    <#list month?keys as i>
                                        <option value="${i}">${month[i]}</option>
                                    </#list>
                                </select>
                                <span class="input-group-text">de</span>
                                <input type="number" name="birthday_year" class="form-control input-year" min="1900" placeholder="2000">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="sex" class="form-label">Sexo:</label>
                            <select class="form-select" name="sex">
                                <#list sex?keys as i>
                                    <option value="${i}">${sex[i]}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col">
                            <label for="color" class="form-label">Cor/raça (Classificação do IBGE):</label>
                            <select class="form-select" name="color">
                                <#list color?keys as i>
                                    <option value="${i}">${color[i]}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="civil-status" class="form-label">Estado Cívil:</label>
                            <select class="form-select" name="civil-status">
                                <#list civilStatus?keys as i>
                                    <option value="${i}">${civilStatus[i]}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <label for="nationality" class="form-label">Nacionalidade</label>
                            <input type="text" class="form-control" name="nationality" placeholder="brasileiro">
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