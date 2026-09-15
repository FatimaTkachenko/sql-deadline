# Домашнее задание к занятию «3.2. SQL»


> ⚠️ **ВАЖНО:** автотесты требуют запущенного окружения.
> Перед `gradlew test` поднимите:
> 1. **Docker Desktop** (Engine running).
> 2. MySQL-контейнер: `docker compose up -d`
> 3. SUT в отдельном окне: `java -jar artifacts/app/app-deadline.jar`
>
> Без окружения автотесты помечаются как **SKIPPED**, а сборка остаётся зелёной.
> С запущенным окружением: `LoginTest` PASSED, `BlockingTest` FAILED (баг SUT, см. [issue #1](https://github.com/FatimaTkachenko/sql-deadline/issues/1)).