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
            <form class="col-8">
                <div class="row">
                    <div class="col">
                        <label for="id" class="form-label">ID</label>
                        <div class="input-group">
                            <input type="text" class="form-control" id="id">
                            <button type="button" class="btn btn-outline-secondary" id="find-physical-person">Buscar</button>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col">
                        <label for="name" class="form-label">Nome</label>
                        <input type="text" class="form-control" id="name" placeholder="nome...">
                    </div>
                </div>
                <div class="row">
                    <div class="col-6">
                        <label for="cnpj" class="form-label">CNPJ</label>
                        <input type="text" class="form-control mask-cnpj" id="cnpj" placeholder="99.999.999.9999-99">
                    </div>
                    <div class="col-6">
                        <label for="cns" class="form-label">CNS</label>
                        <input type="text" class="form-control mask-cns" id="cns" placeholder="99.999-9">
                    </div>
                </div>
            </form>
        </div>
    </div>

    <script type="text/javascript" src="/static/main.js"></script>
</body>
</html>