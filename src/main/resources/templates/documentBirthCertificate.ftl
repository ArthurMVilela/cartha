<#assign title="Criar conta de cliente">
<!DOCTYPE html>
<html lang="en">
<head>
    <#include "./partials/_meta.ftl">
</head>
<body>
<#include "./partials/_topbarMenu.ftl">
<div class="container" style="margin-top:80px">
    <div class="row m-3">
        <div class="col text-center">
            <span>REGISTRO CIVIL DAS PESSOAS NATURAIS</span>
            <h1>CERTIDÃO DE NASCIMENTO</h1>
        </div>
    </div>
    <div class="row m-3">
        <div class="col text-center">
            <h5>NOME:</h5>
            <h5>${birthCertificate.name}</h5>
            <h5>MATRÍCULA:</h5>
            <h5>${birthCertificate.registrationNumber}</h5>
        </div>
    </div>

    <div class="row m-3">
        <div class="col-8">
            <label for="birthday" class="form-label">DATA DE NASCIMENTO POR EXTENSO: </label>
            <input id="birthday" class="form-control" type="text" disabled value="${fullStringBirthday}">
        </div>
        <div class="col-1">
            <label for="birthday-day" class="form-label">DIA: </label>
            <input id="birthday-day" class="form-control" type="text" disabled value="${birthCertificate.dateTimeOfBirth.dayOfMonth}">
        </div>
        <div class="col-1">
            <label for="birthday-month" class="form-label">MÊS: </label>
            <input id="birthday-month" class="form-control" type="text" disabled value="${birthCertificate.dateTimeOfBirth.monthValue}">
        </div>
        <div class="col-2">
            <label for="birthday-year" class="form-label">ANO: </label>
            <input id="birthday-year" class="form-control" type="text" disabled value="${birthCertificate.dateTimeOfBirth.year}">
        </div>
    </div>

    <div class="row m-3">
        <div class="col-2">
            <label for="birthday" class="form-label">HORA: </label>
            <input id="birthday" class="form-control" type="text" disabled
                   value="${birthCertificate.dateTimeOfBirth.hour}:${birthCertificate.dateTimeOfBirth.minute}">
        </div>
        <div class="col-10">
            <label for="birthday-day" class="form-label">MUNICÍPIO DE NASCIMENTO E UNIDADE DA FEDERAÇÃO: </label>
            <input id="birthday-day" class="form-control" type="text" disabled
                   value="${birthCertificate.municipalityOfBirth.name}, ${birthCertificate.municipalityOfBirth.uf}">
        </div>
    </div>
    <div class="row m-3">
        <div class="col-6">
            <label for="birthday" class="form-label">MUNICÍPIO DE REGISTRO E UNIDADE DA FEDERAÇÃO: </label>
            <input id="birthday" class="form-control" type="text" disabled
                   value="${birthCertificate.municipalityOfRegistry.name}, ${birthCertificate.municipalityOfRegistry.uf}">
        </div>
        <div class="col-3">
            <label for="birthday-day" class="form-label">LOCAL DE NASCIMENTO: </label>
            <input id="birthday-day" class="form-control" type="text" disabled
                   value="${birthCertificate.placeOfBirth}">
        </div>
        <div class="col-3">
            <label for="birthday-day" class="form-label">SEXO: </label>
            <input id="birthday-day" class="form-control" type="text" disabled
                   value="${birthCertificate.sex.value}">
        </div>
    </div>
    <div class="row m-3">
        <div class="col-12">
            <span for="birthday" class="form-label">FILIAÇÃO: </span>
            <div class="card" style="min-height: 5rem">
                <ul class="list-group list-group-flush">
                    <#list birthCertificate.affiliation as af>
                        <li class="list-group-item">${af.name} &nbsp;&nbsp; ${af.municipality.name}, ${af.municipality.uf}</li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
    <div class="row m-3">
        <div class="col-12">
            <span for="birthday" class="form-label">AVÓS: </span>
            <div class="card " style="min-height: 10rem">
                <ul class="list-group list-group-flush">
                    <#list birthCertificate.grandparents as gp>
                        <li class="list-group-item">${gp.name} &nbsp;&nbsp; ${gp.municipality.name}, ${gp.municipality.uf} (${gp.type.value})</li>
                    </#list>
                </ul>
            </div>
        </div>
    </div>
    <div class="row m-3">
        <div class="col-2">
            <label for="birthday" class="form-label">GÊMEO: </label>
            <input id="birthday" class="form-control" type="text" disabled
                   value="Não">
        </div>
        <div class="col-10">
            <label for="birthday-day" class="form-label">NOME E MATRÍCULA DO(S) GÊMEO(S): </label>
            <input id="birthday-day" class="form-control" type="text" disabled>
        </div>
    </div>
    <div class="row m-3">
        <div class="col-7">
            <label for="birthday" class="form-label">DATA DO REGISTRO POR EXTENSO: </label>
            <input id="birthday" class="form-control" type="text" disabled
                   value="${fullStringRegistryDay}">
        </div>
        <div class="col-5">
            <label for="birthday-day" class="form-label">NÚMERO DA DECLARAÇÃO DE NASCIDO VIVO: </label>
            <input id="birthday-day" class="form-control" type="text" disabled
                   value="${birthCertificate.dnNumber}">
        </div>
    </div>
    <div class="row m-3">
        <div class="col-12">
            <span for="birthday" class="form-label">OBSERVAÇÕES AVERBAÇÕES: </span>
            <div class="card" style="min-height: 10rem">
                <ul class="list-group list-group-flush">
                </ul>
            </div>
        </div>
    </div>

    <div class="row m-3 justify-content-between">
        <div class="col-5">
            <span>NOME DO OFÍCIO</span><br>
            <span>OFICIAL REGISTRADOR</span><br>
            <span>MUNICÍPIO/DF</span><br>
            <span>ENDEREÇO</span><br>
        </div>
        <div class="col-5">
            <span>O conteúdo da certidão é verdadeiro. Dou fé</span><br>
            <span>Data e local: ${birthCertificate.dateTimeOfBirth.dayOfMonth}/${birthCertificate.dateTimeOfBirth.monthValue}/${birthCertificate.dateTimeOfBirth.year} ${birthCertificate.municipalityOfRegistry.name}, ${birthCertificate.municipalityOfRegistry.uf}</span><br>
            <label for="birthday-day" class="form-label">Assinatura do Oficial: </label><br>
            <input id="birthday-day" class="form-control" type="text" disabled
                   value="">
        </div>
    </div>
</div>

<script type="text/javascript" src="/static/main.js"></script>
</body>
</html>