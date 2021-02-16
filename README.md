# DAT251 Group 9 V2021

Group project for DAT251 Group 9 Spring 2021

| Members | Contact (github) |
| --- | --- |
| Andrè  | [@ImGoze](https://github.com/ImGoze) |
| Erlend | [@ErlendBerntsen](https://github.com/ErlendBerntsen) |
| Hans-Christian | [@H-C-H](https://github.com/H-C-H) |
| Kenneth | [@spydx](https://github.com/spydx) |
| Oscar | [@OscarSommervold](https://github.com/OscarSommervold) |
| Rune | [@runalmaas](https://github.com/runalmaas) |

## Time schedule

**Topic**: DAT251 Gr9 Meetings (Daily/Status/Sprint Planning)
**Time**: This is a recurring meeting Meet anytime
**Room**: [Meeting room](https://uib.zoom.us/j/68675494000?pwd=b2hzWG5Zd0Vac0dDdUtwZmRNN21uQT09)

| Time | Monday | Tuesday | Wednesday | Thursday | Friday |
|---|---|---|---|---|---|
| 1000 | DAT251| | Workday |  |  DAT251 |
| 1200 | Sprint/Daily planning | Workday |  Workday |  
| 1400|       | Workday | | |
| 1600 | | Daily Meeting | | Status Meeting
| |   |     | | Daily meeting 16:30| |

## Project Details

## Tool / Languages

- **Frontend** : React with TypeScript
- **Backend:** Spring Boot, Maven, Java 15, REST API, Swagger
- **DB:** MariaDB and H2 Database for testing
- **Containers:** Docker
- **CI/CD:** GitHub with GitHub Actions
- **Hosting:** Google Cloud with Kubernetes

## Architecture diagram

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