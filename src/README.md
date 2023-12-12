# Jasper Reports Integration in Template App

This README provides an overview of how to integrate Jasper Reports and Handlebars into the Template app. Jasper Reports is a powerful 
reporting library that allows us to create rich and dynamic reports.
Handlebars is a versatile template engine that simplifies dynamic content generation.
## Table of Contents

- [Introduction](#introduction)
- [Prerequisites](#prerequisites)
- [Jasper Reports Setup](#jasper-reports-setup)
- [Handlebars Setup](#handlebars-setup)
- [Generating Reports](#generating-reports)
- [Usage](#usage)
- [Jasper Sample Code](#jasper-sample-code)
- [Handlebars Sample Code](#handlebars-sample-code)

## Introduction

[Jasper Reports](https://community.jaspersoft.com/project/jasperreports-library) is an open-source reporting tool that 
enables developers to design and generate rich and interactive reports. In our Template app, we use Jasper Reports 
to create a pdf file or a html file.

[Handlebars](https://handlebarsjs.com/) is a popular templating engine that allows developers to create dynamic templates for generating HTML, PDF, or other types of documents.
In our Template app, we use Handlebars to create a pdf file or a html file.

## Prerequisites

Before you proceed, ensure that you have the following prerequisites installed:

- Gradle (build.gradle)

      implementation 'net.sf.jasperreports:jasperreports:6.20.5'
      implementation 'com.github.librepdf:openpdf:1.3.30'

- Run this before starting the app

      create schema if not exists template;

## Jasper Reports Setup

1. Import the required Jasper Reports library into the project. You can do this by adding the following dependency to the `build.gradle` file

       implementation 'net.sf.jasperreports:jasperreports:6.20.5'
       implementation 'com.github.librepdf:openpdf:1.3.30'

2. Clean + Build gradle

## Handlebars Setup

1. Import the required Jasper Reports library into the project. You can do this by adding the following dependency to the `build.gradle` file

       implementation 'com.github.jknack:handlebars:4.3.1'
       implementation 'org.xhtmlrenderer:flying-saucer-pdf:9.0.6'

2. Clean + Build gradle

## Usage

1. Use the `TemplateReportService` to generate reports by calling the `exportReport` method.


2. The `TemplateController` class provides REST endpoints to interact with the templates. Additionally, it exposes an endpoint to generate reports using the `TemplateReportService`.


## Jasper Sample Code

Add data to the database before testing:

http://localhost:8082/template/api/v1/templates/add

    {
    "id": -1,
    "code": "proces_verbal_jasper_aranjat",
    "name": "proces_verbal_jasper_aranjat",
    "description": "Proces verbal de tip JASPER",
    "content": "",
    "handlebars_content": "",
    "type": "JASPER",
    "last_updated_dtm": "CURRENT_TIMESTAMP",
    "created_dtm": "CURRENT_TIMESTAMP",
    "active": true
    }

Or use the web upload button using the [proces_verbal_aranjat_bine.jrxml](..%2Ftemplates%2Fmodele%2Fproces_verbal_aranjat_bine.jrxml)

Then download it:

(This is the report):

GET http://localhost:8082/template/api/v1/reports/download/proces_verbal_jasper_aranjat.pdf

    {
        "id": "123456789",
        "serie_proces_verbal": "PV2023",
        "numar_proces_verbal": "12345",
        "zi_proces_verbal": "13",
        "luna_proces_verbal": "09",
        "an_proces_verbal": "2023",
        "ora__proces_verbal": "15:30",
        "localitate_proces_verbal": "București",
        "judet_sector_proces_verbal": "Sector 5",
        "agent": "Agentul nemilos",
        "agent_din": "Sectorul 3",
        "nume_amendat": "Munteanu Andrei Stefan",
        "CNP_amendat": "1234567890123",
        "tip_act_amendat": "Buletin",
        "act_serie_amendat": "AB",
        "numar_serie_amendat": "12345",
        "domiciliu_amendat": "Str. Principală",
        "strada_domiciliu_amendat": "Str. Domiciliu",
        "numar_domiciliu_amendat": "10",
        "bloc_domiciliu_amendat": "B1",
        "apartament_amendat": "Ap. 5",
        "scara_amendat": "Scara 2",
        "sector_judet_amendat": "Sector 5",
        "ziua_actuala": "13",
        "luna_actuala": "09",
        "anul_actual": "2023",
        "ora_accident": "14:45",
        "locul_accident": "Intersecție principală",
        "detalii_accident": "Coliziune ușoară",
        "sanctiune_aplicata": "Amendă",
        "articol_prevedere": "Art. 123",
        "act_normativ_prevedere": "Legea 456/2023",
        "articol_sanctionare": "Art. 789",
        "act_normativ_sanctionare": "Ordonanța 987/2023",
        "puncte_amenda": "3",
        "valoare_minima_amenda": "500 RON",
        "amenda": "750 RON",
        "jumatate_din_minim_amenda": "250 RON",
        "avertisment": "true",
        "punte_penalizare": "true",
        "total amenda": "750 RON",
        "jumatate_total_amenda": "375 RON",
        "total_puncte_penalizare": "5",
        "perioada_suspendare": "30 zile",
        "data_inecput_suspendare": "14/09/2023",
        "retinere_permis": "true",
        "serie_permis": "CD",
        "stat_emitent_permis": "RO",
        "articol_retinere_permis": "Art. 101",
        "data_articol_retinere_permis": "14/09/2023",
        "retinere_certificat_inmatriculare": "true",
        "serie_certificat_inmatriculare": "AB123",
        "stat_emitent_certificat_inmatriculare": "RO",
        "articol_retinere_certificat_inmatriculare": "Art. 202",
        "data_articol_retinere_certificat_inmatriculare": "14/09/2023",
        "retinere_placute": "true",
        "numar_placute_retinere": "AB123CD",
        "stat_emitent_placute_retinere": "RO",
        "articol_retinere_placute_retinere": "Art. 303",
        "data_articol_retinere_placute_retinere": "14/09/2023",
        "marca_vehicul": "VW Golf",
        "numar_placute_imobilizare": "XYZ789",
        "articol_retinere_placute_imobilizare": "Art. 404",
        "data_articol_retinere_placute_imobilizare": "14/09/2023",
        "detalii_confiscare": "N/A",
        "articol_confiscare": "N/A",
        "data_articol_confiscare": "N/A",
        "mentiuni_contravenient": "N/A",
        "refuzare_semnare_pv": "false",
        "incapacitate_semnare_pv": "false",
        "absenta_semnare_pv": "false",
        "nume_martor": "Bob Intalatorul",
        "cnp_martor": "2345678901234",
        "numar_vehicul_politie": "P12345",
        "asigurat_cu_politia_numarul": "A98765",
        "societate_asigurare_vehicul_politie": "ABC Asigurări",
        "valabilitate_vehicul_politie": "31/12/2023",
        "este_persoana_nerezidenta": "false",
        "cod_fiscal_persoana_nerezidenta": "N/A",
        "plata_persoana_fizica": "true",
        "plata_persoana_juridica_sau_nerezidenta": "false",
        "judecatorie": "București",
        "cod_IBAN_fizica": "RO1234567890",
        "cod_IBAN_juridica": "N/A",
        "este_persoana_juridica": "false",
        "este_persoana_fizica": "true"
    }

## Handlebars Sample Code

    A sample code can be found in resources -> db.migration -> scripts -> 202309041042_insert_PV2
    This script will create Verbal Process template.

### Where to find the pdf

    template/reports

or just Download it:

(This is the template)

GET http://localhost:8082/template/api/v1/templates/proces_verbal_aranjat.pdf

## Requirements
    # JavaSdk v.17.0.8
    # Language lvl 17
    # Postgress
          docker run -d --name postgres -p 5432:5432 -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres postgres:13


## Profiles
    # local - local development
    # dev - integrated development environment
    # qa  - QA development environment
