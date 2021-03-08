# DAT251 Group 9 V2021

| Branch | Status |
|-|-|
| main | [![Release Backend Test](https://github.com/spydx/dat251gr9/actions/workflows/backend-ci.yml/badge.svg?branch=main)](https://github.com/spydx/dat251gr9/actions/workflows/backend-ci.yml) [![Release Frontend Test](https://github.com/spydx/dat251gr9/actions/workflows/frontend-ci.yml/badge.svg?branch=main)](https://github.com/spydx/dat251gr9/actions/workflows/frontend-ci.yml) [![Release Docker CI/CD to GitHub Container ](https://github.com/spydx/dat251gr9/actions/workflows/docker-publish.yml/badge.svg?branch=main)](https://github.com/spydx/dat251gr9/actions/workflows/docker-publish.yml) |
| backend | [![Backend CI](https://github.com/spydx/dat251gr9/actions/workflows/backend-ci.yml/badge.svg?branch=backend)](https://github.com/spydx/dat251gr9/actions/workflows/backend-ci.yml) |
| frontend | [![Frontend CI](https://github.com/spydx/dat251gr9/actions/workflows/frontend-ci.yml/badge.svg?branch=frontend)](https://github.com/spydx/dat251gr9/actions/workflows/frontend-ci.yml) |

Group project for DAT251 Group 9 Spring 2021

| Members        | Contact (github)                                       |
| -------------- | ------------------------------------------------------ |
| Andrè          | [@ImGoze](https://github.com/ImGoze)                   |
| Erlend         | [@ErlendBerntsen](https://github.com/ErlendBerntsen)   |
| Hans-Christian | [@H-C-H](https://github.com/H-C-H)                     |
| Kenneth        | [@spydx](https://github.com/spydx)                     |
| Oscar          | [@OscarSommervold](https://github.com/OscarSommervold) |
| Rune           | [@runalmaas](https://github.com/runalmaas)             |

## Links

- [Zoom meeting room](https://uib.zoom.us/j/68675494000?pwd=b2hzWG5Zd0Vac0dDdUtwZmRNN21uQT09)
- [Google drive](https://drive.google.com/drive/folders/1C0JDU_DwFlpF7I6hPTvxrF4koDebtebP)
  - [Timeliste](https://docs.google.com/spreadsheets/d/1pFGBxwwf67vKVIT5Swg6l6naNjio05JOcEfR-ku3wXU/edit#gid=0)
  - [Burndown chart](https://docs.google.com/spreadsheets/d/1R1XAsYmkR_oMGGFmyeBw9pytjMRXtNEMjGVQEyaI4UM/edit#gid=1056924006)

## Time schedule

| Time | Monday                | Tuesday       | Wednesday | Thursday            | Friday |
| ---- | --------------------- | ------------- | --------- | ------------------- | ------ |
| 1000 | DAT251                |               | Workday   |                     | DAT251 |
| 1200 | Sprint/Daily planning | Workday       | Workday   |
| 1400 |                       | Workday       |           |                     |
| 1600 |                       | Daily Meeting |           | Status Meeting      |
|      |                       |               |           | Daily meeting 16:30 |        |

## Project Details

## Tool / Languages

- **Frontend** : React with TypeScript
- **Backend:** Spring Boot, Maven, Java 15, REST API, Swagger
- **DB:** MariaDB and H2 Database for testing
- **Containers:** Docker
- **CI/CD:** GitHub with GitHub Actions
- **Hosting:** Google Cloud with Kubernetes

## Architecture diagram

## Metrics
<img width="721" height="446" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vTkeRE-bTprpYWGNDVnZWy4-Hp98FDCnACCFmUqvs0HEgxSgyPQgvqFy3QLuiTIbJ-igvkPUugNtlFF/pubchart?oid=193482369&amp;format=image"></img>

## Misc

### Creating issues list for Sprint log

Done using [GitHub Cli](https://cli.github.com/)

```sh
gh issue list -m 1| tr "\t" ";" > doc/issues.csv
```

Install it with the following on mac/linux

```sh
brew install gh
````

Windows:

```sh
choco install gh
```

[Other instuctions for GH](https://github.com/cli/cli#installation)
