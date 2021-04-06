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

![](Assignments/Architect%20diagram.png)

## Metrics
<img width="721" height="446" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vTkeRE-bTprpYWGNDVnZWy4-Hp98FDCnACCFmUqvs0HEgxSgyPQgvqFy3QLuiTIbJ-igvkPUugNtlFF/pubchart?oid=137943737&format=image"></img>
<img width="721" height="446" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vTkeRE-bTprpYWGNDVnZWy4-Hp98FDCnACCFmUqvs0HEgxSgyPQgvqFy3QLuiTIbJ-igvkPUugNtlFF/pubchart?oid=719019434&format=image"></img>
<img width="721" height="446" src="https://docs.google.com/spreadsheets/d/e/2PACX-1vTkeRE-bTprpYWGNDVnZWy4-Hp98FDCnACCFmUqvs0HEgxSgyPQgvqFy3QLuiTIbJ-igvkPUugNtlFF/pubchart?oid=193482369&format=image"></img>


## Misc

## Instructions for development on Frontend

1. Create an Access Token on your account
[Personal access token](https://github.com/settings/tokens)
Remember to add:
`read:packages` and all repo actions.
2. Configure Docker to use `docker.pkg.github.com`

```shell
> cat ~/TOKEN.txt | docker login https://docker.pkg.github.com -u GITUSERNAME --password-stdin
Login Succeeded
> rm ~/TOKEN.txt
```

To start development environment

```shell
> docker-compose pull && docker-compose up
```
[Documentation](https://docs.github.com/en/packages/guides/configuring-docker-for-use-with-github-packages)

## Instructions for development on Backend

No need to create a personal token.
But if you are to also do Frontend, then you have to do it.

Bring up the database:

```shell
> docker-compose up -d lop-db
```

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


### Connect to the database in IntelliJ
On the right hand side in IntelliJ you should se a module called "Database". 

From this press the "+"-sign -> Data Source -> MariaDB. 

***If you on the bottom of the page get a warning-sign saying: "Missing drivers", press on the "Install drivers" button.***

Then you just need to fill out the field with following: ```User: root```, and ```password: password```.

We then can test the connection, if its all good press apply and ok and you are good to go!

### Check the deployment with Heroku

If you access to the Heroku engine, you can use the following command on the commandline to get the status of the running application.

```shell
> heroku logs --tail -a lop-backend
```