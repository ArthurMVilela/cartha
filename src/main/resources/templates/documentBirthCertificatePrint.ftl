<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Certidão de nascimento</title>
        <link rel="stylesheet" href="/static/print.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.11/jquery.mask.min.js"></script>
    </head>
    <body>
        <div class="container">
            <div class="header">
                <img alt="Brasão de Armas do Brasil" src="/static/coat-of-arms.png" width="80px" height="80px"><br>
                <span>REGISTRO CIVIL DAS PESSOAS NATURAIS</span>
                <h1>CERTIDÃO DE NASCIMENTO</h1>
                <h2>NOME:</h2>
                <h2>${birthCertificate.name}</h2>
                <h2>MATRÍCULA:</h2>
                <h2 id="registration-number">${birthCertificate.registrationNumber}</h2>
            </div>
            <div class="body">
                <div class="row">
                    <div class="col-8 field">
                        <span class="field__label">DATA DE NASCIMENTO POR EXTENSO</span>
                        <span class="field__content">${fullStringBirthday}</span>
                    </div>
                    <div class="col-1 field">
                        <span class="field__label field__label--center">DIA</span>
                        <span class="field__content">${birthCertificate.dateTimeOfBirth.dayOfMonth}</span>
                    </div>
                    <div class="col-1 field">
                        <span class="field__label field__label--center">MÊS</span>
                        <span class="field__content">${birthCertificate.dateTimeOfBirth.monthValue}</span>
                    </div>
                    <div class="col-2 field">
                        <span class="field__label field__label--center">ANO</span>
                        <span class="field__content">${birthCertificate.dateTimeOfBirth.year?long?c}</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-1 field">
                        <span class="field__label">HORA</span>
                        <span class="field__content">${birthCertificate.dateTimeOfBirth.hour?string["00"]}:${birthCertificate.dateTimeOfBirth.minute?string["00"]}</span>
                    </div>
                    <div class="col-11 field">
                        <span class="field__label">MUNICÍPIO DE NASCIMENTO E UNIDADE DA FEDERAÇÃO</span>
                        <span class="field__content">${birthCertificate.municipalityOfBirth.name}, ${birthCertificate.municipalityOfBirth.uf}</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-7 field">
                        <span class="field__label">MUNICÍPIO DE REGISTRO E UNIDADE DA FEDERAÇÃO</span>
                        <span class="field__content">${birthCertificate.municipalityOfRegistry.name}, ${birthCertificate.municipalityOfRegistry.uf}</span>
                    </div>
                    <div class="col-3 field">
                        <span class="field__label">LOCAL DE NASCIMENTO</span>
                        <span class="field__content">${birthCertificate.placeOfBirth}</span>
                    </div>
                    <div class="col-2 field">
                        <span class="field__label">SEXO</span>
                        <span class="field__content">${birthCertificate.sex.value}</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 field">
                        <span class="field__label">FILIAÇÃO</span>
                        <span class="field__content field__content--min-2-line">
                            <#list birthCertificate.affiliation as af>
                                <span>${af.name} &nbsp;&nbsp; ${af.municipality.name}, ${af.municipality.uf}</span><br>
                            </#list>
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 field">
                        <span class="field__label">AVÓS</span>
                        <span class="field__content field__content--min-4-line">
                            <#list birthCertificate.grandparents as gp>
                                <span>${gp.name} &nbsp;&nbsp; ${gp.municipality.name}, ${gp.municipality.uf} (${gp.type.value})</span><br>
                            </#list>
                        </span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-1 field">
                        <span class="field__label">GÊMEO</span>
                        <span class="field__content">NÃO</span>
                    </div>
                    <div class="col-11 field">
                        <span class="field__label">NOME E MATRÍCULA DO(S) GÊMEO(S)</span>
                        <span class="field__content"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-7 field">
                        <span class="field__label">DATA DO REGISTRO POR EXTENSO</span>
                        <span class="field__content">${fullStringRegistryDay}</span>
                    </div>
                    <div class="col-5 field">
                        <span class="field__label">NÚMERO DA DECLARAÇÃO DE NASCIDO VIVO</span>
                        <span class="field__content" id="dn-number">${birthCertificate.dnNumber}</span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 field ">
                        <span class="field__label">OBSERVAÇÕES AVERBAÇÕES</span>
                        <span class="field__content field__content--min-4-line"></span>
                    </div>
                </div>
                <div class="row">
                    <div class="col-7 field">
                        <span class="field__label">${notary.name?upper_case}</span>
                        <span class="field__label">${official.name?upper_case}</span>
                    </div>
                    <div class="col-5 field">
                        <span class="field__label">O conteúdo da certidão é verdadeiro. Dou fé</span>
                        <span class="field__label">Data e local: ${birthCertificate.dateTimeOfBirth.dayOfMonth?string["00"]}/${birthCertificate.dateTimeOfBirth.monthValue?string["00"]}/${birthCertificate.dateTimeOfBirth.year?long?c} ${birthCertificate.municipalityOfRegistry.name}, ${birthCertificate.municipalityOfRegistry.uf}</span>
                        <div class="field__signature">
                            <div class="field__signature__line"></div>
                            <span class="field__label field__label--center">Assinatura do Oficial</span>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-12 field">
                        <span class="field__label">Documento emitido e autentificado pelo sistema Cartha</span>
                        <span class="field__label">ID do documento: ${birthCertificate.id}</span>
                        <span class="field__label">Hash do documento: ${birthCertificate.hash}</span>

                    </div>
                </div>
            </div>
        </div>

        <script type="text/javascript">
            $(document).ready(function () {
                $("#registration-number").mask("000000 00 00 0000 0 00000 000 0000000 00")
                $("#dn-number").mask("00-00000000-0")
                window.onafterprint = function() { window.close() };
                window.print()

            })
        </script>
    </body>
</html>