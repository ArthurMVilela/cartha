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
        <form class="col" action="/civil-registry/birth/create" method="post">
            <input type="hidden" name="official-id" value="${officialId}">
            <input type="hidden" name="notary-id" value="${notaryId}">

            <div class="m-3">
                <label for="name" class="form-label">Nome: </label>
                <input id="name" type="text" class="form-control" name="name">
                <div class="invalid-feedback">
                    Você deve inserir nome do cartório
                </div>
            </div>

            <div class="m-3">
                <label for="cpf" class="form-label">CPF: </label>
                <input id="cpf" type="text" class="form-control mask-cpf" name="cpf">
                <div class="invalid-feedback">
                    Você deve inserir nome do cartório
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
                <label for="birth-time" class="form-label">Hora de nascimento: </label>
                <div id="birth-time" class="input-group mb-3">
                    <input id="birth-time-h" name="birth-time-h" type="number" class="form-control" min="0" max="23">
                    <span class="input-group-text"> : </span>
                    <input id="birth-time-m" name="birth-time-m" type="number" class="form-control" min="0" max="59">
                </div>
            </div>

            <div class="m-3">
                <label for="registry-day" class="form-label">Data de registro: </label>
                <div id="registry-day" class="input-group mb-3">
                    <input id="registry-day" name="registry-day" type="number" class="form-control" min="1" max="31">
                    <span class="input-group-text"> de </span>
                    <select id="registry-month" name="registry-month" class="form-select">
                        <#list months as i,v>
                            <option value="${i}">${v}</option>
                        </#list>
                    </select>
                    <span class="input-group-text"> de </span>
                    <input id="registry-year" name="registry-year" type="number" class="form-control" min="1900" max="2021">
                </div>
            </div>

            <div class="m-3">
                <label for="municipality-of-birth" class="form-label">Município de nascimento: </label>
                <div id="municipality-of-birth" class="input-group mb-3">
                    <span class="input-group-text"> Município:  </span>
                    <input id="municipality-of-birth-name" name="municipality-of-birth-name" type="text" class="form-control">
                    <span class="input-group-text"> UF:  </span>
                    <select id="municipality-of-birth-uf" name="municipality-of-birth-uf" class="form-select">
                        <#list uf as i,v>
                            <option value="${i}">${v}</option>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="m-3">
                <label for="place-of-birth" class="form-label">Local de nascimento: </label>
                <input id="place-of-birth" type="text" class="form-control" name="place-of-birth">
                <div class="invalid-feedback">
                    Você deve inserir nome do cartório
                </div>
            </div>

            <div class="m-3">
                <label for="municipality-of-registry" class="form-label">Município de registro: </label>
                <div id="municipality-of-registry" class="input-group mb-3">
                    <span class="input-group-text"> Município:  </span>
                    <input id="municipality-of-registry-name" name="municipality-of-registry-name" type="text" class="form-control">
                    <span class="input-group-text"> UF:  </span>
                    <select id="municipality-of-registry-uf" name="municipality-of-registry-uf" class="form-select">
                        <#list uf as i,v>
                            <option value="${i}">${v}</option>
                        </#list>
                    </select>
                </div>
            </div>

            <div class="m-3">
                <div id="affiliation">
                    <h3>Afiliação: </h3>
                    <input type="hidden" name="affiliation-length">
                    <div id="affiliation__container">
                        <div class="affiliation__item">
                            <div class="card m-3">
                                <div class="m-3">
                                    <label class="form-label">Nome: </label>
                                    <input type="text" class="form-control affiliation__item__name" name="">
                                </div>
                                <div class="m-3">
                                    <label class="form-label">CPF: </label>
                                    <input type="text" class="form-control affiliation__item__cpf mask-cpf" name="">
                                </div>
                                <div class="m-3">
                                    <div id="municipality-of-registry" class="input-group mb-3">
                                        <span class="input-group-text"> Município:  </span>
                                        <input name="municipality-name" type="text" class="form-control affiliation__item__municipality-name">
                                        <span class="input-group-text"> UF:  </span>
                                        <select name="municipality-uf" class="form-select affiliation__item__municipality-uf">
                                            <#list uf as i,v>
                                                <option value="${i}">${v}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>

                                <div class="card-body">
                                    <button type="button" class="affiliation__item__remove-btn btn btn-primary">Remover</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button id="btn-add-affiliation" type="button" class="btn btn-primary">Adicionar filiação</button>
                    </div>
                </div>
            </div>

            <div class="m-3">
                <div id="grandparent">
                    <h3>Avós: </h3>
                    <input type="hidden" name="grandparent-length">
                    <div id="grandparent__container">
                        <div class="grandparent__item">
                            <div class="card m-3">
                                <div class="m-3">
                                    <label class="form-label">Nome: </label>
                                    <input type="text" class="form-control grandparent__item__name" name="">
                                </div>
                                <div class="m-3">
                                    <label class="form-label">CPF: </label>
                                    <input type="text" class="form-control grandparent__item__cpf mask-cpf" name="">
                                </div>
                                <div class="m-3">
                                    <label class="form-label">Tipo: </label>
                                    <select name="" class="form-select grandparent__item__type">
                                        <#list grandparentType as i,v>
                                            <option value="${i}">${v}</option>
                                        </#list>
                                    </select>
                                </div>
                                <div class="m-3">
                                    <div id="municipality-of-registry" class="input-group mb-3">
                                        <span class="input-group-text"> Município:  </span>
                                        <input name="municipality-name" type="text" class="form-control grandparent__item__municipality-name">
                                        <span class="input-group-text"> UF:  </span>
                                        <select name="municipality-uf" class="form-select grandparent__item__municipality-uf">
                                            <#list uf as i,v>
                                                <option value="${i}">${v}</option>
                                            </#list>
                                        </select>
                                    </div>
                                </div>

                                <div class="card-body">
                                    <button type="button" class="grandparent__item__remove-btn btn btn-primary">Remover</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div>
                        <button id="btn-add-grandparent" type="button" class="btn btn-primary">Adicionar avó</button>
                    </div>
                </div>
            </div>

            <div class="m-3">
                <label for="dn-number" class="form-label">Declaração de Nascido Vivo: </label>
                <input id="dn-number" type="text" class="form-control mask-dn" name="dn-number">
                <div class="invalid-feedback">
                    Você deve inserir nome do cartório
                </div>
            </div>



            <div class="m-3">
                <button type="submit" class="btn btn-primary">Criar Certidão</button>
            </div>
        </form>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>