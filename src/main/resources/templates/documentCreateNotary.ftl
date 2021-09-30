<#assign title="Criar cartório">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row justify-content-center">
        <form id="create-node-form" class="col" action="/notary/create" method="post">
            <div class="m-3">
                <label for="name" class="form-label">Nome: </label>
                <input id="name" type="text" class="form-control" name="name">
                <div class="invalid-feedback">
                    Você deve inserir nome do cartório
                </div>
            </div>
            <div class="m-3">
                <label for="cnpj" class="form-label">CNPJ: </label>
                <input id="cnpj" type="text" class="form-control mask-cnpj" name="cnpj">
                <div id="cnpj-invalid-msg" class="invalid-feedback">
                    Você deve inserir CNPJ do cartório
                </div>
            </div>
            <div class="m-3">
                <label for="cns" class="form-label">CNS: </label>
                <input id="cns" type="text" class="form-control mask-cns" name="cns">
                <div class="invalid-feedback">
                    Você deve inserir CNS do cartório
                </div>
            </div>
            <div class="m-3">
                <button type="submit" class="btn btn-primary">Criar Cartório</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
<script type="text/javascript" src="/static/createNode.js"></script>
</body>
</html>